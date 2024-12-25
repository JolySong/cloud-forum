package com.forum.dto;


import lombok.Data;

@Data
public class CommentDTO {

    private Long topicId;

    private Long parentId;

    private Long userId;

    private Long replyTo;

    private String content;
}
