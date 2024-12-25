package com.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.common.utils.StrUtil;
import com.forum.dto.TagDTO;
import com.forum.entity.Tag;
import com.forum.mapper.TagMapper;
import com.forum.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {


    /**
     * 根据tagId获取tag
     *
     * @param tagId
     * @return
     */
    @Override
    public Tag getTagNameById(Integer tagId) {
        return getById(tagId);
    }

    /**
     * 根据名字获取tag
     *
     * @param name
     */
    @Override
    public Tag getTagByName(String name) {
        return baseMapper.selectOne(
                new LambdaQueryWrapper<Tag>()
                        .eq(Tag::getName, name));
    }

    /**
     * 获取tag列表
     *
     * @param keyword
     */
    @Override
    public List<Tag> getTagList(String keyword) {

        if (StrUtil.isNotBlank(keyword)) {
            return list(
                    new LambdaQueryWrapper<Tag>()
                            .like(Tag::getName, keyword)
            );
        }
        return list();
    }

    /**
     * 添加tag
     *
     * @param tag
     * @return
     */
    @Override
    public Long addTag(TagDTO tag) {

        Tag tagPO = new Tag();
        BeanUtils.copyProperties(tag, tagPO);

        if (save(tagPO)) {
            return tagPO.getId();
        }
        return 0L;
    }

    /**
     * 删除tag
     *
     * @param tagId
     * @return
     */
    @Override
    public Long deleteTag(Integer tagId) {
        if (removeById(tagId)) {
            return tagId.longValue();
        }
        return 0L;
    }

    /**
     * 更新tag
     *
     * @param tag
     * @return
     */
    @Override
    public Long updateTag(TagDTO tag) {
        Tag tagPO = new Tag();
        BeanUtils.copyProperties(tag, tagPO);

        if (updateById(tagPO)) {
            return tagPO.getId();
        }
        return 0L;
    }
}
