package com.wootech.dropthecode.repository;

import java.util.List;

import com.wootech.dropthecode.domain.Notification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByReceiverId(Long id);
}
