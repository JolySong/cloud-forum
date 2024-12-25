package com.iomc.forum.topic.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 话题创建/更新参数
 *
 * @author forum
 * @since 2024-03-15
 */
@Data
public class TopicDTO {

    private Long id;

    /**
     * 话题标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;
    
    /**
     * 话题内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;
    
    /**
     * 分类ID
     */
    @NotNull(message = "分类不能为空")
    private Long categoryId;
    
    /**
     * 标签列表
     */
    private List<Long> tags;
} 