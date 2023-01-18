package com.revature.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PostTypeTest {

    @Test
    public void testEnumValues() {
        assertEquals(PostType.Top, PostType.valueOf("Top"));
        assertEquals(PostType.Comment, PostType.valueOf("Comment"));
        assertEquals(PostType.Reply, PostType.valueOf("Reply"));
    }
}
