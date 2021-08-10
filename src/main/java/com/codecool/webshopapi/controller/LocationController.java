package com.codecool.webshopapi.controller;

import com.codecool.webshopapi.model.Location;
import com.codecool.webshopapi.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping
    public Location add(@RequestBody Location location) {
        return locationService.add(location);
    }

    @PutMapping("/{id}")
    public Location update(@RequestBody Location location, @PathVariable Long id) {
        location.setId(id);
        return locationService.add(location);
    }

    @GetMapping("/{id}")
    public Location getById(@PathVariable Long id) {
        return locationService.getById(id);
    }

    @GetMapping
    public List<Location> getAll() {
        return locationService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        locationService.delete(id);
    }
}
