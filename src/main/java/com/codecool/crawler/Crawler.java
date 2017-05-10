package com.codecool.crawler;


import com.codecool.model.Flat;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface Crawler {

    Document getRawData();

    List<Flat> getFlats();

    void collectFlatData();

    void storeFlats();

    Flat createFlat(Element element);

}
