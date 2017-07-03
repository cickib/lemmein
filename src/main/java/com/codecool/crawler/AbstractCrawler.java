package com.codecool.crawler;

import com.codecool.model.Flat;
import com.codecool.repository.FlatRepository;
import com.codecool.util.FlatParam;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Setter
public abstract class AbstractCrawler implements Crawler {

    protected static List<Flat> blocks = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    protected String company;
    protected String URL;
    protected String hrefClass;
    protected String streetClass;
    protected String addressClass;
    protected String rentClass;
    protected String sizeClass;
    protected String districtClass;
    protected String districtRegex = "(VII|VI|V)";
    protected String blockClass;
    protected String sizeRegex;
    @Autowired
    private FlatRepository flatRepository;

    protected String[] checkParams(FlatParam flatParam) {
        String rentFrom = (flatParam.getRentFrom() > 0) ? String.valueOf(flatParam.getRentFrom() / 1000) : "";
        String rentTo = (flatParam.getRentTo() > 0 && flatParam.getRentTo() >= Integer.parseInt(rentFrom)) ? String.valueOf(flatParam.getRentTo() / 1000) : "";
        String sizeFrom = (flatParam.getSizeFrom() > 0) ? String.valueOf(flatParam.getSizeFrom()) : "";
        String sizeTo = (flatParam.getSizeFrom() > 0 && flatParam.getSizeTo() >= Integer.parseInt(sizeFrom)) ? String.valueOf(flatParam.getSizeTo()) : "";
        return new String[]{rentFrom, rentTo, sizeFrom, sizeTo};

    }

    protected abstract String concatDistricts(FlatParam flatParam);

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
    public Flat createFlat(Element element) {
        Flat flat = new Flat();
        flat.setCompany(company);

        String sizeString = getElementText(element, sizeClass);
        flat.setSquareMeter(extractSize(sizeString));

        logger.info("Flat created. Company: {}", company);

        return flat;
    }

    @Override
    public void getFlats() {
        Document doc = getRawData();
        Elements elements = doc.getElementsByClass(blockClass);

        logger.info("{} blocks of data collected from {}", elements.size(), company);

        for (int i = 0; i < elements.size(); i++) {
            Flat flat = createFlat(elements.get(i));
            flat.setAdUrl(getHref(doc, i));
            blocks.add(flat);
        }
        storeFlats();
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

    protected String getElementText(Element element, String cLass) {
        return element.getElementsByClass(cLass).text();
    }

    protected int getRent(Element element) {
        element = element.getElementsByClass(rentClass).first();
        return Integer.parseInt(element.text().replaceAll("\\D+", ""));
    }

    protected String getHref(Document doc, int i) {
        String url = doc.getElementsByClass(hrefClass).get(i).select("a").first().attr("href");
        return normalizeUrl(url);
    }

    private String normalizeUrl(String url) {
        String prefixRegex = "(http://|https|www.)";
        if (!(url.contains(company))) {
            url = company + url;
        }
        String prefix = "https://www.";
        Matcher matcher = Pattern.compile(prefixRegex).matcher(url);
        return (matcher.find()) ? url : (prefix + url);
    }

    private int extractSize(String dataString) {
        Matcher matcher = Pattern.compile(sizeRegex).matcher(dataString);
        return (matcher.find()) ? Integer.parseInt(matcher.group(1).replaceAll("\\D+", "").replaceAll("\\s+", "")) : 0;
    }

}