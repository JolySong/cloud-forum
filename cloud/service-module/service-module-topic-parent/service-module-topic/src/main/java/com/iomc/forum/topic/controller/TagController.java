package com.iomc.forum.topic.controller;


import com.iomc.common.core.Res;
import com.iomc.common.core.utils.StrUtils;
import com.iomc.forum.topic.api.dto.TagDTO;
import com.iomc.forum.topic.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 添加标签
     *
     * @param tag
     * @return
     */
    @PostMapping
    public Res addTag(TagDTO tag) {
        if (tag == null || StrUtils.isBlank(tag.getName())) {
            return Res.fail("参数错误");
        }

       return tagService.addTag(tag);
    }

    /**
     * 删除标签
     *
     * @param tagId
     * @return
     */
    @DeleteMapping
    public Res deleteTag(@RequestParam Integer tagId) {
        return tagService.deleteTag(tagId);
    }

    /**
     * 更新标签
     *
     * @param tag
     * @return
     */
    @PutMapping
    public Res updateTag(TagDTO tag) {
        if (tag == null || StrUtils.isBlank(tag.getName())) {
            return Res.fail("参数错误");
        }

        return tagService.updateTag(tag);
    }

    /**
     * 获取标签列表
     *
     * @param keyword
     * @return
     */
    @GetMapping
    public Res getTagList(@RequestParam(required = false, value = "keyword") String keyword) {
        return Res.success(tagService.getTagList(keyword));
    }

    /**
     * 获取标签详情
     *
     * @param tagId
     * @return
     */
    @GetMapping("/{tagId}")
    public Res getTagNameById(@PathVariable Integer tagId) {
        return Res.success(tagService.getTagNameById(tagId));
    }
}
