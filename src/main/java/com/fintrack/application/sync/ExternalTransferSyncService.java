package com.fintrack.application.sync;

import com.fintrack.api.DatopApiClient;
import com.fintrack.api.DatopRequest;
import com.fintrack.api.DatopResponse;
import com.fintrack.api.DatopTransaction;
import com.fintrack.domain.model.TransferHistory;
import com.fintrack.domain.model.TransferType;
import com.fintrack.domain.model.User;
import com.fintrack.domain.repository.TransferHistoryRepository;
import com.fintrack.dto.TransferHistoryRequest;
import com.fintrack.dto.TransferHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExternalTransferSyncService {
    private final TransferHistoryRepository repository;
    private final DatopApiClient datopApiClient;

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

    public void syncFromKftc(User user) {
        // 요청 객체 생성
        DatopRequest request = new DatopRequest(
                "020",                             // orgCode (예: 우리은행)
                "154156456465",           // 사용자 계좌번호
                "20240301", "20240331",            // 임시로 고정
                "ALL"
        );

        // 외부 API 호출
        DatopResponse response = datopApiClient.getTransactionHistory(request);

        // 실패 시 예외 발생
        if (!"A0000".equals(response.rspCode())) {
            throw new RuntimeException("KFTC 연동 실패: " + response.rspMessage());
        }

        // 결과 저장
        for (DatopTransaction dto : response.data()) {
            save(user, new TransferHistoryRequest(
                    new BigDecimal(dto.amount()),
                    LocalDate.now(),
                    TransferType.WITHDRAWAL,        // 타입 매핑 로직 필요
                    "KFTC",            // 은행명 (임시)
                    "agasdgasdgsdg"    // 계좌 정보
            ));
        }
    }

}
