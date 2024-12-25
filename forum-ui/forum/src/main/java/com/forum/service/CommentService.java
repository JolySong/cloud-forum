package com.forum.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forum.dto.CommentDTO;
import com.forum.vo.CommentVO;

public interface CommentService {

    /**
     * 根据主题id获取评论列表
     * @param topicId
     * @param page
     * @return
     */
    IPage<CommentVO> getCommentsByTopicId(Long topicId, Page<CommentVO> page);

    /**
     * 创建评论
     * @param commentDTO
     */
    void addComment(CommentDTO commentDTO);

    /**
     * 回复评论
     * @param commentId
     * @param commentDTO
     */
    void replyComment(Long commentId, CommentDTO commentDTO);

    /**
     * 删除评论
     * @param commentId
     */
    void deleteComment(Long commentId);

    /**
     * 点赞评论
     * @param commentId
     */
    void likeComment(Long commentId);

    /**
     * 取消点赞评论
     * @param commentId
     */
    void unlikeComment(Long commentId);
} 