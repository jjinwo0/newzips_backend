package com.ssafy.happyhouse.global.token;

import lombok.Getter;

@Getter
public enum GrantType {

    BEARER("Bearer");

    GrantType(String type){
        this.type = type;
    }

    private String type;
}
