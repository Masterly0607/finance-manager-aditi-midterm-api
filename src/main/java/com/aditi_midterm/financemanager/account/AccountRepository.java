package com.aditi_midterm.financemanager.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  boolean existsByName(String name);

  Optional<Account> findByName(String bank);
}
