package com.transaction.demo.service;

import com.transaction.demo.domain.Transaction;
import com.transaction.demo.enums.OperationsType;
import com.transaction.demo.exception.ApplicationException;
import com.transaction.demo.repository.AccountRepository;
import com.transaction.demo.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.TransactionSystemException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TransactionService.class })
public class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;

    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private TransactionRepository transactionRepository;

    ///////////////// performTransaction /////////////////////////////////////////////

    @Test
    public void testPerformTransactionForCreditOperationWithInvalidAccount() throws ApplicationException {
        Mockito.when(accountRepository.addAmountToBalance(1L,2.0)).thenReturn(null);

        Assertions.assertThrows(
                TransactionSystemException.class,
                () -> transactionService.performTransaction(1L,2.0, OperationsType.CREDIT_VOUCHER),
                "Failed to add balance to account");
    }

    @Test
    public void testPerformTransactionForCreditOperationPositiveFlow(){
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1L);
        Mockito.when(accountRepository.addAmountToBalance(1L,2.0)).thenReturn(3.0);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.performTransaction(1L,2.0, OperationsType.CREDIT_VOUCHER);

        Assertions.assertEquals(1L, result.getTransactionId());

    }

    @Test
    public void testPerformTransactionForDebitOperationWithLowBalance(){
        Mockito.when(accountRepository.checkBalanceAndDeductAmount(1L,2.0)).thenReturn(null);

        Assertions.assertThrows(
                TransactionSystemException.class,
                () -> transactionService.performTransaction(1L,2.0, OperationsType.NORMAL_PURCHASE),
                "Failed to deduct amount from account");
    }

    @Test
    public void testPerformTransactionForDebitOperationPostiveFlow(){
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1L);

        Mockito.when(accountRepository.checkBalanceAndDeductAmount(1L,2.0)).thenReturn(1.0);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.performTransaction(1L,2.0, OperationsType.NORMAL_PURCHASE);

        Assertions.assertEquals(1L, result.getTransactionId());
    }
}
