package com.codecool.crawler.crawlers;

import com.codecool.crawler.AbstractCrawler;
import com.codecool.model.Flat;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;


@Service
public class AlberletHuCrawler extends AbstractCrawler {
    public AlberletHuCrawler() {
        URL = "http://www.alberlet.hu/kiado_alberlet/berendezes:1/berleti-dij:1-120-ezer-ft/ingatlan-tipus:lakas/kerulet:v+vi+vii/megye:budapest/meret:28-50-m2/limit:48";
        company = "alberlet.hu";
        hrefClass = "advert__images";
        streetClass = "advert__street";
        addressClass = "advert__address";
        rentClass = "advert__price";
        sizeClass = "advert__address-line2";
        districtClass = "advert__city";
        blockClass = "advert__details";
        sizeRegex = "(.*)(?= m2)";
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
    public Flat createFlat(Element element) {
        Flat flat = super.createFlat(element);

        int rent = getRent(element);
        flat.setRent(rent);

        String street = getElementText(element, streetClass);
        flat.setAddress(street);

        return flat;
    }

}