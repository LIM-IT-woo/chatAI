package com.study.chatAI; // 🎯 패키지 경로 일치화

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PersistentChatService {

    private final ChatClient.Builder chatClientBuilder;
    private final ChatMemory chatMemory;

    public PersistentChatService(ChatClient.Builder builder,
                                 @Qualifier("jdbcChatMemory") ChatMemory chatMemory) {
        this.chatClientBuilder = builder;
        this.chatMemory = chatMemory;
    }

    public String chat(String question, String conversationID, String customPrompt) {
        // 🎭 방 ID에 맞춰 동적으로 세팅되는 글래스모피즘 멀티 페르소나 세계관
        // 🎭 방 ID에 맞춰 동적으로 세팅되는 멀티 페르소나 세계관
        String systemInstruction;

        // 🎭 방 ID가 커스텀방(persona-custom)이고, 프론트엔드에서 넘겨받은 프롬프트가 있다면 그걸 사용!
        if (conversationID != null && conversationID.startsWith("persona-custom")
                && customPrompt != null && !customPrompt.trim().isEmpty()) {
            systemInstruction = customPrompt;
        } else {
            systemInstruction = switch (conversationID) {
                case "persona-sherlock" -> """
                        당신은 영국의 천재 탐정 '셜록 홈즈'입니다.
                        매우 냉철하고, 논리적이며, 관찰력이 뛰어난 어조를 사용하세요.
                        상대방의 질문을 예리하게 분석하여 답변하고, 간결하게 2~3줄로 말하세요.
                        """;
                case "persona-gordon" -> """
                        당신은 요리계의 거장 '고든 램지'입니다.
                        매우 열정적이고, 유쾌하며, 주방장다운 화끈하고 에너제틱한 말투를 사용하세요.
                        요리에 관련된 비유나 감탄사를 적극 활용하되, 핵심만 2~3줄로 말하세요.
                        """;
                default -> """
                        당신은 사용자의 다정하고 기분 좋은 AI 친구인 '에이'입니다.
                        상대방의 기분에 깊이 공감해주고, 대화 기록을 기반으로 친근하게 답변하세요.
                        답변은 메신저 대화처럼 너무 길지 않게 2~3줄 이내로 하세요.
                        """;
            };
        }

        return chatClientBuilder
                .defaultSystem(systemInstruction)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build()
                .prompt()
                .user(question)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationID))
                .call()
                .content();
    }
}