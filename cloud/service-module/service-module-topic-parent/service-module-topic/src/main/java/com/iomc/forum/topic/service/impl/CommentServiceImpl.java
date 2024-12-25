package com.iomc.forum.topic.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iomc.common.core.Res;
import com.iomc.common.core.constants.ForumConstant;
import com.iomc.common.core.constants.OperationEnum;
import com.iomc.common.core.exception.BizException;
import com.iomc.common.mq.RocketMQProducerService;
import com.iomc.common.mq.constant.MqConst;
import com.iomc.common.security.utils.SecurityUtils;
import com.iomc.forum.message.api.dto.MessageDTO;
import com.iomc.forum.message.api.utils.MessageUtil;
import com.iomc.forum.topic.api.dto.CommentDTO;
import com.iomc.forum.topic.api.entity.Comment;
import com.iomc.forum.topic.api.entity.Topic;
import com.iomc.forum.topic.api.vo.CommentVO;
import com.iomc.forum.topic.dao.CommentDao;
import com.iomc.forum.topic.service.CommentService;
import com.iomc.forum.topic.service.LikeService;
import com.iomc.forum.topic.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {


    @Autowired
    private LikeService likeService;

    @Autowired
    private TopicService topicService;

    private final RocketMQProducerService rocketMQProducerService;

    @Autowired
    public CommentServiceImpl(LikeService likeService,
                              TopicService topicService,

                            RocketMQProducerService rocketMQProducerService) {
        this.topicService = topicService;
        this.likeService = likeService;
        this.rocketMQProducerService = rocketMQProducerService;
    }

    /**
     * 获取评论列表
     *
     * @param topicId
     * @param page
     * @return
     */
    @Override
    public IPage<CommentVO> getCommentsByTopicId(Long topicId, Page<CommentVO> page) {

        Long currentUserId = null;
        if (SecurityUtils.isLogin()) {
            currentUserId = SecurityUtils.getCurrentUserId();
        }

        // 获取一级评论
        IPage<CommentVO> commentPage = baseMapper.selectCommentsByTopicId(page, topicId, currentUserId);
        // 获取子评论
        Long finalCurrentUserId = currentUserId;
        commentPage.getRecords().forEach(comment -> {
            List<CommentVO> children = baseMapper.selectChildComments(comment.getId(), finalCurrentUserId);
            comment.setChildren(children);
        });
        return commentPage;
    }

    /**
     * 置顶与取消置顶评论
     *
     * @param commentId
     * @param isTop
     */
    @Override
    public void topComment(Long commentId, boolean isTop) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Comment comment = verifyComment(commentId, currentUserId, OperationEnum.UPDATE.getValue());

        comment.setIsTop(isTop ? 1 : 0);
        baseMapper.updateById(comment);
    }

    /**
     * 新增评论
     *
     * @param commentDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addComment(CommentDTO commentDTO) {

        Long currentUserId = SecurityUtils.getCurrentUserId();

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setUserId(currentUserId);
        topicService.commentCount(comment.getTopicId(), true);
        baseMapper.insert(comment);

        Topic one = topicService.getOne(
                new LambdaQueryWrapper<Topic>()
                        .eq(Topic::getId, comment.getTopicId()));

        // 发送主题回复消息
        MessageDTO message =
                MessageUtil.replyToTopicMessage(currentUserId, one.getUserId(),
                        comment.getTopicId(), comment.getId(), comment.getContent());
        rocketMQProducerService.send(MqConst.SERVICE_FORUM_MESSAGE_TOPIC, MqConst.TAG_COMMENT_MESSAGE, JSONObject.toJSONString(message));

    }

    /**
     * 回复评论
     *
     * @param commentId
     * @param commentDTO
     */
    @Override
    public void replyComment(Long commentId, CommentDTO commentDTO) {

        Long currentUserId = SecurityUtils.getCurrentUserId();
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setUserId(currentUserId);
        comment.setParentId(commentId);
        topicService.commentCount(comment.getTopicId(), true);
        baseMapper.insert(comment);

        // 发送评论回复消息
        MessageDTO message =
                MessageUtil.replyToCommentMessage(currentUserId, comment.getReplyTo(),
                        comment.getTopicId(), comment.getId(), comment.getContent());
        rocketMQProducerService.send(MqConst.SERVICE_FORUM_MESSAGE_TOPIC, MqConst.TAG_COMMENT_MESSAGE, JSONObject.toJSONString(message));

    }

    /**
     * 删除评论
     *
     * @param commentId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long commentId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Comment comment = verifyComment(commentId, currentUserId, OperationEnum.DELETE.getValue());

        if (!currentUserId.equals(comment.getUserId()) && !SecurityUtils.isAdmin()) {
            throw new BizException("无权删除该评论");
        }

        baseMapper.deleteById(commentId);
        topicService.commentCount(comment.getTopicId(), false);
    }


    /**
     * 点赞与取消点赞
     *
     * @param commentId
     * @param isLike
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void likeComment(Long commentId, boolean isLike) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Comment comment = verifyComment(commentId, currentUserId, OperationEnum.LIKE.getValue());

        if (isLike) {
            likeService.like(currentUserId, commentId, ForumConstant.LIKE_TYPE_COMMENT);
            comment.setLikeCount(comment.getLikeCount() + 1);
            baseMapper.updateById(comment);

            MessageDTO message =
                    MessageUtil.likeCommentMessage(currentUserId, comment.getUserId(), comment.getTopicId(), comment.getId());
            rocketMQProducerService.send(MqConst.SERVICE_FORUM_MESSAGE_TOPIC, MqConst.TAG_LIKE_MESSAGE, JSONObject.toJSONString(message));

            return;
        }

        likeService.unlike(currentUserId, commentId, ForumConstant.LIKE_TYPE_COMMENT);
        comment.setLikeCount(comment.getLikeCount() - 1);
        baseMapper.updateById(comment);
    }

    /**
     * 查询我的回复
     *
     * @param page
     * @param size
     * @param userId
     * @param keyword
     */
    @Override
    public Res getMyComments(Integer page, Integer size, Long userId, String keyword) {
        IPage<CommentVO> commentVOIPage =
                baseMapper.selectCommentListByUserId(
                        Page.of(page, size), userId, keyword);

        return Res.success(commentVOIPage);
    }

    /**
     * 验证评论
     *
     * @param commentId
     * @param currentUserId
     * @param operationType 1增 2删 3改 4点赞
     * @throws BizException
     * @return
     */
    private Comment verifyComment(Long commentId,
                                  Long currentUserId,
                                  String operationType) {
        Comment comment = baseMapper.selectById(commentId);
        if (comment == null) {
            throw new BizException("评论不存在");
        }

        if (Objects.equals(operationType, OperationEnum.LIKE.getValue())) {
            if (currentUserId.equals(comment.getUserId())) {
                throw new BizException("不能给自己的评论点赞");
            }
        }

        return comment;
    }
}