package com.codecool.crawler.crawlers;

import com.codecool.crawler.AbstractCrawler;
import com.codecool.model.Flat;
import com.codecool.util.FlatParam;
import lombok.Setter;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;


@Service
@Setter
public class AlberletHuCrawler extends AbstractCrawler {
    public AlberletHuCrawler() {
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

    public String createUrl(FlatParam flatParam) {
        try {
            // TODO district conversion, furniture
            return String.format(
                    "http://www.alberlet.hu/kiado_alberlet/berendezes:1/berleti-dij:%s-%s-ezer-ft/ingatlan-tipus:lakas/kerulet:v+vi+vii/megye:budapest/meret:%s-%s-m2/limit:48",
                    flatParam.getRentFrom() / 1000, flatParam.getRentTo() / 1000, flatParam.getSizeFrom(), flatParam.getSizeTo());
        } catch (NullPointerException npe) {
            return "https://www.alberlet.hu/kiado_alberlet/ingatlan-tipus:lakas/megye:budapest/limit:48";

        }
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