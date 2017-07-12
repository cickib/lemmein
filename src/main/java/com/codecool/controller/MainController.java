package com.codecool.controller;

import com.codecool.crawler.CrawlerFactory;
import com.codecool.util.FlatParam;
import com.codecool.util.FlatUtil;
import com.codecool.util.TextReader;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private FlatUtil flatUtil;

    @Autowired
    private CrawlerFactory factory;

    private FlatParam flatParam;

    @Autowired
    private TextReader textReader;

    @GetMapping(value = "/results")
    @ResponseBody
    public String results() throws JSONException {
        logger.info("'/results' route called - method: {}.", RequestMethod.GET);
        return flatUtil.collectFlatsToJson();
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
        return textReader.getTextFromFile("./src/main/resources/text/about.txt");
    }

}
