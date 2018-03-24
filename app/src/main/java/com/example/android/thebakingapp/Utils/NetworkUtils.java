package com.example.android.thebakingapp.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.thebakingapp.RecipeIngredients;
import com.example.android.thebakingapp.RecipeName;
import com.example.android.thebakingapp.RecipeSteps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Arjun Vidyarthi on 06-Jan-18.
 */

public class NetworkUtils {

    private static final String LOG_TAG = "";

    private NetworkUtils() {
        //to prevent instantiating this class.
    }

    private static URL convertToURL(String url) {
        URL URL = null;
        try {
            URL = new URL(url);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return URL;
    }

    private static String getFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();

    }

    private static String makeHTTPRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = getFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static ArrayList<RecipeName> parseResponseForNames(String JSONResponse) {

        if (TextUtils.isEmpty(JSONResponse)) {
            return null;
        }
        ArrayList<RecipeName> names = new ArrayList<>();

        try {
            JSONArray resultsArray = new JSONArray(JSONResponse);

            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject currentRecipe = resultsArray.getJSONObject(i);

                int id = currentRecipe.getInt("id");
                String name = currentRecipe.getString("name");
                String image = currentRecipe.getString("image");
                RecipeName thisName = new RecipeName(id, name);
                if(!(image.equals("")||image==null)){
                    thisName.setImageURL(image);
                }
                names.add(thisName);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
        }

        return names;
    }

    private static ArrayList<RecipeIngredients> parseResponseForIngredients (String JSONResponse, int id){
        if (TextUtils.isEmpty(JSONResponse)) {
            return null;
        }

        ArrayList<RecipeIngredients> ingredients = new ArrayList<>();

        try {
            JSONArray resultsArray = new JSONArray(JSONResponse);
            JSONObject currentRecipe = resultsArray.getJSONObject(id-1);
            JSONArray ingredientArray = currentRecipe.getJSONArray("ingredients");

            for(int i = 0; i<ingredientArray.length(); i++){
                JSONObject currentIng = ingredientArray.getJSONObject(i);
                String quant = currentIng.getString("quantity");
                String measure = currentIng.getString("measure");
                String ingredient = currentIng.getString("ingredient");

                RecipeIngredients thisRecipeIngredient = new RecipeIngredients(quant, measure, ingredient);
                ingredients.add(thisRecipeIngredient);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
        }

        return ingredients;

    }

    private static ArrayList<RecipeSteps> parseResponseForAllSteps(String JSONResponse, int id){
        if(TextUtils.isEmpty(JSONResponse)){
            return null;
        }

        ArrayList<RecipeSteps> steps = new ArrayList<>();

        try{
            JSONArray resultsArray = new JSONArray(JSONResponse);
            JSONObject currentRecipe = resultsArray.getJSONObject(id-1);
            JSONArray stepsArray = currentRecipe.getJSONArray("steps");
            for(int i = 0; i<stepsArray.length();i++){
                JSONObject thisStep = stepsArray.getJSONObject(i);

                int thisStepid = thisStep.getInt("id");
                String shortDesc = thisStep.getString("shortDescription");
                String desc = thisStep.getString("description");
                String video = thisStep.getString("videoURL");
                String thumb = thisStep.getString("thumbnailURL");
                RecipeSteps currentSteps = new RecipeSteps(thisStepid, shortDesc, desc, video, thumb);
                steps.add(currentSteps);
            }

        }

        catch(JSONException e){
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
        }

        return steps;
    }

    public static ArrayList<RecipeName> networkReqForNames(String url) throws JSONException {

        Log.e(LOG_TAG, "onNetworkReq");

        URL URL = convertToURL(url);

        String jsonResponse = null;

        try {
            jsonResponse = makeHTTPRequest(URL);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        ArrayList<RecipeName> recipeNames = parseResponseForNames(jsonResponse);

        return recipeNames;
    }

    public static ArrayList<RecipeIngredients> networkReqForIngredients (String url, int id) throws JSONException{
        Log.e(LOG_TAG, "onNetworkReqForIngredients");

        URL URL = convertToURL(url);

        String jsonResponse = null;

        try {
            jsonResponse = makeHTTPRequest(URL);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        ArrayList<RecipeIngredients> recipeIngredients = parseResponseForIngredients(jsonResponse, id);

        return recipeIngredients;

    }

    public static ArrayList<RecipeSteps> networkReqForAllSteps (String url, int id) throws JSONException{
        Log.e(LOG_TAG, "networkReqForAllSteps");

        URL URL = convertToURL(url);

        String JSONResponse = null;

        try{
            JSONResponse = makeHTTPRequest(URL);
        }
        catch(IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        ArrayList<RecipeSteps> recipeSteps = parseResponseForAllSteps(JSONResponse, id);
        return recipeSteps;

    }
}
