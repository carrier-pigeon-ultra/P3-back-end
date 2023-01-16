package com.revature.services;

import com.revature.exceptions.EmptyFieldsException;
import com.revature.exceptions.WeakPasswordException;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
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
        jacob = new User(1,"jacob@gmail.com","#$Ac2-----RRRRRRRRRRRRR","Jacob","Smith",
                null,"Missoula","Missoula","");
        marcus = new User(2,"mark@gmail.com","#$Ac2-----RRRRRRRRRRRRR","Marcus","Du Pont",
                null,"Missoula","Missoula","");
        oliver = new User(3,"oliver@gmail.com","#$Ac2-----RRRRRRRRRRRRR","Oliver","Johnson",
                null,"Missoula","Missoula","");
        invalidUser = new User(3,null,"#$Ac2-----RRRRRRRRRRRRR","Oliver","Johnson",
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

    /* Tests confirming strongPassword(String password);
     returns false for passwords that don't meet criteria of a strong password and true for ones that do.*/
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


    // Tests for isValidUser
    @Test
    void testIsValidUser_shouldThrowWeakPasswordException_givenUserWithWeakPassword() {
        String marcusGoodPassword = marcus.getPassword();
        marcus.setPassword("A#q");
        Assertions.assertThrows(WeakPasswordException.class, () -> {sut.isValidUser(marcus); });
        marcus.setPassword(marcusGoodPassword);
    }
    @Test
    void testIsValidUser_shouldReturnFalse_givenUserHasEmptyFields() throws WeakPasswordException {
        Assertions.assertFalse(sut.isValidUser(invalidUser));
    }
    @Test
    void testIsValidUser_shouldReturnTrue_givenUserHasNecisaryFieldsDefinedAndStringPassword() throws WeakPasswordException {
        Assertions.assertTrue(sut.isValidUser(marcus));
    }

    @Test
    void testSave_shouldThrowWeakPasswordException_givenUserWithBadPassword() {
        String marcusGoodPassword = marcus.getPassword();
        marcus.setPassword("A#q");
        Assertions.assertThrows(WeakPasswordException.class, () -> {sut.save(marcus);});
        marcus.setPassword(marcusGoodPassword);
    }

    @Test
    void testSave_ShouldReturnUser_givenValidUserAsParam() throws WeakPasswordException, EmptyFieldsException {
        Assertions.assertEquals(jacob,sut.save(jacob));
    }
}
