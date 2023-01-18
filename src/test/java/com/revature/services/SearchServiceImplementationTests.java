package com.revature.services;

import com.revature.dtos.SearchResponse;
import com.revature.exceptions.InvalidOperationException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
public class SearchServiceImplementationTests {
    @MockBean
    private   UserRepository userRepository;
    UserRepository userRepository1 = mock(UserRepository.class);

    SearchServiceImplementation service = new SearchServiceImplementation(userRepository1);

    @Autowired
    @InjectMocks
   private SearchServiceImplementation sut;
    private User user1;
    private User user2;
    private User user3;
    List<User> userList;
    @BeforeEach
    public void setUp() {
        userList = new ArrayList<>();
        user1 = new User(1,"test1@gmail.com", "password1", "John", "Smith", new Date(1),"test home", "current", "Developer",null);
        user2 = new User(2,"test2@gmail.com", "password2", "Mary", "Johnson", new Date(1),"test home", "current", "Developer",null);
        user3= new User(3,"test3@gmail.com", "password3", "Jane", "Doe", new Date(1),"test home", "current", "Developer", null);
        userList.add(user1);
        userList.add(user2);

        for(User user:userList) {
            userRepository.save(user);
        }

    }
    @AfterEach
    public void tearDown() {
        user1 = user2 = null;
        userList = null;
    }

    @Test
    public void givenIdThenShouldReturnUserOfThatId() throws UserNotFoundException {
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user1));
        assertThat(sut.getUserById(user1.getId())).isEqualTo(user1);
    }

    @Test
    public void givenEmailThenShouldReturnUserOfThatEmail() throws UserNotFoundException {
        User expectedUser = user1;
        when(userRepository1.findByEmail("test1@gmail.com")).thenReturn(Optional.of(expectedUser));
        User actualUser = service.getUserByEmail("test1@gmail.com");
        assertEquals(expectedUser, actualUser);

    }
    @Test
    public void testGetUserSearchResult_validInput_returnsList() {
        // Arrange
        String searchText = "John";
        Integer page = 1;
        Integer size = 10;
        List<User> users = Collections.singletonList(new User());
        SearchResponse response = sut.userToSearchResponse(user1);
        when(userRepository.findUsersByName(searchText, PageRequest.of(page, size))).thenReturn(users);
        //when(response).thenReturn(new SearchResponse());

        // Act
        List<SearchResponse> result = sut.getUserSearchResult(searchText, page, size);

        // Assert
        assertEquals(1, result.size());
    }
    @Test
    public void testGetUserSearchResult_emptySearchText_throwsException() {
        // Arrange
        String searchText = "";
        Integer page = 0;
        Integer size = 10;

        // Act & Assert
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(InvalidOperationException.class, () -> {
            sut.getUserSearchResult(searchText, page, size);
        });
    }

    @Test
    public void testGetUserSearchResult_userNotFound_throwsException() {
        // Arrange
        String searchText = "zx";
        Integer page = 0;
        Integer size = 10;
        when(userRepository.findUsersByName(searchText, PageRequest.of(page, size))).thenReturn(Collections.emptyList());

        // Act & Assert
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class, () -> {
            sut.getUserSearchResult(searchText, page, size);
        });
        assertEquals(HttpStatus.NOT_FOUND, ((ResponseStatusException) exception).getStatus());
    }
    @Test
    public void testMapUserToSearchResponse() {

        // Execute the mapping method
        SearchResponse response = sut.userToSearchResponse(user1);

        // Assert that the response object is correctly mapped
        assertEquals(user1.getId(), response.getId());
        assertEquals(user1.getEmail(), response.getEmail());
        assertEquals(user1.getFirstName(), response.getFirstName());
        assertEquals(user1.getLastName(), response.getLastName());
    }

}
