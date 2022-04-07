package com.nnk.springboot.dto.user;

import com.nnk.springboot.domain.Role;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserUpdateDto {
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
}
