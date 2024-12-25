package com.iomc.forum.topic.api.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论视图对象
 *
 * @author forum
 * @since 2024-03-15
 */
@Data
public class CommentVO {
    /**
     * 评论ID
     */
    private Long id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论者ID
     */
    private Long userId;

    /**
     * 评论者名称
     */
    private String userName;

    /**
     * 评论者昵称
     */
    private String nickname;

    /**
     * 评论话题
     */
    private Long topicId;

    /**
     * 评论话题
     */
    private String topicTitle;

    /**
     * 评论者头像
     */
    private String userAvatar;

    /**
     * 父评论ID
     */
    private Long parentId;

    /**
     * 回复目标用户ID
     */
    private Long replyToId;

    /**
     * 回复目标用户名称
     */
    private String replyToName;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 当前用户是否已点赞
     */
    private Boolean isLiked;

    /**
     * 子评论列表
     */
    private List<CommentVO> children;

    /**
     * 评论状态（例如：0: 已删除, 1: 已发布）
     */
    private Integer status;

    /**
     * 审核状态（0: 待审核, 1: 已通过, 2: 已拒绝）
     */
    private Integer audit;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
} 