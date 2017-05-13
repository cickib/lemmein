package com.codecool.crawler;

import com.codecool.model.Flat;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;


@Service
public interface Crawler {

    Document getRawData();

    Flat createFlat(Element element);

    void getFlats();

    void storeFlats();

}
