package com.codecool.controller;

import com.codecool.crawler.Company;
import com.codecool.crawler.CrawlerFactory;
import com.codecool.crawler.crawlers.AlberletHuCrawler;
import com.codecool.crawler.crawlers.IngatlanRobotCrawler;
import com.codecool.model.Flat;
import com.codecool.repository.FlatRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    protected FlatRepository flatRepository;

    private static List<Company> companies = Arrays.asList(Company.values());

    @Autowired
    private FlatUtil flatUtil;

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

    @GetMapping(value = "/results")
    @ResponseBody
    public String results() throws JSONException {
        logger.info("'/results' route called - method: {}.", RequestMethod.GET);
        JSONArray jsonArray = null;

        if (collectAllFlats().size() > 0) {
            logger.info("{} flats collected.", collectAllFlats().size());
            List<JSONObject> js = collectAllFlats()
                    .stream()
                    .map(flat ->flatUtil.createJsonFromFlat(flat))
                    .collect(Collectors.toList());
            jsonArray = new JSONArray(js);
        }

        return new JSONObject().put("flats", jsonArray).toString();
    }


    @PostMapping(value = "/search")
    @ResponseBody
    public String getParams(@RequestBody String data) throws JSONException, IllegalAccessException {
        FlatUtil.FlatParam flatParam = flatUtil.getData(new JSONObject(data));
        System.out.println(flatParam);
        System.out.println(data);
        return "ok";
    }
}
