package com.iomc.forum.message.mq;

import com.alibaba.fastjson2.JSONObject;
import com.iomc.common.mq.constant.MqConst;
import com.iomc.common.redis.utils.RedisUtil;
import com.iomc.forum.message.api.dto.EmailSendDTO;
import com.iomc.forum.message.service.IEmailSendRecordsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(
        topic = MqConst.SERVICE_AUTH_MESSAGE_TOPIC,
        consumerGroup = MqConst.SERVICE_AUTH_MESSAGE_GROUP)
@Slf4j
public class AuthMQConsumerService implements RocketMQListener<MessageExt> {

    @Autowired
    private IEmailSendRecordsService emailSendService;

    @Override
    public void onMessage(MessageExt message) {

        String msgId = message.getMsgId();
        if (RedisUtil.hasKey(MqConst.MESSAGE_ID_KEY + msgId)) {
            log.info("消息ID: {} 已处理", msgId);
            return;
        }


        byte[] body = message.getBody();

        log.info("接收到消息ID: {}", msgId);
        try {
            RedisUtil.set(MqConst.MESSAGE_ID_KEY + msgId, body);
            EmailSendDTO emailSendDTO = JSONObject.parseObject(new String(body), EmailSendDTO.class);
            emailSendService.sendSecurityEmail(emailSendDTO);
        } catch (Exception e) {
            log.error("发送邮件失败：" + e.getMessage());
        }
    }
}
