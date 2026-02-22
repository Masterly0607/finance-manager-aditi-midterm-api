package com.aditi_midterm.financemanager.admin;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aditi_midterm.financemanager.user.Role;
import com.aditi_midterm.financemanager.user.User;
import java.util.List;
import java.util.Optional;



@Repository
public interface AdminRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.role = :role")
    Page<User> getAllUsersWithUserRole(Pageable pageable , Role role);

    @Query("SELECT u FROM User u")
    Page<User> findAll(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);

}
