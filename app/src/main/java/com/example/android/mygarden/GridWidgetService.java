package com.example.android.mygarden;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by enrico on 2/21/18.
 */

public class GridWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(getApplicationContext());
    }

}
