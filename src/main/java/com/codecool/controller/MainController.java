package com.codecool.controller;

import com.codecool.crawler.CrawlerFactory;
import com.codecool.model.Flat;
import com.codecool.repository.FlatRepository;
import com.codecool.util.FlatParam;
import com.codecool.util.FlatUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    protected FlatRepository flatRepository;

    @Autowired
    private FlatUtil flatUtil;

    @Autowired
    private CrawlerFactory factory;

    private FlatParam flatParam;

    @GetMapping(value = "/")
    public String index() {
        logger.info("'/' route called - method: {}.", RequestMethod.GET);
        return "index";
    }

    @GetMapping(value = "/dashboard")
    public String dashboard() {
        logger.info("'/dashboard' route called - method: {}.", RequestMethod.GET);
        return "dashboard";
    }

    @GetMapping(value = "/results")
    @ResponseBody
    public String results() throws JSONException {
        logger.info("'/results' route called - method: {}.", RequestMethod.GET);
        return flatUtil.collectFlatsToJson(collectAllFlats());
    }

    @PostMapping(value = "/search")
    @ResponseBody
    public String getSearchParams(@RequestBody String data) throws JSONException, IllegalAccessException {
        logger.info("'/search' route called - method: {}.", RequestMethod.POST);
        flatParam = flatUtil.extractData(new JSONObject(data));
        flatParam.getSites().forEach(company -> factory.getCrawler(company, flatParam).getFlats());
        return "ok";
    }

    @GetMapping(value = "/search")
    public String renderSearch() {
        logger.info("'/search' route called - method: {}.", RequestMethod.GET);
        return "search_bar";
    }

    @GetMapping(value = "/display")
    public String display() {
        logger.info("'/display' route called - method: {}.", RequestMethod.GET);
        return "display_flats";
    }

    private List<Flat> collectAllFlats() {
        return flatRepository.findAllByOrderByDate();
    }

}
