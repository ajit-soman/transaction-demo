package com.transaction.demo.service;

import com.transaction.demo.domain.Account;
import com.transaction.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * This service will have business logic for account related operations
 */
@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Creates Account using document number . if same document number is present
     * then DataIntegrityViolationException is thrown
     *
     * @param documentNumber document number
     * @return accountId newly generated account ID
     */
    public Long saveAccount(Long documentNumber) throws DataIntegrityViolationException {
        Account account = new Account(documentNumber);
        Account savedAccount = accountRepository.save(account);
        return savedAccount.getAccountId();
    }

    /**
     * Gets account using account ID
     *
     * @param accountId existing account ID
     * @return accountId if found else return null
     */
    public Account getAccount(Long accountId) {
        return accountRepository
                .findById(accountId)
                .orElse(null);
    }

}
