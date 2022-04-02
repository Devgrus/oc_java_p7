package com.nnk.springboot.dto.ruleName;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class RuleNameUpdateDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 125, message = "125 characters maximum")
    private String name;

    @Size(max = 125, message = "125 characters maximum")
    private String description;

    @Size(max = 125, message = "125 characters maximum")
    private String json;

    @Size(max = 512, message = "512 characters maximum")
    private String template;

    @Size(max = 125, message = "125 characters maximum")
    private String sqlStr;

    @Size(max = 125, message = "125 characters maximum")
    private String sqlPart;
}
