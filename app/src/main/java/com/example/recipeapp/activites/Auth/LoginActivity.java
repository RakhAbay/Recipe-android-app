package com.example.recipeapp.activites.Auth;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeapp.activites.SearchRecipes.RecipesActivity;
import com.example.recipeapp.R;
import com.example.recipeapp.entities.LoggedInUser;
import com.example.recipeapp.utils.RetrofitManager;


public class LoginActivity extends AppCompatActivity {

    RetrofitManager retrofitManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retrofitManager = new RetrofitManager(this);

        EditText usernameText = findViewById(R.id.login_username);
        EditText passwordText = findViewById(R.id.password);

        //usernameText.setOnClickListener(view -> {});

        Button loginButton = findViewById(R.id.sign_in);
        loginButton.setOnClickListener(v -> {
            try {
                retrofitManager.login(usernameText.getText().toString(), passwordText.getText().toString(),
                        (user, responseCode) -> {
                            if (responseCode == 200) {
                                LoggedInUser loggedInUser = LoggedInUser.getInstance();
                                loggedInUser.setUserId(user.getId());
                                loggedInUser.setUsername(user.getUsername());
                                loggedInUser.setEmail(user.getEmail());
                                startActivity(new Intent(getApplicationContext(), RecipesActivity.class));
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button startMoviesIntentButton = findViewById(R.id.to_movies_search_button);
        startMoviesIntentButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecipesActivity.class);
            startActivity(intent);
        });
    }

    public void registerIntent(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}