package com.example.recipeapp.activites.Auth;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeapp.R;
import com.example.recipeapp.entities.User;
import com.example.recipeapp.utils.RetrofitManager;


public class RegistrationActivity extends AppCompatActivity {

    RetrofitManager retrofitManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        retrofitManager = new RetrofitManager(this);

        Button registerButton = findViewById(R.id.sign_up);
        EditText usernameText = findViewById(R.id.username);
        EditText emailText = findViewById(R.id.email);
        EditText passwordText = findViewById(R.id.password);

        registerButton.setOnClickListener(v -> {
            try {
                User payload = new User(usernameText.getText().toString(), emailText.getText().toString(), passwordText.getText().toString());
                retrofitManager.register(payload, (user, responseCode) -> {
                    if (responseCode == 200) {
                        loginIntent(findViewById(R.id.already_have_account));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void loginIntent(View view) {
        Intent intent = new Intent(this, com.example.recipeapp.activites.Auth.LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}