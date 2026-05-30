package com.yupi.template.model.enums;

import lombok.Getter;

@Getter
public enum ImageMethodEnum {

    PEXELS("PEXELS", "PEXELS"),


    PICSUM("PICSUM", "PICSUM");


    private final String value;

    private final String description;

    ImageMethodEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
}
