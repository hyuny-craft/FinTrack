package com.fintrack.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fintrack.application.TransactionService;
import com.fintrack.application.UserService;
import com.fintrack.domain.model.Transaction;
import com.fintrack.domain.model.TransactionType;
import com.fintrack.domain.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Transaction> record(@RequestParam String email, @RequestParam String bankName, @RequestParam String accountNumber,
                                              @RequestParam BigDecimal amount, @RequestParam TransactionType type, @RequestParam LocalDate date) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(transactionService.record(user, bankName, accountNumber, amount, type, date));
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Transaction>> getAll(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(transactionService.getByUser(user));
    }

    @GetMapping("/{email}/range")
    public ResponseEntity<List<Transaction>> getByPeriod(@PathVariable String email, @RequestParam LocalDate from, @RequestParam LocalDate to) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(transactionService.getByUserAndPeriod(user, from, to));
    }

}
