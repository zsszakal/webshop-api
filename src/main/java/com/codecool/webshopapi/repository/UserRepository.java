package com.codecool.webshopapi.repository;

import com.codecool.webshopapi.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
