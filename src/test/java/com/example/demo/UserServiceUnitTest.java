package com.example.demo;


import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {
    // изолируем репозиторий
    @Mock
    private UserRepository userRepository; // изолируем репозиторий

    // объект тестируемого класса
    @InjectMocks
    private UserService userService; // объект тестируемого класса

    @Test
    public void saveUserHappyFlowUnitTest() {

        // создаем тестового юзера и заполняем его поля: id, firsName, lastName
        User user = new User();
        user.setId(1);
        user.setFirstName("Sergey");
        user.setLastName("LD");

        // подставляем результат работы репозитория - возвращает созданного user
        given(userRepository.save(user)).willReturn(Optional.of(user).get());

        // выполнение метода
        userService.saveUser(user);

        // проверка, что результат выполнения метода соответствует ожидаемым значениям
        verify(userRepository).save(new User(1, "Sergey", "LD"));
    }
}
