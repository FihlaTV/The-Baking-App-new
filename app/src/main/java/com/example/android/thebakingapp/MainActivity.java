package com.example.android.thebakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    static Boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {

            ListFragment listFragment = new ListFragment();
            RecipeFragment recipeFragment = new RecipeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();


            fragmentManager.beginTransaction().add(R.id.list_container, listFragment).commit();
//            if (findViewById(R.id.steps_ing_container) != null) {
//                twoPane = true;
//                fragmentManager.beginTransaction().add(R.id.steps_ing_container, recipeFragment).commit();
//            }

        }

    }
}