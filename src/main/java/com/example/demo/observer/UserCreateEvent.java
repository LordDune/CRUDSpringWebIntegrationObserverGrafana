package com.example.demo.observer;

import com.example.demo.model.User;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;


public class UserCreateEvent extends ApplicationEvent {
    private User user;

    public UserCreateEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
