package com.transaction.demo.domain;

import com.transaction.demo.enums.OperationsType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Transaction domain class
 */
@Entity
@Getter
@Setter
@ApiModel("Transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @NotNull
    private Account account;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OperationsType operationsType;

    @NotNull
    private Double amount;

    @NotNull
    private Double closingBalance;

    @NotNull
    private Date eventDate = new Date();


}
