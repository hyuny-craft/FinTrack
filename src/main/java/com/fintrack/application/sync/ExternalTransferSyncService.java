package com.fintrack.application.sync;

import com.fintrack.domain.model.TransferHistory;
import com.fintrack.domain.model.User;
import com.fintrack.domain.repository.TransferHistoryRepository;
import com.fintrack.dto.TransferHistoryRequest;
import com.fintrack.dto.TransferHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExternalTransferSyncService {
    private final TransferHistoryRepository repository;

    public void save(User user, TransferHistoryRequest request) {
        TransferHistory history = TransferHistory.builder()
                .user(user)
                .amount(request.getAmount())
                .date(request.getDate())
                .type(request.getType())
                .bankName(request.getBankName())
                .accountNumber(request.getAccountNumber())
                .build();
        repository.save(history);
    }

    public List<TransferHistoryResponse> getAll(User user) {
        return repository.findByUser(user).stream()
                .map(t -> new TransferHistoryResponse(
                        t.getId(), t.getAmount(), t.getDate(), t.getType(),
                        t.getBankName(), t.getAccountNumber()
                ))
                .collect(Collectors.toList());
    }
}
