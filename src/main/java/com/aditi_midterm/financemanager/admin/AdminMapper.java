package com.aditi_midterm.financemanager.admin;

import org.mapstruct.Mapper;

import com.aditi_midterm.financemanager.admin.dto.AdminResponse;
import com.aditi_midterm.financemanager.user.User;

@Mapper(componentModel = "spring"
)
public interface AdminMapper {
    //Entity -> Response Dto

    AdminResponse toAdminResponse(User user);



}
