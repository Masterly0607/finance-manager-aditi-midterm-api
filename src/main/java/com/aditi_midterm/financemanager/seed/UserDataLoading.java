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

        // Define all users
        User[] users = new User[] {
                User.builder()
                        .email("user1@gmail.com")
                        .passwordHash(passwordConfig.passwordEncoder().encode("password1"))
                        .role(Role.USER)
                        .isActive(true)
                        .build(),

                User.builder()
                        .email("user2@gmail.com")
                        .passwordHash(passwordConfig.passwordEncoder().encode("password2"))
                        .role(Role.USER)
                        .isActive(true)
                        .build(),

                User.builder()
                        .email("user3@gmail.com")
                        .passwordHash(passwordConfig.passwordEncoder().encode("password3"))
                        .role(Role.USER)
                        .isActive(true)
                        .build(),

                User.builder()
                        .email("admin1@gmail.com")
                        .passwordHash(passwordConfig.passwordEncoder().encode("adminpass1"))
                        .role(Role.ADMIN)
                        .isActive(true)
                        .build(),

                User.builder()
                        .email("admin2@gmail.com")
                        .passwordHash(passwordConfig.passwordEncoder().encode("adminpass2"))
                        .role(Role.ADMIN)
                        .isActive(true)
                        .build()
        };

        // Save users only if they don't exist
        for (User user : users) {
            if (!userRepository.existsByEmail(user.getEmail())) {
                userRepository.save(user);
            }
        }

        System.out.println("Seeding complete ✅");
    }

}
