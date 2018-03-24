package com.example.android.thebakingapp;

/**
 * Created by Arjun Vidyarthi on 10-Mar-18.
 */

public class RecipeName {
    int id;
    String name;
    String imageURL = null;

    public RecipeName(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }
}
