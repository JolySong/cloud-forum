package com.forum.controller;


import com.forum.common.R;
import com.forum.dto.TagDTO;
import com.forum.entity.Tag;
import com.forum.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 添加标签
     *
     * @param tag
     * @return
     */
    @PostMapping("/tags/add")
    public R addTag(TagDTO tag) {
        if (tag == null || tag.getName() == null) {
            return R.error("参数错误");
        }

        Tag tagByName = tagService.getTagByName(tag.getName());
        if (tagByName != null) {
            return R.error("标签名称已存在");
        }
        Long l = tagService.addTag(tag);
        if (l == 0L) {
            return R.error("添加失败");
        }

        return R.ok(l);
    }

    /**
     * 获取标签列表
     *
     * @param keyword
     * @return
     */
    @GetMapping("/tags")
    public R getTagList(@RequestParam(required = false, value = "keyword") String keyword) {
        return R.ok(tagService.getTagList(keyword));
    }
}
