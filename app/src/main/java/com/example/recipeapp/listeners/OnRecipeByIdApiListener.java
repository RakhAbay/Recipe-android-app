package com.example.recipeapp.listeners;


import com.example.recipeapp.dto.DetailedRecipeDto;


public interface OnRecipeByIdApiListener {
    void onResponse(DetailedRecipeDto recipe);
}
