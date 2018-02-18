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
        Sandwich sandwich = new Sandwich();

        try {
            JSONObject jsonObject = new JSONObject(json);

            if (jsonObject.has(NAME_FIELD)) {
                JSONObject name = jsonObject.getJSONObject(NAME_FIELD);
                String mainName = name.optString(MAIN_NAME_FIELD, null);
                sandwich.setMainName(mainName);

                if (name.has(ALSO_KNOWN_AS_FIELD) && !name.isNull(ALSO_KNOWN_AS_FIELD)) {
                    JSONArray alsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS_FIELD);
                    List<String> known = new ArrayList<>();
                    for (int l = 0; l < alsoKnownAs.length(); l++) {
                        known.add(alsoKnownAs.optString(l, null));
                    }

                    sandwich.setAlsoKnownAs(known);
                }
            }


            if (jsonObject.has(DESCRIPTION_FIELD)) {
                String description = jsonObject.optString(DESCRIPTION_FIELD, null);
                sandwich.setDescription(description);
            }


            if (jsonObject.has(PLACE_OF_ORIGIN_FIELD)) {
                String placeOfOrigin = jsonObject.optString(PLACE_OF_ORIGIN_FIELD, null);
                sandwich.setPlaceOfOrigin(placeOfOrigin);
            }


            if (jsonObject.has(IMAGE_FIELD)) {
                String image = jsonObject.optString(IMAGE_FIELD, null);
                sandwich.setImage(image);
            }


            if (jsonObject.has(INGREDIENTS_FIELD) && !jsonObject.isNull(INGREDIENTS_FIELD)) {
                JSONArray ingredients = jsonObject.getJSONArray(INGREDIENTS_FIELD);
                List<String> ingredient = new ArrayList<>();
                for (int k = 0; k < ingredients.length(); k++) {
                    ingredient.add(ingredients.optString(k, null));
                }

                sandwich.setIngredients(ingredient);
            }

        } catch (JSONException e) {
            Timber.d(e.getMessage());
        }

        return sandwich;
    }
}