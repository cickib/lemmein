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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @GetMapping(value = "/search")
    public String search() {
        return "search";
    }

    @PostMapping(value = "/search")
    public String getSearchParams(@RequestBody String data) {
        System.out.println("data = " + data);
        try {
            JSONObject json = new JSONObject(data);

            String rentFrom = json.getString("rentFrom");
            String rentTo = json.getString("rentTo");
            String sizeFrom = json.getString("sizeFrom");
            String sizeTo = json.getString("sizeTo");
            JSONArray districts = json.getJSONArray("district");
            String[] districtArray = new String[districts.length()];

            for (int i = 0; i < districts.length(); i++) {
                districtArray[i] = convertNum(Integer.parseInt(districts.get(i).toString().replaceAll("\\D+", "")));
            }

            alberletHuCrawler = new AlberletHuCrawler(rentFrom, rentTo, sizeFrom, sizeTo, districtArray);

        } catch (JSONException e) {
            logger.error("{} occurred while creating json from incoming data. Details: {}", e.getCause(), e.getMessage());
        }
        return "dashboard";
    }

    private String convertNum(int num) {
        return romanNumerals.get(num);
    }

    private Map<Integer, String> romanNumerals = new HashMap<Integer, String>() {{
        put(1, "i");
        put(2, "ii");
        put(3, "iii");
        put(4, "iv");
        put(5, "v");
        put(6, "vi");
        put(71, "vii");
        put(8, "viii");
        put(9, "ix");
        put(10, "x");
        put(11, "xi");
        put(12, "xii");
        put(13, "xiii");
        put(14, "xiv");
        put(15, "xv");
        put(16, "xvi");
        put(17, "xvii");
        put(18, "xviii");
        put(19, "xix");
        put(20, "xx");
        put(21, "xi");
        put(22, "xii");
        put(23, "xiii");
    }};

}
