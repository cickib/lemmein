package com.codecool.crawler;


import com.codecool.crawler.crawlers.AlberletHuCrawler;
import com.codecool.crawler.crawlers.IngatlanRobotCrawler;


public class CrawlerFactory {

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
