package com.transaction.demo.dto;

import com.transaction.demo.enums.OperationsType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object (DTO) for Transaction with relevant fields
 * required in endpoint
 */
@Getter
@Setter
public class TransactionDTO {
    @NotNull
    @Positive
    private Long accountId;
    @NotNull
    private OperationsType operationsType;
    @NotNull
    @Positive
    @Digits(integer = 10, fraction = 2)
    private Double amount;

}
