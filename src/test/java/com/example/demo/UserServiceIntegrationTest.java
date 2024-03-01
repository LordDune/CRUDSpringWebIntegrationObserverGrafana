package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService; // объект тестируемого класса

    @Test
    void saveUserHappyFlowIntegrationTest() {
        // создаем тестового юзера и заполняем его поля: id, firsName, lastName
        User user = new User();
        user.setId(1);
        user.setFirstName("Sergey");
        user.setLastName("LD");

        // добавляем в репозиторий нового юзера с двумя полями: firsName, lastName (id должен присвоиться автоматически)
        userRepository.save(new User("Sergey", "LD"));
        // проверяем, совпадают ли созданный нами user и user, полученный из репозитория
        assertEquals(user, userService.getUser(1)); //
    }
}
