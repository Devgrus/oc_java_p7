package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.user.UserUpdateDto;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Save a user
     * @param user user information
     * @return user
     */
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Get a user information before update
     * @param id user id
     * @return user information
     */
    @Transactional
    public UserUpdateDto getUserUpdateFormData(Integer id) {
        User user = userRepository.findById(id).orElseThrow(()-> new NoSuchElementException("ID NOT FOUND"));
        return UserUpdateDto.builder()
                .username(user.getUsername())
                .fullname(user.getFullname())
                .role(user.getRole())
                .build();
    }

    /**
     * Update a user
     * @param id user id
     * @param dto user information
     */
    @Transactional
    public void update(Integer id, UserUpdateDto dto) {
        User user = userRepository.findById(id).orElseThrow(()-> new NoSuchElementException("ID NOT FOUND"));
        user.setUsername(dto.getUsername());
        user.setFullname(dto.getFullname());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
    }
}
