package com.example.recipeapp.dto;


import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


public class RecipeDto {
    private Long id;
    private String title;
    @SerializedName("image")
    private String imageUrl;

    public RecipeDto(Long id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
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

    @NonNull
    @Override
    public String toString() {
        return "{"+id+", "+title+"}";
    }
}
