package com.codecool.webshopapi.dao.implementation;

import com.codecool.webshopapi.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductDao extends CrudRepository <Product, Long> {
}
