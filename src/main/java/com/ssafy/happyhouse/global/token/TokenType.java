package com.ssafy.happyhouse.global.token;

public enum TokenType {

    ACCESS, REFRESH;

    public static boolean isAccessToken(String tokenType){
        return TokenType.ACCESS.name().equals(tokenType);
    }
}
