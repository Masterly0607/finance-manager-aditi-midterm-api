package com.aditi_midterm.financemanager.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aditi_midterm.financemanager.admin.dto.AdminResponse;
import com.aditi_midterm.financemanager.admin.dto.UserRequestDto;
import com.aditi_midterm.financemanager.shared.ApiResponse;
import com.aditi_midterm.financemanager.shared.Pagination;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(AdminController.BASE_URL)
public class AdminController {

    public static final String BASE_URL = "/api/admin";
    private final AdminService adminService;

    // Create a new user
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<?>> createUser(
            @Valid @RequestBody UserRequestDto userRequestDto) {
        ApiResponse<?> response = adminService.createUser(userRequestDto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<Pagination<AdminResponse>>> getAllUsers(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String role,
            HttpServletResponse response) {
        System.out.println("filter user role: " + role);
        ApiResponse<Pagination<AdminResponse>> responses = adminService.getAllUser(pageable, role);
        return ResponseEntity.ok(responses);
    }

    // ======================= Delete User =======================
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<?>> deleteUser(
            @PathVariable Long id) {
        ApiResponse<?> response = adminService.deleteUser(id);
        return ResponseEntity.ok(response);
        // return ResponseEntity.ok(ApiResponse.success(null, "User deleted successfully"));
    }

    // @PatchMapping("/user/{id}")
    // public String getID(@PathVariable Long id){
    // return "the id that would return is : "+ id;
    // }

    // ======================= Toggle User Role =======================
    @PatchMapping("/users/{id}/role")
    public ResponseEntity<ApiResponse<AdminResponse>> toggleUserRole(
            @PathVariable Long id) {
        ApiResponse<AdminResponse> toggleUser = adminService.toggleUserRole(id);
        return ResponseEntity.ok(toggleUser);
    }

    // ======================= Toggle User Status =======================
    @PatchMapping("/users/{id}/status")
    public ResponseEntity<ApiResponse<AdminResponse>> toggleUserStatus(
            @PathVariable Long id) {
        ApiResponse<AdminResponse> toggleUser = adminService.toggleUserStatus(id);
        return ResponseEntity.ok(toggleUser);
    }

}
