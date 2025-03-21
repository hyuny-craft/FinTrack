package side.fintrack.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import side.fintrack.application.NotificationService;
import side.fintrack.application.UserService;
import side.fintrack.domain.model.Notification;
import side.fintrack.domain.model.User;

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
    public ResponseEntity<List<Notification>> getAll(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(notificationService.getByUser(user));
    }

}
