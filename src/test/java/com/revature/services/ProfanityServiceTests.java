package com.revature.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.revature.models.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ProfanityServiceTests {

    @Autowired
    ProfanityService profTest;

    @Test
    public void checkIfCursesAreCensored(){
        String toBeCensored = "fuck this";
        String expectedOutput = "**** this";
        String action = profTest.censorString(toBeCensored);
        Assertions.assertEquals(action, expectedOutput);
    }

    @Test
    public void checkInnocentWordNotCensored(){
        String toBeCensored = "bass";
        String expectedOutput = "bass";
        String action = profTest.censorString(toBeCensored);
        Assertions.assertEquals(action, expectedOutput);
    }

    @Test
    public void checkThatCommentAreCnesored() {


        User jacob = new User(1,"password","jacob@gmail.com","Jacob","Smith",
                null,"Missoula","Missoula","",null),
        marcus = new User(2,"password","mark@gmail.com","Marcus","Du Pont",
                null,"Missoula","Missoula","",null);

        Post postA = new Post(1,"","",null,jacob,PostType.Top),
        postB = new Post(2,"","",null,jacob,PostType.Comment),
        postC = new Post(3,"","",null,marcus,PostType.Comment);

        String toBeCensored = "fuck this";
        String expectedOutput = "**** this";

        List<Post> comments = new ArrayList<>();
        comments.add(postB);
        comments.add(postC);

        postC.setText(toBeCensored);
        postA.setComments(comments);

        profTest.censorPostAndChildComments(postA);

        Assertions.assertEquals(postC.getText(),expectedOutput);

    }

}