package com.codecool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = "/register")
    public String register(@RequestBody String data) {
        logger.info("'/register' route called - method: {}.", RequestMethod.GET);
        return "ok";
    }

}
