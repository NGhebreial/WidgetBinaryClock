package m0n0l0c0.widgettest;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link MainFrameConfigureActivity MainFrameConfigureActivity}
 */
public class MainFrame extends AppWidgetProvider
{

    private static final String TAG = "MAINFRAME";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d(TAG, "onUpdate()");
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            MainFrameConfigureActivity.deleteTitlePref(context, appWidgetIds[i]);
        }
    }
    /**La primera vez que el widget es llamado se crea el filtro llamando
     * al receiver*/
    @Override
    public void onEnabled(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new WidgetReceiver();
        context.getApplicationContext().registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the LAST widget is disabled
        // Unregister service
        //TODO
//        BroadcastReceiver mReceiver = new WidgetReceiver();
//        context.getApplicationContext().unregisterReceiver(mReceiver);
    }
    //Solo se ejecuta al crearse el widget
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Log.d(TAG, "updateAppWidget()");
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main_frame);

        // PINTAR RELOJ Y MANTENER UN MINUTO
        Intent intent = new Intent(context, Service.class);
        context.startService(intent);


        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(paint.getStrokeWidth() + 8);
        paint.setColor(Color.MAGENTA);

        Bitmap bmp = Bitmap.createBitmap(110, 55, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bmp);
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, canvas.getHeight() / 3, paint);

        views.setImageViewBitmap(R.id.image_view, bmp);
        //views.setTextViewText(R.id.appwidget_text, widgetText);
        ComponentName component = new ComponentName( context, MainFrame.class);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.image_view);
        appWidgetManager.updateAppWidget(component, views);

        // Instruct the widget manager to update the widget
        //appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

