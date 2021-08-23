package com.codecool.webshopapi.integration;

import com.codecool.webshopapi.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class UserIntegrationTests {

    @LocalServerPort
    private int port;

    private String baseUrl;


    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp() {
        this.baseUrl = "http://localhost:" + port + "/user";
    }

    @Test
    public void getUserById_withOnePostedUser_returnsUserWithSameId() {
        User testUser = createUser( 6666, "Vienna");
        User testUserResult = testRestTemplate.postForObject(baseUrl, testUser, User.class);
        User result = testRestTemplate.getForObject(baseUrl + "/" + testUserResult.getId(), User.class);
        assertEquals(testUserResult.getId(), result.getId());
    }

    @Test
    public void updateUser_withoutUser_returnsBadRequest() {
        User testUser = createUser( 6666, "Vienna");

        HttpEntity<User> httpEntity = createHttpEntityWithMediaTypeJson(testUser);
        ResponseEntity<Object> putResponse = testRestTemplate
                .exchange(baseUrl + "/100", HttpMethod.PUT, httpEntity, Object.class);

        assertEquals(HttpStatus.NOT_FOUND, putResponse.getStatusCode());
    }


    private HttpEntity<User> createHttpEntityWithMediaTypeJson(User testUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(testUser, headers);
    }


    private User createUser(int userCode, String name) {
        User user = new User();
        user.setLocation(user.getLocation());
        user.setName(name);
        return user;
    }
}
