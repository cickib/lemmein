package com.codecool.controller;

import com.codecool.model.MyUser;
import com.codecool.security.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    @ResponseBody
    public String register(@RequestBody MyUser user) throws JSONException {
        logger.info("'/register' route called - method: {}.", RequestMethod.GET);
        JSONObject response = new JSONObject().put("status", "fail");
        try {
            userService.create(user);
            logger.info("New user registered. Username: '{}'", user.getUserName());
            response.put("status", "ok");
        } catch (Exception e) {
            logger.error("{} occurred while creating a new user: {}", e.getCause(), e.getMessage());
        }
        return response.toString();
    }

}
