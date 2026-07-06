package com.study.chatAI;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomCharacterRepository extends JpaRepository<CustomCharacter, String> {
}