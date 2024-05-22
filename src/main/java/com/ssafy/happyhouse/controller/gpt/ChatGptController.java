package com.ssafy.happyhouse.controller.gpt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.happyhouse.dto.gpt.ChatGptDto;
import com.ssafy.happyhouse.service.gpt.ChatGptService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Locale;

@Slf4j
@RequestMapping("/chatGpt")
@RestController
@RequiredArgsConstructor
public class ChatGptController {

    private final ChatGptService chatGptService;

    @PostMapping(value="/askStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> ask(Locale locale,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestBody ChatGptDto.Question questionRequest){
        try {

            return chatGptService.ask(questionRequest);

        }catch (JsonProcessingException je){

            log.error(je.getMessage());
            return Flux.empty();
        }
    }
}
