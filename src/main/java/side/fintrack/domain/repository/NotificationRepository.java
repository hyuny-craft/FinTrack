package side.fintrack.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import side.fintrack.domain.model.Notification;
import side.fintrack.domain.model.User;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUser(User user);
}
