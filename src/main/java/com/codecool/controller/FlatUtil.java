package com.codecool.controller;

import com.codecool.model.Flat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
@Getter
@Setter
@NoArgsConstructor
public class FlatUtil {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);


    protected JSONObject createJsonFromFlat(Flat flat) {
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

    protected FlatParam getData(JSONObject json) throws IllegalAccessException {
        return extractData(json);
    }

    private FlatParam extractData(JSONObject json) throws IllegalAccessException {
        FlatParam flatParam = new FlatParam();
        for (Field field : Arrays.asList(FlatParam.class.getDeclaredFields()).subList(0, 4)) {
            field.setAccessible(true);
            int fieldVal = 0;
            try {
                String jsonValue = json.get(field.getName()).toString();
                if (jsonValue != null && jsonValue.length() > 0) {
                    fieldVal = Integer.parseInt(jsonValue);
                }
            } catch (JSONException ignored) {
                logger.error("Ignored {}: occurred while trying to create FlatParam object: {}", ignored.getClass().getSimpleName(), ignored.getMessage());
            }
            field.set(flatParam, fieldVal);
        }
        flatParam.setDistricts(extractList(json, "districts")
                .stream()
                .map(d -> d.replace("d", ""))
                .map(Integer::parseInt)
                .collect(Collectors.toList()));
        flatParam.setSites(extractList(json, "sites"));
        return flatParam;
    }

    private List<String> extractList(JSONObject json, String key) {
        List<String> extracted = new ArrayList<>();
        try {
            JSONArray jsonArray = json.getJSONArray(key);
            extracted = Arrays.asList(jsonArray
                    .join(",")
                    .replaceAll("\"", "")
                    .split(","));

            Pattern pattern = Pattern.compile(".All");
            extracted = extracted
                    .stream()
                    .filter(pattern.asPredicate().negate())
                    .collect(Collectors.toList());
        } catch (JSONException e) {
            logger.error("{} occurred while trying to extract {}: {}", e.getClass().getSimpleName(), key, e.getMessage());
        }
        return extracted;
    }

    @Getter
    @Setter
    @ToString
    class FlatParam {
        int rentFrom;
        int renTo;
        int sizeFrom;
        int sizeTo;
        List<Integer> districts;
        List<String> sites;
    }
}
