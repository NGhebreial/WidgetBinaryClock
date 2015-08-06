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
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link MainFrameConfigureActivity MainFrameConfigureActivity}
 */
public class MainFrame extends AppWidgetProvider
{

    private static final String TAG = "MAINFRAME";
    private boolean screenOff;
    private static final BroadcastReceiver mybroadcast = new MainFrame();
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i], Color.MAGENTA);
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
        Log.d(TAG, "onEnabled()");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "PROVIDER() " + intent.getAction());
        //Trabajar solo cuando el intent tenga una accion valida
        if(intent.getAction()!=null){
            switch(intent.getAction()){
                case Intent.ACTION_SCREEN_OFF:
                    screenOff = true;
                    stopService(context);
                    //this.startService(context);
                    break;
                case Intent.ACTION_SCREEN_ON:
                    screenOff = false;
                    this.startService(context);
                    break;
                //Cuando se crea el primer widget
                case AppWidgetManager.ACTION_APPWIDGET_ENABLED:
                    this.startService(context);
                    break;
                //Cuando se elimine uno de los widget
                case AppWidgetManager.ACTION_APPWIDGET_DELETED:
                    //stopService(context);
                    break;
                //Cuando todos los widget son eliminados
                case AppWidgetManager.ACTION_APPWIDGET_DISABLED:
                    if(mybroadcast.isOrderedBroadcast()){
                        Log.d(TAG, "isOrderedBroadcast()");
                        context.unregisterReceiver(mybroadcast);
                    }
                    stopService(context);
                    break;
                case AppWidgetManager.ACTION_APPWIDGET_UPDATE:
                    //TODO: comprobar que lo hace bien
                    updateAppWidget(context,AppWidgetManager.getInstance(context),1, Color.DKGRAY);
                    break;
            }
        }
        if(intent.getStringExtra("updateView")!=null && intent.getStringExtra("updateView").matches("updateView")){
            //TODO: comprobar que lo hace bien
            updateAppWidget(context,AppWidgetManager.getInstance(context),1, Color.CYAN);
            Log.d(TAG, " ACTUALIZA ");
        }

    }
    //Solo se ejecuta al crearse el widget
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Integer color) {

        Log.d(TAG, "updateAppWidget() "+mybroadcast.isOrderedBroadcast());
        if(!mybroadcast.isOrderedBroadcast()){
            Log.d(TAG, "registerReceiver()");
            IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            context.getApplicationContext().registerReceiver(mybroadcast, filter);
        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main_frame);

        // PINTAR RELOJ Y MANTENER UN MINUTO


        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(paint.getStrokeWidth() + 8);
        paint.setColor(color);

        Bitmap bmp = Bitmap.createBitmap(110, 55, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bmp);
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, canvas.getHeight() / 3, paint);

        views.setImageViewBitmap(R.id.image_view, bmp);
        //views.setTextViewText(R.id.appwidget_text, widgetText);
        ComponentName component = new ComponentName( context, MainFrame.class);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.image_view);
        appWidgetManager.updateAppWidget(component, views);

    }
    private void startService(Context context){
        Intent i = new Intent(context, Service.class);
        i.putExtra("screenOff", screenOff);
        context.startService(i);
    }
    private void stopService(Context context){
        Intent i = new Intent(context, Service.class);
        context.stopService(i);
    }
}

