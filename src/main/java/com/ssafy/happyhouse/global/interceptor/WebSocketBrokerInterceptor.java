package com.ssafy.happyhouse.global.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class WebSocketBrokerInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        final StompCommand commandType = accessor.getCommand();

        if (StompCommand.CONNECT == commandType) {
            // 웹소켓 연결 시 유저 인증
        } else if (StompCommand.SEND == commandType) {
            // pub시 메시지 처리 작업 작성
        } else if (StompCommand.SUBSCRIBE == commandType) {
            // sub시 처리할 작업 작성
        }

        return message;
    }
}
