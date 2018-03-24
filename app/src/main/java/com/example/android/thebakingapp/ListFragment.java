package com.example.android.thebakingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.thebakingapp.Utils.NetworkUtils;

import java.util.ArrayList;

/**
 * Created by Arjun Vidyarthi on 10-Mar-18.
 */

public class ListFragment extends android.support.v4.app.Fragment implements ListAdapter.ListAdapterOnClickHandler {
    String LOG_TAG;
    RecyclerView recyclerView;
    ListAdapter adapter;
    Activity context;
    int smallestScreenWidthDp;
    public ListFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        final View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_list);
        recyclerView.setHasFixedSize(true);


        Configuration configuration = context.getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp;
        smallestScreenWidthDp = configuration.smallestScreenWidthDp;

        if(smallestScreenWidthDp>=600){
            Log.e(LOG_TAG, "TAB");
            GridLayoutManager layoutManagerGrid = new GridLayoutManager(rootView.getContext(), 3);
            recyclerView.setLayoutManager(layoutManagerGrid);
        }
        else {
            Log.e(LOG_TAG, "PHONE");
            LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
            recyclerView.setLayoutManager(layoutManager);
        }


        adapter = new ListAdapter(rootView.getContext(), ListFragment.this);
        String[] URL = {"https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json"};
        new RecipeAyncTask().execute(URL);
        return rootView;

    }

    private class RecipeAyncTask extends AsyncTask<String, Void, ArrayList<RecipeName>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<RecipeName> doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }

            String URL = strings[0];

            try {
                return NetworkUtils.networkReqForNames(URL);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error with AyncTask retrieval!");
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<RecipeName> recipeNames) {
            if (recipeNames != null) {
                adapter.setRecipeNames(recipeNames);
                recyclerView.setAdapter(adapter);
            } else return;
        }

    }

    @Override
    public void onClick(RecipeName thisRecipe) {

        if(smallestScreenWidthDp>=600){
            Intent intent = new Intent(context, MasterFlowActivity.class);
            Bundle extras = new Bundle();

            extras.putInt("RECIPE_ID", thisRecipe.getId());
            extras.putBoolean("TWO_PANE", true);
            intent.putExtras(extras);
            getActivity().startActivity(intent);
        }
        else {

            try {
                Intent intent = new Intent(context, RecipeActivity.class);
                Bundle extras = new Bundle();

                extras.putInt("RECIPE_ID", thisRecipe.getId());
                extras.putBoolean("TWO_PANE", MainActivity.twoPane);
                intent.putExtras(extras);
                getActivity().startActivity(intent);

            } catch (NullPointerException e) {
                Intent intent = new Intent(context, RecipeActivity.class);
                Bundle extras = new Bundle();

                extras.putInt("RECIPE_ID", thisRecipe.getId());
                extras.putBoolean("TWO_PANE", false);
                intent.putExtras(extras);
                getActivity().startActivity(intent);
            }
        }

    }
}
