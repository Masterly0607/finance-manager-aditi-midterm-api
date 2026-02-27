package com.aditi_midterm.financemanager.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Note: The main goal of a repository is to handle database access and hide database logic from the
// rest of the application.(Repository = communication with the database)
public interface UserRepository extends JpaRepository<User, Long> {
  // Optional = It can be: empty (no value) or contain one value to prevent null.
  // <User> = specifies the type of object that the Optional can contain.
  // <> = Generics are used to specify the type of object a class, method, or container works with.
  // Note: findByEmail() and existsByEmail are Derived Query Methods which created from method
  // names.(Ex: find = select,Email = column email)
  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);
}
