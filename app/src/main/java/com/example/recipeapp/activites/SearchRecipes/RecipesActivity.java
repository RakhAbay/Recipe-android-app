package com.example.recipeapp.activites.SearchRecipes;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.recipeapp.activites.Auth.LoginActivity;
import com.example.recipeapp.activites.RecipeDetails.RecipeDetailsActivity;
import com.example.recipeapp.R;
import com.example.recipeapp.activites.UserProfile.UserProfile;
import com.example.recipeapp.dto.RecipeDto;
import com.example.recipeapp.entities.LoggedInUser;
import com.example.recipeapp.listeners.OnRecipeSearchApiListener;
import com.example.recipeapp.utils.RetrofitManager;

import java.util.List;

public class RecipesActivity extends AppCompatActivity
{

    private static final String TAG = "RecipesActivity";
    private static final String LIST_STATE = "listState";
    private RecipesAdapter adapter;
    private SearchView searchTextView;
    private TextView EmptyStateTextView;
    private ListView recipesListView;
    private Parcelable ListState = null;
    private Button profileIntentButton;
    private Button loginIntentButton;
    private RetrofitManager retrofitManager;
    Button allButton;
    Button pastaButton;
    Button burgerButton;
    Button beefButton;
    LoggedInUser loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allButton = findViewById(R.id.all_button);
        pastaButton = findViewById(R.id.pasta_button);
        burgerButton = findViewById(R.id.burger_button);
        beefButton = findViewById(R.id.beef_button);

        profileIntentButton = findViewById(R.id.to_profile_button);
        loginIntentButton = findViewById(R.id.to_login_button);
        retrofitManager = new RetrofitManager(this);




        recipesListView = findViewById(R.id.list);
        searchTextView = findViewById(R.id.search_bar);
        EmptyStateTextView = findViewById(R.id.empty_view);
        recipesListView.setEmptyView(EmptyStateTextView);


    }

    public void choosePasta(View view) {
        if (pastaButton.getCurrentTextColor() != -1) {
            pastaButton.setBackgroundResource(R.drawable.button);
            pastaButton.setTextColor(ContextCompat.getColor(this, R.color.white));

            allButton.setBackgroundResource(R.color.white);
            allButton.setTextColor(Color.parseColor("#a3a3a3"));
            burgerButton.setBackgroundResource(R.color.white);
            burgerButton.setTextColor(Color.parseColor("#a3a3a3"));
            beefButton.setBackgroundResource(R.color.white);
            beefButton.setTextColor(Color.parseColor("#a3a3a3"));

            retrofitManager.searchRecipes("pasta",
                    new OnRecipeSearchApiListener() {
                        @Override
                        public void onResponse(List<RecipeDto> recipes) {
                            adapter = new RecipesAdapter(RecipesActivity.this, recipes);
                            recipesListView.setAdapter(adapter);
                        }
                    }
            );
        }
    }
    public void chooseBurger(View view) {
        if (burgerButton.getCurrentTextColor() != -1) {
            burgerButton.setBackgroundResource(R.drawable.button);
            burgerButton.setTextColor(ContextCompat.getColor(this, R.color.white));

            allButton.setBackgroundResource(R.color.white);
            allButton.setTextColor(Color.parseColor("#a3a3a3"));
            pastaButton.setBackgroundResource(R.color.white);
            pastaButton.setTextColor(Color.parseColor("#a3a3a3"));
            beefButton.setBackgroundResource(R.color.white);
            beefButton.setTextColor(Color.parseColor("#a3a3a3"));

            retrofitManager.searchRecipes("burger",
                    new OnRecipeSearchApiListener() {
                        @Override
                        public void onResponse(List<RecipeDto> recipes) {
                            adapter = new RecipesAdapter(RecipesActivity.this, recipes);
                            recipesListView.setAdapter(adapter);
                        }
                    }
            );
        }
    }
    public void chooseBeef(View view) {
        if (beefButton.getCurrentTextColor() != -1) {
            beefButton.setBackgroundResource(R.drawable.button);
            beefButton.setTextColor(ContextCompat.getColor(this, R.color.white));

            allButton.setBackgroundResource(R.color.white);
            allButton.setTextColor(Color.parseColor("#a3a3a3"));
            pastaButton.setBackgroundResource(R.color.white);
            pastaButton.setTextColor(Color.parseColor("#a3a3a3"));
            burgerButton.setBackgroundResource(R.color.white);
            burgerButton.setTextColor(Color.parseColor("#a3a3a3"));

            retrofitManager.searchRecipes("beef",
                    new OnRecipeSearchApiListener() {
                        @Override
                        public void onResponse(List<RecipeDto> recipes) {
                            adapter = new RecipesAdapter(RecipesActivity.this, recipes);
                            recipesListView.setAdapter(adapter);
                        }
                    }
            );
        }
    }
    public void chooseAll(View view) {
        if (allButton.getCurrentTextColor() != -1) {
            allButton.setBackgroundResource(R.drawable.button);
            allButton.setTextColor(ContextCompat.getColor(this, R.color.white));

            beefButton.setBackgroundResource(R.color.white);
            beefButton.setTextColor(Color.parseColor("#a3a3a3"));
            pastaButton.setBackgroundResource(R.color.white);
            pastaButton.setTextColor(Color.parseColor("#a3a3a3"));
            burgerButton.setBackgroundResource(R.color.white);
            burgerButton.setTextColor(Color.parseColor("#a3a3a3"));

            retrofitManager.searchRecipes("",
                    new OnRecipeSearchApiListener() {
                        @Override
                        public void onResponse(List<RecipeDto> recipes) {
                            adapter = new RecipesAdapter(RecipesActivity.this, recipes);
                            recipesListView.setAdapter(adapter);
                        }
                    }
            );
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        ListState = state.getParcelable(LIST_STATE);
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
        searchTextView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                retrofitManager.searchRecipes(searchTextView.getQuery().toString(),
                        new OnRecipeSearchApiListener() {
                            @Override
                            public void onResponse(List<RecipeDto> recipes) {
                                adapter = new RecipesAdapter(RecipesActivity.this, recipes);
                                recipesListView.setAdapter(adapter);
                            }
                        }
                );
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        retrofitManager.searchRecipes("",
                new OnRecipeSearchApiListener() {
                    @Override
                    public void onResponse(List<RecipeDto> recipes) {
                        adapter = new RecipesAdapter(RecipesActivity.this, recipes);
                        recipesListView.setAdapter(adapter);
                    }
                }
        );

        recipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecipeDto currentRecipe = adapter.getItem(position);

                Intent recipeDetailsIntent = new Intent(getApplicationContext(), RecipeDetailsActivity.class);
                recipeDetailsIntent.putExtra("recipeId", currentRecipe.getId());
                recipeDetailsIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(recipeDetailsIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();

        loggedInUser = LoggedInUser.getInstance();
        if (!(loggedInUser.getUsername() == "")) {
            loginIntentButton.setVisibility(View.GONE);
        } else {
            //profileIntentButton.setVisibility(View.GONE);
        }

        profileIntentButton.setOnClickListener(v -> {
            //if (loggedInUser.getUsername() != "") {
                Intent intent = new Intent(this, UserProfile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            //}
        });
        loginIntentButton.setOnClickListener(v -> {
            if (loggedInUser.getUsername() == "") {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        searchTextView.clearFocus();
        if (ListState != null)
            recipesListView.onRestoreInstanceState(ListState);
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        searchTextView.clearFocus();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
        loggedInUser = null;
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart");
        super.onRestart();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
        } else {
            EmptyStateTextView.setText(R.string.no_internet);
        }

    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        loggedInUser = null;
        retrofitManager = null;
        super.onDestroy();
    }


    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        ListState = recipesListView.onSaveInstanceState();
        state.putParcelable(LIST_STATE, ListState);
    }

    public void loginIntent(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
