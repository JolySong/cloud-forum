package com.iomc.common.mq;

import com.iomc.common.core.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 生产者消息服务封装
 */
@Component
@Slf4j
public class RocketMQProducerService {

    private final RocketMQTemplate rocketMQTemplate;

    public RocketMQProducerService(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     * 构建消息对象
     *
     * @param message 消息内容
     * @return
     */
    public Message<byte[]> buildMessage(String message) {


        byte[] bytes = null;
        try {
            bytes = message.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("消息编码失败：{}", e.getMessage());
            throw new BizException("消息编码失败: " + e.getMessage());
        }
        return MessageBuilder.withPayload(bytes).build();
    }

    /**
     * 发送普通消息
     *
     * @param topic 主题
     * @param message 消息内容
     */
    public SendResult send(String topic, String message) {
       return send(topic, null, message);
    }

    /**
     * 发送普通消息
     *
     * @param topic 主题
     * @param tag 标签
     * @param message 消息内容
     */
    public SendResult send(String topic, String tag, String message) {

        try {
            SendResult sendResult = rocketMQTemplate.syncSend(topic + ":" + tag, buildMessage(message));
            log.info("【sendMsg】sendResult={}", message);
            return sendResult;
        } catch (Exception e) {
            log.error("发送消息失败：{}", e.getStackTrace());
            throw new BizException("发送消息失败: " + e.getMessage());
        }
    }

    /**
     * 发送异步消息（通过线程池执行发送到broker的消息任务，执行完后回调：在SendCallback中可处理相关成功失败时的逻辑）
     * （适合对响应时间敏感的业务场景）
     */
    public void sendAsyncMsg(String topic, String msgBody) {
        rocketMQTemplate.asyncSend(topic, buildMessage(msgBody), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                // 处理消息发送成功逻辑
            }
            @Override
            public void onException(Throwable throwable) {
                // 处理消息发送异常逻辑
            }
        });
    }

    /**
     * 发送单向消息（不等待服务端确认）
     *
     * @param topic 主题
     * @param message 消息内容
     */
    public void sendOneWay(String topic, String message) {
        try {
            rocketMQTemplate.sendOneWay(topic, message);
        } catch (Exception e) {
            log.error("发送消息失败：{}", e.getStackTrace());
            throw new BizException("发送消息失败: " + e.getMessage());
        }
    }

    /**
     * 发送延时消息
     *
     * @param topic 主题
     * @param message 消息内容
     * @param level 延时级别（1-18）
     */
    public void sendDelayMessage(String topic, String message, int level) {
        try {
            rocketMQTemplate.convertAndSend(topic + ":" + level, message);
        } catch (Exception e) {
            log.error("发送消息失败：{}", e.getStackTrace());
            throw new BizException("发送消息失败: " + e.getMessage());
        }
    }
}
