package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.ruleName.RuleNameAddDto;
import com.nnk.springboot.dto.ruleName.RuleNameUpdateDto;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

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
                .name("AA").description("BB").json("{}").sqlStr("SELCT * ...").sqlPart("OO").build();

        RuleName ruleName = dto.toEntity();

        //when
        when(ruleNameService.save(any())).thenReturn(ruleName);

        //then
        mockMvc.perform(post("/ruleName/validate")
                .param("name", dto.getName())
                .param("description", dto.getDescription())
                .param("json", dto.getJson())
                .param("template", dto.getTemplate())
                .param("sqlStr", dto.getSqlStr())
                .param("sqlPart", dto.getSqlPart()))
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    public void validateTestNameIsNull() throws Exception {
        //given
        RuleNameAddDto dto = RuleNameAddDto.builder()
                .name("").description("BB").json("{}").sqlStr("SELCT * ...").sqlPart("OO").build();

        RuleName ruleName = dto.toEntity();

        //when

        //then
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", dto.getName())
                        .param("description", dto.getDescription())
                        .param("json", dto.getJson())
                        .param("template", dto.getTemplate())
                        .param("sqlStr", dto.getSqlStr())
                        .param("sqlPart", dto.getSqlPart()))
                .andExpect(model().attributeErrorCount("ruleNameAddDto", 1));
    }

    @Test
    public void showUpdateFormTest() throws Exception {
        //given
        Integer id = 1;

        RuleNameUpdateDto dto = RuleNameUpdateDto.builder().name("AA").description("BB").json("{}").sqlStr("SELCT * ...").sqlPart("OO").build();

        //when
        when(ruleNameService.getRuleNameUpdateFormData(id)).thenReturn(dto);

        //then
        mockMvc.perform(get("/ruleName/update/" + id))
                .andExpect(model().attribute("ruleNameUpdateDto", dto));
    }

    @Test
    public void showUpdateFormTestIdNotFound() throws Exception {
        //given
        Integer id = 1;

        //when
        when(ruleNameService.getRuleNameUpdateFormData(id)).thenThrow(new NoSuchElementException("ID NOT FOUND"));

        //then
        mockMvc.perform(get("/ruleName/update/" + id))
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    public void updateRuleNameTest() throws Exception {
        //given
        int id = 1;

        RuleNameUpdateDto dto = RuleNameUpdateDto.builder().name("AA").description("BB").json("{}").sqlStr("SELCT * ...").sqlPart("OO").build();

        //when
        doNothing().when(ruleNameService).update(any(), any());

        //then
        mockMvc.perform(post("/ruleName/update/" + id)
                .param("name", dto.getName())
                .param("description", dto.getDescription())
                .param("json", dto.getJson())
                .param("template", dto.getTemplate())
                .param("sqlStr", dto.getSqlStr())
                .param("sqlPart", dto.getSqlPart()))
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    public void updateRuleNameTestIdNotFound() throws Exception {
        //given
        Integer id = 1;

        RuleNameUpdateDto dto = RuleNameUpdateDto.builder().name("AA").description("BB").json("{}").sqlStr("SELCT * ...").sqlPart("OO").build();

        //when
        doThrow(NoSuchElementException.class).when(ruleNameService).update(id, dto);

        //then
        mockMvc.perform(post("/ruleName/update/" + id)
                        .param("name", dto.getName())
                        .param("description", dto.getDescription())
                        .param("json", dto.getJson())
                        .param("template", dto.getTemplate())
                        .param("sqlStr", dto.getSqlStr())
                        .param("sqlPart", dto.getSqlPart()))
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    public void deleteRuleNameTest() throws Exception {
        //given
        Integer id = 1;

        //when
        doNothing().when(ruleNameService).delete(id);

        //then
        mockMvc.perform(get("/ruleName/delete/" + id))
                .andExpect(redirectedUrl("/ruleName/list"));
    }
}
