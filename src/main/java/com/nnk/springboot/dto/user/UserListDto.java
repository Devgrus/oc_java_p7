package com.nnk.springboot.dto.user;

import com.nnk.springboot.domain.Role;
import lombok.*;

@Getter
@Builder
public class UserListDto {
    private Integer id;
    private String username;
    private String fullname;
    private Role role;
}
