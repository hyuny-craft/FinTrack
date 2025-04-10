package com.fintrack.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fintrack.domain.model.Notification;
import com.fintrack.domain.model.User;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUser(User user);
}
