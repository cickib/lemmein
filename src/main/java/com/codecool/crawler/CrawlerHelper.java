//package com.codecool.crawler;
//
//
//import com.codecool.model.Flat;
//import com.codecool.repository.FlatRepository;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Service
//public class CrawlerHelper {
//
//    private CrawlerConfig config = new CrawlerConfig();
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
//
//    @Autowired
//    private FlatRepository flatRepository;
//
//    public Document getRawData() {
//        logger.info("Raw data collected from {}", config.getCompany());
//        Document doc = null;
//        try {
//            doc = Jsoup.connect(config.getURL()).get();
//        } catch (IOException e) {
//            logger.error("{} occurred while retrieving blocks of data. Details: {}", e.getCause(), e.getMessage());
//        }
//        return doc;
//    }
//
//
//    public Flat createFlat(Element element) {
//        Flat flat = new Flat();
//        flat.setCompany(config.getCompany());
//
//        String district = extractDistrict(element);
//        flat.setDistrict(district);
//
//        String sizeString = getElementText(element, config.getSizeClass());
//        flat.setSquareMeter(extractSize(sizeString));
//
//        logger.info("Flat created. Company: {}", config.getCompany());
//
//        return flat;
//    }
//
//    public void storeFlats(List<Flat> blocks) {
//        if (blocks.size() > 0) {
//            blocks.forEach(flat -> {
//                if (!(flatRepository.existsByAdUrl(flat.getAdUrl()))) {
//                    flatRepository.save(flat);
//                }
//            });
//        }
//    }
//
//    public String getElementText(Element element, String cLass) {
//        return element.getElementsByClass(cLass).text();
//    }
//
//    public int getRent(Element element) {
//        element = element.getElementsByClass(config.getRentClass()).first();
//        return Integer.parseInt(element.text().replaceAll("\\D+", ""));
//    }
//
//    public String getHref(Document doc, int i) {
//        String url = doc.getElementsByClass(config.getHrefClass()).get(i).select("a").first().attr("href");
//        return normalizeUrl(url);
//    }
//
//    private String normalizeUrl(String url) {
//        String prefixRegex = "(http://|https|www.)";
//        if (!(url.contains(config.getCompany()))) {
//            url = config.getCompany() + url;
//        }
//        String prefix = "https://www.";
//        Matcher matcher = Pattern.compile(prefixRegex).matcher(url);
//        return (matcher.find()) ? url : (prefix + url);
//    }
//
//    private String extractDistrict(Element element) {
//        String district = getElementText(element, config.getDistrictClass()).replaceAll("\\s+", "");
//        Matcher matcher = Pattern.compile(config.getDistrictRegex()).matcher(district);
//        return (matcher.find()) ? matcher.group(1) : "";
//    }
//
//    private int extractSize(String dataString) {
//        Matcher matcher = Pattern.compile(config.getSizeRegex()).matcher(dataString);
//        return (matcher.find()) ? Integer.parseInt(matcher.group(1).replaceAll("\\D+", "").replaceAll("\\s+", "")) : 0;
//    }
//
//}
