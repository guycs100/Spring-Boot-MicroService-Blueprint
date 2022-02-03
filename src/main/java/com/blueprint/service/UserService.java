package com.blueprint.service;

import com.blueprint.model.User;
import com.blueprint.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public User newUser(User user){
        User user2 = repository.save(user);
        return user2;
    }
    public Optional<User> getUserById(long id){
        Optional<User> user = repository.findById(id);
        return user;
    }

    @Transactional(timeout = 10)
    public void updateSamePhoneForAllUsers(int phoneNumber) {
        List<User> users = repository.findAll();
        for(User user : users) {
            user.setPhoneNumber(phoneNumber);
            repository.save(user);
        }
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex) {}
        System.out.println("exit updateSamePhoneForAllUsers");
        //throw new RuntimeException("stam");
    }

    @PostConstruct
    public void post() {
        System.out.println("Post Construct");
    }
}
