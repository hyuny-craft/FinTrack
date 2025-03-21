package side.fintrack.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import side.fintrack.domain.model.Transaction;
import side.fintrack.domain.model.User;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUser(User user);

    List<Transaction> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);
}
