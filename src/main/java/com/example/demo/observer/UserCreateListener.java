package com.example.demo.observer;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserCreateListener implements ApplicationListener<UserCreateEvent> {
    @Override
    public void onApplicationEvent(UserCreateEvent event) {
        System.out.println("Добавлен новый пользователь: "
                + event.getUser().getFirstName()
                + " "
                + event.getUser().getLastName());
    }
}
