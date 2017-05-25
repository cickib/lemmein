package com.codecool.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    protected FlatRepository flatRepository;
    @Autowired
    private IngatlanRobotCrawler ingatlanRobotCrawler;

    private AlberletHuCrawler alberletHuCrawler;

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

    @GetMapping(value = "/results")
    @ResponseBody
    public String results() throws JSONException {
        logger.info("'/results' route called - method: {}.", RequestMethod.GET);
        JSONArray jsonArray = null;

        if (collectAllFlats().size() > 0) {
            logger.info("{} flats collected.", collectAllFlats().size());
            List<JSONObject> js = collectAllFlats()
                    .stream()
                    .map(this::createJsonFromFlat)
                    .collect(Collectors.toList());
            jsonArray = new JSONArray(js);
        }

        return new JSONObject().put("flats", jsonArray).toString();
    }

    private JSONObject createJsonFromFlat(Flat flat) {
        JSONObject json = new JSONObject();
        List<Field> fields = Arrays.asList(Flat.class.getDeclaredFields()).subList(0, 7);
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                json.put(field.getName(), field.get(flat));
            }
        } catch (IllegalAccessException | JSONException ignored) {
            logger.error("Ignored {}: occurred while trying to collect profile data {}", ignored.getClass().getSimpleName(), ignored.getMessage());
        }
        return json;
    }

}
