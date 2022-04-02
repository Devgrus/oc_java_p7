package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.user.UserAddDto;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    public void validateTest() throws Exception {
        //given
        UserAddDto dto = UserAddDto.builder().username("User").password("aaa").fullname("A a").role(Role.USER).build();

        User user = dto.toEntity();

        //when
        when(userService.save(any())).thenReturn(user);

        //then
        mockMvc.perform(post("/user/validate")
                .param("username", dto.getUsername())
                .param("password", dto.getPassword())
                .param("fullname", dto.getFullname())
                .param("role", dto.getRole().toString()))
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    public void validateTestWithWrongValues() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/user/validate")
                        .param("username", " ")
                        .param("password", "")
                        .param("fullname", " ")
                        .param("role", "USER"))
                .andExpect(model().attributeErrorCount("user", 3));
    }

    @Test
    public void validateTestWithWrongRoleValue() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/user/validate")
                        .param("username", " ")
                        .param("password", "")
                        .param("fullname", " ")
                        .param("role", "DD"))
                .andExpect(model().attributeErrorCount("user", 1));
    }
}
