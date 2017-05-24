package com.codecool.crawler.crawlers;

import com.codecool.crawler.AbstractCrawler;
import com.codecool.model.Flat;
import org.jsoup.nodes.Element;


public class AlberletHuCrawler extends AbstractCrawler {
    public AlberletHuCrawler(String rentFrom, String rentTo, String sizeFrom, String sizeTo, String[] districts) {
        URL = createUrl(rentFrom, rentTo, sizeFrom, sizeTo, districts);
        company = "alberlet.hu";
        hrefClass = "advert__images";
        streetClass = "advert__street";
        addressClass = "advert__address";
        rentClass = "advert__price";
        sizeClass = "advert__address-line2";
        districtClass = "advert__city";
        blockClass = "advert__details";
        sizeRegex = "(.*)(?= m2)";
        System.out.println("URL = " + URL);
    }

    private String createUrl(String rentFrom, String rentTo, String sizeFrom, String sizeTo, String[] districts) {
        String plus = "+";
        String part1 = String.format("http://www.alberlet.hu/kiado_alberlet/berendezes:1/berleti-dij:%s-%s-ezer-ft/ingatlan-tipus:lakas/kerulet:", rentFrom, rentTo);
        String part2 = "";
        for (String district : districts) {
            part2 += district + plus;
        }
        part2 = part2.replaceAll("\\+$", "");
        String part3 = String.format("/megye:budapest/meret:%s-%s-m2/limit:48", sizeFrom, sizeTo);
        return part1 + part2 + part3;
    }

    @Override
    public Flat createFlat(Element element) {
        Flat flat = super.createFlat(element);

        int rent = getRent(element);
        flat.setRent(rent);

        String street = getElementText(element, streetClass);
        flat.setAddress(street);

        return flat;
    }

}