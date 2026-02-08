package com.aditi_midterm.financemanager.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    boolean existsByIdAndUserId(Long id, Long userId);
    List<Account> findByUserId(Long userId);
    Optional<Account> findByName(String bank);
    Optional<Account> findByIdAndUserId(Long id, Long userId);
}
