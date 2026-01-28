package com.aditi_midterm.financemanager.transaction;

import com.aditi_midterm.financemanager.transaction.dto.TransactionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findTransactionById(Long id);
}
