package com.aditi_midterm.financemanager.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findTransactionById(Long id, Long userId);
    Optional<Transaction> findByIdAndAccountUserId(Long id, Long userId);
    Page<Transaction> findByAccountUserId(Long userId, Pageable pageable);
    Page<Transaction> findByAccountUserIdAndAccountId(
            Long userId,
            Long accountId,
            Pageable pageable);

    Page<Transaction> findByAccountUserIdAndType(
            Long userId,
            TransactionType type,
            Pageable pageable);
    @Query("""
       select coalesce(sum(t.amount), 0)
       from Transaction t
       where t.account.user.id = :userId
         and t.type = :type
       """)
    BigDecimal sumAmountByUserAndType(@Param("userId") Long userId, @Param("type") TransactionType type);

    @Query("""
        SELECT t FROM Transaction t
        WHERE t.account.user.id = :userId
        AND (:accountId IS NULL OR t.account.id = :accountId)
        AND (:type IS NULL OR t.type = :type)
        AND (:search IS NULL OR LOWER(t.note) LIKE LOWER(CONCAT('%', :search, '%')))
    """)
    Page<Transaction> findWithFilters(
            @Param("userId") Long userId,
            @Param("accountId") Long accountId,
            @Param("type") TransactionType type,
            @Param("search") String search,
            Pageable pageable
    );
}
