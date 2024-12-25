package com.iomc.forum.topic.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iomc.common.core.Res;
import com.iomc.forum.topic.api.dto.TopicDTO;
import com.iomc.forum.topic.api.entity.Topic;
import com.iomc.forum.topic.api.vo.TopicVO;


/**
 * 话题服务接口
 *
 * @author forum
 * @since 2024-03-15
 */
public interface TopicService extends IService<Topic> {

    /**
     * 创建话题
     *
     * @param topicDTO 话题创建参数
     */
    Res createTopic(TopicDTO topicDTO);

    /**
     * 删除话题
     *
     * @param id 话题ID
     */
    Res deleteTopic(Long id);

    /**
     * 更新话题
     *
     * @param topicDTO 话题更新参数
     */
    Res updateTopic(TopicDTO topicDTO);

    /**
     * 获取话题列表
     *
     * @param page 分页参数
     * @return 话题列表（分页）
     */
    Res getTopicList(Page<TopicVO> page, Long categoryId, String type, String sort);

    /**
     * 获取作者相关话题
     *
     * @param page
     * @param userId 作者ID
     * @param keyword 关键词
     *
     */
    Res getAuthorTopics(Page<TopicVO> page, Long userId, String keyword);

    /**
     * 获取话题详情
     *
     * @param id 话题ID
     * @return 话题详情
     */
    Res getTopicDetail(Long id);

    /**
     * 点赞话题与取消
     *
     * @param id
     * @param isLike
     */
    Res likeTopic(Long id, boolean isLike);

    /**
     * 收藏与取消
     *
     * @param id
     * @param isCollect
     */
    Res collectTopic(Long id, boolean isCollect);

    /**
     * 调整话题评论数量
     *
     * @param id
     * @param isAdd
     * */
    Res commentCount(Long id, boolean isAdd);

    /**
     * 关注与取消
     */
    Res follow(Long id, boolean isFollow);

    /**
     * 获取用户的主题列表
     *
     * @param page
     * @param size
     * @param userId
     * @param keyword
     * @return
     */
    Res getMyTopics(Integer page, Integer size, Long userId, String keyword);

    /**
     * 获取用户的收藏列表
     *
     * @param page
     * @param size
     * @param userId
     * @param keyword
     * @return
     */
    Res getMyCollects(Integer page, Integer size, Long userId, String keyword);
}