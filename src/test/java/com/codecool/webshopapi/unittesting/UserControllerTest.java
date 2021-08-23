package com.codecool.webshopapi.unittesting;


import com.codecool.webshopapi.controller.UserController;
import com.codecool.webshopapi.model.User;
import com.codecool.webshopapi.service.UserService;
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

@WebMvcTest({UserController.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static final User TEST_USER = new User();
    private static final User TEST_USER_2 = new User();


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void add_RightUser_shouldReturnUser() throws Exception {
        User user = TEST_USER;
        when(userService.add(any())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(TEST_USER)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(user.getName())));

        verify(userService, times(1))
                .add(any());
    }

    @Test
    void add_inputRightUser_shouldReturnUser() throws Exception {
        Long id = 1L;
        TEST_USER.setId(id);
        User user = TEST_USER;
        when(userService.add(any())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(TEST_USER)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.name", is(user.getName())));

        verify(userService, times(1))
                .add(any());

        TEST_USER.setId(null);
    }


    @Test
    void getById_inputValidId_shouldReturnRightUser() throws Exception {
        Long id = 1L;
        TEST_USER.setId(id);
        User user = TEST_USER;
        when(userService.getById(anyLong())).thenReturn(user);
        mockMvc.perform(get("/users/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.name", is(user.getName())));
        verify(userService, times(1))
                .getById(id);
        TEST_USER.setId(null);
    }

    @Test
    void getAll_shouldReturnAllUsers() throws Exception {
        TEST_USER.setId(1L);
        TEST_USER_2.setId(2L);
        List<User> users = List.of(TEST_USER, TEST_USER_2);

        when(userService.getAll()).thenReturn(users);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(TEST_USER.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(TEST_USER.getName())))
                .andExpect(jsonPath("$[1].id", is(TEST_USER_2.getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(TEST_USER_2.getName())));

        verify(userService, times(1))
                .getAll();

        TEST_USER.setId(null);
        TEST_USER_2.setId(null);
    }

    @Test
    void delete_inputValidId_shouldReturnOkStatus() throws Exception {
        doNothing().when(userService).delete(any());
        mockMvc.perform(delete("/users/{id}", anyLong()))
                .andExpect(status().isOk());
        verify(userService, times(1))
                .delete(anyLong());
    }
}
