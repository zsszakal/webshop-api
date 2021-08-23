package com.codecool.webshopapi.integration;

import com.codecool.webshopapi.model.Location;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class LocationIntegrationTests {

    @LocalServerPort
    private int port;

    private String baseUrl;


    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp() {
        this.baseUrl = "http://localhost:" + port + "/location";
    }

    @Test
    public void getLocationById_withOnePostedLocation_returnsLocationWithSameId() {
        Location testLocation = createLocation( 6666, "Vienna");
        Location testLocationResult = testRestTemplate.postForObject(baseUrl, testLocation, Location.class);
        Location result = testRestTemplate.getForObject(baseUrl + "/" + testLocationResult.getId(), Location.class);
        assertEquals(testLocationResult.getId(), result.getId());
    }

    @Test
    public void updateLocation_withoutLocation_returnsBadRequest() {
        Location testLocation = createLocation( 6666, "Vienna");

        HttpEntity<Location> httpEntity = createHttpEntityWithMediaTypeJson(testLocation);
        ResponseEntity<Object> putResponse = testRestTemplate
                .exchange(baseUrl + "/100", HttpMethod.PUT, httpEntity, Object.class);

        assertEquals(HttpStatus.NOT_FOUND, putResponse.getStatusCode());
    }


    private HttpEntity<Location> createHttpEntityWithMediaTypeJson(Location testLocation) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(testLocation, headers);
    }

    private boolean equalsWithId(Location location, Object object) {
        if (location == object) return true;
        if (location == null || object == null || location.getClass() != object.getClass()) return false;

        Location otherLocation = (Location) object;

        if (!Objects.equals(location.getId(), otherLocation.getId())) return false;
        if (!Objects.equals(location.getName(), otherLocation.getName())) return false;
        return true;
    }

    private boolean contain(List<Location> locations, Location location) {
        for (Location otherLocation : locations) {
            if (equalsWithId(location, otherLocation)) return true;
        }
        return false;
    }

    private Location createLocation(int locationCode, String name) {
        Location location = new Location();
        location.setLocationCode(locationCode);
        location.setName(name);
        return location;
    }
}
