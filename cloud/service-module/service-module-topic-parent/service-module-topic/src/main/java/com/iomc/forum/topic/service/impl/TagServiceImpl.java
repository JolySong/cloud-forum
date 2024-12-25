package com.iomc.forum.topic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iomc.common.core.Res;
import com.iomc.common.core.utils.ListUtils;
import com.iomc.common.core.utils.StrUtils;
import com.iomc.forum.topic.api.dto.TagDTO;
import com.iomc.forum.topic.api.entity.Tag;
import com.iomc.forum.topic.api.mapstruct.TagMapper;
import com.iomc.forum.topic.api.vo.TagVO;
import com.iomc.forum.topic.dao.TagDao;
import com.iomc.forum.topic.service.TagService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService {

    /**
     * 将tag转换为tagVO
     *
     * @param tag
     * @return
     */
    private static TagVO getTagVO(Tag tag) {
        if (tag == null) {
            return null;
        }

        return TagMapper.INSTANCE.toVO(tag);
    }


    /**
     * 根据tagId获取tag
     *
     * @param tagId
     * @return
     */
    @Override
    public TagVO getTagNameById(Integer tagId) {
        Tag tag = getById(tagId);
        return getTagVO(tag);
    }


    /**
     * 根据名字获取tag
     *
     * @param name
     */
    @Override
    public TagVO getTagByName(String name) {
        Tag tag = baseMapper.selectOne(
                new LambdaQueryWrapper<Tag>()
                        .eq(Tag::getName, name));

        return getTagVO(tag);
    }

    /**
     * 获取tag列表
     *
     * @param keyword
     */
    @Override
    public List<TagVO> getTagList(String keyword) {

        List<Tag> list = null;
        if (StrUtils.isNotBlank(keyword)) {
            list = list(
                    new LambdaQueryWrapper<Tag>()
                            .like(Tag::getName, keyword)
            );
        } else {
            list = list();
        }

        if (ListUtils.isEmpty(list)) {
            return null;
        }

        List<TagVO> listVO = new ArrayList<>();
        for (Tag tag : list) {
            listVO.add(TagMapper.INSTANCE.toVO(tag));
        }
        return listVO;
    }

    /**
     * 添加tag
     *
     * @param tag
     * @return
     */
    @Override
    public Res addTag(TagDTO tag) {

        if (getTagByName(tag.getName()) != null) {
            return Res.fail("标签名称已存在");
        }

        Tag entity = TagMapper.INSTANCE.toEntity(tag);

        return Res.success(save(entity));
    }

    /**
     * 删除tag
     *
     * @param tagId
     * @return
     */
    @Override
    public Res deleteTag(Integer tagId) {
        return Res.success(removeById(tagId));
    }

    /**
     * 更新tag
     *
     * @param tag
     * @return
     */
    @Override
    public Res updateTag(TagDTO tag) {

        TagVO tagByName = getTagByName(tag.getName());
        if (StrUtils.isNotBlank(tag.getName()) && !tagByName.getId().equals(tag.getId())) {
            return Res.fail("标签名称已存在");
        }

        Tag entity = TagMapper.INSTANCE.toEntity(tag);
        return Res.success(updateById(entity));
    }
}
