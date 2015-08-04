package m0n0l0c0.widgettest;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * Created by guillermo on 02/08/15.
 */
public class Service extends IntentService
{

    private static final String TAG = "I_S";
    private static final String SERVICE_NAME = "WIDGET_SERVICE_SCREEN";

    public Service() {
        super(SERVICE_NAME);
    }
    /**Cuando el servicio es creado ?? */
    @Override
    public void onCreate() {
        super.onCreate();
        //NO ES NECESARIO
//        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
//        filter.addAction(Intent.ACTION_SCREEN_OFF);
//        BroadcastReceiver mReceiver = new WidgetReceiver();
//        registerReceiver(mReceiver, filter);
    }
    /** Se ejecuta cada vez que el receiver detecta on/off en la pantalla
     * de forma que se recibe el estado actual de la pantalla*/
    @Override
    public void onStart(Intent intent, int startId) {
        boolean screenOff = intent.getBooleanExtra("screenOff", false);
        if (screenOff) {
            //Dejar de actualizar la vista si la pantalla esta apagada
            Log.d(TAG, "SCREEN OFF ");
        } else {
            /*Cuando la pantalla esta en on ->
            * actualizar la vista cada minuto
            * enviando la situacion actual horaria*/
            /** ¿Actualizar cada segundo la vista desde
             * el servicio?
             * ¿Actualizar la vista desde la view, refrescandola desde el servicio
             * solo cada minuto?*/
            Log.d(TAG, "SCREEN ONN ");
        }
    }
    //??
    @Override
    protected void onHandleIntent(Intent intent) {
        // START
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
