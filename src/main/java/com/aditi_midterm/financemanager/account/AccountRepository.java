package com.aditi_midterm.financemanager.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    boolean existsByIdAndUserId(Long id, Long userId);
    List<Account> findAllByUserId(Long userId);
    Optional<Account> findByName(String bank);
    Optional<Account> findByIdAndUserId(Long id, Long userId);
    @Query("select coalesce(sum(a.balance), 0) from Account a where a.user.id = :userId")
    BigDecimal sumBalanceByUserId(@Param("userId") Long userId);
}
