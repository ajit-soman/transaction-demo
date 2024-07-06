package com.transaction.demo.repository;

import com.transaction.demo.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository which handles DB related operations for transaction
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
