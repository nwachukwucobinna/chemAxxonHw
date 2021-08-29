package com.odana.CostRegister.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.odana.CostRegister.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        List<User> users = Arrays.asList(
                new User("test@test.com","test","test","test"),
                new User("test2@test.com","test","test2","test"),
                new User("test3@test.com","test","test3","test")
        );

        String email = "test3@test.com";

       users = userRepository.saveAllAndFlush(users);

       User userByEmailFromUsers = users.stream()
               .filter(user -> user.getEmail().matches(email))
               .findFirst().get();

       User userByEmail = userRepository.findByEmail(email);

        //when a user exists with the email
        assertThat(userByEmail)
                .isNotNull();
        assertThat(userByEmail
                .getId())
                .isEqualTo(userByEmailFromUsers.getId());

        //when a user does not exist with the email
        assertThat(userRepository.findByEmail("fake@test.com"))
                .isNull();
    }
}
