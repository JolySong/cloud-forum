package com.iomc.forum.topic.api.dto;


import lombok.Data;

@Data
public class CommentDTO {

    private Long topicId;

    private Long parentId;

    private Long userId;

    private Long replyTo;

    private String content;
}
