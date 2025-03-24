package com.fintrack.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fintrack.domain.model.Notification;
import com.fintrack.domain.model.User;
import com.fintrack.domain.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Transactional
    public Notification send(User user, String message) {
        Notification notification = new Notification(user, message, LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    public List<Notification> getByUser(User user) {
        return notificationRepository.findAllByUser(user);
    }
}
