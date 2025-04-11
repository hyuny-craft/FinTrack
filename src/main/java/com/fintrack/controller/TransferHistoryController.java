package com.fintrack.controller;

import com.fintrack.application.sync.ExternalTransferSyncService;
import com.fintrack.domain.model.User;
import com.fintrack.dto.TransferHistoryRequest;
import com.fintrack.dto.TransferHistoryResponse;
import com.fintrack.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransferHistoryController {
    private final ExternalTransferSyncService transferHistoryService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestParam String email,
                                     @RequestBody @Valid TransferHistoryRequest request) {
        User user = userService.findByEmail(email);
        transferHistoryService.save(user, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{email}")
    public ResponseEntity<List<TransferHistoryResponse>> getAll(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(transferHistoryService.getAll(user));
    }
}
