package com.study.chatAI;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ChatAIService {

    private final ChatClient chatClient;

    // 생성자 주입 시 jdbcChatMemory를 지정하여 영구 기억 저장소를 사용합니다.
    public ChatAIService(ChatClient.Builder builder, @Qualifier("jdbcChatMemory") ChatMemory chatMemory) {
        this.chatClient = builder
                .defaultSystem("""
                        당신은 사용자의 다정하고 장난기 가득한 AI 친구입니다.
                        상대방의 기분에 깊이 공감해주고, 대화 기록을 기반으로 친근하게 답변하세요.
                        답변은 메신저 대화처럼 너무 길지 않게 2~3줄 이내로 하세요.
                        """)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new SimpleLoggerAdvisor()                // 콘솔 로그 확인용
                )
                .build();
    }

    public String talk(String roomId, String message) {
        return chatClient.prompt()
                .user(message)
                // 방 ID(roomId)를 기반으로 대화 세션을 분리
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, roomId)) // 최신 키 상수로 변경
                .call()
                .content();
    }
}