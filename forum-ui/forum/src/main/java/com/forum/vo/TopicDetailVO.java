package com.forum.vo;

import com.forum.entity.Tag;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 话题详情视图对象
 *
 * @author forum
 * @since 2024-03-15
 */
@Data
public class TopicDetailVO {
    /**
     * 话题ID
     */
    private Long id;

    /**
     * 话题标题
     */
    private String title;

    /**
     * 话题内容
     */
    private String content;

    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * 作者名称
     */
    private String authorName;

    /**
     * 作者头像
     */
    private String authorAvatar;

    /**
     * 作者简介
     */
    private String authorBio;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 标签列表
     */
    private List<TagVO> tags;

    /**
     * 是否精选
     */
    private Boolean isFeatured;

    /** 是否置顶 */
    private Boolean isTop;

    /**
     * 浏览数
     */
    private Integer viewCount;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 收藏数
     */
    private Integer collectCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 当前用户是否已点赞
     */
    private Boolean isLiked;

    /**
     * 当前用户是否已收藏
     */
    private Boolean isCollected;

    /**
     * 当前用户是否关注了作者
     */
    private Boolean isFollowedAuthor;

    /** 作者统计信息 */
    private UserStatVO author;

    /**
     * 评论列表
     */
    private List<CommentVO> comments;

    /**
     * 相关话题推荐
     */
    private List<TopicVO> relatedTopics;

    /**
     * 最后评论时间
     */
    private LocalDateTime lastCommentAt;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
} 