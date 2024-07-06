package com.transaction.demo.enums;

import lombok.Getter;

/**
 * Transaction Operation Types with id
 */
@Getter
public enum OperationsType {
    NORMAL_PURCHASE(1),
    PURCHASE_WITH_INSTALLMENTS(2),
    WITHDRAWAL(3),
    CREDIT_VOUCHER(4);
    int id;

    OperationsType(int id) {
        this.id = id;
    }
}
