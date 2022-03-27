package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    private String role;
}