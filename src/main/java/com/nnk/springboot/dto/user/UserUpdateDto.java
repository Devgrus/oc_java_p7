package com.nnk.springboot.dto.user;

import com.nnk.springboot.domain.Role;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserUpdateDto {
    @NotBlank(message = "Username is mandatory")
    @Size(max = 125, message = "125 characters maximum")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(max = 125, message = "125 characters maximum")
    private String password;

    @NotBlank(message = "FullName is mandatory")
    @Size(max = 125, message = "125 characters maximum")
    private String fullname;

    @NotNull(message = "Role is mandatory")
    private Role role;
}
