package com.example.recipeapp.activites.UserProfile;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.recipeapp.activites.Favorites.FavoritesActivity;
import com.example.recipeapp.activites.SearchRecipes.RecipesActivity;
import com.example.recipeapp.R;
import com.example.recipeapp.entities.LoggedInUser;


public class UserProfile extends AppCompatActivity {

    TextView username;
    TextView email;
    View favoriteView;
    LoggedInUser loggedInUser;

    private Switch aSwitch2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        SharedPreferences appSettingPrefs = getSharedPreferences("AppSettingPrefs", 0);
        SharedPreferences.Editor sharedPreferencesEdit = appSettingPrefs.edit();
        Boolean isNightModeOn = appSettingPrefs.getBoolean("NightMode", false);

        aSwitch2 = findViewById(R.id.mode2);

        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            aSwitch2.setChecked(true);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        aSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sharedPreferencesEdit.putBoolean("NightMode", true);
                    sharedPreferencesEdit.apply();
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sharedPreferencesEdit.putBoolean("NightMode", false);
                    sharedPreferencesEdit.apply();
                }

            }
        });

        loggedInUser = LoggedInUser.getInstance();

        username = findViewById(R.id.username);
        email = findViewById(R.id.profile_email);

        username.setText(loggedInUser.getUsername());
        email.setText(loggedInUser.getEmail());

        favoriteView = findViewById(R.id.profile_favorites);

        favoriteView.setOnClickListener(view -> {
            startActivity(new Intent(this, FavoritesActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });

        Button logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(view -> {
            loggedInUser.setUserId(0L);
            loggedInUser.setUsername("");
            loggedInUser.setEmail("");
            startActivity(new Intent(this, RecipesActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
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