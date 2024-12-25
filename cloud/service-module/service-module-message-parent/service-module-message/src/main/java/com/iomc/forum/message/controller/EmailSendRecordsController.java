package com.iomc.forum.message.controller;


import com.iomc.common.mq.RocketMQProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 邮件记录表 前端控制器
 * </p>
 *
 * @author song
 * @since 2024-12-11
 */
@RestController
public class EmailSendRecordsController {


    @Autowired
    private RocketMQProducerService rocketMQProducerService;


    /**
     * 发送测试
     */
    @GetMapping("/send")
    public void sendEmail() {

        String topic = "test";

        Message<String> msg = MessageBuilder.withPayload("Hello,RocketMQ").build();
        rocketMQProducerService.send(topic, "Hello,RocketMQ111");
    }

}
