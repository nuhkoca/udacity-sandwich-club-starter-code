package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class JsonUtils {
    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject("name");


            String mainName = name.getString("mainName");
            sandwich.setMainName(mainName);

            String description = jsonObject.getString("description");
            sandwich.setDescription(description);

            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            sandwich.setPlaceOfOrigin(placeOfOrigin);

            Timber.d(jsonObject.getString("placeOfOrigin"));

            String image = jsonObject.getString("image");
            sandwich.setImage(image);


            JSONArray ingredients = jsonObject.getJSONArray("ingredients");
            List<String> ingredient = new ArrayList<>();
            for (int k = 0; k < ingredients.length(); k++) {
                ingredient.add(ingredients.getString(k));
            }

            sandwich.setIngredients(ingredient);

            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            List<String> known = new ArrayList<>();
            for (int l = 0; l < alsoKnownAs.length(); l++) {
                known.add(alsoKnownAs.getString(l));
            }

            sandwich.setAlsoKnownAs(known);

        } catch (JSONException e) {
            Timber.d(e.getMessage());
        }

        return sandwich;
    }
}