package ro.multibit.rolocolor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by tavi on 21/05/16.
 */
public class ActivityGlassMain extends Activity {
    public static final String tag = ActivityGlassMain.class.getSimpleName()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(tag, "Glass OnCreate");
    }
}
