package com.iomc.forum.topic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iomc.forum.topic.api.entity.TopicView;
import com.iomc.forum.topic.dao.TopicViewDao;
import com.iomc.forum.topic.service.TopicViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicViewServiceImpl extends ServiceImpl<TopicViewDao, TopicView> implements TopicViewService {


}
