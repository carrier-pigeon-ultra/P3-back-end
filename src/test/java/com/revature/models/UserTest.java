package com.revature.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {
    @Autowired
    @InjectMocks
    private User sut;

    @Mock
    private Date mockBirthday;

//    @Test
//    public void testUser() {
//        User user = new User(1, "email", "password", "firstName", "lastName");
//
////        when(mockBirthday.toString()).thenReturn("01-01-2000");
//
//        assertEquals(1, user.getId());
//        assertEquals("email", user.getEmail());
//        assertEquals("password", user.getPassword());
//        assertEquals("firstName", user.getFirstName());
//        assertEquals("lastName", user.getLastName());
//        assertEquals(new java.sql.Date(1), user.getBirthday());
//        assertEquals("hometown", user.getHometown());
//        assertEquals("currentResidence", user.getCurrentResidence());
//        assertEquals("biography", user.getBiography());
//        assertEquals(null, user.getResetPasswordToken());
//    }
}
