package com.fintrack.adapter;

import com.fintrack.application.NotificationService;
import com.fintrack.application.UserService;
import com.fintrack.domain.model.Notification;
import com.fintrack.domain.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Notification> send(@RequestParam String email, @RequestParam String message) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(notificationService.send(user, message));
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Notification>> getAll(@PathVariable
                                                     @Schema(description = "이메일", example = "user@fintrack.com")
                                                     String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(notificationService.getByUser(user));
    }

}
