package com.wootech.dropthecode.repository;

import com.wootech.dropthecode.domain.chatting.Chat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
