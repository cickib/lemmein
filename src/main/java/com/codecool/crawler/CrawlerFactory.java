package com.codecool.crawler;


import com.codecool.crawler.crawlers.AlberletHuCrawler;
import com.codecool.crawler.crawlers.IngatlanRobotCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Based on given information, generates objects of a class implementing Crawler interface
 */
@Service
public class CrawlerFactory {

    @Autowired
    private IngatlanRobotCrawler ingatlanRobotCrawler;

    @Autowired
    private AlberletHuCrawler alberletHuCrawler;

    /**
     * Factory method
     *
     * @param company enum representation of website name
     * @return specified implementation of Crawler
     */
    public Crawler getCrawler(Company company) {
        switch (company) {
            case ALBERLETHU:
                return alberletHuCrawler;
            case INGATLANROBOT:
                return ingatlanRobotCrawler;
            default:
                return null;
        }
    }

}
