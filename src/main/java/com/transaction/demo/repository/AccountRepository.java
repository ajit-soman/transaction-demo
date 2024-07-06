package com.transaction.demo.repository;

import com.transaction.demo.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository which handles DB related operations for account
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * Atomically check balance & deduct amount if there is sufficient balance in account
     *
     * @param accountId account ID
     * @param amount    amount to deduct
     * @return updated balance
     */
    @Query(value = "UPDATE account SET balance = round((balance - :amount)::numeric, 2) WHERE account_id =:accountId AND balance >=:amount RETURNING balance", nativeQuery = true)
    Double checkBalanceAndDeductAmount(@Param("accountId") Long accountId, @Param("amount") Double amount);

    /**
     * Atomically add amount to account
     *
     * @param accountId account ID
     * @param amount    amount to credit
     * @return updated balance
     */
    @Query(value = "UPDATE account SET balance = round((balance + :amount)::numeric, 2) WHERE account_id =:accountId RETURNING balance", nativeQuery = true)
    Double addAmountToBalance(@Param("accountId") Long accountId, @Param("amount") Double amount);
}
