package com.forum.vo;


import lombok.Data;

@Data
public class AuthorInfoVO {

    private Long id;

    private String username;

    /** 主题数 */
    private Long authorTopicCount;

    /** 粉丝数 */
    private Long authorFollowerCount;

    /** 获赞数 */
    private Long authorLikeCount;

    /** 当前用户是否关注 */
    private Boolean isFollowed;
}
