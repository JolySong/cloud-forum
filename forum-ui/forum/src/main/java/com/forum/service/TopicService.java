package com.forum.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.forum.dto.TopicDTO;
import com.forum.entity.Topic;
import com.forum.vo.TopicDetailVO;
import com.forum.vo.TopicVO;

/**
 * 话题服务接口
 *
 * @author forum
 * @since 2024-03-15
 */
public interface TopicService extends IService<Topic> {
    /**
     * 获取话题列表
     *
     * @param page 分页参数
     * @return 话题列表（分页）
     */
    IPage<TopicVO> getTopicList(Page<TopicVO> page, Long categoryId, String type, String sort);

    /**
     * 获取话题详情
     *
     * @param id 话题ID
     * @return 话题详情
     */
    TopicDetailVO getTopicDetail(Long id);

    /**
     * 创建话题
     *
     * @param topicDTO 话题创建参数
     */
    void createTopic(TopicDTO topicDTO);

    /**
     * 更新话题
     *
     * @param id 话题ID
     * @param topicDTO 话题更新参数
     */
    void updateTopic(Long id, TopicDTO topicDTO);

    /**
     * 删除话题
     *
     * @param id 话题ID
     */
    void deleteTopic(Long id);

    /**
     * 点赞话题
     */
    void likeTopic(Long id);

    /**
     * 取消点赞
     */
    void unlikeTopic(Long id);

    /**
     * 收藏话题
     */
    void collectTopic(Long id);

    /**
     * 取消收藏
     */
    void uncollectTopic(Long id);

    /** 增加话题评论数量 */
    void incrCommentCount(Long id);

    /** 减少话题评论数量 */
    void decrCommentCount(Long id);
} 