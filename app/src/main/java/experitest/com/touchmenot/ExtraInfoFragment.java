package experitest.com.touchmenot;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;



public class ExtraInfoFragment extends Fragment {

    final String TAG = "EXTRAINFO<TouchMeNot>";
    TextView mGPSTextView;
    TextView mWifiTextView;
    hGPSManager mGPSManager;
    hWifiManager mWifiManager;
    int mWidth, mHeight;
    public final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 1;

    public ExtraInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_extra_info, container, false);

        mGPSTextView = root.findViewById(R.id.gps);
        mWifiTextView = root.findViewById(R.id.wifi);

        mGPSManager = new hGPSManager(this);
        mGPSManager.setUpListener();

        mWifiManager = new hWifiManager(this);
        mWifiManager.setUpWifiReceiver();

        return root;
    }

    public void setGPSTextView(String location) {
        mGPSTextView.setText("GPS location (Lat/Lon): "+location);
    }

    public void setWifiTextView(String ssid) {
        mWifiTextView.setText("Wifi: "+ssid);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted!
                    if(mGPSManager!=null)
                        mGPSManager.setUpListener();
                } else {
                    this.setGPSTextView("No GPS permissions");
                }
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View theview, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(theview, savedInstanceState);
        theview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mWidth = getView().getWidth();
                mHeight = getView().getHeight();
            }
        });
    }

    @Override
    public void onDestroyView() {
        if(mGPSManager!=null)
            mGPSManager.removeListener();
        if(mWifiManager!=null)
            mWifiManager.removeWifiReceiver();
        super.onDestroyView();
    }

    public int getHeight() {
        return mHeight;
    }
}
