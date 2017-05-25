package com.codecool.controller;

import com.codecool.crawler.Company;
import com.codecool.crawler.CrawlerFactory;
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

import java.util.Arrays;
import java.util.List;


@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    protected FlatRepository flatRepository;

    private static List<Company> companies = Arrays.asList(Company.values());

    @Autowired
    private CrawlerFactory factory;

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
        companies.forEach(company -> factory.getCrawler(company).getFlats());
        return flatRepository.findAllByOrderByDate();
    }

}
