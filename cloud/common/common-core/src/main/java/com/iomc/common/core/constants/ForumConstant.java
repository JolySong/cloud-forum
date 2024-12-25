package com.iomc.common.core.constants;

public interface ForumConstant {

    /** 点赞类型-主题 */
    Integer LIKE_TYPE_TOPIC = 0;
    /** 点赞类型-评论 */
    Integer LIKE_TYPE_COMMENT = 1;

    /**
     * 审核状态正在审核
     */
    Integer AUDIT_STATUS_AUDITING = 0;
    /**
     * 审核状态审核通过
     */
    Integer AUDIT_STATUS_PASS = 1;
    /**
     * 审核状态审核未通过
     */
    Integer AUDIT_STATUS_FAIL = 2;

}
