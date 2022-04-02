package com.nnk.springboot.domain;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;
}
