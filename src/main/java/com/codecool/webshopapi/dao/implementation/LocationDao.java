package com.codecool.webshopapi.dao.implementation;

import com.codecool.webshopapi.model.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationDao extends CrudRepository <Location, Long> {
}
