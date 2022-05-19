package com.example.recipeapp.SearchRecipes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipeapp.R;
import com.example.recipeapp.dto.RecipeDto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipesAdapter extends ArrayAdapter<RecipeDto> {
    public RecipesAdapter(Activity context, List<RecipeDto> recipes) {
        super(context, 0, recipes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        RecipeDto currentRecipe = getItem(position);

        ImageView recipeImageView = listItemView.findViewById(R.id.imageItem);
        Picasso.get().load(currentRecipe.getImageUrl()).into(recipeImageView);

        TextView recipeTitle = listItemView.findViewById(R.id.title);
        recipeTitle.setText(currentRecipe.getTitle());

        return listItemView;
    }
}

