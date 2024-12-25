package com.forum.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forum.common.R;
import com.forum.dto.TopicDTO;
import com.forum.service.CommentService;
import com.forum.service.TopicService;
import com.forum.vo.CommentVO;
import com.forum.vo.TopicDetailVO;
import com.forum.vo.TopicVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {

    @Autowired
    private final TopicService topicService;

    @Autowired
    private final CommentService commentService;

    /**
     * 获取话题列表
     */
    @GetMapping
    public R<IPage<TopicVO>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String sort) {
        Page<TopicVO> page = new Page<>(current, size);
        IPage<TopicVO> result = topicService.getTopicList(page, categoryId, type, sort);
        return R.ok(result);
    }

    /**
     * 获取话题详情
     */
    @GetMapping("/{id}")
    public R<TopicDetailVO> detail(@PathVariable Long id) {
        TopicDetailVO topic = topicService.getTopicDetail(id);
        return R.ok(topic);
    }

    /**
     * 创建话题
     */
    @PostMapping
    public R<Void> create(@RequestBody @Validated TopicDTO topicDTO) {
        topicService.createTopic(topicDTO);
        return R.ok();
    }

    /**
     * 更新话题
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody @Validated TopicDTO topicDTO) {
        topicService.updateTopic(id, topicDTO);
        return R.ok();
    }

    /**
     * 删除话题
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return R.ok();
    }

    /**
     * 点赞话题
     */
    @PostMapping("/{id}/like")
    public R<Void> like(@PathVariable Long id) {
        topicService.likeTopic(id);
        return R.ok();
    }

    /**
     * 取消点赞
     */
    @DeleteMapping("/{id}/like")
    public R<Void> unlike(@PathVariable Long id) {
        topicService.unlikeTopic(id);
        return R.ok();
    }

    /**
     * 收藏话题
     */
    @PostMapping("/{id}/collect")
    public R<Void> collect(@PathVariable Long id) {
        topicService.collectTopic(id);
        return R.ok();
    }

    /**
     * 取消收藏
     */
    @DeleteMapping("/{id}/collect")
    public R<Void> uncollect(@PathVariable Long id) {
        topicService.uncollectTopic(id);
        return R.ok();
    }


    /**
     * 获取话题评论
     *
     * @param topicId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/{topicId}/comments")
    public R<IPage<CommentVO>> getCommentsByTopicId(@PathVariable Long topicId,
                                                    @RequestParam(required = false, name = "page",
                                                            defaultValue = "1") Integer page,
                                                    @RequestParam(required = false, name = "size",
                                                            defaultValue = "10") Integer size) {

        // 获取评论列表（分页）
        Page<CommentVO> commentPage = new Page<>(page, size);
        IPage<CommentVO> comments = commentService.getCommentsByTopicId(topicId, commentPage);
        return R.ok(comments);

    }
} 