package side.fintrack.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import side.fintrack.domain.model.Transaction;
import side.fintrack.domain.model.TransactionType;
import side.fintrack.domain.model.User;
import side.fintrack.domain.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Transactional
    public Transaction record(User user, String bankName, String accountNumber, BigDecimal amount, TransactionType type, LocalDate date) {
        Transaction tx = new Transaction(user, bankName, accountNumber, amount, type, date);
        return transactionRepository.save(tx);
    }

    public List<Transaction> getByUser(User user) {
        return transactionRepository.findAllByUser(user);
    }

    public List<Transaction> getByUserAndPeriod(User user, LocalDate from, LocalDate to) {
        return transactionRepository.findByUserAndDateBetween(user, from, to);
    }
}
