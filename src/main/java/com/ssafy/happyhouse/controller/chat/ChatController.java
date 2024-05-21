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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template;

    @MessageMapping("/room/{roomId}/entered")
    public void entered(@DestinationVariable(value = "roomId") String roomId, Chat chat) {
        log.info("## room id = {}", roomId);
        log.info("## message = {}", chat);

        Chat enterd = new Chat();
        enterd.setSender(chat.getSender());
        enterd.setTimestamp(LocalDateTime.now().toString());
        enterd.setMessage(chat.getSender() + "님이 입장하셨습니다.");
        template.convertAndSend("/sub/room/" + roomId, enterd);
    }

    @MessageMapping("/room/{roomId}")
    public void sendMessage(@DestinationVariable(value = "roomId") String roomId, Chat chat) {
        log.info("## room id = {}", roomId);
        log.info("## message = {}", chat);

        // JSON 형식으로 전송
//        String payload = String.format("{\"sender\":\"%s\", \"message\":\"%s\", \"timestamp\":\"%s\"}",
//                chat.getSender(), chat.getMessage(), chat.getTimestamp());
        template.convertAndSend("/sub/room/" + roomId, chat);
    }
}

