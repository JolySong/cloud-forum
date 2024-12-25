package com.iomc.forum.topic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iomc.forum.topic.api.entity.Collect;

public interface CollectService  extends IService<Collect>  {
    boolean isCollected(Long userId, Long topicId);
    void collect(Long userId, Long topicId);
    void uncollect(Long userId, Long topicId);
} 