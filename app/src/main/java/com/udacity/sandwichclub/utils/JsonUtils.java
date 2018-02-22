package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class JsonUtils {

    private static final String NAME_FIELD = "name";
    private static final String MAIN_NAME_FIELD = "mainName";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String PLACE_OF_ORIGIN_FIELD = "placeOfOrigin";
    private static final String IMAGE_FIELD = "image";
    private static final String INGREDIENTS_FIELD = "ingredients";
    private static final String ALSO_KNOWN_AS_FIELD = "alsoKnownAs";

    public static Sandwich parseSandwichJson(String json) {

        String mainName = "";
        String description = "";
        String placeOfOrigin = "";
        String image = "";
        List<String> known = null;
        List<String> ingredient = null;

        try {
            JSONObject jsonObject = new JSONObject(json);

            if (jsonObject.has(NAME_FIELD)) {
                JSONObject name = jsonObject.getJSONObject(NAME_FIELD);
                mainName = name.optString(MAIN_NAME_FIELD, null);


                if (name.has(ALSO_KNOWN_AS_FIELD) && !name.isNull(ALSO_KNOWN_AS_FIELD)) {
                    JSONArray alsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS_FIELD);
                    known = new ArrayList<>();
                    for (int l = 0; l < alsoKnownAs.length(); l++) {
                        known.add(alsoKnownAs.optString(l, null));
                    }
                }
            }


            if (jsonObject.has(DESCRIPTION_FIELD)) {
                description = jsonObject.optString(DESCRIPTION_FIELD, null);
            }


            if (jsonObject.has(PLACE_OF_ORIGIN_FIELD)) {
                placeOfOrigin = jsonObject.optString(PLACE_OF_ORIGIN_FIELD, null);
            }


            if (jsonObject.has(IMAGE_FIELD)) {
                image = jsonObject.optString(IMAGE_FIELD, null);
            }


            if (jsonObject.has(INGREDIENTS_FIELD) && !jsonObject.isNull(INGREDIENTS_FIELD)) {
                JSONArray ingredients = jsonObject.getJSONArray(INGREDIENTS_FIELD);
                ingredient = new ArrayList<>();
                for (int k = 0; k < ingredients.length(); k++) {
                    ingredient.add(ingredients.optString(k, null));
                }
            }

        } catch (JSONException e) {
            Timber.d(e.getMessage());
        }

        return Sandwich.create(mainName, known, description, placeOfOrigin, image, ingredient);
    }
}