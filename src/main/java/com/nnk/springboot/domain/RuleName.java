package com.nnk.springboot.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "RuleName")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Size(max = 125)
    private String name;

    @Size(max = 125)
    private String description;

    @Size(max = 125)
    private String json;

    @Size(max = 512)
    private String template;

    @Size(max = 125)
    private String sqlStr;

    @Size(max = 125)
    private String sqlPart;
}
