package com.transaction.demo.service;

import com.transaction.demo.domain.Account;
import com.transaction.demo.domain.Transaction;
import com.transaction.demo.enums.OperationsType;
import com.transaction.demo.exception.ApplicationException;
import com.transaction.demo.repository.AccountRepository;
import com.transaction.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service will have business logic for transaction related operations
 */
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Performs transaction
     * <br/>
     * This method evaluate kind of transaction based on operation type
     * and does credit/debit operation on a given account ID and amount
     *
     * @param accountId      existing account ID
     * @param amount         amount
     * @param operationsType Operation type
     * @return transaction details
     * @throws ApplicationException       if operation type is invalid
     * @throws TransactionSystemException if in case amount is failed to
     *                                    deduct/credit from account
     */
    @Transactional
    public Transaction performTransaction(Long accountId, Double amount, OperationsType operationsType) throws TransactionSystemException {
        boolean isCreditOperation = switch (operationsType) {
            case WITHDRAWAL, NORMAL_PURCHASE, PURCHASE_WITH_INSTALLMENTS -> false;
            case CREDIT_VOUCHER -> true;
        };

        if (isCreditOperation) {
            return creditToAccountAndCreateTransaction(accountId, amount, operationsType);
        }
        // deduct operation
        return deductFromAccountAndCreateTransaction(accountId, amount, operationsType);
    }

    /**
     * Adds amount to account and creates transaction entry
     *
     * @param accountId      account ID
     * @param amount         amount
     * @param operationsType operation Type
     * @return transaction details
     * @throws TransactionSystemException if in case failure happen during addition of amount to account
     */
    private Transaction creditToAccountAndCreateTransaction(Long accountId, Double amount, OperationsType operationsType) throws TransactionSystemException {
        Double updatedBalance = accountRepository.addAmountToBalance(accountId, amount);
        if (updatedBalance == null) {
            throw new TransactionSystemException("Failed to add balance to account");
        }
        Transaction transaction = buildTransaction(accountId, amount, updatedBalance, operationsType);
        return transactionRepository.save(transaction);
    }

    /**
     * Check if account has sufficient balance and then deduct amount & creates a transaction entry
     *
     * @param accountId      account ID
     * @param amount         amount
     * @param operationsType operation Type
     * @return transaction details
     * @throws TransactionSystemException if amount failed to deduct from account
     */
    private Transaction deductFromAccountAndCreateTransaction(Long accountId, Double amount, OperationsType operationsType) throws TransactionSystemException {
        Double updatedBalance = accountRepository.checkBalanceAndDeductAmount(accountId, amount);
        if (updatedBalance == null) {
            throw new TransactionSystemException("Failed to deduct amount from account");
        }
        double negativeAmount = amount * -1;
        Transaction transaction = buildTransaction(accountId, negativeAmount, updatedBalance, operationsType);
        return transactionRepository.save(transaction);
    }

    /**
     * Builder for transaction object with given parameters
     *
     * @param accountId      account ID
     * @param amount         amount
     * @param updatedBalance balance after credit/debit operation
     * @param operationsType operation Type
     * @return transaction object
     */
    private Transaction buildTransaction(Long accountId, Double amount, Double updatedBalance, OperationsType operationsType) {
        Account account = new Account();
        account.setAccountId(accountId);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setClosingBalance(updatedBalance);
        transaction.setOperationsType(operationsType);
        return transaction;
    }
}
