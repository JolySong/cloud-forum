package com.forum.controller;



import com.forum.common.R;
import com.forum.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private com.forum.service.CommentService commentService;


    /**
     * 评论
     *
     * @param commentDTO
     * @return
     */
    @PostMapping
    public R createComment(@RequestBody CommentDTO commentDTO) {
        if (null == commentDTO || null == commentDTO.getTopicId()) {
            return R.error("参数错误");
        }

        if (StringUtils.isEmpty(commentDTO.getContent())) {
            return R.error("评论内容不能为空");
        }

        if (commentDTO.getContent().length() < 5) {
            return R.error("评论内容过短");
        }

        if (commentDTO.getContent().length() > 500) {
            return R.error("评论内容过长");
        }

        commentService.addComment(commentDTO);
        return R.ok();
    }

    /**
     * 回复评论
     */
    @PostMapping("/{commentId}/reply")
    public R replyComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
        if (null == commentDTO || null == commentDTO.getContent()) {
            return R.error("参数错误");
        }
        if (commentDTO.getParentId() != null && commentDTO.getReplyTo() == null) {
            return R.error("回复评论时，回复对象不能为空");
        }
        commentService.replyComment(commentId, commentDTO);
        return R.ok();
    }


    /**
     * 删除评论
     *
     * @param commentId
     * @return
     */
    @DeleteMapping("/{commentId}")
    public R deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return R.ok();
    }

    /**
     * 点赞评论
     *
     * @param commentId
     */
    @PostMapping("/{commentId}/like")
    public R likeComment(@PathVariable Long commentId) {
        commentService.likeComment(commentId);
        return R.ok();
    }

    /**
     * 取消点赞评论
     *
     * @param commentId
     */
    @DeleteMapping("/{commentId}/like")
    public R unLikeComment(@PathVariable Long commentId) {
        commentService.unlikeComment(commentId);
        return R.ok();
    }


}
