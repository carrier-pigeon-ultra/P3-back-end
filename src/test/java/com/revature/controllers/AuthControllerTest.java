package com.revature.controllers;

import com.revature.controllers.AuthController;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.LoginResponse;
import com.revature.dtos.RegisterRequest;
import com.revature.dtos.SearchResponse;
import com.revature.exceptions.EmptyFieldsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.WeakPasswordException;
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


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
public class AuthControllerTest {

        @MockBean
        private SearchServiceImplementation searchService;
    @MockBean
    private AuthService authService;
    @Mock
    private HttpSession httpSession;
    @Mock
    private HttpServletResponse httpServletResponse;
    @MockBean
    UserRepository userRepository;
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
        user1 = new User(1,"test1@gmail.com", "password1", "John", "Smith", new Date(1),"test home", "current", "Developer");
        user2 = new User(2,"test2@gmail.com", "password2", "Mary", "Johnson", new Date(1),"test home", "current", "Developer");
        user3= new User(3,"test3@gmail.com", "password3", "Jane", "Doe", new Date(1),"test home", "current", "Developer");
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
    public void login_validCredentials_returnsOK() throws UserNotFoundException {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");

        when(authService.findByCredentials(anyString(), anyString())).thenReturn(Optional.of(user));

        ResponseEntity<LoginResponse> response = sut.login(loginRequest, httpServletResponse);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("test@example.com", response.getBody());
        verify(httpSession).setAttribute(eq("user"), eq(user));
    }
   @Test
   public void login_invalidCredentials_returnsBadRequest() throws UserNotFoundException {
       LoginRequest loginRequest = new LoginRequest();
       loginRequest.setEmail("test@example.com");
       loginRequest.setPassword("wrong-password");

       when(authService.findByCredentials(anyString(), anyString())).thenReturn(Optional.empty());

       ResponseEntity<?> response = sut.login(loginRequest, httpServletResponse);

       assertEquals(400, response.getStatusCodeValue());
       verify(httpSession).setAttribute(eq("user"), eq(null));
   }
    @Test
    public void testLogout() {
        // Call the logout method
        sut.logout(httpSession);

        // Verify that the removeAttribute method is called on the session
        verify(httpSession).removeAttribute("user");

        // Verify that the method returns a ResponseEntity with an OK status
        assertEquals(ResponseEntity.ok().build(), sut.logout(httpSession));
    }

    @Test
    public void testRegister() throws WeakPasswordException, EmptyFieldsException {
        // Create a mock RegisterRequest
        RegisterRequest request = new RegisterRequest("test@example.com", "password", "John", "Doe", new Date(1));

        // Create a mock User object to be returned from authService.register
        User createdUser = new User(1,"test@example.com", "password", "John", "Doe", new Date(1), "", "", "",null);

        // Mock the authService.register method to return the createdUser when called
        when(authService.register(any(User.class))).thenReturn(createdUser);

        // Call the register method
        ResponseEntity<User> response = sut.register(request);

        // Verify that authService.register is called with the correct RegisterRequest
        verify(authService).register(argThat(user -> user.getEmail().equals(request.getEmail())
                && user.getPassword().equals(request.getPassword())
                && user.getFirstName().equals(request.getFirstName())
                && user.getLastName().equals(request.getLastName())
                && user.getBirthday().equals(request.getBirthday())));

        // Verify that the method returns a ResponseEntity with a CREATED status
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verify that the method returns the correct User object in the response body
        assertEquals(createdUser, response.getBody());
    }

@Test
    void testSearchUserController() {
        // given
        String searchText = "John";
        Integer page = 1;
        Integer size = 1;

        List<SearchResponse> ActualUserSearchResult = new ArrayList<>();
        ActualUserSearchResult.add(new SearchResponse(user1));
        when(searchService.getUserSearchResult(searchText, page, size)).thenReturn(ActualUserSearchResult);

        // when
        ResponseEntity<List<SearchResponse>> expectedUserSearchResult = (ResponseEntity<List<SearchResponse>>) sut.searchUser(searchText, page, size);

        // then
        assertEquals(HttpStatus.OK, expectedUserSearchResult.getStatusCode());
        assertEquals(ActualUserSearchResult, expectedUserSearchResult.getBody());
        verify(searchService).getUserSearchResult(searchText, page, size);
    }
@Test
public void testGetUserById() throws UserNotFoundException {

    // Mock the searchService.getUserById method to return the targetedUser when called
    when(searchService.getUserById(1)).thenReturn(user1);

    // Call the getUserById method
    ResponseEntity<SearchResponse> response = sut.getUserById(1);


    // Verify that searchService.getUserById is called with the correct userId
    verify(searchService).getUserById(1);

    // Verify that the method returns a ResponseEntity with an OK status
    assertEquals(HttpStatus.OK, response.getStatusCode());

    // Verify that the method returns the correct SearchResponse object in the response body
    assertEquals(new SearchResponse(user1), response.getBody());
        }


}
