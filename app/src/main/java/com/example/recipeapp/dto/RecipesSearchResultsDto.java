package com.example.recipeapp.dto;


import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class RecipesSearchResultsDto {
    @SerializedName("results")
    private List<RecipeDto> recipes;

    public List<RecipeDto> getRecipes() {
        return this.recipes;
    }

    public void setRecipes(List<RecipeDto> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public String toString() {
        return recipes.get(0).toString();
    }
}