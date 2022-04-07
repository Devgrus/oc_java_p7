package com.nnk.springboot.dto.user;

import com.nnk.springboot.domain.Provider;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.User;
import lombok.*;

import javax.validation.constraints.*;

@Data
@Builder
public class UserAddDto {
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

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .fullname(fullname)
                .role(role)
                .provider(Provider.LOCAL)
                .build();
    }
}
