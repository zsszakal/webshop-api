package com.codecool.webshopapi.repository;

import com.codecool.webshopapi.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository <Product, Long> {
}
