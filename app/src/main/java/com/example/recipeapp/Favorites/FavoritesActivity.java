package com.example.recipeapp.Favorites;


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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeapp.Auth.LoginActivity;
import com.example.recipeapp.RecipeDetails.RecipeDetailsActivity;
import com.example.recipeapp.R;
import com.example.recipeapp.SearchRecipes.RecipesAdapter;
import com.example.recipeapp.UserProfile.UserProfile;
import com.example.recipeapp.dto.DetailedRecipeDto;
import com.example.recipeapp.dto.RecipeDto;
import com.example.recipeapp.entities.LoggedInUser;
import com.example.recipeapp.entities.User;
import com.example.recipeapp.listeners.OnAuthListener;
import com.example.recipeapp.listeners.OnRecipeByIdApiListener;
import com.example.recipeapp.utils.RetrofitManager;

import java.util.ArrayList;
import java.util.List;


public class FavoritesActivity extends AppCompatActivity {

    private static final String LIST_STATE = "listState2";
    public static final String BASE_URL = "http://10.0.2.2:8080/api/user/";

    private RetrofitManager retrofitManager;

    private RecipesAdapter adapter;
    private TextView EmptyStateTextView;
    private ListView recipesListView;
    private Parcelable ListState = null;
    private Button profileIntentButton;

    List<RecipeDto> favoriteMovies;
//    Retrofit retrofit;
//    UserJsonDataApi userJsonDataApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        profileIntentButton = findViewById(R.id.profile_button);
        retrofitManager = new RetrofitManager(this);

        LoggedInUser loggedInUser = LoggedInUser.getInstance();

        recipesListView = findViewById(R.id.list_favorites);
        EmptyStateTextView = findViewById(R.id.empty_view);
        recipesListView.setEmptyView(EmptyStateTextView);


        recipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecipeDto currentRecipe = adapter.getItem(position);

                Intent recipeDetailsIntent = new Intent(getApplicationContext(), RecipeDetailsActivity.class);
                recipeDetailsIntent.putExtra("recipeId", currentRecipe.getId());

                startActivity(recipeDetailsIntent);
            }
        });

        favoriteMovies = new ArrayList<>();

        retrofitManager.getUserById(loggedInUser.getUserId(), new OnAuthListener() {
            @Override
            public void onResponse(User user, int responseCode) {
                if (responseCode == 200) {
                    for (Long recipeId : user.getFavorites()) {
                        retrofitManager.searchRecipeById(recipeId, new OnRecipeByIdApiListener() {
                                    @Override
                                    public void onResponse(DetailedRecipeDto recipe) {
                                        favoriteMovies.add(new RecipeDto(recipe.getId(), recipe.getTitle(), recipe.getImageUrl()));
                                        adapter = new RecipesAdapter(FavoritesActivity.this, favoriteMovies);
                                        recipesListView.setAdapter(adapter);
                                    }
                                }
                        );
                    }
                    adapter = new RecipesAdapter(FavoritesActivity.this, favoriteMovies);
                    recipesListView.setAdapter(adapter);
                }
            }
        });
        adapter = new RecipesAdapter(FavoritesActivity.this, favoriteMovies);
        recipesListView.setAdapter(adapter);
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        userJsonDataApi = retrofit.create(UserJsonDataApi.class);
//        Call<User> call = userJsonDataApi.getUser(String.valueOf(loggedInUser.getUserId()));
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
//                if (response.code() == 200) {
//                    assert response.body() != null;
//                    for (Long recipeId : response.body().getFavorites()) {
//                        retrofitManager.searchRecipeById(recipeId, new OnRecipeByIdApiListener() {
//                                    @Override
//                                    public void onResponse(DetailedRecipeDto recipe) {
//                                        favoriteMovies.add(new RecipeDto(recipe.getId(), recipe.getTitle(), recipe.getImageUrl()));
//                                        adapter = new RecipesAdapter(FavoritesActivity.this, favoriteMovies);
//                                        recipesListView.setAdapter(adapter);
//                                    }
//                                }
//                        );
//                    }
//                    adapter = new RecipesAdapter(FavoritesActivity.this, favoriteMovies);
//                    recipesListView.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Log.i("DEBUG", call.toString());
//                Log.i("DEBUG", t.getMessage());
//            }
//        });
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

    }


    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        ListState = state.getParcelable(LIST_STATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ListState != null)
            recipesListView.onRestoreInstanceState(ListState);
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
