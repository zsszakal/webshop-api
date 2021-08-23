package com.codecool.webshopapi.integration;

import com.codecool.webshopapi.model.Product;

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
public class ProductIntegrationTests {

    @LocalServerPort
    private int port;

    private String baseUrl;


    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp() {
        this.baseUrl = "http://localhost:" + port + "/product";
    }

    @Test
    public void getProductById_withOnePostedProduct_returnsProductWithSameId() {
        Product testProduct = createProduct( 6666, "Vienna");
        Product testProductResult = testRestTemplate.postForObject(baseUrl, testProduct, Product.class);
        Product result = testRestTemplate.getForObject(baseUrl + "/" + testProductResult.getId(), Product.class);
        assertEquals(testProductResult.getId(), result.getId());
    }

    @Test
    public void updateProduct_withoutProduct_returnsBadRequest() {
        Product testProduct = createProduct( 6666, "Vienna");

        HttpEntity<Product> httpEntity = createHttpEntityWithMediaTypeJson(testProduct);
        ResponseEntity<Object> putResponse = testRestTemplate
                .exchange(baseUrl + "/100", HttpMethod.PUT, httpEntity, Object.class);

        assertEquals(HttpStatus.NOT_FOUND, putResponse.getStatusCode());
    }


    private HttpEntity<Product> createHttpEntityWithMediaTypeJson(Product testProduct) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(testProduct, headers);
    }


    private Product createProduct(int productCode, String name) {
        Product product = new Product();
        product.setPrice(product.getPrice());
        product.setName(name);
        return product;
    }
}
