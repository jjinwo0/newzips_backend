package com.ssafy.happyhouse.dto.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ChatGptDto {

    /**
     * Client가 입력하는 질문에 대한 DTO
     */
    @Data
    public static class Question {
        private String message;
    }

    /**
     * DTO에 담고 전송할 실질적 검색 데이터
     */
    @Data
    @NoArgsConstructor
    public static class ChatGptMessage {

        private String role;

        private String content;

        @Builder
        public ChatGptMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

    /**
     * GPT에 요청할 DTO
     */
    @Data
    @NoArgsConstructor
    public static class Request{

        private String model;

        @JsonProperty("max_tokens")
        private int maxTokens;

        private Double temperature;

        private Boolean stream;

        private List<ChatGptMessage> messages;

        @Builder
        public Request(String model, int maxTokens, Double temperature, Boolean stream, List<ChatGptMessage> messages) {
            this.model = model;
            this.maxTokens = maxTokens;
            this.temperature = temperature;
            this.stream = stream;
            this.messages = messages;
        }
    }

    @Data
    public static class Response {

        private String id;

        private String object;

        private long created;

        private String model;

        private Usage usage;

        private List<Choice> choices;

    }

    @Data
    public static class Usage {
        @JsonProperty("prompt_tokens")
        private int promptTokens;
        @JsonProperty("completion_tokens")
        private int completionTokens;
        @JsonProperty("total_tokens")
        private int totalTokens;
    }

    @Data
    public static class Choice {
        private ChatGptMessage message;

        @JsonProperty("finish_reason")
        private String finishReason;

        private int index;
    }
}
