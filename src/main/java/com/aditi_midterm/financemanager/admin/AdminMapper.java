package com.aditi_midterm.financemanager.admin;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aditi_midterm.financemanager.admin.dto.AdminResponse;
import com.aditi_midterm.financemanager.admin.dto.UserRequestDto;
import com.aditi_midterm.financemanager.user.User;

@Mapper(componentModel = "spring"
)
public interface AdminMapper {
    //Entity -> Response Dto

    AdminResponse toAdminResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User  toUser(UserRequestDto userRequestDto);


}
