package com.codecool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class RoutesController {
    private static final Logger logger = LoggerFactory.getLogger(RoutesController.class);

    @GetMapping(value = {"/", "/login", "/logout", "/display"})
    public String index() {
        logger.info("'/' route called - method: {}.", RequestMethod.GET);
        return "index";
    }
}
