package com.example.recipeapp.listeners;


import com.example.recipeapp.entities.User;


public interface OnAuthListener {
    void onResponse(User user, int responseCode);
}