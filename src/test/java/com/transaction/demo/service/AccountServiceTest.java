package com.transaction.demo.service;

import com.transaction.demo.domain.Account;
import com.transaction.demo.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AccountService.class })
public class AccountServiceTest {

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    ///////////////// saveAccount /////////////////////////////////////////////

    @Test
    public void testSaveAccount(){
        Account account = new Account();
        account.setAccountId(1L);
        account.setDocumentNumber(123L);
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);

        Long accountId = accountService.saveAccount(123L);

        Assertions.assertEquals(1L, accountId);
    }

    ///////////////// getAccount /////////////////////////////////////////////

    @Test
    public void testGetAccount(){
        Account account = new Account();
        account.setAccountId(1L);
        account.setDocumentNumber(123L);
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Account existingAccount = accountService.getAccount(1L);

        Assertions.assertEquals(1L, existingAccount.getAccountId());
    }

}
