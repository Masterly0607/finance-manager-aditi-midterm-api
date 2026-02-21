package com.aditi_midterm.financemanager.admin;


import org.springframework.data.domain.Pageable;

import com.aditi_midterm.financemanager.admin.dto.AdminResponse;
import com.aditi_midterm.financemanager.admin.dto.UserRequestDto;
import com.aditi_midterm.financemanager.shared.ApiResponse;
import com.aditi_midterm.financemanager.shared.Pagination;
import com.aditi_midterm.financemanager.user.User;

public interface AdminService {

    ApiResponse<User> createUser(UserRequestDto userRequestDto);

    // List<User> getAllUsers();
    ApiResponse<Pagination<AdminResponse>> getAllUser(Pageable pageable, String role);

    ApiResponse<AdminResponse> toggleUserRole(Long id);

}
