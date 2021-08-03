package com.codecool.webshopapi.service;

import com.codecool.webshopapi.model.Location;
import com.codecool.webshopapi.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location add(Location location) {
        return locationRepository.save(location);
    }

    public Location getById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    public List<Location> getAll() {
        List<Location> locations = new ArrayList<>();
        locationRepository.findAll().forEach(locations::add);
        return locations;
    }

    public void delete(Long id) {
        locationRepository.deleteById(id);
    }
}