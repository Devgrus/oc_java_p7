package com.nnk.springboot.dto.user;

import com.nnk.springboot.domain.Provider;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.User;
import lombok.*;
import org.springframework.security.core.parameters.P;

import javax.validation.constraints.*;

@Data
@Builder
public class UserAddDto {
    @NotBlank(message = "Username is mandatory")
    @Size(max = 125, message = "125 characters maximum")
    @Pattern(regexp = "^(?!GITHUB_|LOCAL_).*", message = "Forbidden value included")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(max = 125, message = "125 characters maximum")
    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9]).+", message = "Password must contain one digit."),
            @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must contain one lowercase letter."),
            @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must contain one upper letter."),
            @Pattern(regexp = "(?=^.{8,}$).+", message = "Password must contain 8 characters."),
            @Pattern(regexp = "(?=.*[!@#$%^&*+=?-_()/\"\\.,<>~`;:]).+", message ="Password must contain one special character."),
            @Pattern(regexp = "(?=\\S+$).+", message = "Password must contain no whitespace.")
    })
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
