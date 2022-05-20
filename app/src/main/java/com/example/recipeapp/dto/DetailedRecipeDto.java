package com.example.recipeapp.dto;


import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


public class DetailedRecipeDto {
    private Long id;
    private String title;
    @SerializedName("image")
    private String imageUrl;
    private String instructions;
    private String summary;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @NonNull
    @Override
    public String toString() {
        return id+" "+title;
    }
}
