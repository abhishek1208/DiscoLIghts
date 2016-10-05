package com.example.abhishekyadav.sensors;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MAINACTIVITY";
    TextView tv_main;
    SensorManager sm;
    SensorEventListener eventListener;

    @Override
    protected void onDestroy() {
        sm.unregisterListener(eventListener);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        tv_main= (TextView) findViewById(R.id.tv_main);
//        Sensor accelerSensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        for (Sensor sensor : sm.getSensorList(Sensor.TYPE_ALL)) {
            Log.d(TAG, "onCreate: name " + sensor.getName());
            Log.d(TAG, "onCreate: vendor" + sensor.getVendor());
            Log.d(TAG, "onCreate: version" + sensor.getVersion());
            Log.d(TAG, "onCreate: maxRange" + sensor.getMaximumRange());
            Log.d(TAG, "onCreate: power" + sensor.getPower());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                Log.d(TAG, "onCreate: type" + sensor.getStringType());
            }
        }

        eventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
//                Log.d(TAG, "onSensorChanged: "+event.accuracy);
                Log.d(TAG, "onSensorChanged: x"+event.values[0]);
                Log.d(TAG, "onSensorChanged: y"+event.values[1]);
                Log.d(TAG, "onSensorChanged: z"+event.values[2]);
                int x= (int) ((event.values[0]/22)*255);
                int y= (int) ((event.values[1]/22)*255);
                int z= (int) ((event.values[2]/22)*255);

                tv_main.setBackgroundColor(Color.rgb(x,z,y));




            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sm.registerListener(eventListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),2000);


    }
}
