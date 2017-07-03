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
            String[] params = checkParams(flatParam);
            return "https://www.ingatlanrobot.hu/ingatlanok/kiado-lakas--varos=" +
                    concatDistricts(flatParam) +
                    "--ar=" + params[0] + "-" + params[1] +
                    "--terulet=" + params[2] + "-" + params[3] + "/";
        } catch (Exception e) {
            return "https://www.ingatlanrobot.hu/ingatlanok/kiado-lakas--varos=Budapest--ar=--terulet=/";
        }
    }

    protected String concatDistricts(FlatParam flatParam) {
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

    private String extractDistrict(Element element) {
        String district = getElementText(element, districtClass);
        String[] splittedStr = district.split("\\s+");
        return (splittedStr.length > 1) ? splittedStr[3] : "N/A";
    }

    @Override
    public Flat createFlat(Element element) {
        Flat flat = super.createFlat(element);

        String district = extractDistrict(element);
        flat.setDistrict(district);

        int rent = getRent(element);
        flat.setRent(fixRentDecimals(rent));

        String[] str = getElementText(element, streetClass).split(districtRegex);
        String street = str[str.length - 1].replace(streetRegex, "").trim();
        flat.setAddress(street);

        return flat;
    }
}
