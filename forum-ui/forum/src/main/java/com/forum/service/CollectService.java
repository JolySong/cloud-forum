package com.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.forum.entity.Collect;

public interface CollectService  extends IService<Collect>  {
    boolean isCollected(Long userId, Long topicId);
    void collect(Long userId, Long topicId);
    void uncollect(Long userId, Long topicId);
} 