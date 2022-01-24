package com.blueprint.service;

import com.blueprint.model.User;
import com.blueprint.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public static User newUser(User user){
        User user2 = repository.save(user);
        return user2;
    }
    public static Optional<User> getUserById(long id){
        Optional<User> user = repository.findById(id);
        return user;
    }
}
