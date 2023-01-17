package com.revature.services;

import com.revature.exceptions.EmptyFieldsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.WeakPasswordException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
public class AuthServiceTests {

    @Autowired
    @InjectMocks
    AuthService auth;


    @MockBean
    UserService userService;



    @Test
    public void TestAuthPassingFindByCreds() throws UserNotFoundException {

        User jacob = new User(1,"jacob@gmail.com","#$Ac2-----RRRRRRRRRRRRR","Jacob","Smith",
                null, null,"Missoula","Missoula","");

        when(userService.findByCredentials(jacob.getEmail(), jacob.getPassword())).thenReturn(Optional.of(jacob));

        auth.findByCredentials(jacob.getEmail(), jacob.getPassword());
        verify(userService).findByCredentials(jacob.getEmail(),jacob.getPassword());
    }

    @Test
    public void TestAuthPassingRegister() throws WeakPasswordException, EmptyFieldsException {
        User testUser = new User();
        testUser.setEmail("test@test.org");
        testUser.setPassword("test");
        auth.register(testUser);
        verify(userService).save(testUser);
    }

}
