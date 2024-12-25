package com.forum.vo;

import com.forum.entity.Tag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TopicVO {
    private Long id;
    private String title;
    private String preview;
    private String content;
    private String authorName;
    private String authorAvatar;
    private String categoryName;
    private Integer viewCount;
    private Integer likeCount;
    private Integer collectCount;
    private Integer commentCount;
    private Integer isFeatured;
    private Integer isTop;
    private Integer status;
    private List<Tag> tags;
    private CategoryVO category;
    private UserVO author;
    private LocalDateTime createdAt;
} 