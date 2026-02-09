package com.aditi_midterm.financemanager.seed;

import com.aditi_midterm.financemanager.security.PasswordConfig;
import com.aditi_midterm.financemanager.user.Role;
import com.aditi_midterm.financemanager.user.User;
import com.aditi_midterm.financemanager.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class UserDataLoading implements CommandLineRunner {

  private final UserRepository userRepository;
  private final PasswordConfig passwordConfig;

  @Override
  public void run(String... args) throws Exception {

    if (userRepository.existsByEmail("johndoe@gmail.com")) {
      return;
    }

    User user =
        User.builder()
            .email("johndoe@gmail.com")
            .passwordHash(passwordConfig.passwordEncoder().encode("password"))
            .role(Role.valueOf("ADMIN"))
            .isActive(true)
            .build();
    userRepository.save(user);
  }
}
