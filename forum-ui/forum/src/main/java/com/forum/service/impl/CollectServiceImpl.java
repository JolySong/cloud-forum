package com.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.entity.Collect;
import com.forum.mapper.CollectMapper;
import com.forum.service.CollectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Override
    public boolean isCollected(Long userId, Long topicId) {
        return baseMapper.countByUserAndTopic(userId, topicId) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void collect(Long userId, Long topicId) {
        if (!isCollected(userId, topicId)) {
            Collect collect = new Collect();
            collect.setUserId(userId);
            collect.setTopicId(topicId);
            baseMapper.insert(collect);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uncollect(Long userId, Long topicId) {
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getUserId, userId)
                .eq(Collect::getTopicId, topicId);
        baseMapper.delete(wrapper);
    }
} 