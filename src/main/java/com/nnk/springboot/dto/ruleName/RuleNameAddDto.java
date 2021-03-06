package com.nnk.springboot.dto.ruleName;

import com.nnk.springboot.domain.RuleName;

import lombok.*;
import javax.validation.constraints.*;

@Data
@Builder
public class RuleNameAddDto {
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

    public RuleName toEntity() {
        return RuleName.builder()
                .name(name)
                .description(description)
                .json(json)
                .template(template)
                .sqlStr(sqlStr)
                .sqlPart(sqlPart)
                .build();
    }
}
