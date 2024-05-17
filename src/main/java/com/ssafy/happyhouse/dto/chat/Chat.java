package com.ssafy.happyhouse.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Chat {

    private MessageType messageType; // 메시지 타입 (CHAT, JOIN, LEAVE)
    private String content; // 메시지 내용
    private String sender; // 발신자
}
