package com.iomc.forum.topic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iomc.forum.topic.api.entity.Comment;
import com.iomc.forum.topic.api.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDao extends BaseMapper<Comment> {

    /**
     * 根据话题id获取评论列表
     *
     * @param page
     * @param topicId
     * @param userId
     * @return
     */
    IPage<CommentVO> selectCommentsByTopicId(Page<CommentVO> page,
                                             @Param("topicId") Long topicId,
                                             @Param("userId") Long userId);

    /**
     * 根据父评论id获取子评论列表
     *
     * @param parentId
     * @param userId
     * @return
     */
    List<CommentVO> selectChildComments(@Param("parentId") Long parentId,
                                        @Param("userId") Long userId);


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

} 