package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.ruleName.RuleNameAddDto;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(RuleNameController.class)
public class RuleNameControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RuleNameService ruleNameService;

    @Test
    public void validateTest() throws Exception {
        //given
        RuleNameAddDto dto = RuleNameAddDto.builder()
                .name("abc").description("abc").build();

        RuleName ruleName = dto.toEntity();

        //when
        when(ruleNameService.save(any())).thenReturn(ruleName);

        //then
        mockMvc.perform(post("/ruleName/validate")
                .param("name", dto.getName())
                .param("description", dto.getDescription()))
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    public void validateTestNameIsNull() throws Exception {
        //given
        RuleNameAddDto dto = RuleNameAddDto.builder()
                .name(null).description("abc").build();

        RuleName ruleName = dto.toEntity();

        //when

        //then
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", dto.getName())
                        .param("description", dto.getDescription()))
                .andExpect(model().attributeErrorCount("ruleNameAddDto", 1));
    }
}
