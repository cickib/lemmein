package com.codecool.util;

import com.codecool.crawler.Company;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@ToString
public class FlatParam {
    private int rentFrom;
    private int rentTo;
    private int sizeFrom;
    private int sizeTo;
    private List<Integer> districts;
    private List<Company> sites;
}