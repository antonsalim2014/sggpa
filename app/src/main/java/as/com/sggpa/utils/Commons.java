package as.com.sggpa.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by anton on 2/10/2016.
 */
public class Commons {
    public static String getDeviceID(Context context)
    {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
}
