package experitest.com.touchmenot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class hWifiManager {
    private ExtraInfoFragment mParent;
    BroadcastReceiver mWifireceiver;

    public hWifiManager(ExtraInfoFragment theParent) {
        this.mParent = theParent;

    }

    public void setUpWifiReceiver() {
        mWifireceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!mParent.isAdded())
                    return;
                if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                    NetworkInfo ninfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                    if (ConnectivityManager.TYPE_WIFI == ninfo.getType()) {
                        if (ninfo.isConnected()) {
                            WifiManager wifiservice= (WifiManager) mParent.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                            WifiInfo winfo = wifiservice.getConnectionInfo();
                            String ssid = winfo.getSSID();
                            mParent.setWifiTextView(ssid);
                        }
                        else {
                            mParent.setWifiTextView("Not connected");
                        }
                    }
                }
            }
        };
        IntentFilter intent = new IntentFilter();
        intent.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        mParent.getContext().registerReceiver(mWifireceiver, intent);
    }

    public void removeWifiReceiver() {
        mParent.getContext().unregisterReceiver(mWifireceiver);
    }

}
