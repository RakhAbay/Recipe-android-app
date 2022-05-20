package com.example.recipeapp.activites.SearchRecipes;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

    private static final String LIST_STATE = "listState";
    private RecipesAdapter adapter;
    private SearchView searchTextView;
    private TextView EmptyStateTextView;
    private ListView recipesListView;
    private Parcelable ListState = null;
    private Button profileIntentButton;
    private Button loginIntentButton;
    private RetrofitManager retrofitManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileIntentButton = findViewById(R.id.to_profile_button);
        loginIntentButton = findViewById(R.id.to_login_button);
        retrofitManager = new RetrofitManager(this);

        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        if (!(loggedInUser.getUsername() == "")) {
            loginIntentButton.setVisibility(View.GONE);
        } else {
            profileIntentButton.setVisibility(View.GONE);
        }


        recipesListView = findViewById(R.id.list);
        searchTextView = findViewById(R.id.search_bar);
        EmptyStateTextView = findViewById(R.id.empty_view);
        recipesListView.setEmptyView(EmptyStateTextView);

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

        Button searchButton = findViewById(R.id.search_button);

        recipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecipeDto currentRecipe = adapter.getItem(position);

                Intent recipeDetailsIntent = new Intent(getApplicationContext(), RecipeDetailsActivity.class);
                recipeDetailsIntent.putExtra("recipeId", currentRecipe.getId());

                startActivity(recipeDetailsIntent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmptyStateTextView.setText(null);
                retrofitManager.searchRecipes(searchTextView.getQuery().toString(),
                        new OnRecipeSearchApiListener() {
                            @Override
                            public void onResponse(List<RecipeDto> recipes) {
                                adapter = new RecipesAdapter(RecipesActivity.this, recipes);
                                recipesListView.setAdapter(adapter);
                            }
                        }
                );
                searchTextView.clearFocus();
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
        } else {
                EmptyStateTextView.setText(R.string.no_internet);
        }

        profileIntentButton.setOnClickListener(v -> {
            if (loggedInUser.getUsername() != "") {
                Intent intent = new Intent(this, UserProfile.class);
                startActivity(intent);
            }
        });
        loginIntentButton.setOnClickListener(v -> {
        if (loggedInUser.getUsername() == "") {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    });
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        ListState = state.getParcelable(LIST_STATE);
    }

    @Override
    protected void onStart() { // TODO: infuse the lifecycle methods with functional code
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchTextView.clearFocus();
        if (ListState != null)
            recipesListView.onRestoreInstanceState(ListState);
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


    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        ListState = recipesListView.onSaveInstanceState();
        state.putParcelable(LIST_STATE, ListState);
    }

    public void loginIntent(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
