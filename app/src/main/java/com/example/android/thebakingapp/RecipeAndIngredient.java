package com.example.android.thebakingapp;


import com.orm.SugarRecord;

/**
 * Created by Arjun Vidyarthi on 17-Mar-18.
 */

public class RecipeAndIngredient extends SugarRecord<RecipeAndIngredient> {
    String title;
    String ingredients;

    public RecipeAndIngredient() {

    }

    public RecipeAndIngredient(String title, String ingredients) {
        this.title = title;
        this.ingredients = ingredients;
    }
}
