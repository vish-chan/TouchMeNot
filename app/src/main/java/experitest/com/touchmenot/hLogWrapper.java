package experitest.com.touchmenot;

import android.util.Log;

public class hLogWrapper {
    public static void write(char level, String tag, String msg) {
        switch(level) {
            case 'V':
                Log.v(tag, msg); break;
            case 'D':
                Log.d(tag, msg); break;
            case 'E':
                Log.e(tag, msg); break;
            default:
                Log.v(tag, msg); break;
        }
    }
}
