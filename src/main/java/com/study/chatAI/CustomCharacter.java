package com.study.chatAI;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CustomCharacter {

    @Id
    private String roomId;

    private String name;
    private String description;

    // 🎯 프론트엔드가 보내는 JSON Key(systemPrompt)와 100% 일치화
    @Lob
    @Column(columnDefinition = "CLOB")
    private String systemPrompt;

    // 🎯 프론트엔드가 보내는 JSON Key(avatarBase64)와 100% 일치화
    @Lob
    @Column(columnDefinition = "CLOB")
    private String avatarBase64;

    public CustomCharacter(String roomId, String name, String description, String systemPrompt, String avatarBase64) {
        this.roomId = roomId;
        this.name = name;
        this.description = description;
        this.systemPrompt = systemPrompt;
        this.avatarBase64 = avatarBase64;
    }
}