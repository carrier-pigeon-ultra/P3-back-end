package com.revature.Utility;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
public class JWTUtilityTestSuite {

    @MockBean
    UserRepository userRepository;
    User jacob;

    @Autowired
    @InjectMocks
    JWTUtility sut;



    @BeforeEach
    void setup(){
        jacob = new User(1,"jacob@gmail.com","#$Ac2-----RRRRRRRRRRRRR","Jacob","Smith",
                null,"Missoula","Missoula","",null);
        when(userRepository.findById(jacob.getId())).thenReturn(Optional.ofNullable(jacob));
    }


    @Test
    void testCreateBytes_bytesShouldBeNonNull_givenBytesCreatedFromSecret() {
        Assertions.assertNotNull(sut.getBytes());
    }

    @Test
    void testCreateToken_tokenShouldBeNonNullAndNonEmtpy_givenToeknSuccessfullyCreated() {

        String token = sut.createToken(jacob);

        Assertions.assertNotNull(token);
        Assertions.assertNotEquals(token.trim(),"");
    }


    @Test
    void testExtractTokenDetails_ShouldReturnUser_givenTokenDetailsExtracted() throws Exception {
        String token = sut.createToken(jacob);
        User result = sut.extractTokenDetails(token);

        Assertions.assertEquals(jacob,result);
    }


}
