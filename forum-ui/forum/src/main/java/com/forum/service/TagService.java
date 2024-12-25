package com.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.forum.dto.TagDTO;
import com.forum.entity.Tag;

import java.util.List;

public interface TagService extends IService<Tag> {

    /**
     * 根据tagId获取tag
     * @param tagId
     * @return
     */
    Tag getTagNameById(Integer tagId);

    /**
     * 根据名字获取tag
     */
    Tag getTagByName(String name);

    /**
     * 获取tag列表
     *
     * @param keyword
     */
    List<Tag> getTagList(String keyword);

    /**
     * 添加tag
     *
     * @param tag
     * @return
     */
    Long addTag(TagDTO tag);

    /**
     * 删除tag
     *
     * @param tagId
     * @return
     */
    Long deleteTag(Integer tagId);

    /**
     * 更新tag
     *
     * @param tag
     * @return
     */
    Long updateTag(TagDTO tag);
}
