package com.example.android.thebakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MasterFlowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_flow);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int id = extras.getInt("RECIPE_ID");
        Boolean twoPane = extras.getBoolean("TWO_PANE");

        Bundle bundle = new Bundle();
        bundle.putInt("RECIPE_ID", id);
        bundle.putBoolean("TWO_PANE", twoPane);

        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setArguments(bundle);
        FlowFragment flowFragment = new FlowFragment();


        if (savedInstanceState == null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.steps_ing_container_master, recipeFragment)
                    .commit();
            fragmentManager.beginTransaction()
                    .add(R.id.steps_container_flow, flowFragment)
                    .commit();

        }

    }
}
