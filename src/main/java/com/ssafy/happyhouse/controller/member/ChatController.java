package com.ssafy.happyhouse.controller.member;

import com.ssafy.happyhouse.dto.chat.Chat;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/room/public")
    public Chat sendMessage(Chat message) {

        return message;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/room/public")
    public Chat addUser(Chat message, SimpMessageHeaderAccessor accessor) {

        accessor.getSessionAttributes().put("username", message.getSender());

        return message;
    }
}
