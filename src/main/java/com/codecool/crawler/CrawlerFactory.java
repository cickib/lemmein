package com.codecool.crawler;


import com.codecool.crawler.crawlers.AlberletHuCrawler;
import com.codecool.crawler.crawlers.IngatlanRobotCrawler;

/**
 * Based on given information, generates objects of a class implementing Crawler interface
 */
public class CrawlerFactory {

    /**
     * Factory method
     *
     * @param company enum representation of website name
     * @return specified implementation of Crawler
     */
    public static Crawler getCrawler(Company company) {
        switch (company) {
            case ALBERLETHU:
                return new AlberletHuCrawler();
            case INGATLANROBOT:
                return new IngatlanRobotCrawler();
            default:
                return null;
        }
    }

}
