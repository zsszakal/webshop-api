package com.codecool.webshopapi.dao.implementation;

import com.codecool.webshopapi.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {
}
