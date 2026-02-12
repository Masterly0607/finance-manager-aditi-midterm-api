package com.aditi_midterm.financemanager.transaction;

import com.aditi_midterm.financemanager.transaction.dto.TransactionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findTransactionById(Long id);

    @Query("""
       select coalesce(sum(t.amount), 0)
       from Transaction t
       where t.account.user.id = :userId
         and t.type = :type
       """)
    BigDecimal sumAmountByUserAndType(@Param("userId") Long userId, @Param("type") TransactionType type);
}
