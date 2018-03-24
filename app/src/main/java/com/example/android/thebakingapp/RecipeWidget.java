package com.example.android.thebakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {
    long id = 0;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        List<RecipeAndIngredient> list = RecipeAndIngredient.listAll(RecipeAndIngredient.class);
        if (list.size() != 0) {
            views.setTextViewText(R.id.wid_name, list.get(0).title);
            views.setTextViewText(R.id.wid_ing, list.get(0).ingredients);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.wid_ing);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.wid_name);
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

