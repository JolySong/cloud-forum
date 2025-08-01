package com.iomc.forum.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iomc.forum.message.api.dto.MessageQueryDTO;
import com.iomc.forum.message.api.entity.Message;
import com.iomc.forum.message.api.vo.MessagePageVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author song
 * @since 2024-12-12
 */
public interface IMessageService extends IService<Message> {

    /**
     * 分页查询用户消息
     *
     * @param queryDTO 查询参数
     * @param userId 用户ID
     * @return 消息分页结果
     */
    MessagePageVO getMessagePage(MessageQueryDTO queryDTO, Long userId);

    /**
     * 获取用户未读消息统计
     *
     * @param userId 用户ID
     * @return 未读消息统计
     */
    Map<String, Long> getUnreadCounts(Long userId);

    /**
     * 标记消息已读
     *
     * @param messageId 消息ID
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean markAsRead(Long messageId, Long userId);

    /**
     * 批量标记消息已读
     *
     * @param messageIds 消息ID列表
     * @param userId 用户ID
     * @return 更新数量
     */
    int markBatchAsRead(List<Long> messageIds, Long userId);

    /**
     * 标记所有消息已读
     *
     * @param userId 用户ID
     * @return 更新数量
     */
    int markAllAsRead(Long userId);

    /**
     * 创建消息
     *
     * @param message 消息实体
     * @return 是否成功
     */
    boolean createMessage(Message message);

    /**
     * 批量创建消息
     *
     * @param messages 消息列表
     * @return 是否成功
     */
    boolean createBatchMessages(List<Message> messages);

    /**
     * 使消息失效
     *
     * @param messageId 消息ID
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean invalidateMessage(Long messageId, Long userId);
}
