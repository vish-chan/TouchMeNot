package experitest.com.touchmenot;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class hGPSManager {
    final static String TAG = "GPSManager<TouchMeNot>";
    LocationListener mLocListener;
    ExtraInfoFragment mParent;
    final double ROUND = 100000;

    public hGPSManager(ExtraInfoFragment theParent) {
        this.mParent = theParent;
        mLocListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (!mParent.isAdded())
                    return;
                double lat = Math.round(location.getLatitude() * ROUND) / ROUND;
                double lon = Math.round(location.getLongitude() * ROUND) / ROUND;
                mParent.setGPSTextView(lat + "/" + lon);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }


    public void setUpListener() {
        if (ContextCompat.checkSelfPermission(mParent.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mParent.getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(mParent.getActivity())
                        .setTitle("Permission required")
                        .setMessage("GPS permission is required to show current location.")
                        .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(mParent.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        mParent.MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                hLogWrapper.write('D', hGPSManager.TAG, "shouldShowRequestPermissionRationale block");

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(mParent.getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        mParent.MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
                hLogWrapper.write('D', hGPSManager.TAG, "requestPermissions block");
            }
        } else {
            LocationManager locationManager = (LocationManager) mParent.getContext().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocListener);
            hLogWrapper.write('D', hGPSManager.TAG, "setUpListerner() success!");
        }

    }

    public void removeListener() {
        LocationManager locationManager = (LocationManager) mParent.getContext().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(mLocListener);
        hLogWrapper.write('D', hGPSManager.TAG, "removeListener()");
    }
}
