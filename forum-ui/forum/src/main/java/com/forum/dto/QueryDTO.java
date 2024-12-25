package com.forum.dto;


import lombok.Data;

@Data
public class QueryDTO {

    private Integer page;

    private Integer size;

    private String keyword;
}
