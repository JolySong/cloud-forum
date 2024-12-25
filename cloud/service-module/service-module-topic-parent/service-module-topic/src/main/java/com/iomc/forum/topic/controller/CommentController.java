package com.iomc.forum.topic.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iomc.common.core.Res;
import com.iomc.forum.topic.api.dto.CommentDTO;
import com.iomc.forum.topic.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;


    /**
     * 获取话题评论列表
     *
     * @param topicId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/{topicId}")
    public Res getCommentsByTopicId(@PathVariable Long topicId,
                                    @RequestParam(required = false, name = "page",
                                                            defaultValue = "1") Integer page,
                                    @RequestParam(required = false, name = "size",
                                                            defaultValue = "10") Integer size) {

        return Res.success(commentService.getCommentsByTopicId(topicId, Page.of(page, size)));
    }

    /**
     * 添加评论
     *
     * @param commentDTO
     * @return
     */
    @PostMapping
    public Res addComment(@RequestBody CommentDTO commentDTO) {
        commentService.addComment(commentDTO);
        return Res.success();
    }


    /**
     * 评论回复
     *
     * @param commentId
     * @param commentDTO
     * @return
     */
    @PostMapping("/{commentId}/reply")
    public Res replyComment(@PathVariable Long commentId,
                            @RequestBody CommentDTO commentDTO) {
        commentService.replyComment(commentId, commentDTO);
        return Res.success();
    }

    /**
     * 删除评论
     *
     * @param commentId
     * @return
     */
    @DeleteMapping("/{commentId}")
    public Res deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return Res.success();
    }

    /**
     * 评论点赞
     *
     * @param commentId
     * @return
     */
    @PostMapping("/{commentId}/like")
    public Res likeComment(@PathVariable Long commentId) {
        commentService.likeComment(commentId, true);
        return Res.success();
    }

    /**
     * 评论取消点赞
     *
     * @param commentId
     * @return
     */
    @DeleteMapping("/{commentId}/like")
    public Res unlikeComment(@PathVariable Long commentId) {
        commentService.likeComment(commentId, false);
        return Res.success();
    }

    /**
     * 置顶评论
     *
     * @param commentId
     * @return
     */
    @PostMapping("/{commentId}/top")
    public Res topComment(@PathVariable Long commentId) {
        commentService.topComment(commentId, true);
        return Res.success();
    }

    /**
     * 取消置顶
     *
     * @param commentId
     * @return
     */
    @DeleteMapping("/{commentId}/top")
    public Res unTopComment(@PathVariable Long commentId) {
        commentService.topComment(commentId, false);
        return Res.success();
    }
}
