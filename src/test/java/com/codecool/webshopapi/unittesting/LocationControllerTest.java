package com.codecool.webshopapi.unittesting;

import com.codecool.webshopapi.controller.LocationController;
import com.codecool.webshopapi.model.Location;
import com.codecool.webshopapi.service.LocationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({LocationController.class})
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationService locationService;

    private static final Location TEST_LOCATION = new Location(1L, 65323, "Las Vegas", null);
    private static final Location TEST_LOCATION_2 = new Location(66L, 25584, "Manchester", null);
    private static final Location TEST_LOCATION_WITH_INVALID_NAME = new Location(122L, 4352, "", null);


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void add_inputRightLocation_shouldLocation() throws Exception {
        Location location = TEST_LOCATION;
        when(locationService.add(any())).thenReturn(location);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(TEST_LOCATION)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(location.getName())));

        verify(locationService, times(1))
                .add(any());
    }

    @Test
    void updateById_inputRightLocation_shouldReturnLocation() throws Exception {
        Long id = 1L;
        TEST_LOCATION.setId(id);
        Location location = TEST_LOCATION;
        when(locationService.add(any())).thenReturn(location);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/locations/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(TEST_LOCATION)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(location.getId().intValue())))
                .andExpect(jsonPath("$.name", is(location.getName())));

        verify(locationService, times(1))
                .add(any());

        TEST_LOCATION.setId(null);
    }


    @Test
    void getById_inputValidId_shouldReturnRightLocation() throws Exception {
        Long id = 1L;
        TEST_LOCATION.setId(id);
        Location location = TEST_LOCATION;
        when(locationService.getById(anyLong())).thenReturn(location);
        mockMvc.perform(get("/locations/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(location.getId().intValue())))
                .andExpect(jsonPath("$.name", is(location.getName())));
        verify(locationService, times(1))
                .getById(id);
        TEST_LOCATION.setId(null);
    }

    @Test
    void getAll_shouldReturnAllLocations() throws Exception {
        TEST_LOCATION.setId(1L);
        TEST_LOCATION_2.setId(2L);
        List<Location> locations = List.of(TEST_LOCATION, TEST_LOCATION_2);

        when(locationService.getAll()).thenReturn(locations);
        mockMvc.perform(get("/locations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(TEST_LOCATION.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(TEST_LOCATION.getName())))
                .andExpect(jsonPath("$[1].id", is(TEST_LOCATION_2.getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(TEST_LOCATION_2.getName())));

        verify(locationService, times(1))
                .getAll();

        TEST_LOCATION.setId(null);
        TEST_LOCATION_2.setId(null);
    }

    @Test
    void delete_inputValidId_shouldReturnOkStatus() throws Exception {
        doNothing().when(locationService).delete(any());
        mockMvc.perform(delete("/locations/{id}", anyLong()))
                .andExpect(status().isOk());
        verify(locationService, times(1))
                .delete(anyLong());
    }
}
