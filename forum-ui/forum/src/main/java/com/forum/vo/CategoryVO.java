package com.forum.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO {

    /** 主键 */
    private Long id;
    /** 分类名 */
    private String name;
    /** 分类描述 */
    private String description;
    /** 排序 */
    private Integer sort;
    /** 状态 */
    private Integer status;
    /** 文章数量 */
    private Integer topicCount;
    /** 创建时间 */
    private LocalDateTime createdAt;
} 