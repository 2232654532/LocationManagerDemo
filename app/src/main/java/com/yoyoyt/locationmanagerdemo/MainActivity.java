package com.yoyoyt.locationmanagerdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    LocationManager mLocationManager;
    Location mlocation;
    TextView mTextView;
    private TelephonyManager telephonyManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView)findViewById(R.id.textView1);

        Location mlocal = getLocation();
        String strResult = "getAccuracy:" + mlocal.getAccuracy() + "\r\n"
                + "getAltitude:" + mlocal.getAltitude() + "\r\n"
                + "getBearing:" + mlocal.getBearing() + "\r\n"
                + "getElapsedRealtimeNanos:" + String.valueOf(mlocal.getElapsedRealtimeNanos()) + "\r\n"
                + "getLatitude:" + mlocal.getLatitude() + "\r\n"
                + "getLongitude:" + mlocal.getLongitude() + "\r\n"
                + "getProvider:" + mlocal.getProvider()+ "\r\n"
                + "getSpeed:" + mlocal.getSpeed() + "\r\n"
                + "getTime:" + mlocal.getTime() + "\r\n";
//      mTextView.setText(strResult);
//        phone();
        phoneSignalStrengthsChanged();
    }

    /**
     * 信号质量的改变
     */
    private void phoneSignalStrengthsChanged(){
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        final int type = telephonyManager.getNetworkType();


        PhoneStateListener phoneStateListener = new PhoneStateListener() {

            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                // TODO Auto-generated method stub
                super.onSignalStrengthsChanged(signalStrength);
                StringBuffer sb = new StringBuffer();
                String strength = String.valueOf(signalStrength
                        .getGsmSignalStrength());
                if (type == TelephonyManager.NETWORK_TYPE_UMTS
                        || type == TelephonyManager.NETWORK_TYPE_HSDPA) {
                    sb.append("联通3g").append("信号强度:").append(strength);
                } else if (type == TelephonyManager.NETWORK_TYPE_GPRS
                        || type == TelephonyManager.NETWORK_TYPE_EDGE) {
                    sb.append("移动或者联通2g").append("信号强度:").append(strength);
                }else if(type==TelephonyManager.NETWORK_TYPE_CDMA){
                    sb.append("电信2g").append("信号强度:").append(strength);
                }else if(type==TelephonyManager.NETWORK_TYPE_EVDO_0
                        ||type==TelephonyManager.NETWORK_TYPE_EVDO_A|| type==TelephonyManager.NETWORK_TYPE_EVDO_B){
                    sb.append("电信3g").append("信号强度:").append(strength);
                }else if (type==TelephonyManager.NETWORK_TYPE_LTE){
                    sb.append("移动、联通或电信4g").append("信号强度:").append(strength);
                }else if (type==TelephonyManager.NETWORK_TYPE_1xRTT){
                    sb.append("无线电传输2g网络").append("信号强度:").append(strength);
                }else{
                    sb.append("非以上信号").append("信号强度:").append(strength);
                }

                mTextView.setText(sb.toString());
            }

        };

        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SERVICE_STATE
                |PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
                |PhoneStateListener.LISTEN_CALL_STATE
                |PhoneStateListener.LISTEN_DATA_CONNECTION_STATE
                |PhoneStateListener.LISTEN_DATA_ACTIVITY);
    }

    private void  phone(){
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_SERVICE_STATE
                |PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
                |PhoneStateListener.LISTEN_CALL_STATE
                |PhoneStateListener.LISTEN_DATA_CONNECTION_STATE
                |PhoneStateListener.LISTEN_DATA_ACTIVITY);


    }
    // create a phone state listener
    PhoneStateListener phoneListener = new PhoneStateListener() {


        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            int level = signalStrength.getLevel();
            Toast.makeText(MainActivity.this,level+"",Toast.LENGTH_SHORT).show();
            mTextView.setText(level+"");
        }
    };
    private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
//            mSignalStrength = signalStrength;
//            updateSignalStrength();
        }
    };

    public Location getLocation(){
        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        mlocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (mlocation == null) {
            mlocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, mLocationListener);
        return mlocation;
    }

    LocationListener mLocationListener = new LocationListener() {
        @TargetApi(17)
        @Override
        public void onLocationChanged(Location mlocal) {
            if(mlocal == null) return;
            String strResult = "getAccuracy:" + mlocal.getAccuracy() + "\r\n"
                    + "getAltitude:" + mlocal.getAltitude() + "\r\n"
                    + "getBearing:" + mlocal.getBearing() + "\r\n"
                    + "getElapsedRealtimeNanos:" + String.valueOf(mlocal.getElapsedRealtimeNanos()) + "\r\n"
                    + "getLatitude:" + mlocal.getLatitude() + "\r\n"
                    + "getLongitude:" + mlocal.getLongitude() + "\r\n"
                    + "getProvider:" + mlocal.getProvider()+ "\r\n"
                    + "getSpeed:" + mlocal.getSpeed() + "\r\n"
                    + "getTime:" + mlocal.getTime() + "\r\n";
            Log.i("Show", strResult);
            if (mTextView != null) {
                mTextView.setText(strResult);
            }
        }

        @Override
        public void onProviderDisabled(String arg0) {
        }

        @Override
        public void onProviderEnabled(String arg0) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

            if (status== LocationProvider.OUT_OF_SERVICE){
                Toast.makeText(MainActivity.this,"GPS信号不在服务区内",Toast.LENGTH_SHORT).show();
            }else if (status==LocationProvider.TEMPORARILY_UNAVAILABLE){
                Toast.makeText(MainActivity.this,"GPS信号暂时不可用",Toast.LENGTH_SHORT).show();
            }else if (status==LocationProvider.AVAILABLE){
                Toast.makeText(MainActivity.this,"GPS信号可用",Toast.LENGTH_SHORT).show();
            }
        }
    };


}

