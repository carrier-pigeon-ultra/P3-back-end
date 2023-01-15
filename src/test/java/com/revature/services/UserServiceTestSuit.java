package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTestSuit {

    @MockBean
    UserRepository userRepository;

    @Autowired
    @InjectMocks
    UserService sut;

    User jacob, marcus, oliver, invalidUser;

    @BeforeEach
    void setUp() {

        // Setup users.
        jacob = new User(1,"password","jacob@gmail.com","Jacob","Smith",
                null,"Missoula","Missoula","");
        marcus = new User(2,"password","mark@gmail.com","Marcus","Du Pont",
                null,"Missoula","Missoula","");
        oliver = new User(3,"password","oliver@gmail.com","Oliver","Johnson",
                null,"Missoula","Missoula","");
        invalidUser = new User(3,null,null,"Oliver","Johnson",
                null,"Missoula","Missoula","");

        List<User> userList = new ArrayList<>();
        userList.add(jacob);
        userList.add(marcus);
        userList.add(oliver);
        userList.add(invalidUser);


        // Setup save and findByEmailAndPassword methods in userRepository.
        for(User user: userList) {
            when(userRepository.save(user)).thenReturn(user);
            when(userRepository.findByEmailAndPassword(user.getEmail(),user.getPassword())).thenReturn(Optional.of(user));
        }


    }


    @Test
    void testStrongPassword_shouldReturnFalse_givenNullPassword() {
        String password = null;
        Assertions.assertFalse(sut.strongPassword(password));
    }

    @Test
    void testStrongPassword_shouldReturnFalse_givenPasswordLessThan8CharsInLength() {
        String password = "#$Ac2";
        Assertions.assertFalse(sut.strongPassword(password));
    }

    @Test
    void testStrongPassword_shouldReturnFalse_givenPasswordDoesNotContainLowercaseLetter() {
        String password = "AAA3333111J********";
        Assertions.assertFalse(sut.strongPassword(password));
    }

    @Test
    void testStrongPassword_shouldReturnFalse_givenPasswordDoesNotContainUppercaseLetter() {
        String password = "aaa3333111a********";
        Assertions.assertFalse(sut.strongPassword(password));
    }

    @Test
    void testStrongPassword_shouldReturnFalse_givenPasswordDoesNotContainNumber() {
        String password = "aaaGGGGGGGGGGa********";
        Assertions.assertFalse(sut.strongPassword(password));
    }

    @Test
    void testStrongPassword_shouldReturnTrue_givenPasswordNotNullAndHas8OrMoreCharsAndHasUppercaseLetterAndHasLowercaseLetterAndHasNUmber(){
        String password = "#$Ac2-----RRRRRRRRRRRRR";
        Assertions.assertTrue(sut.strongPassword(password));
    }

}
