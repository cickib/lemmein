package com.codecool.controller;

import com.codecool.model.Flat;
import com.codecool.repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private FlatRepository flatRepository;

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/dashboard")
    public String dashboard(Model model) {
        List<Flat> flats = flatRepository.findAll();
        model.addAttribute("flats", flats);
        return "dashboard";
    }
}
