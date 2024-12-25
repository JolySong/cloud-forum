package com.iomc.forum.topic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iomc.common.core.Res;
import com.iomc.common.core.exception.BizException;
import com.iomc.forum.topic.api.dto.CommentDTO;
import com.iomc.forum.topic.api.entity.Comment;
import com.iomc.forum.topic.api.vo.CommentVO;


public interface CommentService extends IService<Comment> {

    /**
     * 根据主题id获取评论列表
     * @param topicId
     * @param page
     * @return
     */
    IPage<CommentVO> getCommentsByTopicId(Long topicId, Page<CommentVO> page);

    /**
     * 置顶与取消置顶评论
     *
     * @param commentId
     * @param isTop
     */
    void topComment(Long commentId, boolean isTop);

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
     * 点赞与取消点赞
     *
     * @param commentId
     * @param isLike
     */
    void likeComment(Long commentId, boolean isLike);


    /**
     * 查询我的回复
     *
     * @param page
     * @param size
     * @param userId
     * @param keyword
     */
    Res getMyComments(Integer page, Integer size, Long userId, String keyword);
} 