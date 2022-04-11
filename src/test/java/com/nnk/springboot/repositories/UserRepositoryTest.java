package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Provider;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void initEach() {
        userRepository.deleteAll();
        userRepository.save(User.builder()
                .username("abc@test.com")
                .fullname("fullname")
                .password("123456")
                .role(Role.USER)
                .provider(Provider.LOCAL)
                .build());
    }

    @Test
    public void readTest() {
        assertThat(userRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void findByUsernameTest() {
        assertThat(userRepository.findByUsername("abc@test.com").isPresent()).isTrue();
    }

    @Test
    public void createTest() {
        User user = User.builder()
                .username("bcd@test.com")
                .fullname("fullname2")
                .password("123456")
                .role(Role.USER)
                .provider(Provider.LOCAL)
                .build();

        assertThat(userRepository.save(user).getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void updateTest() {
        assertThat(userRepository.findById(1).isPresent()).isTrue();

        String newUsername = "efg@test.com";

        userRepository.findById(1).ifPresent(i->i.setUsername(newUsername));

        assertThat(userRepository.findById(1).get().getUsername()).isEqualTo(newUsername);

    }

    @Test
    public void deleteByIdTest() {
        assertThat(userRepository.findById(1).isPresent()).isTrue();

        userRepository.deleteById(1);

        assertThat(userRepository.findById(1).isPresent()).isFalse();
    }
}
