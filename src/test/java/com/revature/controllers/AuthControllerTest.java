package com.revature.controllers;

import com.revature.controllers.AuthController;
import com.revature.dtos.SearchResponse;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.services.AuthService;
import com.revature.services.SearchServiceImplementation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
public class AuthControllerTest {
/*
        @MockBean
        private SearchServiceImplementation searchService;
    @MockBean
    private AuthService authService;
        @Autowired
        @InjectMocks
        private AuthController sut;
    private User user1;
    private User user2;
    private User user3;
    List<User> userList;
    @BeforeEach
    public void setUp() {
        userList = new ArrayList<>();
        user1 = new User(1,"test1@gmail.com", "password1", "John", "Smith", new Date(1),"test home", "current", "Developer",null);
        user2 = new User(2,"test2@gmail.com", "password2", "Mary", "Johnson", new Date(1),"test home", "current", "Developer",null);
        user3= new User(3,"test3@gmail.com", "password3", "Jane", "Doe", new Date(1),"test home", "current", "Developer",null);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
    }
    @AfterEach
    public void tearDown() {
        user1 = user2 = user3 = null;
        userList = null;
    }
    @Test
    void testSearchUserController() {
        // given
        String searchText = "John";
        Integer page = 1;
        Integer size = 1;

//        List<SearchResponse> userSearchResult = Arrays.asList(
//                new SearchResponse(user1)
//                );
        List<SearchResponse> ActualUserSearchResult = new ArrayList<>();
        ActualUserSearchResult.add(new SearchResponse(user1));
        when(searchService.getUserSearchResult(searchText, page, size)).thenReturn(ActualUserSearchResult);

        // when
        //ResponseEntity<List<SearchResponse>> response = (ResponseEntity<List<SearchResponse>>) sut.searchUser(searchText, page, size);
        ResponseEntity<List<SearchResponse>> expectedUserSearchResult = (ResponseEntity<List<SearchResponse>>) sut.searchUser(searchText, page, size);

        // then
        assertEquals(HttpStatus.OK, expectedUserSearchResult.getStatusCode());
        assertEquals(ActualUserSearchResult, expectedUserSearchResult.getBody());
        verify(searchService).getUserSearchResult(searchText, page, size);
    }
        @Test
    public void testGetUserById() throws UserNotFoundException {
            // given
            int userId = 1;

            when(searchService.getUserById(userId)).thenReturn(user1);

            ResponseEntity<?> response = sut.getUserById(userId);

            // then
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(user1, response.getBody());
        }
*/

}
