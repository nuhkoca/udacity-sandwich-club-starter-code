package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import timber.log.Timber;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String NULL_TEXT = "Not available";

    private static final String IMAGE_STATE = "image";
    private static final String ORIGIN_STATE = "origin";
    private static final String DESCRIPTION_STATE = "description";
    private static final String INGREDIENTS_STATE = "ingredients";
    private static final String ALSO_KNOWN_SATE = "also-known";

    private static String imagePath = "";

    private TextView originTv, descriptionTv, ingredientsTv, alsoKnownAsTV;
    ImageView ingredientsIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        alsoKnownAsTV = findViewById(R.id.also_known_tv);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null) {
            Picasso.with(this)
                    .load(savedInstanceState.getString(IMAGE_STATE))
                    .into(ingredientsIv);

            originTv.setText(savedInstanceState.getString(ORIGIN_STATE));
            descriptionTv.setText(savedInstanceState.getString(DESCRIPTION_STATE));
            ingredientsTv.setText(savedInstanceState.getString(INGREDIENTS_STATE));
            alsoKnownAsTV.setText(savedInstanceState.getString(ALSO_KNOWN_SATE));
        } else {
            Intent intent = getIntent();
            if (intent == null) {
                closeOnError();
            }

            int position = 0;
            if (intent != null) {
                position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
            }
            if (position == DEFAULT_POSITION) {
                // EXTRA_POSITION not found in intent
                closeOnError();
                return;
            }

            String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
            String json = sandwiches[position];
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);
            setTitle(sandwich.getMainName());

            imagePath = sandwich.getImage();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        Timber.d(sandwich.getImage());

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            originTv.setText(NULL_TEXT);
        } else {
            originTv.setText(sandwich.getPlaceOfOrigin());
        }

        descriptionTv.setText(sandwich.getDescription());


        int ingredientCount = 0;
        if (sandwich.getIngredients().size() > 0) {
            for (String ingredient : sandwich.getIngredients()) {
                ingredientCount++;
                if (ingredientCount != sandwich.getIngredients().size()) {
                    ingredientsTv.append(ingredient + "\n");
                }

                ingredientsTv.append(ingredient);
            }
        } else {
            ingredientsTv.setText(NULL_TEXT);
        }


        int knownCount = 0;
        if (sandwich.getAlsoKnownAs().size() > 0) {
            for (String knownOnes : sandwich.getAlsoKnownAs()) {
                knownCount++;
                if (knownCount != sandwich.getAlsoKnownAs().size()) {
                    alsoKnownAsTV.append(knownOnes + "\n");
                }

                alsoKnownAsTV.append(knownOnes);
            }
        } else {
            alsoKnownAsTV.setText(NULL_TEXT);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClicked = item.getItemId();

        if (itemThatWasClicked == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(IMAGE_STATE, imagePath);
        outState.putString(ORIGIN_STATE, originTv.getText().toString());
        outState.putString(DESCRIPTION_STATE, descriptionTv.getText().toString());
        outState.putString(INGREDIENTS_STATE, ingredientsTv.getText().toString());
        outState.putString(ALSO_KNOWN_SATE, alsoKnownAsTV.getText().toString());

        super.onSaveInstanceState(outState);
    }
}