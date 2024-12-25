package com.forum.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forum.common.constant.ForumConstant;
import com.forum.common.constant.OperationEnum;
import com.forum.common.exception.BusinessException;
import com.forum.dto.CommentDTO;
import com.forum.entity.Comment;
import com.forum.mapper.CommentMapper;
import com.forum.service.CommentService;
import com.forum.common.utils.SecurityUtils;
import com.forum.service.LikeService;
import com.forum.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Autowired
    private final CommentMapper commentMapper;

    @Autowired
    private final LikeService likeService;

    @Autowired
    private TopicServiceImpl topicService;

    /**
     * 获取评论列表
     *
     * @param topicId
     * @param page
     * @return
     */
    @Override
    public IPage<CommentVO> getCommentsByTopicId(Long topicId, Page<CommentVO> page) {

        Long currentUserId = SecurityUtils.getCurrentUserId();

        // 获取一级评论
        IPage<CommentVO> commentPage = commentMapper.selectCommentsByTopicId(page, topicId, currentUserId);
        // 获取子评论
        commentPage.getRecords().forEach(comment -> {
            List<CommentVO> children = commentMapper.selectChildComments(comment.getId(), currentUserId);
            comment.setChildren(children);
        });
        return commentPage;
    }

    /**
     * 新增评论
     *
     * @param commentDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setUserId(SecurityUtils.getCurrentUserId());
        topicService.incrCommentCount(comment.getTopicId());
        commentMapper.insert(comment);
    }

    /**
     * 回复评论
     *
     * @param commentId
     * @param commentDTO
     */
    @Override
    public void replyComment(Long commentId, CommentDTO commentDTO) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setUserId(SecurityUtils.getCurrentUserId());
        comment.setParentId(commentId);
        topicService.incrCommentCount(comment.getTopicId());
        commentMapper.insert(comment);
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
            throw new BusinessException("无权删除该评论");
        }
        
        commentMapper.deleteById(commentId);
        topicService.decrCommentCount(comment.getTopicId());
    }


    /**
     * 点赞
     * @param commentId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void likeComment(Long commentId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Comment comment = verifyComment(commentId, currentUserId, OperationEnum.LIKE.getValue());

        likeService.like(currentUserId, commentId, ForumConstant.LIKE_TYPE_COMMENT);

        comment.setLikeCount(comment.getLikeCount() + 1);
        commentMapper.updateById(comment);
    }

    /**
     * 取消点赞
     * @param commentId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlikeComment(Long commentId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Comment comment = verifyComment(commentId, currentUserId, OperationEnum.LIKE.getValue());

        likeService.unlike(currentUserId, commentId, ForumConstant.LIKE_TYPE_COMMENT);
        comment.setLikeCount(comment.getLikeCount() - 1);
        commentMapper.updateById(comment);
    }


    /**
     * 验证评论
     *
     * @param commentId
     * @param currentUserId
     * @param operationType 1增 2删 3改 4点赞
     * @throws BusinessException
     * @return
     */
    private Comment verifyComment(Long commentId,
                                  Long currentUserId,
                                  String operationType) {
        Comment comment = commentMapper.selectById(commentId);
        if (comment == null) {
            throw new BusinessException("评论不存在");
        }

        if (Objects.equals(operationType, OperationEnum.LIKE.getValue())) {
            if (currentUserId.equals(comment.getUserId())) {
                throw new BusinessException("不能给自己的评论点赞");
            }
        }

        return comment;
    }
}