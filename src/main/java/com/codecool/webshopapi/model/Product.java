package com.codecool.webshopapi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Product {

    private Long id;
    private String name;
    private int price;
    private String manufacturer;

}
