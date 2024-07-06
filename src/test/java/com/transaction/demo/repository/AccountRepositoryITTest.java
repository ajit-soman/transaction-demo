package com.transaction.demo.repository;

import com.transaction.demo.domain.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryITTest {

    @Autowired
    private AccountRepository accountRepository;

    ///////////////// save /////////////////////////////////////////////

    @Test
    void testSave(){
        Account account = new Account();
        account.setDocumentNumber(777L);
        Account savedAccount = accountRepository.save(account);
        Assertions.assertNotNull(savedAccount.getAccountId());
    }

    ///////////////// checkBalanceAndDeductAmount /////////////////////////////////////////////

    @Test
    void testCheckBalanceAndDeductAmountWithInsufficientBalance(){
        Account account = new Account();
        account.setDocumentNumber(777L);
        account.setBalance(2.0);
        Account savedAccount = accountRepository.save(account);

        Double deductedAmount = accountRepository.checkBalanceAndDeductAmount(savedAccount.getAccountId(), 5.0);
        Assertions.assertNull(deductedAmount);
    }

    @Test
    void testCheckBalanceAndDeductAmountWithPositiveFlow(){
        Account account = new Account();
        account.setDocumentNumber(777L);
        account.setBalance(3.0);
        Account savedAccount = accountRepository.save(account);

        Double deductedAmount = accountRepository.checkBalanceAndDeductAmount(savedAccount.getAccountId(), 1.0);
        Assertions.assertEquals(2.0, deductedAmount);
    }

    ///////////////// addAmountToBalance /////////////////////////////////////////////

    @Test
    void testAddAmountToBalance(){
        Account account = new Account();
        account.setDocumentNumber(777L);
        account.setBalance(3.0);
        Account savedAccount = accountRepository.save(account);
        Double deductedAmount = accountRepository.addAmountToBalance(savedAccount.getAccountId(), 1.0);
        Assertions.assertEquals(4.0, deductedAmount);
    }


}
