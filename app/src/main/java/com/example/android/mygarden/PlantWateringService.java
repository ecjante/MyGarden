package com.example.android.mygarden;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.android.mygarden.provider.PlantContract;
import com.example.android.mygarden.provider.PlantContract.PlantEntry;
import com.example.android.mygarden.utils.PlantUtils;

/**
 * Created by enrico on 2/20/18.
 */

public class PlantWateringService extends IntentService {

    public static final String ACTION_WATER_PLANTS = "com.example.android.mygarden.action.water_plants";

    public PlantWateringService() {
        super(PlantWateringService.class.getSimpleName());
    }

    public static void startActionWaterPlants(Context context) {
        Intent intent = new Intent(context, PlantWateringService.class);
        intent.setAction(ACTION_WATER_PLANTS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_WATER_PLANTS.equals(action)) {
                handleActionWaterPlants();
            }
        }
    }

    private void handleActionWaterPlants() {
        Uri PLANTS_URI = PlantContract.BASE_CONTENT_URI.buildUpon()
                .appendPath(PlantContract.PATH_PLANTS).build();

        ContentValues contentValues = new ContentValues();
        long timeNow = System.currentTimeMillis();
        contentValues.put(PlantEntry.COLUMN_LAST_WATERED_TIME, timeNow);
        getContentResolver().update(
                PLANTS_URI,
                contentValues,
                PlantEntry.COLUMN_LAST_WATERED_TIME + " > ?",
                new String[] { String.valueOf(timeNow - PlantUtils.MAX_AGE_WITHOUT_WATER) }
        );
    }
}
