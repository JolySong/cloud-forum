package com.iomc.forum.topic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iomc.forum.topic.api.entity.Collect;
import com.iomc.forum.topic.dao.CollectDao;
import com.iomc.forum.topic.service.CollectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CollectServiceImpl extends ServiceImpl<CollectDao, Collect> implements CollectService {

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