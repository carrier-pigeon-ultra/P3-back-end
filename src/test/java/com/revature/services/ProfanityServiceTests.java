package com.revature.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

}