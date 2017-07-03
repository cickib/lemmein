package com.codecool.crawler.crawlers;

import com.codecool.crawler.AbstractCrawler;
import com.codecool.model.Flat;
import com.codecool.util.FlatParam;
import com.codecool.util.FlatUtil;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;


@Service
public class IngatlanRobotCrawler extends AbstractCrawler {

    private String streetRegex = "(I )";

    public IngatlanRobotCrawler() {
        company = "ingatlanrobot.hu";
        blockClass = "main_normal_ad";
        hrefClass = "ads_img_main_box";
        streetClass = "ad_t_l1b";
        rentClass = "ads_list_price";
        sizeClass = "ads_list";
        districtClass = streetClass;
        sizeRegex = "(?<=ezer)(.*)(?=m²)";
    }

    public String createUrl(FlatParam flatParam) {
        try {
            return "https://www.ingatlanrobot.hu/ingatlanok/kiado-lakas--varos=" +
                    concatDistricts(flatParam) + "--ar=" +
                    flatParam.getRentFrom() / 1000 + "-" + flatParam.getRentTo() / 1000 +
                    "--terulet=" + flatParam.getSizeFrom() + "-" + flatParam.getSizeTo() + "/";
        } catch (NullPointerException npe) {
            return "https://www.ingatlanrobot.hu/ingatlanok/kiado-lakas--varos=Budapest--ar=--terulet=/";

        }
    }

    private String concatDistricts(FlatParam flatParam) {
        String districts = "";
        for (int district : flatParam.getDistricts()) {
            districts += ",";
            districts += "Budapest-" + FlatUtil.getRomanDistrict(district) + "-kerulet";
        }
        return districts.replaceFirst(",", "");
    }

    private int fixRentDecimals(int rent) {
        rent *= 100;
        return ((long) (Math.log10(rent) + 1) == 6) ? rent : rent * 10;
    }

    @Override
    public Flat createFlat(Element element) {
        Flat flat = super.createFlat(element);

        int rent = getRent(element);
        flat.setRent(fixRentDecimals(rent));

        String[] str = getElementText(element, streetClass).split(districtRegex);
        String street = str[str.length - 1].replace(streetRegex, "").trim();
        flat.setAddress(street);

        return flat;
    }
}
