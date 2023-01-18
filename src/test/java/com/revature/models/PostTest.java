package com.revature.models;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(Post.class)
public class PostTest {
    @Mock
    private User author;

    @Mock
    private Post mockPost1;
    @Mock
    private Post mockPost2;
    @InjectMocks
    private Post post;
    @Mock
    private List<Post> mockComments;
   //Post mockPost = Mockito.mock(Post.class);
    @Test
    public void testNoArgsConstructor() {
        Post post = new Post();
        assertNotNull(post);
    }

    @Test
    public void testAllArgsConstructor() {
        List<Post> comments = new ArrayList<>();
        Post post = new Post(1, "text", "imageUrl", comments, author, PostType.Comment);
        assertEquals(1, post.getId());
        assertEquals("text", post.getText());
        assertEquals("imageUrl", post.getImageUrl());
        assertEquals(comments, post.getComments());
        assertEquals(author, post.getAuthor());
        assertEquals(PostType.Comment, post.getPostType());
    }

    @Test
    public void testId() {
        Post post = new Post();
        post.setId(1);
        assertEquals(1, post.getId());
    }

    @Test
    public void testText() {
        Post post = new Post();
        post.setText("text");
        assertEquals("text", post.getText());
    }

    @Test
    public void testImageUrl() {
        Post post = new Post();
        post.setImageUrl("imageUrl");
        assertEquals("imageUrl", post.getImageUrl());
    }

    @Test
    public void testComments() {
        Post post = new Post();
        List<Post> comments = new ArrayList<>();
        post.setComments(comments);
        assertEquals(comments, post.getComments());
    }

    @Test
    public void testAuthor() {
        Post post = new Post();
        post.setAuthor(author);
        assertEquals(author, post.getAuthor());
    }

    @Test
    public void testPostType() {
        Post post = new Post();
        post.setPostType(PostType.Top);
        assertEquals(PostType.Top, post.getPostType());
    }

    @Test
    public void testHashCode() throws Exception {
        //PowerMockito.whenNew(Post.class).withNoArguments().thenReturn(new Post());
        Post post = new Post();
        post.setId(1);
        post.setText("text");
        post.setImageUrl("imageUrl");
        post.setComments(mockComments);
        post.setAuthor(new User());
        post.setPostType(PostType.Comment);

        when(new User().hashCode()).thenReturn(1);
        when(mockComments.hashCode()).thenReturn(1);

        int expectedHashCode = 31 * 1 + "text".hashCode();
        expectedHashCode = 31 * expectedHashCode + "imageUrl".hashCode();
        expectedHashCode = 31 * expectedHashCode + 1;
        expectedHashCode = 31 * expectedHashCode + 1;
        expectedHashCode = 31 * expectedHashCode + PostType.Comment.hashCode();

        assertEquals(expectedHashCode, post.hashCode());
    }

    @Test
    public void testEquals() {
        when(mockPost1.getId()).thenReturn(1);
        when(mockPost1.getText()).thenReturn("some text");
        when(mockPost1.getImageUrl()).thenReturn("image.com/img.jpg");
        when(mockPost1.getComments()).thenReturn(Arrays.asList(mockPost2));
        when(mockPost1.getAuthor()).thenReturn(new User());
        when(mockPost1.getPostType()).thenReturn(PostType.Comment);

        when(mockPost2.getId()).thenReturn(1);
        when(mockPost2.getText()).thenReturn("some text");
        when(mockPost2.getImageUrl()).thenReturn("image.com/img.jpg");
        when(mockPost2.getComments()).thenReturn(Arrays.asList(mockPost2));
        when(mockPost2.getAuthor()).thenReturn(new User());
        when(mockPost2.getPostType()).thenReturn(PostType.Comment);

        assertEquals(mockPost1, mockPost2);
    }

}

