package com.example.recipeapp;


import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.recipeapp.dto.RecipeDto;
import com.example.recipeapp.dto.RecipesSearchResultsDto;
import com.example.recipeapp.listeners.OnRecipeSearchApiListener;
import com.example.recipeapp.utils.RetrofitManager;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class WidgetService extends RemoteViewsService {
    RetrofitManager retrofitManager;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ExampleWidgetItemFactory(getApplicationContext(), intent);
    }

    class ExampleWidgetItemFactory implements RemoteViewsFactory {
        private Context context;
        private int appWidgetId;
        private List<RecipeDto> exampleData = new ArrayList<>(Collections.nCopies(5, new RecipeDto(0L, "", "")));


        ExampleWidgetItemFactory(Context context, Intent intent) {
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            //connect to data source
            retrofitManager = new RetrofitManager(getApplicationContext());
            retrofitManager.searchRecipes("", new OnRecipeSearchApiListener() {
                @Override
                public void onResponse(List<RecipeDto> recipes) {
                    for (int i = 0; i < 5; i++) {
                        exampleData.set(i, recipes.get(i));
                    }
                }
            });
        }

        @Override
        public void onDataSetChanged() {
            //refresh data
//            Date date = new Date();
//            String timeFormatted = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
//            exampleData = new String[]{"one\n" + timeFormatted, "two\n" + timeFormatted,
//                    "three\n" + timeFormatted};
//            SystemClock.sleep(3000);
            retrofitManager = new RetrofitManager(getApplicationContext());
            retrofitManager.searchRecipes("", new OnRecipeSearchApiListener() {
                @Override
                public void onResponse(List<RecipeDto> recipes) {
                    for (int i = 0; i < 5; i++) {
                        exampleData.set(i, recipes.get(i));
                    }
                }
            });
        }

        @Override
        public void onDestroy() {
            //close data source
        }

        @Override
        public int getCount() {
            return exampleData.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item);
            views.setTextViewText(R.id.widget_item_text, exampleData.get(position).getTitle());

            Intent fillIntent = new Intent();
            fillIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            views.setOnClickFillInIntent(R.id.widget_item_text, fillIntent);

            SystemClock.sleep(500);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}