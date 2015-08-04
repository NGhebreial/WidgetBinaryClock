package m0n0l0c0.widgettest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

/**
 * Created by guillermo on 02/08/15.
 */
public class WidgetCanvas extends View
{
    private static final String TAG = "MAINFRAME";

    Context ctx;
    Paint paint;

    public WidgetCanvas(Context context) {
        super(context);
        ctx = context;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ( null != paint ) {
            paint.setStrokeWidth(10);
            canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, canvas.getHeight()/2, paint);
        } else {
            Log.d(TAG, "DRAW()");
        }
    }
}
