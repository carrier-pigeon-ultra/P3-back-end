package com.revature.services;

import com.revature.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class AuthServiceTests {

    @Autowired
    AuthService auth;

    @MockBean
    UserService user;

    @BeforeAll
    public void attachMockUser(){
        auth = new AuthService(user);
    }

    @Test
    public void TestAuthPassingFindByCreds(){
        auth.findByCredentials("testing@user.com", "user");
        verify(user).findByCredentials("testing@user.com","user");
    }

    @Test
    public void TestAuthPassingRegister(){
        User testUser = new User();
        testUser.setEmail("test@test.org");
        testUser.setPassword("test");
        auth.register(testUser);
        verify(user).save(testUser);
    }

}
