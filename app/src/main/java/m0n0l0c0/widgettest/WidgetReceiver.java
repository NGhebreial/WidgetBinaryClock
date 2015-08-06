package m0n0l0c0.widgettest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by nghebreial on 04/08/2015.
 */
public class WidgetReceiver extends BroadcastReceiver {
    private static final String TAG = "RECEIVER";
    private boolean screenOff;
    /** Cambia el valor de la variable global para hacer una llamada al
     * servicio pasando el valor de la variable*/
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "PROVIDER() " + intent.getAction());
        if (intent.getAction()!=null && intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            screenOff = true;
            this.startService(context);
        } else if (intent.getAction()!=null && intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            screenOff = false;
            this.startService(context);
            context.getApplicationContext().unregisterReceiver(this);
        }
        else if (intent.getStringExtra("destroyService")!=null && intent.getStringExtra("destroyService").matches("destroyService")) {
           stopService(context);
        }

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
