package com.forum.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forum.vo.CommentVO;
import com.forum.vo.TopicVO;
import com.forum.vo.UserLikeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserProfileMapper {

    /**
     * 分页查询我的话题列表
     *
     * @param page 分页参数
     * @param userId 用户ID
     * @param keyword 关键词
     * @return 话题列表
     */
    IPage<TopicVO> selectTopicListByUserId(Page<TopicVO> page,
                                           @Param("userId") Long userId,
                                           @Param("keyword") String keyword);

    /**
     * 分页查询我的收藏话题列表
     *
     * @param page 分页参数
     * @param userId 用户ID
     * @param keyword 关键词
     *
     * @return 话题列表
     */
    IPage<TopicVO> selectCollectListByUserId(Page<TopicVO> page,
                                             @Param("userId") Long userId,
                                             @Param("keyword") String keyword);

    /**
     * 分页查询我的评论列表
     *
     * @param page 分页参数
     * @param userId 用户ID
     * @param keyword 关键词
     *
     * @return 评论列表
     */
    IPage<CommentVO> selectCommentListByUserId(Page<CommentVO> page,
                                               @Param("userId") Long userId,
                                               @Param("keyword") String keyword);

    /**
     * 分页查询我的点赞列表
     *
     * @param page 分页参数
     * @param userId 用户ID
     * @param keyword 关键词
     *
     * @return 点赞列表
     */
    IPage<UserLikeVO> selectLikeListByUserId(Page<UserLikeVO> page,
                                             @Param("userId") Long userId,
                                             @Param("keyword") String keyword);
}
