package com.transaction.demo.dto;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object (DTO) for Account with relevant fields
 * required in endpoint
 */
@Getter
@Setter
public class AccountDTO {
    @NotNull
    @Positive
    private Long documentNumber;
}
