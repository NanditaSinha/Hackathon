package com.onlinebanking.hackathon.Controller;

import com.onlinebanking.hackathon.Entity.Transaction;
import com.onlinebanking.hackathon.Service.TransactionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transferFunds(@RequestBody TransferRequest request) {
        transactionService.transferFunds(request.getFromAccountId(), request.getToAccountId(), request.getAmount());
        return ResponseEntity.ok().body("Transfer successful");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionDetails(@PathVariable Long id) {
        return transactionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

@Data
class TransferRequest {
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
}
