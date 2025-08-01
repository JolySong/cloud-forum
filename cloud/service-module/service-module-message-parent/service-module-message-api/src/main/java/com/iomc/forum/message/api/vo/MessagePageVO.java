package com.iomc.forum.message.api.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 消息分页VO
 *
 * @author forum
 * @since 2024-12-25
 */
@Data
public class MessagePageVO {
    
    /**
     * 消息列表
     */
    private List<MessageVO> records;
    
    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 当前页码
     */
    private Long current;
    
    /**
     * 每页大小
     */
    private Long size;
    
    /**
     * 未读消息统计
     */
    private Map unreadCounts;
} 