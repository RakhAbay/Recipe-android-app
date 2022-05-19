package com.example.recipeapp.UserProfile;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeapp.Favorites.FavoritesActivity;
import com.example.recipeapp.SearchRecipes.RecipesActivity;
import com.example.recipeapp.R;
import com.example.recipeapp.entities.LoggedInUser;


public class UserProfile extends AppCompatActivity {

    TextView username;
    TextView email;
    View favoriteView;
    LoggedInUser loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        loggedInUser = LoggedInUser.getInstance();

        username = findViewById(R.id.username);
        email = findViewById(R.id.profile_email);

        username.setText(loggedInUser.getUsername());
        email.setText(loggedInUser.getEmail());

        favoriteView = findViewById(R.id.profile_favorites);

        favoriteView.setOnClickListener(view -> {
            startActivity(new Intent(this, FavoritesActivity.class));
        });

        Button logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(view -> {
            loggedInUser.setUserId(0L);
            loggedInUser.setUsername("");
            loggedInUser.setEmail("");
            startActivity(new Intent(this, RecipesActivity.class));
        });
    }

    @Override
    protected void onStart() { // TODO: infuse the lifecycle methods with functional code
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}