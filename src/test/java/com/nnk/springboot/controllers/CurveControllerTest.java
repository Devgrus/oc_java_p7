package com.nnk.springboot.controllers;

import com.nnk.springboot.config.WithMockCustomUser;
import com.nnk.springboot.config.auth.CustomUserDetailsService;
import com.nnk.springboot.config.oauth.CustomOauth2UserService;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Role;
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

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    CustomOauth2UserService customOauth2UserService;

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
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
    @WithMockCustomUser(role = Role.ADMIN)
    public void validateTestCurveIdIsNotInteger() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "ab")
                        .param("term", "5.0")
                        .param("value", "5.0"))
                .andExpect(model().attributeErrorCount("curvePoint", 1));
    }

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void validateTestTermIsNotNumber() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "1")
                        .param("term", "a")
                        .param("value", "5.0"))
                .andExpect(model().attributeErrorCount("curvePoint", 1));
    }

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void validateTestValueIsNotNumber() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "1")
                        .param("term", "5.0")
                        .param("value", "aa"))
                .andExpect(model().attributeErrorCount("curvePoint", 1));
    }

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void updateCurveTest() throws Exception {
        //given
        Integer id = 1;

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
    @WithMockCustomUser(role = Role.ADMIN)
    public void updateCurveTestCurveIdIsNotInteger() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId", "ab")
                        .param("term", "5.0")
                        .param("value", "5.0"))
                .andExpect(model().attributeErrorCount("curvePoint", 1));
    }

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void updateCurveTestTermIsNotNumber() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId", "1")
                        .param("term", "a")
                        .param("value", "5.0"))
                .andExpect(model().attributeErrorCount("curvePoint", 1));
    }

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void updateCurveTestValueIsNotNumber() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId", "1")
                        .param("term", "5.0")
                        .param("value", "aa"))
                .andExpect(model().attributeErrorCount("curvePoint", 1));
    }

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void deleteCurveTest() throws Exception {
        //given
        Integer id = 1;

        //when
        doNothing().when(curvePointService).delete(id);

        //then
        mockMvc.perform(get("/curvePoint/delete/" + id.toString()))
                .andExpect(redirectedUrl("/curvePoint/list"));
    }
}
