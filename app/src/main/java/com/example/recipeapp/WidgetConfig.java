package com.example.recipeapp;

import static com.example.recipeapp.RecipeAppWidget.ACTION_TOAST;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeapp.activites.RecipeDetails.RecipeDetailsActivity;

public class WidgetConfig extends AppCompatActivity {
    public static final String SHARED_PREFS = "prefs";
    public static final String KEY_BUTTON_TEXT = "keyButtonText";

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private EditText editTextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_config);

        Intent configIntent = getIntent();
        Bundle extras = configIntent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_CANCELED, resultValue);

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        editTextButton = findViewById(R.id.edit_text_button);
    }

    public void confirmConfiguration(View v) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        Intent buttonIntent = new Intent(this, RecipeDetailsActivity.class);
        buttonIntent.putExtra("recipeId", (long)716426);
        PendingIntent buttonPendingIntent = PendingIntent.getActivity(this, 0, buttonIntent, 0);

        String buttonText = editTextButton.getText().toString();

        Intent serviceIntent = new Intent(this, WidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

        Intent clickIntent = new Intent(this, RecipeAppWidget.class);
        clickIntent.setAction(ACTION_TOAST);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(this,
                0, clickIntent, 0);

        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.recipe_app_widget);
        views.setOnClickPendingIntent(R.id.widget_button, buttonPendingIntent);
        views.setCharSequence(R.id.widget_button, "setText", buttonText);
        views.setRemoteAdapter(R.id.widget_stack_view, serviceIntent);
        views.setEmptyView(R.id.widget_stack_view, R.id.widget_empty_view);
        views.setPendingIntentTemplate(R.id.widget_stack_view, clickPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_BUTTON_TEXT + appWidgetId, buttonText);
        editor.apply();

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}
