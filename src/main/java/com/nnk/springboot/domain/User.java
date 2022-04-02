package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Username is mandatory")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    @NotNull(message = "Role is mandatory")
    @Enumerated(EnumType.STRING)
    private Role role;
}
