package com.example.recipeapp.RecipeDetails;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeapp.activites.Auth.LoginActivity;
import com.example.recipeapp.SearchRecipes.RecipesActivity;
import com.example.recipeapp.R;
import com.example.recipeapp.dto.DetailedRecipeDto;
import com.example.recipeapp.entities.LoggedInUser;
import com.example.recipeapp.listeners.OnRecipeByIdApiListener;
import com.example.recipeapp.utils.RetrofitManager;
import com.squareup.picasso.Picasso;


public class RecipeDetailsActivity extends AppCompatActivity  {

    private TextView titleTextView;
    private ImageView imageView;
    private Button add_fav;
    private TextView overviewTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        RetrofitManager retrofitManager = new RetrofitManager(this);

        titleTextView = findViewById(R.id.detailTitle);
        imageView = findViewById(R.id.detailImageItem);
        add_fav = findViewById(R.id.add_btn_favorities);
        overviewTextView = findViewById(R.id.overview);


        /**
         * Retrieval of recipeId
         */
        Intent myIntent = getIntent();
        Long recipeId = myIntent.getLongExtra("recipeId", 1);

        retrofitManager.searchRecipeById(recipeId,
                new OnRecipeByIdApiListener() {
                    @Override
                    public void onResponse(DetailedRecipeDto recipe) {
                        Picasso.get().load(recipe.getImageUrl()).into(imageView);
                        titleTextView.setText(recipe.getTitle());
                        /**
                         * filtering out unneeded html tags
                         */
                        if (recipe.getInstructions() != null) {
                            String instructions = recipe.getInstructions().replaceAll("<ol>", "")
                                    .replaceAll("</ol>", "")
                                    .replaceAll("<span>", "")
                                    .replaceAll("</span>", "")
                                    .replaceAll("\"", "")
                                    .replaceAll("<li>", "")
                                    .replaceAll("</li>", "")
                                    .replaceAll("<b>", "")
                                    .replaceAll("</b>", "");
                            overviewTextView.setText(instructions);
                        }

                    }
                });

        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        Long userId = loggedInUser.getUserId();
        if (loggedInUser.getUsername() == "") {
            add_fav.setVisibility(View.GONE);
        }

        Log.i("DEBUG", loggedInUser.toString());
        add_fav.setOnClickListener(v -> {
            if(loggedInUser.getUsername() == ""){
                startActivity(new Intent(this, LoginActivity.class));
                return;
            }
            try {
                retrofitManager.addToFavorites(userId, recipeId);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    public void moviesIntent(View view) {
        Intent intent = new Intent(getApplicationContext(), RecipesActivity.class);
        Intent myIntent = getIntent();
        if (!(myIntent.getStringExtra("username") == null)) {
            intent.putExtra("username", myIntent.getStringExtra("username"));
        }
        startActivity(intent);
    }
}