package com.revature.controllers;

import com.revature.annotations.AuthRestriction;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.dtos.SearchResponse;
import com.revature.exceptions.EmptyFieldsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.WeakPasswordException;
import com.revature.models.Post;
import com.revature.models.PostType;
import com.revature.models.User;
import com.revature.repositories.PostRepository;
import com.revature.repositories.UserRepository;
import com.revature.services.AuthService;
import com.revature.services.PostService;
import com.revature.services.SearchServiceImplementation;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith({MockitoExtension.class})
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
public class PostControllerTests {
    @MockBean
    private SearchServiceImplementation searchService;
    @MockBean
    private PostService postService;
    @Mock
    private HttpSession httpSession;
    @MockBean
    PostRepository postRepository;

    @MockBean
    UserRepository userRepository;
    @Autowired
    @InjectMocks
    private PostController sut;
    User jacob, marcus, oliver;
    Post postA, postB, postC, postD, postE, postF, postG;

    List<Post> topPosts;
    List<Post> jacobsPosts;

    @BeforeEach
    void setUp() {

        // Setup users.
        jacob = new User(1, "password", "jacob@gmail.com", "Jacob", "Smith",
                null, "Missoula", "Missoula", "", null);
        marcus = new User(2, "password", "mark@gmail.com", "Marcus", "Du Pont",
                null, "Missoula", "Missoula", "", null);
        oliver = new User(3, "password", "oliver@gmail.com", "Oliver", "Johnson",
                null, "Missoula", "Missoula", "", null);

        // Setup posts.
        postA = new Post(1, "", "", null, jacob, PostType.Top);
        postB = new Post(2, "", "", null, jacob, PostType.Top);
        postC = new Post(3, "", "", null, marcus, PostType.Top);
        postD = new Post(4, "", "", null, marcus, PostType.Top);
        postE = new Post(5, "", "", null, oliver, PostType.Top);
        postF = new Post(6, "", "", null, oliver, PostType.Top);

        // Setup top posts list.
        topPosts = new ArrayList<>();
        topPosts.add(postA);
        topPosts.add(postB);
        topPosts.add(postC);
        topPosts.add(postD);
        topPosts.add(postE);
        topPosts.add(postF);

        // Setup jacobs list.
        jacobsPosts = new ArrayList<>();
        jacobsPosts.add(postA);
        jacobsPosts.add(postB);

        // Set up userRepository methods.
        when(userRepository.findById(jacob.getId())).thenReturn(Optional.ofNullable(jacob));
        when(userRepository.findById(marcus.getId())).thenReturn(Optional.ofNullable(marcus));
        when(userRepository.findById(oliver.getId())).thenReturn(Optional.ofNullable(oliver));

        // Set up postRepository methods.
        when(postRepository.findAllByPostType(PostType.Top)).thenReturn(topPosts);

        for (Post post : topPosts) {
            when(postRepository.save(post)).thenReturn(post);
        }

        when(postRepository.findAllByAuthor(jacob)).thenReturn(jacobsPosts);
    }

    @Test
    public void testGetAllPosts() {
        // Create a mock list of Post objects
        List<Post> posts = Arrays.asList(new Post(1, "Test Post 1", "imageUrl", null, marcus, PostType.Comment), new Post(2, "Test Post 2", "imageUrl", null, jacob, PostType.Top));

        // Mock the postService.getAll method to return the posts when called
        when(postService.getAll()).thenReturn(posts);

        // Call the getAllPosts method
        ResponseEntity<List<Post>> response = sut.getAllPosts();

        // Verify that postService.getAll is called
        //verify(postService).getAll();

        // Verify that the method returns a ResponseEntity with an OK status
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the method returns the correct list of Post objects in the response body
        assertEquals(posts, response.getBody());
    }

//    @Test
//    public void createPost_shouldReturnForbiddenForUnauthenticatedUser() {
//        // given
//        when(authService.isAuthenticated()).thenReturn(false);
//        Post post = new Post();
//
//        // when
//        ResponseEntity<Post> result = sut.upsertPost(post);
//
//        // then
//        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
//    }
//
//    @Test
//    public void createPost_shouldReturnOkForAuthenticatedUser() {
//        // given
//        when(authService.isAuthenticated()).thenReturn(true);
//        Post post = new Post();
//        when(postService.create(post)).thenReturn(post);
//
//        // when
//        ResponseEntity<Post> result = sut.upsertPost(post);
//
//        // then
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//        assertEquals(post, result.getBody());
//    }
//}


    @Test
    public void getAllTopPosts_shouldReturnOkResponse() {
        // when
        ResponseEntity<List<Post>> result = sut.getAllTopPosts();

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(topPosts, result.getBody());
    }

    @Test
    public void testGetUsersPostsByUserId() {
        //List<Post> expectedPosts = Arrays.asList(new Post(), new Post());
        //when(postService.getByUserID(1)).thenReturn(postA);

        ResponseEntity<List<Post>> response = sut.getUsersPosts(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postA, response.getBody());
        verify(postService).getByUserID(1);
    }
}




