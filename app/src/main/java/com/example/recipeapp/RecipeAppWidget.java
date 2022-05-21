package com.example.recipeapp;

import static com.example.recipeapp.WidgetConfig.KEY_BUTTON_TEXT;
import static com.example.recipeapp.WidgetConfig.SHARED_PREFS;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.recipeapp.activites.Favorites.FavoritesActivity;
import com.example.recipeapp.activites.RecipeDetails.RecipeDetailsActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, RecipeDetailsActivity.class);
            intent.putExtra("recipeId", (long)716426);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            String buttonText = prefs.getString(KEY_BUTTON_TEXT + appWidgetId, "Press me");

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget);
            views.setOnClickPendingIntent(R.id.widget_button, pendingIntent);
            views.setCharSequence(R.id.widget_button, "setText", buttonText);

            appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    }
}