package com.transaction.demo.controller;

import com.transaction.demo.domain.Transaction;
import com.transaction.demo.dto.TransactionDTO;
import com.transaction.demo.response.Response;
import com.transaction.demo.service.TransactionService;
import com.transaction.demo.util.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controllers that handles transaction
 */
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    /**
     * Performs transaction for a given account based on operation Type
     *
     * @param transactionDTO transaction data transfer object (DTO)
     * @return ResponseEntity with transaction ID
     */
    @Operation(summary = "Perform Transaction", description = "Performs transaction for a given account based on operation Type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction successful"),
            @ApiResponse(responseCode = "400", description = "Failure in transaction")
    })
    @PostMapping
    ResponseEntity<Response<Long>> performTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {
        try {
            Transaction transaction = transactionService.performTransaction(transactionDTO.getAccountId(), transactionDTO.getAmount(), transactionDTO.getOperationsType());
            return ResponseHandler.generateSuccess("Transaction created successfully", transaction.getTransactionId());

        } catch (Exception ex) {
            LOGGER.error("failure during transaction", ex);
            return ResponseHandler.generateError(ex.getMessage(), null);
        }
    }
}
