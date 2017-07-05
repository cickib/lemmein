package com.codecool.config;

import com.codecool.model.MyUser;
import com.codecool.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class DataLoader {

    @Autowired
    private UserService userService;

    @PostConstruct
    private void populateDb() {
        userService.create(new MyUser("leiwand", "1234"));
    }
}
