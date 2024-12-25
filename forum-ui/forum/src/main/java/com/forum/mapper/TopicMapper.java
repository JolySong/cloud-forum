package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forum.entity.Topic;
import com.forum.vo.TopicVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TopicMapper extends BaseMapper<Topic> {
    @Select("SELECT t.*, u.nickname as authorName, u.avatar as authorAvatar " +
            "FROM topics t " +
            "LEFT JOIN users u ON t.user_id = u.id " +
            "WHERE t.category_id = #{categoryId} " +
            "AND t.id != #{topicId} " +
            "AND t.status = 1 " +
            "ORDER BY t.created_at DESC " +
            "LIMIT #{limit}")
    List<TopicVO> selectRelatedTopics(@Param("topicId") Long topicId,
                                     @Param("categoryId") Long categoryId,
                                     @Param("limit") Integer limit);

    /**
     * 查询话题标签
     */
    List<String> selectTopicTags(@Param("topicId") Long topicId);

    /**
     * 分页查询话题列表
     *
     * @param page 分页参数
     * @param categoryId 分类ID（可选）
     * @param type 类型（可选）0全部 1热门 2最新
     * @param sort 排序方式（可选）0最新发布 1最多浏览 2最多评论
     * @return 话题列表
     */
    IPage<TopicVO> selectTopicList(Page<TopicVO> page,
                                  @Param("categoryId") Long categoryId,
                                  @Param("type") String type, @Param("sort") String sort);

    /**
     * 查询话题详情
     *
     * @param id
     * @return
     */
    TopicVO selectTopicDetail(@Param("id") Long id);

    /**
     * 更新话题浏览数
     * @param id
     * @return
     */
    @Update("UPDATE topics SET view_count = view_count + 1 WHERE id = #{id}")
    int incrViewCount(@Param("id") Long id);

    /**
     * 增加话题点赞数
     * @param id
     * @return
     */
    @Update("UPDATE topics SET like_count = like_count + 1 WHERE id = #{id}")
    int incrLikeCount(@Param("id") Long id);

    /**
     * 减少话题点赞数
     * @param id
     * @return
     */
    @Update("UPDATE topics SET like_count = like_count - 1 WHERE id = #{id}")
    int decrLikeCount(@Param("id") Long id);

    /**
     * 增加话题收藏数
     * @param id
     * @return
     */
    @Update("UPDATE topics SET collect_count = collect_count + 1 WHERE id = #{id}")
    int incrCollectCount(@Param("id") Long id);

    /**
     * 减少话题收藏数
     * @param id
     * @return
     */
    @Update("UPDATE topics SET collect_count = collect_count - 1 WHERE id = #{id}")
    int decrCollectCount(@Param("id") Long id);

    /**
     * 增加话题评论数
     * @param id
     */
    @Update("UPDATE topics SET comment_count = comment_count + 1 WHERE id = #{id}")
    int incrCommentCount(@Param("id") Long id);

    /**
     * 减少话题评论数
     * @param id
     */
    @Update("UPDATE topics SET comment_count = comment_count - 1 WHERE id = #{id}")
    int decrCommentCount(@Param("id") Long id);
} 