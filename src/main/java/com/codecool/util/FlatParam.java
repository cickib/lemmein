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
    int rentFrom;
    int rentTo;
    int sizeFrom;
    int sizeTo;
    List<Integer> districts;
    List<Company> sites;
}