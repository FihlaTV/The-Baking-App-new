package com.example.android.thebakingapp;

/**
 * Created by Arjun Vidyarthi on 12-Mar-18.
 */

public class RecipeIngredients {
    String quantity;
    String measure;
    String ingredient;

    public RecipeIngredients(String quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;

    }

    public String getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

}
