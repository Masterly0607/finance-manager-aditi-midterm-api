package com.aditi_midterm.financemanager.transfer;

import com.aditi_midterm.financemanager.transfer.dto.TransferRequest;
import com.aditi_midterm.financemanager.transfer.dto.TransferResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransferController {

  private final TransferService transferService;

  @PostMapping
  public ResponseEntity<TransferResponse> transfer(
      @Valid @RequestBody TransferRequest transferRequest) {

    TransferResponse transferResponse = transferService.transfer(transferRequest);
    return ResponseEntity.ok(transferResponse);
  }
}
