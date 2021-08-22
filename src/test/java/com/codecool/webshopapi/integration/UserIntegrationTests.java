package com.codecool.webshopapi.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

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
        this.baseUrl = "http://localhost:" + port + "/song";
    }

    /*

    @Test
    public void addNewSong_emptyDatabase_shouldReturnSameSong() {
        Song testSong = new Song(null, "The Nine");
        Song result = testRestTemplate.postForObject(baseUrl, testSong, Song.class);
        assertEquals(testSong.getName(), result.getName());
    }

    @Test
    public void getSongs_emptyDatabase_returnsEmptyList() {
        List<Song> songList = List.of(testRestTemplate.getForObject(baseUrl, Song[].class));
        assertEquals(0, songList.size());
    }

    @Test
    public void getSongById_withOnePostedSong_returnsSongWithSameId() {
        Song testSong = new Song(null, "The Nine");
        testSong = testRestTemplate.postForObject(baseUrl, testSong, Song.class);
        Song result = testRestTemplate.getForObject(baseUrl + "/" + testSong.getId(), Song.class);
        assertEquals(testSong.getId(), result.getId());
    }

    @Test
    public void updateSong_withOnePostedSong_returnsUpdatedSong() {
        Song testSong = new Song(null, "The Nine");
        testSong = testRestTemplate.postForObject(baseUrl, testSong, Song.class);

        testSong.setName("Updated name");
        testRestTemplate.put(baseUrl, testSong);
        Song updatedSong = testRestTemplate.getForObject(baseUrl + "/" + testSong.getId(), Song.class);

        assertEquals("Updated name", updatedSong.getName());
    }

    @Test
    public void deleteSongById_withSomePostedSongs_getAllShouldReturnRemainingSongs() {
        Song testSong1 = new Song(null, "The Nine");
        Song testSong2 = new Song(null, "The Ten");
        Song testSong3 = new Song(null, "The Eleven");
        List<Song> testSongs = new ArrayList<>();
        testSongs.add(testSong1);
        testSongs.add(testSong2);
        testSongs.add(testSong3);

        testSongs.forEach(testSong ->
                testSong.setId(testRestTemplate.postForObject(baseUrl, testSong, Song.class).getId())
        );
        
        testRestTemplate.delete(baseUrl + "/" + testSong2.getId());
        testSongs.remove(testSong2);

        List<Song> remainingSongs = List.of(testRestTemplate.getForObject(baseUrl, Song[].class));

        assertEquals(testSongs.size(), remainingSongs.size());
        for(int i = 0; i< testSongs.size(); i++){
            assertEquals(testSongs.get(i).getName(), remainingSongs.get(i).getName());
        }
    }*/
}
