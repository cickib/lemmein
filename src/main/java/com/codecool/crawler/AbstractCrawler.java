package com.codecool.crawler;

import com.codecool.model.Flat;
import com.codecool.repository.FlatRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public abstract class AbstractCrawler implements Crawler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    protected List<Flat> blocks = new ArrayList<>();
    protected String company = "company";
    protected String URL = "url";

    protected String hrefClass = "href";
    protected String streetClass = "street";
    protected String addressClass = "address";
    protected String rentClass = "rent";
    protected String sizeClass = "squareMeter";
    protected String districtClass = "district";
    protected String blockClass = "block";


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

}