package com.revature.Utility;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import com.revature.Utility.GetSiteUrl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
public class GetSiteUrlTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private GetSiteUrl sut;
    @Test
    public void testGetSiteURL() {
        // Arrange
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/myapp"));
        when(request.getServletPath()).thenReturn("/myapp");

        // Act
        String siteURL = sut.getSiteURL(request);

        // Assert
        assertEquals("http://localhost:8080", siteURL);
    }
}
