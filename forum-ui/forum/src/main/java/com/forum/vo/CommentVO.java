package com.forum.vo;

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
     * 创建时间
     */
    private LocalDateTime createdAt;
} 