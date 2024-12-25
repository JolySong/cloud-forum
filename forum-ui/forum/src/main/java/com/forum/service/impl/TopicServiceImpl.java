package com.forum.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.common.constant.ForumConstant;
import com.forum.common.exception.BusinessException;
import com.forum.common.utils.SecurityUtils;
import com.forum.dto.TopicDTO;
import com.forum.entity.Category;
import com.forum.entity.Topic;
import com.forum.entity.TopicView;
import com.forum.entity.User;
import com.forum.mapper.TopicMapper;
import com.forum.mapper.TopicViewMapper;
import com.forum.service.*;
import com.forum.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {
    
    private final UserService userService;
    private final CategoryService categoryService;
    private final TopicTagService topicTagService;
    private final LikeService likeService;
    private final CollectService collectService;
    private final FollowService followService;

    private final TopicViewMapper topicViewMapper;
    
    @Override
    public IPage<TopicVO> getTopicList(Page<TopicVO> page, Long categoryId, String type, String sort) {
        return baseMapper.selectTopicList(page, categoryId, type, sort);
    }
    
    @Override
    public TopicDetailVO getTopicDetail(Long id) {
        Topic topic = getById(id);
        if (topic == null) {
            throw new BusinessException("话题不存在");
        }
        
        // 增加浏览量
        baseMapper.incrViewCount(id);

        // 记录用户浏览的话题
        Long currentUserId = SecurityUtils.getCurrentUserId();
        if (currentUserId != null) {
            TopicView topicView = new TopicView();
            topicView.setTopicId(id);
            topicView.setUserId(currentUserId);
            topicViewMapper.insert(topicView);
        }

        return convertToDetailVO(topic);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createTopic(TopicDTO topicDTO) {
        Topic topic = new Topic();
        topic.setTitle(topicDTO.getTitle());
        topic.setContent(topicDTO.getContent());
        topic.setCategoryId(topicDTO.getCategoryId());
        topic.setUserId(SecurityUtils.getCurrentUserId());
        topic.setStatus(1);
        
        save(topic);
        
        // 保存标签
        if (topicDTO.getTags() != null && !topicDTO.getTags().isEmpty()) {
            topicTagService.saveTags(topic.getId(), topicDTO.getTags());
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTopic(Long id, TopicDTO topicDTO) {
        Topic topic = getById(id);
        if (topic == null) {
            throw new BusinessException("话题不存在");
        }
        
        if (!topic.getUserId().equals(SecurityUtils.getCurrentUserId())) {
            throw new BusinessException("无权修改他人话题");
        }
        
        BeanUtils.copyProperties(topicDTO, topic);
        updateById(topic);
    }
    
    @Override
    public void deleteTopic(Long id) {
        Topic topic = getById(id);
        if (topic == null) {
            throw new BusinessException("话题不存在");
        }
        
        if (!topic.getUserId().equals(SecurityUtils.getCurrentUserId())) {
            throw new BusinessException("无权删除他人话题");
        }
        
        removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void likeTopic(Long id) {
        Topic topic = getById(id);
        if (topic == null) {
            throw new BusinessException("话题不存在");
        }
        
        Long userId = SecurityUtils.getCurrentUserId();
        likeService.like(userId, id, ForumConstant.LIKE_TYPE_TOPIC);
        
        // 更新点赞数
        baseMapper.incrLikeCount(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlikeTopic(Long id) {
        Topic topic = getById(id);
        if (topic == null) {
            throw new BusinessException("话题不存在");
        }
        
        Long userId = SecurityUtils.getCurrentUserId();
        likeService.unlike(userId, id, ForumConstant.LIKE_TYPE_TOPIC);
        
        // 更新点赞数
        baseMapper.decrLikeCount(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void collectTopic(Long id) {
        Topic topic = getById(id);
        if (topic == null) {
            throw new BusinessException("话题不存在");
        }

        Long userId = SecurityUtils.getCurrentUserId();
        collectService.collect(userId, id);

        baseMapper.incrCollectCount(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uncollectTopic(Long id) {
        Topic topic = getById(id);
        if (topic == null) {
            throw new BusinessException("话题不存在");
        }

        Long userId = SecurityUtils.getCurrentUserId();
        collectService.uncollect(userId, id);

        baseMapper.decrCollectCount(id);
    }

    /**
     * 增加话题评论数量
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrCommentCount(Long id) {
        Topic topic = getById(id);
        if (topic == null) {
            throw new BusinessException("话题不存在");
        }
        baseMapper.incrCommentCount(id);
    }

    /**
     * 减少话题评论数量
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decrCommentCount(Long id) {
        Topic topic = getById(id);
        if (topic == null) {
            throw new BusinessException("话题不存在");
        }
        baseMapper.decrCommentCount(id);
    }

    /**
     * 将Topic实体转换为TopicDetailVO
     *
     * @param topic 话题实体
     * @return 话题详情VO
     */
    private TopicDetailVO convertToDetailVO(Topic topic) {
        if (topic == null) {
            return null;
        }

        TopicDetailVO vo = new TopicDetailVO();
        // 复制基本属性
        BeanUtils.copyProperties(topic, vo);

        // 获取作者信息
        User author = userService.getById(topic.getUserId());
        if (author != null) {
            vo.setAuthorId(author.getId());
            vo.setAuthorName(author.getNickname());
            vo.setAuthorAvatar(author.getAvatar());
            vo.setAuthorBio(author.getBio());

            // 获取话题作者话题数量、获赞数量、粉丝数量
            UserStatVO userStats = userService.getUserStats(vo.getAuthorId());
            vo.setAuthor(userStats);
        }

        // 获取分类信息
        Category category = categoryService.getById(topic.getCategoryId());
        if (category != null) {
            vo.setCategoryName(category.getName());
        }

        // 获取标签列表
        List<TagVO> tags = topicTagService.getTagsByTopicId(topic.getId());
        vo.setTags(tags);

        // 获取当前用户的交互状态
        Long currentUserId = SecurityUtils.getCurrentUserId();
        if (currentUserId != null) {
            // 判断当前用户是否点赞、收藏、关注
            vo.setIsLiked(likeService.isLiked(currentUserId, topic.getId(), ForumConstant.LIKE_TYPE_TOPIC));
            vo.setIsCollected(collectService.isCollected(currentUserId, topic.getId()));
            vo.setIsFollowedAuthor(followService.isFollowed(currentUserId, topic.getUserId()));
        }

        // 获取相关话题推荐
        List<TopicVO> relatedTopics = baseMapper.selectRelatedTopics(
                topic.getId(),
                topic.getCategoryId(),
                5  // 推荐数量
        );
        vo.setRelatedTopics(relatedTopics);

        return vo;
    }
} 