package com.nnk.springboot.dto.ruleName;

import lombok.*;

@Getter
@Builder
public class RuleNameListDto {
    private Integer id;
    private String name;
    private String description;
    private String json;
    private String template;
    private String sqlStr;
    private String sqlPart;
}
