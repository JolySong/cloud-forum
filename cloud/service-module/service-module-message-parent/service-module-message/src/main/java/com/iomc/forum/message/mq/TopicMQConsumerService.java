package com.iomc.forum.message.mq;

import com.alibaba.fastjson2.JSONObject;
import com.iomc.common.mq.constant.MqConst;
import com.iomc.common.redis.utils.RedisUtil;
import com.iomc.forum.message.api.dto.MessageDTO;
import com.iomc.forum.message.api.entity.Message;
import com.iomc.forum.message.api.mapstruct.MessageMapper;
import com.iomc.forum.message.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(
        topic = MqConst.SERVICE_FORUM_MESSAGE_TOPIC,
//        selectorType = SelectorType.TAG,
//        selectorExpression = MqConst.TAG_EDIT_TOPIC_MESSAGE,
        consumerGroup = MqConst.SERVICE_AUTH_MESSAGE_GROUP)
@Slf4j
public class TopicMQConsumerService implements RocketMQListener<MessageExt> {

    @Autowired
    private IMessageService messageService;

    @Override
    public void onMessage(MessageExt message) {

        String tags = message.getTags();
        if (MqConst.TAG_TOPIC_MESSAGE.equals(tags)) {
            log.info("接收到话题的消息");
        } else if (MqConst.TAG_LIKE_MESSAGE.equals(tags)) {
            log.info("接收到点赞的消息");
        } else if (MqConst.TAG_FOLLOW_MESSAGE.equals(tags)){
            log.info("接收到关注的消息");
        } else if (MqConst.TAG_COMMENT_MESSAGE.equals(tags)){
            log.info("接收到回复的消息: {}", tags);

        } else {
            log.info("接收到未知的消息: {}", tags);
        }

        String msgId = message.getMsgId();
        if (RedisUtil.hasKey(MqConst.MESSAGE_ID_KEY + msgId)) {
            log.info("消息重复。ID: {} 已处理", msgId);
            return;
        }
        RedisUtil.set(MqConst.MESSAGE_ID_KEY + msgId, msgId);

        byte[] body = message.getBody();
        try {
            MessageDTO dto = JSONObject.parseObject(new String(body), MessageDTO.class);
            if (dto == null || dto.getType() == null) {
                log.error("消息格式错误，无法保存。ID: {}", msgId);
                return;
            }
            Message entity = MessageMapper.INSTANCE.toEntity(dto);
            messageService.save(entity);
        } catch (Exception e) {
            log.error("保存消息(id:{})失败：{}", msgId, e.getMessage());
        }
    }
}
