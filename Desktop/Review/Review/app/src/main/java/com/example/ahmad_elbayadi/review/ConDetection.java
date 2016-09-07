package com.example.ahmad_elbayadi.review;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ahmad_Elbayadi on 05/09/2016.
 */
public class ConDetection {
    private Context _context;

    public ConDetection(Context context) {
        this._context = context;
    }

    //chechk if there connection or not .....
    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager)
                _context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo nInfo = connectivity.getActiveNetworkInfo();

            if (nInfo != null && nInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }
}
