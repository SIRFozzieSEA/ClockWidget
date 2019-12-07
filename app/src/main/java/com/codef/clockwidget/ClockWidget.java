package com.codef.clockwidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;

public class ClockWidget extends AppWidgetProvider {

    String TAG = AppWidgetProvider.class.getSimpleName();

    private static String CLOCK_WIDGET_UPDATE = "com.codef.clockwidget.UPDATE";

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(createClockTickIntent(context));
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        setAlarmManager(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, intent.getAction());
        super.onReceive(context, intent);
        if (CLOCK_WIDGET_UPDATE.equals(intent.getAction())) {
            Bitmap clockBitmap = ClockWidgetUtilities.recreateClock(context);
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] ids = appWidgetManager.getAppWidgetIds(thisAppWidget);
            boolean increaserefreshrate = false;
            for (int appWidgetID : ids) {
                updateAppWidget(clockBitmap, context, appWidgetManager, appWidgetID);
            }
            setAlarmManager(context);
        }
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Bitmap clockBitmap = ClockWidgetUtilities.recreateClock(context);
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.clock_widget_layout);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
            updateAppWidget(clockBitmap, context, appWidgetManager, appWidgetId);
        }
        setAlarmManager(context);
    }

    public void updateAppWidget(Bitmap clockBitmap, Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.clock_widget_layout);
        remoteViews.setImageViewBitmap(R.id.imageView1, clockBitmap);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    private PendingIntent createClockTickIntent(Context context) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(CLOCK_WIDGET_UPDATE);
        return PendingIntent.getBroadcast(context, 23, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void setAlarmManager(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, (60 - calendar.get(Calendar.SECOND)));
        alarmManager.setExact(AlarmManager.RTC, calendar.getTimeInMillis(), createClockTickIntent(context));
    }


}
