package com.study.chatAI;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AiController {

    // 🎯 사진에 찍힌 실제 클래스명과 100% 일치하도록 매핑 수정 완료!
    private final ChatAIService chatAIService;
    private final PersistentChatService persistentChatService;
    private final CustomCharacterRepository customCharacterRepository;

    // 🔗 생성자 파라미터 이름과 타입을 프로젝트에 존재하는 실제 파일들과 완벽히 일치시켰습니다.
    public AiController(ChatAIService chatAIService,
                        PersistentChatService persistentChatService,
                        CustomCharacterRepository customCharacterRepository) {
        this.chatAIService = chatAIService;
        this.persistentChatService = persistentChatService;
        this.customCharacterRepository = customCharacterRepository;
    }

    // 💾 1. 새로운 커스텀 캐릭터를 H2 DB에 저장하는 API
    @PostMapping("/api/characters")
    public String saveCharacter(@RequestBody CustomCharacter character) {
        customCharacterRepository.save(character);
        return "OK";
    }

    // 📋 2. 저장된 모든 커스텀 캐릭터 목록을 프론트엔드로 반환하는 API
    @GetMapping("/api/characters")
    public List<CustomCharacter> getCharacters() {
        return customCharacterRepository.findAll();
    }

    // 💬 3. 기존 대화 API
    @GetMapping("/api/chat-persistent")
    public String chatPersistent(
            @RequestParam("conversationId") String conversationId,
            @RequestParam("question") String question,
            @RequestParam(value = "customPrompt", required = false, defaultValue = "") String customPrompt) {

        // 🎯 타입과 변수명을 완벽히 일치시켜 호출합니다.
        return persistentChatService.chat(question, conversationId, customPrompt);
    }
}