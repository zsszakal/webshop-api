package com.codecool.webshopapi.unittesting;

import com.codecool.webshopapi.controller.ProductController;
import com.codecool.webshopapi.model.Product;
import com.codecool.webshopapi.service.ProductService;
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

@WebMvcTest({ProductController.class})
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private static final Product TEST_PRODUCT = new Product();
    private static final Product TEST_PRODUCT_2 = new Product();


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void add_RightProduct_shouldReturnProduct() throws Exception {
        Product product = TEST_PRODUCT;
        when(productService.add(any())).thenReturn(product);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(TEST_PRODUCT)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(product.getName())));

        verify(productService, times(1))
                .add(any());
    }

    @Test
    void add_inputRightProduct_shouldReturnProduct() throws Exception {
        Long id = 1L;
        TEST_PRODUCT.setId(id);
        Product product = TEST_PRODUCT;
        when(productService.add(any())).thenReturn(product);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/products/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(TEST_PRODUCT)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(product.getId().intValue())))
                .andExpect(jsonPath("$.name", is(product.getName())));

        verify(productService, times(1))
                .add(any());

        TEST_PRODUCT.setId(null);
    }


    @Test
    void getById_inputValidId_shouldReturnRightProduct() throws Exception {
        Long id = 1L;
        TEST_PRODUCT.setId(id);
        Product product = TEST_PRODUCT;
        when(productService.getById(anyLong())).thenReturn(product);
        mockMvc.perform(get("/products/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(product.getId().intValue())))
                .andExpect(jsonPath("$.name", is(product.getName())));
        verify(productService, times(1))
                .getById(id);
        TEST_PRODUCT.setId(null);
    }

    @Test
    void getAll_shouldReturnAllProducts() throws Exception {
        TEST_PRODUCT.setId(1L);
        TEST_PRODUCT_2.setId(2L);
        List<Product> products = List.of(TEST_PRODUCT, TEST_PRODUCT_2);

        when(productService.getAll()).thenReturn(products);
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(TEST_PRODUCT.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(TEST_PRODUCT.getName())))
                .andExpect(jsonPath("$[1].id", is(TEST_PRODUCT_2.getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(TEST_PRODUCT_2.getName())));

        verify(productService, times(1))
                .getAll();

        TEST_PRODUCT.setId(null);
        TEST_PRODUCT_2.setId(null);
    }

    @Test
    void delete_inputValidId_shouldReturnOkStatus() throws Exception {
        doNothing().when(productService).delete(any());
        mockMvc.perform(delete("/products/{id}", anyLong()))
                .andExpect(status().isOk());
        verify(productService, times(1))
                .delete(anyLong());
    }
}
