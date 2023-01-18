package com.revature.dtos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ForgotPasswordRequestTest {

    @InjectMocks
    private ForgotPasswordRequest forgotPasswordRequest;

    @Test
    public void testSetEmail() {
        // Arrange
        String email = "test@example.com";

        // Act
        forgotPasswordRequest.setEmail(email);

        // Assert
        assertEquals(email, forgotPasswordRequest.getEmail());
    }
}
