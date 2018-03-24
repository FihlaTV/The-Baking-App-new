package com.example.android.thebakingapp;

/**
 * Created by Arjun Vidyarthi on 13-Mar-18.
 */

public class RecipeSteps {
    private int id;
    private String shortDesc;
    private String description;
    private String videoURL;
    private String thumbURL;

    public RecipeSteps(int id, String shortDesc, String description, String videoURL, String thumbURL) {
        this.id = id;
        this.shortDesc = shortDesc;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbURL = thumbURL;
    }

    public int getId() {
        return id;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbURL() {
        return thumbURL;
    }
}
