package com.ssafy.happyhouse.dto.chat;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum MessageType {

    CHAT, JOIN, LEAVE;

    private String messageType;

    public static MessageType from(String type){

        return MessageType.valueOf(type.toUpperCase());
    }

    public static boolean isMessageType(String type) {

        return Arrays.stream(MessageType.values())
                .filter(message -> message.name().equals(type))
                .collect(Collectors.toList()).size() != 0;
    }
}
