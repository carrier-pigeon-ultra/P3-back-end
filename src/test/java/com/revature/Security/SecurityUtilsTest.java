package com.revature.Security;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@RunWith(MockitoJUnitRunner.class)
public class SecurityUtilsTest {

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;
    @Autowired
    @InjectMocks
    private SecurityUtils sut;
    @Before
    public void setUp() {
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testCurrentLoginWithUserDetails() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("user1");

        // Act
        String login = sut.currentLogin();

        // Assert
        assertEquals("user1", login);
    }

    @Test
    public void testCurrentLoginWithString() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("user1");

        // Act
        String login = sut.currentLogin();

        // Assert
        assertEquals("user1", login);
    }

    @Test
    public void testCurrentLoginWithNullAuthentication() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(null);

        // Act
        String login = sut.currentLogin();

        // Assert
        assertEquals(null, login);
    }
}
