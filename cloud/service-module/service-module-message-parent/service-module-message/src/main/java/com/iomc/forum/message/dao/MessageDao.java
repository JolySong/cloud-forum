package com.iomc.forum.message.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iomc.common.mybatis.mapper.SpiceBaseMapper;
import com.iomc.forum.message.api.entity.Message;
import com.iomc.forum.message.api.vo.MessageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息表 Mapper 接口
 * </p>
 *
 * @author song
 * @since 2024-12-12
 */
@Mapper
public interface MessageDao extends SpiceBaseMapper<Message> {

    /**
     * 分页查询用户消息
     *
     * @param page 分页参数
     * @param userId 用户ID
     * @param type 消息类型
     * @param isRead 是否已读
     * @return 消息分页结果
     */
    IPage<MessageVO> selectMessagePage(Page<MessageVO> page, 
                                      @Param("userId") Long userId,
                                      @Param("type") String type,
                                      @Param("isRead") Boolean isRead);

    /**
     * 获取用户未读消息统计
     *
     * @param userId 用户ID
     * @return 未读消息统计
     */
    Map<String, Long> selectUnreadCounts(@Param("userId") Long userId);

    /**
     * 批量更新消息已读状态
     *
     * @param userId 用户ID
     * @param messageIds 消息ID列表
     * @return 更新数量
     */
    int updateBatchRead(@Param("userId") Long userId, @Param("messageIds") List<Long> messageIds);

    /**
     * 更新用户所有消息为已读
     *
     * @param userId 用户ID
     * @return 更新数量
     */
    int updateAllRead(@Param("userId") Long userId);
}
