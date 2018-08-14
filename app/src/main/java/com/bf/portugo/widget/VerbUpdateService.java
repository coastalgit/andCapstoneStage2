package com.bf.portugo.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LiveData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bf.portugo.R;
import com.bf.portugo.model.Verb;
import com.bf.portugo.repo.VerbRoomRepository;
import com.bf.portugo.util.VerbHelper;

import java.util.List;


/*
 * For use with widget updates.
 *
 * @author frielb
 * Created on 13/08/2018
 */
public class VerbUpdateService extends IntentService {

    private static final String TAG = VerbUpdateService.class.getSimpleName();

    public static final String ACTION_GET_VERB = "portugo.action.getverb";
    public static final String ACTION_UPDATE_VERB_WIDGET = "portugo.action.updateverbwidget";
    public static final String EXTRA_VERB = "portugo.extraverb";

    public VerbUpdateService() {
        super(TAG);
    }

    public static void startActionGetVerb(Context context) {
        Log.d(TAG, "startActionGetVerb: ");
        Intent intent = new Intent(context, VerbUpdateService.class);
        intent.setAction(ACTION_GET_VERB);
        context.startService(intent);
    }

    public static void startActionUpdateVerbWidgets(Context context, Verb verb) {
        Log.d(TAG, "startActionUpdateVerbWidgets: ");
        Intent intent = new Intent(context, VerbUpdateService.class);
        intent.setAction(ACTION_UPDATE_VERB_WIDGET);
        intent.putExtra(EXTRA_VERB, verb);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_VERB.equals(action)) {
                performGetVerb();
            }
            else if (ACTION_UPDATE_VERB_WIDGET.equals(action)){
                if (intent.hasExtra(EXTRA_VERB)){
                    Verb v = (Verb) intent.getSerializableExtra(EXTRA_VERB);
                    Log.d(TAG, "onHandleIntent: verb intent:["+v.getWord_pt()+"]");
                    performVerbWidgetUpdate(v);
                }
            }
        }
    }

    private void performGetVerb() {
        VerbRoomRepository mRepo = new VerbRoomRepository(getApplication());
        mRepo.fetchVerbsFromRoomDB(new VerbRoomRepository.IRoomQueryTaskComplete() {
            @Override
            public void onVerbListFromRoom(List<Verb> verbs) {
                int verbcount = VerbHelper.getListRecordCount(verbs);
                Log.d(TAG, "performGetVerb: count="+String.valueOf(verbcount));
                if (verbcount > 0) {
                    Verb randomVerb = VerbHelper.getRandomVerb(verbs);
                    Log.d(TAG, "performGetVerb: Random verb:["+randomVerb.getWord_pt()+"]");
                    startActionUpdateVerbWidgets(VerbUpdateService.this, randomVerb);
                }
            }
        });
    }

    private void performVerbWidgetUpdate(Verb newVerb) {
        Log.d(TAG, "performVerbWidgetUpdate: ");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, VerbWidgetProvider.class));
        VerbWidgetProvider.updateVerbWidgets(this,appWidgetManager, newVerb, appWidgetIds);
    }
}
