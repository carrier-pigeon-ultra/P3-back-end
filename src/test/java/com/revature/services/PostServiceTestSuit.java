package com.revature.services;

import com.revature.models.*;
import com.revature.repositories.PostRepository;
import com.revature.repositories.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

/**
 * Note: Tests will fail if run individually because environment variables are set for entire test suite.
 * */
@SpringBootTest
public class PostServiceTestSuit {

    @MockBean
    PostRepository postRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    @InjectMocks
    PostService sut;

    User jacob, marcus, oliver;
    Post postA, postB, postC, postD, postE, postF, postG;

    List<Post> topPosts;
    List<Post> jacobsPosts;

    @BeforeEach
    void setUp() {

        // Setup users.
        jacob = new User(1,"password","jacob@gmail.com","Jacob","Smith",
                null,"Missoula","Missoula","");
        marcus = new User(2,"password","mark@gmail.com","Marcus","Du Pont",
                null,"Missoula","Missoula","");
        oliver = new User(3,"password","oliver@gmail.com","Oliver","Johnson",
                null,"Missoula","Missoula","");

        // Setup posts.
        postA = new Post(1,"","",null,jacob,PostType.Top);
        postB = new Post(2,"","",null,jacob,PostType.Top);
        postC = new Post(3,"","",null,marcus,PostType.Top);
        postD = new Post(4,"","",null,marcus,PostType.Top);
        postE = new Post(5,"","",null,oliver,PostType.Top);
        postF = new Post(6,"","",null,oliver,PostType.Top);

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

        for( Post post : topPosts) {
            when(postRepository.save(post)).thenReturn(post);
        }

        when(postRepository.findAllByAuthor(jacob)).thenReturn(jacobsPosts);
    }

    @Test
    void testUpsert_shouldReturnPost_givenValidPostGiven(){
        assertEquals(postA,sut.upsert(postA));
    }

    @Test
    void testGetAllTop_shouldReturnTopPosts_givenMethodIsCalled() {
        assertEquals(sut.getAllTop(),topPosts);
    }

    @Test
    void testGetByUserID_shouldReturnUserPosts_givenUserId() {
        assertEquals(sut.getByUserID(jacob.getId()),jacobsPosts);
    }

    @Test
    void testDeleteByUserID_shouldDeletePostGivenVlidPostId() {
        assertNull(sut.deletePostById(postA.getId()));
    }

}
