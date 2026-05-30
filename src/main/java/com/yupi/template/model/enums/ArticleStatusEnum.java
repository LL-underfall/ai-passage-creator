package com.yupi.template.model.enums;

import lombok.Getter;

@Getter
public enum ArticleStatusEnum {

    PENDING("PENDING", "待处理"),


    PROCESSING("PROCESSING", "处理中"),


    COMPLETED("COMPLETED", "已完成"),


    FAILED("FAILED", "已失败");


    private final String value;

    private final String description;

    ArticleStatusEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
}
