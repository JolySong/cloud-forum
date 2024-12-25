
package com.iomc.forum.topic.api.vo;

import com.iomc.forum.user.api.vo.UserProfileVO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TopicVO {
    /**
     * 主题ID
     */
    private Long id;

    /**
     * 主题标题
     */
    private String title;

    /**
     * 主题预览内容
     */
    private String preview;

    /**
     * 主题详细内容
     */
    private String content;

    /**
     * 分类编码
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 点赞次数
     */
    private Integer likeCount;

    /**
     * 收藏次数
     */
    private Integer collectCount;

    /**
     * 评论次数
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
     * 是否为精选主题（1: 是, 0: 否）
     */
    private Boolean isFeatured;

    /**
     * 是否置顶（1: 是, 0: 否）
     */
    private Boolean isTop;

    /**
     * 评论状态（例如：0: 已删除, 1: 已发布）
     */
    private Integer status;

    /**
     * 审核状态（0: 待审核, 1: 已通过, 2: 已拒绝）
     */
    private Integer audit;

    /**
     * 主题分类信息
     */
    private CategoryVO category;

    /**
     * 主题标签列表
     */
    private List<TagVO> tags;

    /**
     * 作者ID
     */
    private Long userId;

    /**
     * 作者信息
     */
    private UserProfileVO author;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 最后评论时间
     */
    private LocalDateTime lastCommentAt;
}