package com.ssafy.happyhouse.controller.chat;

import com.ssafy.happyhouse.entity.chat.Chat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template;

    @MessageMapping("/room/{roomId}/entered")
    public void entered(@DestinationVariable(value = "roomId")String roomId, Chat chat) {

        log.info("## room id = {}", roomId);
        log.info("## message = {}", chat);

        final String payload = chat.getSender() + "님이 입장하셨습니다.";

        template.convertAndSend("/sub/room/" + roomId, payload);
    }

    @MessageMapping("/room/{roomId}")
    public void sendMessage(@DestinationVariable(value = "roomId")String roomId, Chat chat) {

        log.info("## room id = {}", roomId);
        log.info("## message = {}", chat);

        template.convertAndSend("/sub/room/" + roomId, chat.getMessage());
    }
}
