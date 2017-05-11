package com.codecool.crawler.crawlers;

import com.codecool.crawler.AbstractCrawler;
import com.codecool.model.Flat;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class IngatlanRobotCrawler extends AbstractCrawler {

    private String streetRegex = "(I )";

    public IngatlanRobotCrawler() {
        URL = "https://www.ingatlanrobot.hu/ingatlanok/kiado-lakas--varos=Budapest--varosresz=Ter%C3%A9zv%C3%A1ros,Erzs%C3%A9betv%C3%A1ros,Lip%C3%B3tv%C3%A1ros--ar=1-120--terulet=28-/";
        company = "ingatlanrobot.hu";
        blockClass = "main_normal_ad";
        hrefClass = "ads_img_main_box";
        streetClass = "ad_t_l1b";
        rentClass = "ads_list_price";
        sizeClass = "ads_list";
        districtClass = streetClass;
     sizeRegex = "(?<=ezer)(.*)(?=m²)";
    }

    @Override
    public void collectFlatData() {
        Document doc = getRawData();
        Elements elements = doc.getElementsByClass(blockClass);

        logger.info("{} blocks of data collected from {}", elements.size(), company);

        for (int i = 0; i < elements.size(); i++) {
            Flat flat = createFlat(elements.get(i));
            flat.setAdUrl(company + getHref(doc, i));
            blocks.add(flat);
        }
        storeFlats();
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
