package com.iomc.forum.topic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iomc.common.core.Res;
import com.iomc.forum.topic.api.dto.TagDTO;
import com.iomc.forum.topic.api.entity.Tag;
import com.iomc.forum.topic.api.vo.TagVO;

import java.util.List;

public interface TagService extends IService<Tag> {

    /**
     * 根据tagId获取tag
     * @param tagId
     * @return
     */
    TagVO getTagNameById(Integer tagId);

    /**
     * 根据名字获取tag
     */
    TagVO getTagByName(String name);

    /**
     * 获取tag列表
     *
     * @param keyword
     */
    List<TagVO> getTagList(String keyword);

    /**
     * 添加tag
     *
     * @param tag
     * @return
     */
    Res addTag(TagDTO tag);

    /**
     * 删除tag
     *
     * @param tagId
     * @return
     */
    Res deleteTag(Integer tagId);

    /**
     * 更新tag
     *
     * @param tag
     * @return
     */
    Res updateTag(TagDTO tag);
}
