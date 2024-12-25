package com.iomc.forum.topic.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iomc.common.core.Res;
import com.iomc.common.core.constants.ForumConstant;
import com.iomc.common.core.exception.BizException;
import com.iomc.common.mq.RocketMQProducerService;
import com.iomc.common.mq.constant.MqConst;
import com.iomc.common.security.utils.SecurityUtils;
import com.iomc.forum.message.api.dto.MessageDTO;
import com.iomc.forum.message.api.utils.MessageUtil;
import com.iomc.forum.topic.api.dto.TopicDTO;
import com.iomc.forum.topic.api.entity.Category;
import com.iomc.forum.topic.api.entity.Topic;
import com.iomc.forum.topic.api.entity.TopicView;
import com.iomc.forum.topic.api.mapstruct.CategoryMapper;
import com.iomc.forum.topic.api.mapstruct.TopicMapper;
import com.iomc.forum.topic.api.vo.TagVO;
import com.iomc.forum.topic.api.vo.TopicVO;
import com.iomc.forum.topic.dao.TopicDao;
import com.iomc.forum.topic.service.*;
import com.iomc.forum.user.api.feign.UserServiceFeign;
import com.iomc.forum.user.api.vo.UserProfileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class TopicServiceImpl extends ServiceImpl<TopicDao, Topic> implements TopicService {

    private final UserServiceFeign userServiceFeign;
    private final CategoryService categoryService;
    private final TopicTagService topicTagService;
    private final TopicViewService topicViewService;
    private final LikeService likeService;
    private final FollowService followService;
    private final CollectService collectService;
    private final RocketMQProducerService rocketMQProducerService;

    @Autowired
    public TopicServiceImpl(UserServiceFeign userServiceFeign,
                            CategoryService categoryService,
                            TopicTagService topicTagService,
                            TopicViewService topicViewService,
                            LikeService likeService,
                            FollowService followService,
                            CollectService collectService,
                            RocketMQProducerService rocketMQProducerService) {
        this.userServiceFeign = userServiceFeign;
        this.categoryService = categoryService;
        this.topicTagService = topicTagService;
        this.topicViewService = topicViewService;
        this.likeService = likeService;
        this.followService = followService;
        this.collectService = collectService;
        this.rocketMQProducerService = rocketMQProducerService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Res createTopic(TopicDTO topicDTO) {

        Topic topic =
                baseMapper.selectOne(
                        new LambdaQueryWrapper<Topic>()
                                .eq(Topic::getTitle, topicDTO.getTitle()));

        if (topic != null) {
            throw new BizException("话题标题已存在");
        }

        topic = TopicMapper.INSTANCE.toEntity(topicDTO);
        topic.setUserId(SecurityUtils.getCurrentUserId());
        save(topic);

        // 发送话题待审核消息
        MessageDTO message = MessageUtil.topicAuditMessage(topic.getId(), SecurityUtils.getCurrentUserId());
        rocketMQProducerService.send(MqConst.SERVICE_FORUM_MESSAGE_TOPIC, MqConst.TAG_TOPIC_MESSAGE, JSONObject.toJSONString(message));
        
        // 保存标签
        if (topicDTO.getTags() != null && !topicDTO.getTags().isEmpty()) {
            topicTagService.saveTags(topic.getId(), topicDTO.getTags());
        }

        return Res.success(topic.getId());
    }

    /**
     * 删除话题
     * @param id 话题ID
     */
    @Override
    public Res deleteTopic(Long id) {
        Topic topic = getById(id);
        if (topic == null) {
            throw new BizException("话题不存在");
        }
        
        if (!topic.getUserId().equals(SecurityUtils.getCurrentUserId())) {
            throw new BizException("无权删除他人话题");
        }
        
        return Res.success(removeById(id));
    }

    /**
     * 更新话题
     *
     * @param topicDTO 话题更新参数
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Res updateTopic(TopicDTO topicDTO) {

        Topic topic =
                baseMapper.selectOne(
                        new LambdaQueryWrapper<Topic>()
                                .eq(Topic::getTitle, topicDTO.getTitle()));
        if (topic != null && !topic.getId().equals(topicDTO.getId())) {
            return Res.fail("话题标题已存在");
        }

        topic = getById(topicDTO.getId());
        if (!topic.getUserId().equals(SecurityUtils.getCurrentUserId()) && !SecurityUtils.isAdmin()) {
            return Res.fail("无权修改他人话题");
        }

        if (topicDTO.getCategoryId() != null) {
            Category category = categoryService.getById(topicDTO.getCategoryId());
            if (category == null) {
                return Res.fail("分类不存在");
            }
        }

        topicTagService.deleteTags(topic.getId());
        topicTagService.saveTags(topic.getId(), topicDTO.getTags());

        topic = TopicMapper.INSTANCE.toEntity(topicDTO);
        topic.setAudit(ForumConstant.AUDIT_STATUS_AUDITING);
        updateById(topic);
        // 发送话题待审核消息
        MessageDTO message = MessageUtil.topicAuditMessage(topic.getId(), SecurityUtils.getCurrentUserId());
        rocketMQProducerService.send(MqConst.SERVICE_FORUM_MESSAGE_TOPIC, MqConst.TAG_TOPIC_MESSAGE, JSONObject.toJSONString(message));
        return Res.success();
    }


    /**
     * 获取话题列表
     *
     * @param page       分页参数
     * @param categoryId
     * @param type
     * @param sort
     * @return 话题列表（分页）
     */
    @Override
    public Res getTopicList(Page<TopicVO> page, Long categoryId, String type, String sort) {
        return Res.success(baseMapper.selectTopicList(page, categoryId, type, sort, null, null));
    }

    /**
     * 获取作者相关话题
     *
     * @param page
     * @param userId  作者ID
     * @param keyword 关键词
     */
    @Override
    public Res getAuthorTopics(Page<TopicVO> page, Long userId, String keyword) {

        IPage<TopicVO> topicVOIPage =
                baseMapper.selectTopicList(page, null, null, null, keyword, userId);

        return Res.success(topicVOIPage.getRecords());
    }


    /**
     * 获取话题详情
     *
     * @param id 话题ID
     * @return
     */
    @Override
    public Res getTopicDetail(Long id) {
        Topic topic = getById(id);
        if (topic == null) {
            throw new BizException("话题不存在");
        }

        // 增加浏览量
        baseMapper.incrViewCount(id);
        // 记录用户浏览的话题
        if (SecurityUtils.isLogin()) {
            topicViewService.save(new TopicView(id, SecurityUtils.getCurrentUserId()));
        }

        return Res.success(convertToDetailVO(topic));
    }


    /**
     * 点赞与取消话题
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Res likeTopic(Long id, boolean isLike) {
        Topic topic = getById(id);
        if (topic == null) {
            throw new BizException("话题不存在");
        }
        
        Long userId = SecurityUtils.getCurrentUserId();
        if (isLike) {
            likeService.like(userId, id, ForumConstant.LIKE_TYPE_TOPIC);
            // 发送话题点赞消息
            MessageDTO message = MessageUtil.likeTopicMessage(userId, topic.getUserId(), id);
            rocketMQProducerService.send(MqConst.SERVICE_FORUM_MESSAGE_TOPIC, MqConst.TAG_LIKE_MESSAGE, JSONObject.toJSONString(message));
            return Res.success(baseMapper.incrLikeCount(id));
        }

        // 更新点赞数
        likeService.unlike(userId, id, ForumConstant.LIKE_TYPE_TOPIC);
        return Res.success(baseMapper.decrLikeCount(id));
    }


    /**
     * 收藏与取消话题
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Res collectTopic(Long id, boolean isCollect) {
        Topic topic = getById(id);
        if (topic == null) {
            throw new BizException("话题不存在");
        }

        Long userId = SecurityUtils.getCurrentUserId();
        if (isCollect) {
            collectService.collect(userId, id);
            return Res.success(baseMapper.incrCollectCount(id));
        }

        collectService.uncollect(userId, id);
        return Res.success(baseMapper.decrCollectCount(id));
    }

    /**
     * 关注与取消
     *
     * @param id
     * @param isFollow
     */
    @Override
    public Res follow(Long id, boolean isFollow) {
        Topic topic = getById(id);
        if (topic == null) {
            return Res.fail("话题不存在");
        }

        Long userId = SecurityUtils.getCurrentUserId();
        if (isFollow) {
            followService.follow(userId, id);
            MessageDTO message = MessageUtil.followMessage(userId, topic.getUserId());
            rocketMQProducerService.send(MqConst.SERVICE_FORUM_MESSAGE_TOPIC, MqConst.TAG_FOLLOW_MESSAGE, JSONObject.toJSONString(message));
            return Res.success();
        }

        followService.unfollow(userId, id);
        return Res.success();
    }

    /**
     * 获取用户的主题列表
     *
     * @param page
     * @param size
     * @param userId
     * @param keyword
     * @return
     */
    @Override
    public Res getMyTopics(Integer page, Integer size, Long userId, String keyword) {

        return Res.success(
                baseMapper.selectTopicListByUserId(
                        Page.of(page, size),  userId, keyword));
    }

    /**
     * 获取用户的收藏列表
     *
     * @param page
     * @param size
     * @param userId
     * @param keyword
     * @return
     */
    @Override
    public Res getMyCollects(Integer page, Integer size, Long userId, String keyword) {
        return Res.success(
                baseMapper.selectCollectListByUserId(
                        Page.of(page, size),  userId, keyword));
    }


    /**
     * 话题评论数量
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Res commentCount(Long id, boolean isAdd) {
        Topic topic = getById(id);
        if (topic == null) {
            throw new BizException("话题不存在");
        }

        if (isAdd) {
            return Res.success(baseMapper.incrCommentCount(id));
        }

        return Res.success(baseMapper.decrCommentCount(id));
    }



    /**
     * 将Topic实体转换为TopicVO
     *
     * @param topic 话题实体
     * @return 话题详情VO
     */
    private TopicVO convertToDetailVO(Topic topic) {
        if (topic == null) {
            return null;
        }

        TopicVO vo = TopicMapper.INSTANCE.toVO(topic);
        // 获取分类信息
        Category category = categoryService.getById(topic.getCategoryId());
        if (category != null) {
            vo.setCategoryName(category.getName());
            vo.setCategory(CategoryMapper.INSTANCE.toVO(category));
        }
        // 获取标签列表
        List<TagVO> tags = topicTagService.getTagsByTopicId(topic.getId());
        vo.setTags(tags);
        // 查询作者信息
        Res<UserProfileVO> res = userServiceFeign.getUserProfile(topic.getUserId());
        UserProfileVO userProfile = res.getData();
        if (null == userProfile || null == userProfile.getId()) {
            log.info("用户服务->getUserProfile()->调用未查询到信息:{} 参数: {}", res.getMessage(), topic.getUserId());
        }
        vo.setAuthor(userProfile);

        // 获取当前用户的交互状态
        if(SecurityUtils.isLogin()) {
            Long currentUserId = SecurityUtils.getCurrentUserId();
            // 判断当前用户是否点赞、收藏
            vo.setIsLiked(likeService.isLiked(currentUserId, topic.getId(), ForumConstant.LIKE_TYPE_TOPIC));
            vo.setIsCollected(collectService.isCollected(currentUserId, topic.getId()));
        }

        return vo;
    }
} 