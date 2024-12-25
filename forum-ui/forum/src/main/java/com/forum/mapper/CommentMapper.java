package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forum.entity.Comment;
import com.forum.vo.CommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {

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



} 