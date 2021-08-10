package com.codecool.webshopapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer locationCode;
    private String name;
    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private List<User> users;
}
