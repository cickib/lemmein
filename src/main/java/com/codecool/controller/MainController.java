package com.codecool.controller;

import com.codecool.crawler.crawlers.AlberletHuCrawler;
import com.codecool.crawler.crawlers.IngatlanRobotCrawler;
import com.codecool.model.Flat;
import com.codecool.repository.FlatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    protected FlatRepository flatRepository;
    @Autowired
    private AlberletHuCrawler alberletHuCrawler;
    @Autowired
    private IngatlanRobotCrawler ingatlanRobotCrawler;

    @GetMapping(value = "/")
    public String index() {
        logger.info("'/' route called - method: {}.", RequestMethod.GET);
        return "index";
    }

    @GetMapping(value = "/dashboard")
    public String dashboard(Model model) {
        logger.info("'/dashboard' route called - method: {}.", RequestMethod.GET);
        model.addAttribute("flats", collectAllFlats());
        return "dashboard";
    }

    private List<Flat> collectAllFlats() {
        alberletHuCrawler.getFlats();
        ingatlanRobotCrawler.getFlats();
        return flatRepository.findAllByOrderByDate();
    }

}
