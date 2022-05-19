package com.example.recipeapp.listeners;


import com.example.recipeapp.dto.RecipeDto;

import java.util.List;


public interface OnRecipeSearchApiListener {
    void onResponse(List<RecipeDto> recipes);
}
