package com.transaction.demo.repository;

import com.transaction.demo.domain.Account;
import com.transaction.demo.domain.Transaction;
import com.transaction.demo.enums.OperationsType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionRepositoryITTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    ///////////////// save /////////////////////////////////////////////

    @Test
    void testSave(){
        Account account = new Account();
        account.setDocumentNumber(777L);
        Account savedAccount = accountRepository.save(account);

        Assertions.assertNotNull(savedAccount.getAccountId());

        Transaction transaction = new Transaction();
        transaction.setOperationsType(OperationsType.CREDIT_VOUCHER);
        transaction.setAmount(2.0);
        transaction.setClosingBalance(2.0);
        transaction.setAccount(savedAccount);

        Transaction savedTransaction = transactionRepository.save(transaction);
        Assertions.assertNotNull(savedTransaction.getTransactionId());
    }
}
