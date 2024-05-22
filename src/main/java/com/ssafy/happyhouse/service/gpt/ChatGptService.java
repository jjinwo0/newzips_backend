package com.ssafy.happyhouse.service.gpt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.ssafy.happyhouse.dto.gpt.ChatGptDto;
import com.ssafy.happyhouse.global.config.ChatGptConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatGptService {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE );

    public Flux<String> ask(ChatGptDto.Question questionRequest) throws JsonProcessingException {

        WebClient client = WebClient.builder()
                .baseUrl(ChatGptConfig.CHAT_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(ChatGptConfig.AUTHORIZATION,ChatGptConfig.BEARER + apiKey)
                .build();

        List<ChatGptDto.ChatGptMessage> messages = new ArrayList<>();

        messages.add(ChatGptDto.ChatGptMessage.builder()
                .role(ChatGptConfig.ROLE)
                .content(questionRequest.getMessage())
                .build());

        ChatGptDto.Request chatGptRequest = new ChatGptDto.Request(
                ChatGptConfig.CHAT_MODEL,
                ChatGptConfig.MAX_TOKEN,
                ChatGptConfig.TEMPERATURE,
                ChatGptConfig.STREAM,
                messages
        );

        String requestValue = objectMapper.writeValueAsString(chatGptRequest);

        Flux<String> eventStream = client.post()
                .bodyValue(requestValue)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class);
        return eventStream;
    }
}
