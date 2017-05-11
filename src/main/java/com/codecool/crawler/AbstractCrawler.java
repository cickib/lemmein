package com.codecool.crawler;

import com.codecool.model.Flat;
import com.codecool.repository.FlatRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public abstract class AbstractCrawler implements Crawler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    protected List<Flat> blocks = new ArrayList<>();

    protected String company;
    protected String URL;
    protected String hrefClass;

    protected String streetClass;
    protected String addressClass;
    protected String rentClass;
    protected String sizeClass;
    protected String districtClass;
    protected String districtRegex = "\\(VII|VI|V\\)";
    protected String blockClass;
    
    @Autowired
    protected FlatRepository flatRepository;

    @Override
    public List<Flat> getFlats() {
        collectFlatData();
        return flatRepository.findAll();
    }

    @Override
    public Document getRawData() {
        logger.info("Raw data collected from {}", company);
        Document doc = null;
        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            logger.error("{} occurred while retrieving blocks of data. Details: {}", e.getCause(), e.getMessage());
        }
        return doc;
    }

    @Override
    public void storeFlats() {
        if (blocks.size() > 0) {
            blocks.forEach(flat -> {
                if (!(flatRepository.existsByAdUrl(flat.getAdUrl()))) {
                    flatRepository.save(flat);
                }
            });
        }
    }

    protected String getHref(Document doc, int i) {
        return doc.getElementsByClass(hrefClass).get(i).select("a").first().attr("href");
    }

    protected int getRent(Element element) {
        return Integer.parseInt(element.text().replaceAll("\\D+", ""));
    }

    protected String extractDistrict(Element element) {
        String district = element.getElementsByClass(districtClass).text();
        String retained = district.replaceAll(districtRegex, "");
        district = Arrays.stream(district.split("")).filter(c ->
                !retained.contains(c)).collect(Collectors.joining());
        return district;
    }

}