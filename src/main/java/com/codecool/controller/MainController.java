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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;


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
        JSONObject response = new JSONObject().put("status", "fail");
        try {
            flatParam = flatUtil.extractData(new JSONObject(data));
            flatParam.getSites().forEach(company -> factory.getCrawler(company, flatParam).getFlats());
            response.put("status", "ok");
        } catch (Exception e) {
            logger.error("{} occurred while processing search params: {}", e.getCause(), e.getMessage());
        }
        return response.toString();
    }

    @GetMapping(value = "/about")
    @ResponseBody
    public String about() {
        logger.info("'/about' route called - method: {}.", RequestMethod.GET);
        return getTextFromFile("./src/main/resources/text/about.txt");
    }

    private List<Flat> collectAllFlats() {
        return flatRepository.findAllByOrderByDate();
    }

    private String getTextFromFile(String fileName) {
        StringBuilder text = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(text::append);
        } catch (IOException e) {
            logger.error("{} occurred while reading from file: {}.", e.getCause(), e.getMessage());
        }
        return text.toString();
    }

}
