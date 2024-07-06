package com.transaction.demo.controller;

import com.transaction.demo.domain.Account;
import com.transaction.demo.dto.AccountDTO;
import com.transaction.demo.response.Response;
import com.transaction.demo.service.AccountService;
import com.transaction.demo.util.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controllers that has endpoint for account related operations
 */
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    /**
     * creates account using document number
     *
     * @param accountDTO account data transfer object (DTO)
     * @return ResponseEntity which will have newly created account ID
     */
    @Operation(summary = "Create Account", description = "Creates account using document number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Failed to create account")
    })
    @PostMapping
    ResponseEntity<Response<Long>> createAccount(@RequestBody @Valid AccountDTO accountDTO) {
        try {
            Long accountId = accountService.saveAccount(accountDTO.getDocumentNumber());
            return ResponseHandler.generateSuccess("Account created successfully", accountId);

        } catch (DataIntegrityViolationException ex) {
            LOGGER.error("Got data integrity exception while creating account", ex);
            return ResponseHandler.generateError("Document number already exists", null);
        } catch (Exception ex) {
            LOGGER.error("Got exception while creating account", ex);
            return ResponseHandler.generateError("Failed to create account due to " + ex.getMessage(), null);
        }

    }

    /**
     * Get account details by account ID
     *
     * @param accountId account ID
     * @return ResponseEntity which has account details
     */
    @Operation(summary = "Get Account", description = "Get account details by account ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved account"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })

    @GetMapping("/{accountId}")
    ResponseEntity<Response<Account>> getAccount(@PathVariable @Positive Long accountId) {
        Account existingAccountDetails = accountService.getAccount(accountId);
        if (existingAccountDetails == null) {
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, "Account not found", null);
        }
        return ResponseHandler.generateSuccess("Account details", existingAccountDetails);
    }

}
