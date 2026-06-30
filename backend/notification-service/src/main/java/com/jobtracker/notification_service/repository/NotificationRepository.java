package com.jobtracker.notification_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobtracker.notification_service.model.Notifications;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notifications, Long> {

    List<Notifications> findByUserId(Long userId);

    List<Notifications> findByWasSentFalseAndScheduledForBefore(LocalDateTime dateTime);
}
