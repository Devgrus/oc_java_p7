package com.nnk.springboot.services;

import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.user.UserUpdateDto;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @Test
    public void getUserUpdateFormDataTest() {
        //given
        User user = User.builder()
                .id(1).username("aa").password("aaa").fullname("a a").role(Role.USER).build();

        //when
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        //then
        assertThat(userService.getUserUpdateFormData(user.getId()).getUsername()).isEqualTo(user.getUsername());
        assertThat(userService.getUserUpdateFormData(user.getId()).getFullname()).isEqualTo(user.getFullname());
    }

    @Test
    public void getUserUpdateFormDataTestIdNotFound() {
        //given
        Integer id = 1;

        //when
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(()-> userService.getUserUpdateFormData(id)).hasMessage("ID NOT FOUND");
    }

    @Test
    public void updateTest() {
        //given
        User user = User.builder()
                .id(1).username("aa").password("aaa").fullname("a a").role(Role.USER).build();

        UserUpdateDto dto = UserUpdateDto.builder()
                .username("bb").fullname("b b").role(Role.ADMIN).build();

        //when
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.update(user.getId(), dto);

        //then
        assertThat(user.getUsername()).isEqualTo(dto.getUsername());
        assertThat(user.getFullname()).isEqualTo(dto.getFullname());
        assertThat(user.getRole()).isEqualTo(dto.getRole());
    }

    @Test
    public void updateTestIdNotFound() {
        //given
        Integer id = 1;

        UserUpdateDto dto = UserUpdateDto.builder()
                .username("bb").fullname("b b").role(Role.ADMIN).build();

        //when
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(()-> userService.update(id, dto)).hasMessage("ID NOT FOUND");
    }
}
