package com.example.recipeapp.activites.Auth;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.recipeapp.activites.SearchRecipes.RecipesActivity;
import com.example.recipeapp.R;
import com.example.recipeapp.entities.LoggedInUser;
import com.example.recipeapp.utils.RetrofitManager;


public class LoginActivity extends AppCompatActivity {

    RetrofitManager retrofitManager;
    private Switch aSwitch2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences appSettingPrefs = getSharedPreferences("AppSettingPrefs", 0);
        SharedPreferences.Editor sharedPreferencesEdit = appSettingPrefs.edit();
        Boolean isNightModeOn = appSettingPrefs.getBoolean("NightMode", false);

        aSwitch2 = findViewById(R.id.mode2);


        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        retrofitManager = new RetrofitManager(this);

        EditText usernameText = findViewById(R.id.login_username);
        EditText passwordText = findViewById(R.id.password);


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

    public void registerIntent(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}