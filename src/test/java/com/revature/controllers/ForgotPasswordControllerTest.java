package com.revature.controllers;

import com.revature.dtos.ResetPasswordRequest;
import com.revature.models.User;
import com.revature.services.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith({MockitoExtension.class})
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
public class ForgotPasswordControllerTest {

    @Mock
    private UserService userService;
    private User user;
    private ResetPasswordRequest resetPasswordRequest;
    @InjectMocks
    private ResetPasswordController sut;

    @Before
    public void setUp() {
        resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setPassword("new_password");
       user = new User();
        //userService.setToken("test_token");
    }

    @Test
    public void resetPassword_shouldUpdatePassword() throws IOException {
        when(userService.get(eq("test_token"))).thenReturn(user);

        sut.resetPassword(resetPasswordRequest, "test_token");

        verify(userService).updatePassword(user, "new_password");
    }
}
