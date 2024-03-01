package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.observer.UserCreateEvent;
import com.example.demo.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    private ApplicationEventPublisher publisher;


    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User saveUser(User user){
        publisher.publishEvent(new UserCreateEvent(this, user));
        return userRepository.save(user);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public User getUser(int id) {
        return userRepository.getUser(id);
    }
}
