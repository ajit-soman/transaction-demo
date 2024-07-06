package com.transaction.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Account domain class
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @NotNull
    private Double balance = 0.0;

    @Column(unique = true)
    @NotNull
    private Long documentNumber;

    public Account(Long documentNumber){
        this.documentNumber = documentNumber;
    }

}
