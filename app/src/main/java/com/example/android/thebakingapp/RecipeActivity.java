package com.example.android.thebakingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.android.thebakingapp.Utils.NetworkUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {


    int id;
    Boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        id = extras.getInt("RECIPE_ID");
        twoPane = extras.getBoolean("TWO_PANE");

        Bundle bundle = new Bundle();
        bundle.putInt("RECIPE_ID", id);
        bundle.putBoolean("TWO_PANE", twoPane);

        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setArguments(bundle);

        if (savedInstanceState == null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.ing_steps_container, recipeFragment)
                    .commit();

        }

    }


}
