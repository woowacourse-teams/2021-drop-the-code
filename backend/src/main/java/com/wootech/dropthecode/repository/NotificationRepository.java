package com.wootech.dropthecode.repository;

import com.wootech.dropthecode.domain.Notification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
