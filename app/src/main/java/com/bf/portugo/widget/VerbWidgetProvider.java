package com.bf.portugo.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bf.portugo.R;
import com.bf.portugo.model.Verb;
import com.bf.portugo.ui.activity.LearnMainActivity;
import com.bf.portugo.ui.activity.LearnVerbActivity;


/*
 * @author frielb
 * Created on 13/08/2018
 */
public class VerbWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Verb verb) {


        //CharSequence widgetText = context.getString(R.string.tv_);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.verb_widget_provider);
        views.setTextViewText(R.id.tv_widget_en, verb.getWord_en());
        views.setTextViewText(R.id.tv_widget_pt, verb.getWord_pt());

        Intent intent = new Intent(context, LearnVerbActivity.class);
        intent.putExtra(LearnVerbActivity.KEY_VERB, verb);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // TODO: 13/08/2018 Launch LearnVerbActivity
        views.setOnClickPendingIntent(R.id.layout_widget_verb, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //super.onUpdate(context, appWidgetManager, appWidgetIds);

        // TODO: 13/08/2018 from db
        Verb v = new Verb();
        v.setWord_pt("comer");
        v.setWord_en("to eat");

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, v);
            Toast.makeText(context, "Widget updated ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
}
