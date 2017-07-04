package com.codecool.util;

import com.codecool.controller.MainController;
import com.codecool.crawler.Company;
import com.codecool.model.Flat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
@Getter
@Setter
@NoArgsConstructor
public class FlatUtil {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    public String collectFlatsToJson(List<Flat> flats) {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = null;

        if (flats.size() > 0) {
            logger.info("{} flats collected.", flats.size());
            List<JSONObject> js = flats
                    .stream()
                    .map(this::createJsonFromFlat)
                    .collect(Collectors.toList());
            jsonArray = new JSONArray(js);
        }
        try {
            result.put("flats", jsonArray);
        } catch (JSONException e) {
            logger.error("{} occurred while collecting flats to json: {}", e.getClass().getSimpleName(), e.getMessage());
        }
        return result.toString();
    }

    private JSONObject createJsonFromFlat(Flat flat) {
        JSONObject json = new JSONObject();
        List<Field> fields = Arrays.asList(Flat.class.getDeclaredFields()).subList(0, 7);
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                json.put(field.getName(), field.get(flat));
            }
        } catch (IllegalAccessException | JSONException ignored) {
            logger.error("Ignored {}: occurred while trying to collect profile data: {}", ignored.getClass().getSimpleName(), ignored.getMessage());
        }
        return json;
    }


    public FlatParam extractData(JSONObject json) throws IllegalAccessException {
        FlatParam flatParam = new FlatParam();
        for (Field field : Arrays.asList(FlatParam.class.getDeclaredFields()).subList(0, 4)) {
            field.setAccessible(true);
            field.set(flatParam, getIntFromJson(json, field));
        }
        setDistrictData(flatParam, json);
        setSiteData(flatParam, json);

        return flatParam;
    }

    private int getIntFromJson(JSONObject json, Field field) {
        int val = 0;
        try {
            String jsonValue = json.get(field.getName()).toString();
            if (jsonValue != null && jsonValue.length() > 0) {
                val = Integer.parseInt(jsonValue);
            }
        } catch (JSONException e) {
            logger.error("Ignored {}: occurred while trying to get integer value from json. Details: {}",
                    e.getClass().getSimpleName(), e.getMessage());
        }
        return val;
    }

    private List<String> extractList(JSONObject json, String key) {
        List<String> extracted = new ArrayList<>();

        try {
            JSONObject list = (JSONObject) json.get(key);
            Iterator jsonIt = list.keys();
            while (jsonIt.hasNext()) {
                String current = jsonIt.next().toString();
                if ((Boolean) list.get(current)) {
                    extracted.add(current);
                }
            }
        } catch (JSONException e) {
            logger.error("{} occurred while trying to extract {}: {}", e.getClass().getSimpleName(), key, e.getMessage());
        }
        return extracted;
    }

    private void setDistrictData(FlatParam flatParam, JSONObject json) {
        List<String> extracted = extractList(json, "districts");

        if (extracted.size() > 0) {
            flatParam.setDistricts(extracted
                    .stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toList()));
        } else {
            flatParam.setDistricts(IntStream.range(1, 23).boxed().collect(Collectors.toList()));
        }
    }

    private void setSiteData(FlatParam flatParam, JSONObject json) {
        List<String> extracted = extractList(json, "sites");
        if (extracted.size() > 0) {
            flatParam.setSites(extracted
                    .stream()
                    .map(Company::valueOf)
                    .collect(Collectors.toList()));
        } else {
            flatParam.setSites(Arrays.asList(Company.values()));
        }
    }

    public static Roman getRomanDistrict(int district) {
        return Roman.values()[district - 1];
    }

}
