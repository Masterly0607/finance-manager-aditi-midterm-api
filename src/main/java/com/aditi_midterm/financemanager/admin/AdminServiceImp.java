package com.aditi_midterm.financemanager.admin;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aditi_midterm.financemanager.admin.dto.AdminResponse;
import com.aditi_midterm.financemanager.admin.dto.UserRequestDto;
import com.aditi_midterm.financemanager.exception.BadRequestException;
import com.aditi_midterm.financemanager.shared.ApiResponse;
import com.aditi_midterm.financemanager.shared.Pagination;
import com.aditi_midterm.financemanager.user.Role;
import com.aditi_midterm.financemanager.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImp implements AdminService {

    private static final Logger LOGGER = Logger.getLogger(AdminServiceImp.class.getName());
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminMapper adminMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Pagination<AdminResponse>> getAllUser(Pageable pageable, String role) {
        Page<User> page;
        if (role == null || role.isEmpty()) {
            // return all USERS
            page = adminRepository.findAll(pageable);
        } else {
            try {
                Role filterRole = Role.valueOf(role.toUpperCase());
                page = adminRepository.getAllUsersWithUserRole(pageable, filterRole);

            } catch (IllegalArgumentException e) {
                page = Page.empty(pageable);
            }
        }

        Pagination<AdminResponse> pagination = new Pagination<>(
                page.getContent()
                        .stream()
                        .map(adminMapper::toAdminResponse)
                        .toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast() ? 1 : 0);

        return ApiResponse.success(pagination, "Users fetches successfully");
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<AdminResponse> toggleUserRole(Long id) {
        // Find user by ID
        User user = adminRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found"));

        // Toggle role
        if (user.getRole() == Role.ADMIN) {
            user.setRole(Role.USER);
        } else {
            user.setRole(Role.ADMIN);
        }
        User updatedUser = adminRepository.save(user);
        AdminResponse response = adminMapper.toAdminResponse(updatedUser);

        return ApiResponse.success(response, "User role toggle successfully");
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<User> createUser(UserRequestDto userRequestDto) {
        // check if email already exists
        Optional<User> user = adminRepository.findByEmail(userRequestDto.email());
        if (user.isPresent()) {
            throw new BadRequestException("Email already existed");
        }
        try {

            String hashPassword = passwordEncoder.encode(userRequestDto.passwordHash());

            User newuser = User.builder()
                .email(userRequestDto.email())
                .passwordHash(hashPassword)
                .role(Role.valueOf(userRequestDto.role().toUpperCase()))
                .isActive(userRequestDto.isActive()  != null ? userRequestDto.isActive() : true)
                .build();

            LOGGER.info(() -> "new user info: "+ newuser);

            adminRepository.save(newuser);
            return ApiResponse.success(newuser, "User created successfully");

        } catch (Exception e) {
            throw new BadRequestException("Internal server error: " + e.getMessage());
        }
    }
}
