package com.fintrack.application.sync;

import com.fintrack.api.DatopApiClient;
import com.fintrack.api.DatopResponse;
import com.fintrack.api.DatopTransaction;
import com.fintrack.domain.model.TransferHistory;
import com.fintrack.domain.model.User;
import com.fintrack.domain.repository.TransferHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test") // application-test.yml에서 테스트 API 키 관리
class ExternalTransferSyncServiceTest {

    @Autowired
    private ExternalTransferSyncService service;
    @Autowired
    private TransferHistoryRepository repository;
    @Autowired
    private DatopApiClient datopApiClient;

    @BeforeEach
    void setUp() {
        repository = mock(TransferHistoryRepository.class);
        datopApiClient = mock(DatopApiClient.class);
        service = new ExternalTransferSyncService(repository, datopApiClient);
    }


    @InjectMocks
    private ExternalTransferSyncServiceTest transferHistoryService;

    @Mock
    private TransferHistoryRepository transferHistoryRepository;

    @Test
    void testSyncFromKftc_shouldSaveFetchedTransactions() {
        // given
        User user = new User(); // accountNumber 포함

        DatopTransaction tx1 = new DatopTransaction("20240301", "10000", "900000", "이체");
        DatopTransaction tx2 = new DatopTransaction("20240302", "20000", "880000", "지출");
        DatopResponse response = new DatopResponse("A0000", "OK", List.of(tx1, tx2));

        when(datopApiClient.getTransactionHistory(any())).thenReturn(response);

        // when
        service.syncFromKftc(user);

        // then
        verify(repository, times(2)).save(any(TransferHistory.class));
    }

    @Test
    void testSyncFromKftc_shouldThrowWhenApiFails() {
        // given
        User user = new User();
        DatopResponse errorResponse = new DatopResponse("E9999", "오류", List.of());

        when(datopApiClient.getTransactionHistory(any())).thenReturn(errorResponse);

        // then
        assertThrows(RuntimeException.class, () -> service.syncFromKftc(user));
    }
}