package com.codecool.crawler.crawlers;

import com.codecool.crawler.AbstractCrawler;
import com.codecool.model.Flat;
import com.codecool.util.FlatParam;
import com.codecool.util.FlatUtil;
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
            String[] params = checkParams(flatParam);
            return String.format(
                    "http://www.alberlet.hu/kiado_alberlet/berendezes:1/berleti-dij:%s-%s-ezer-ft/" +
                            "ingatlan-tipus:lakas/kerulet:%s/megye:budapest/meret:%s-%s-m2/limit:48",
                    params[0], params[1], concatDistricts(flatParam),
                    params[2], params[3]);
        } catch (Exception e) {
            return "https://www.alberlet.hu/kiado_alberlet/ingatlan-tipus:lakas/megye:budapest/limit:48";
        }
    }

    protected String concatDistricts(FlatParam flatParam) {
        String districts = "";
        for (int district : flatParam.getDistricts()) {
            districts += "+" + FlatUtil.getRomanDistrict(district).toString().toLowerCase();
        }
        return districts.replaceFirst("\\+", "");
    }

    private String extractDistrict(Element element) {
        String district = getElementText(element, districtClass).replaceAll("\\s+", "");
        String[] splittedStr = district.split("\\W+");
        return (splittedStr.length > 1) ? splittedStr[1] : "N/A";
    }

    @Override
    public Flat createFlat(Element element) {
        Flat flat = super.createFlat(element);

        String district = extractDistrict(element);
        flat.setDistrict(district);

        int rent = getRent(element);
        flat.setRent(rent);

        String street = getElementText(element, streetClass);
        flat.setAddress(street);

        return flat;
    }

}