package com.bf.portugo.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bf.portugo.R;
import com.bf.portugo.model.Verb;
import com.bf.portugo.ui.activity.LearnVerbActivity;


/*
 * @author frielb
 * Created on 13/08/2018
 */
public class VerbWidgetProvider extends AppWidgetProvider {

    private static final String TAG = VerbWidgetProvider.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Verb verb) {

        // Construct the RemoteViews object and pending intent
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.verb_widget_provider);
        String en_txt = context.getString(R.string.app_name);
        String pt_txt = context.getString(R.string.noverbsavail);
        if ((verb != null) && (verb.getWord_pt().length()>0)) {
            en_txt = verb.getWord_en();
            pt_txt = verb.getWord_pt();
            Log.d(TAG, "updateAppWidget: Verb:["+pt_txt+"]");
            Intent intent = new Intent(context, LearnVerbActivity.class);
            intent.putExtra(LearnVerbActivity.KEY_VERB, verb);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.layout_widget_verb, pendingIntent);
        }

        views.setTextViewText(R.id.tv_widget_en, en_txt);
        views.setTextViewText(R.id.tv_widget_pt, pt_txt);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateVerbWidgets(Context context, AppWidgetManager appWidgetManager,
                                         Verb widgetVerb, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, widgetVerb);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        VerbUpdateService.startActionGetVerb(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        VerbUpdateService.startActionGetVerb(context);

    }

}
