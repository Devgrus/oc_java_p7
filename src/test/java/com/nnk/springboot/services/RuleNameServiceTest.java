package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.ruleName.RuleNameUpdateDto;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @Mock
    RuleNameRepository ruleNameRepository;

    @InjectMocks
    RuleNameService ruleNameService;

    @Test
    public void getRuleNameUpdateFormDataTest() {
        //given
        RuleName ruleName = RuleName.builder()
                .id(1).name("AA").description("BB").json("{}").sqlStr("SELCT * ...").sqlPart("OO").build();

        //when
        when(ruleNameRepository.findById(ruleName.getId())).thenReturn(Optional.of(ruleName));

        //then
        assertThat(ruleNameService.getRuleNameUpdateFormData(ruleName.getId()).getName()).isEqualTo("AA");
        assertThat(ruleNameService.getRuleNameUpdateFormData(ruleName.getId()).getDescription()).isEqualTo("BB");
    }

    @Test
    public void getRuleNameUpdateFormDataTestIdNotFound() {
        //given
        Integer id = 1;

        //when
        when(ruleNameRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(()-> ruleNameService.getRuleNameUpdateFormData(id)).hasMessage("ID NOT FOUND");
    }

    @Test
    public void updateTest() {
        //given
        RuleName ruleName = RuleName.builder()
                .id(1).name("AA").description("BB").json("{}").sqlStr("SELCT * ...").sqlPart("OO").build();

        RuleNameUpdateDto dto = RuleNameUpdateDto.builder().name("CC").description("DD").build();

        //when
        when(ruleNameRepository.findById(ruleName.getId())).thenReturn(Optional.of(ruleName));
        ruleNameService.update(ruleName.getId(), dto);

        //then
        assertThat(ruleName.getName()).isEqualTo(dto.getName());
        assertThat(ruleName.getDescription()).isEqualTo(dto.getDescription());
    }

    @Test
    public void updateTestIdNotFound() {
        //given
        Integer id = 1;

        RuleNameUpdateDto dto = RuleNameUpdateDto.builder().name("CC").description("DD").build();

        //when
        when(ruleNameRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(()-> ruleNameService.update(id, dto)).hasMessage("ID NOT FOUND");
    }

}
