package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.curvePoint.CurvePointAddDto;
import com.nnk.springboot.dto.curvePoint.CurvePointUpdateDto;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(CurveController.class)
public class CurveControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CurvePointService curvePointService;

    @Test
    public void validateTest() throws Exception {
        //given
        CurvePointAddDto dto = CurvePointAddDto.builder()
                .curveId(11)
                .term(5d)
                .value(5d).build();

        CurvePoint curvePoint = dto.toEntity();

        //when
        when(curvePointService.save(any())).thenReturn(curvePoint);

        //then
        mockMvc.perform(post("/curvePoint/validate")
                .param("curveId", dto.getCurveId().toString())
                .param("term", dto.getTerm().toString())
                .param("value", dto.getValue().toString()))
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    public void validateTestCurveIdIsNotInteger() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "ab")
                        .param("term", "5.0")
                        .param("value", "5.0"))
                .andExpect(model().attributeErrorCount("curvePointAddDto", 1));
    }

    @Test
    public void validateTestTermIsNotNumber() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "1")
                        .param("term", "a")
                        .param("value", "5.0"))
                .andExpect(model().attributeErrorCount("curvePointAddDto", 1));
    }

    @Test
    public void validateTestValueIsNotNumber() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "1")
                        .param("term", "5.0")
                        .param("value", "aa"))
                .andExpect(model().attributeErrorCount("curvePointAddDto", 1));
    }

    @Test
    public void updateCurveTest() throws Exception {
        //given
        Integer id = 1;

        CurvePointUpdateDto curvePointUpdateDto = CurvePointUpdateDto.builder()
                .curveId(2).term(1d).value(1d)
                .build();

        //when
        doNothing().when(curvePointService).update(any(), any());

        //then
        mockMvc.perform(post("/curvePoint/update/" + id)
                .param("curveId", "1")
                .param("term", "5.0")
                .param("value", "1"))
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    public void updateCurveTestCurveIdIsNotInteger() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId", "ab")
                        .param("term", "5.0")
                        .param("value", "5.0"))
                .andExpect(model().attributeErrorCount("curvePointUpdateDto", 1));
    }

    @Test
    public void updateCurveTestTermIsNotNumber() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId", "1")
                        .param("term", "a")
                        .param("value", "5.0"))
                .andExpect(model().attributeErrorCount("curvePointUpdateDto", 1));
    }

    @Test
    public void updateCurveTestValueIsNotNumber() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId", "1")
                        .param("term", "5.0")
                        .param("value", "aa"))
                .andExpect(model().attributeErrorCount("curvePointUpdateDto", 1));
    }
}
