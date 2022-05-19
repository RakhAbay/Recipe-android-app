package com.example.recipeapp.utils;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.recipeapp.dto.DetailedRecipeDto;
import com.example.recipeapp.dto.RecipesSearchResultsDto;
import com.example.recipeapp.entities.User;
import com.example.recipeapp.listeners.OnAuthListener;
import com.example.recipeapp.listeners.OnRecipeByIdApiListener;
import com.example.recipeapp.listeners.OnRecipeSearchApiListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class RetrofitManager {

    private static final String BACKEND_USER_API_REQUEST_URL = "http://10.0.2.2:8080/api/user/";
    private static final String RECIPES_API_BASE_URL = "https://api.spoonacular.com/recipes/";
    private static final String RECIPE_API_KEY = "3bcec8b15ac64bea905e10fddbd9e175";

    Context context;
    public RetrofitManager(Context context) {
        this.context = context;
    }

    /**
     * Methods to invoke http requests
     */
    Retrofit retrofitRecipe = new Retrofit.Builder()
            .baseUrl(RECIPES_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public void searchRecipes(String query, OnRecipeSearchApiListener listener) {
        RecipeApi RecipeApi = retrofitRecipe.create(RecipeApi.class);
        Call<RecipesSearchResultsDto> call = RecipeApi.callRecipes(query);
        call.enqueue(new Callback<RecipesSearchResultsDto>() {
            @Override
            public void onResponse(@NonNull Call<RecipesSearchResultsDto> call, @NonNull Response<RecipesSearchResultsDto> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                assert response.body() != null;
                listener.onResponse(response.body().getRecipes());
            }

            @Override
            public void onFailure(@NonNull Call<RecipesSearchResultsDto> call, @NonNull Throwable t) {
                Log.i("DEBUG", call.toString());
                Log.i("DEBUG", t.getMessage());
            }
        });
    }
    public void searchRecipeById(Long recipeId, OnRecipeByIdApiListener listener) {
        RecipeApi RecipeApi = retrofitRecipe.create(RecipeApi.class);
        Log.i("DEBUG", String.valueOf(recipeId));
        Call<DetailedRecipeDto> call = RecipeApi.callRecipeById(String.valueOf(recipeId));
        call.enqueue(new Callback<DetailedRecipeDto>() {
            @Override
            public void onResponse(@NonNull Call<DetailedRecipeDto> call, @NonNull Response<DetailedRecipeDto> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                assert response.body() != null;
                Log.i("DEBUG", response.body().toString()); // TODO: remove
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<DetailedRecipeDto> call, @NonNull Throwable t) {
                Log.i("DEBUG", call.toString());
                Log.i("DEBUG", t.getMessage());
            }
        });
    }

    Retrofit retrofitUser = new Retrofit.Builder()
            .baseUrl(BACKEND_USER_API_REQUEST_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public void login(String username, String password, OnAuthListener listener) {
        UserApi userApi = retrofitUser.create(RetrofitManager.UserApi.class);
        Call<User> call = userApi.login(username, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                assert response.body() != null;
                listener.onResponse(response.body(), response.code());
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.i("DEBUG", call.toString());
                Log.i("DEBUG", t.getMessage());
            }
        });
    }
    public void register(User user, OnAuthListener listener) {
        UserApi userApi = retrofitUser.create(RetrofitManager.UserApi.class);
        Call<User> call = userApi.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                assert response.body() != null;
                listener.onResponse(response.body(), response.code());
            }
            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.i("DEBUG", call.toString());
                Log.i("DEBUG", t.getMessage());
            }
        });
    }
    public void getUserById(Long userId, OnAuthListener listener) {
        UserApi userApi = retrofitUser.create(RetrofitManager.UserApi.class);
        Call<User> call = userApi.getUser(String.valueOf(userId));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                assert response.body() != null;
                listener.onResponse(response.body(), response.code());
            }
            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.i("DEBUG", call.toString());
                Log.i("DEBUG", t.getMessage());
            }
        });
    }
    public void addToFavorites(Long userId, Long recipeId) {
        UserApi userApi = retrofitUser.create(RetrofitManager.UserApi.class);
        Call<Void> call = userApi.addToFavorites(String.valueOf(userId),String.valueOf(recipeId));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.code()==200){
                    Toast toast = Toast.makeText(context.getApplicationContext(),
                            "Added Successfully", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.i("DEBUG", call.toString());
                Log.i("DEBUG", t.getMessage());
            }
        });
    }

    /**
     * Interfaces for defining http requests
     */
    public interface RecipeApi {
        @Headers({"x-api-key: " + RECIPE_API_KEY})
        @GET("complexSearch")
        Call<RecipesSearchResultsDto> callRecipes(@Query("query") String query);
        @Headers({"x-api-key: " + RECIPE_API_KEY})
        @GET("{id}/information")
        Call<DetailedRecipeDto> callRecipeById(@Path("id") String recipeId);
    }
    public interface UserApi {
        @GET("{id}")
        Call<User> getUser(@Path("id") String id);

        @POST("save")
        Call<User> createUser(@Body User user);

        @POST("login")
        Call<User> login(@Query("username") String username, @Query("password") String password);

        @PUT("favorite/add")
        Call<Void> addToFavorites(@Query("userId") String userId, @Query("movieId") String movieId);
    }
}
