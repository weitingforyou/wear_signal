package com.example.carine.save_signal;


import android.app.Activity;
import android.view.View;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;

public class MainActivity extends Activity {

    private SensorManager sm;
    private Sensor mAccelerometer;
    TextView tv,tv1,tv2, status;
    Button bt_start, bt_save;
    int aidx=0;
    float[][] A = new float[2000][3], B = new float[2000][3], C = new float[2000][3],
            D = new float[2000][3], E = new float[2000][3], F = new float[2000][3],
            DTW = new float[2000][2000];
    int file_ct = 1;
    String output="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rect_activity_main);
        tv = (TextView)findViewById(R.id.xval);
        tv1 = (TextView)findViewById(R.id.yval);
        tv2 = (TextView)findViewById(R.id.zval);
        status = (TextView)findViewById(R.id.textView);
        bt_start = (Button)findViewById(R.id.button);
        bt_save = (Button)findViewById(R.id.button2);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);


        sm = (SensorManager)getSystemService(SENSOR_SERVICE);//創立一個Sensormanager來取用感測器資料
        mAccelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//選取你要的感測器


        bt_start.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                status.setText("count : " + String.valueOf(file_ct));
                sm.registerListener(myAccelerometerListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            }
        });

        bt_save.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0){
                String filename;
                sm.unregisterListener(myAccelerometerListener);
                try {
                    filename = "/storage/emulated/0/DCIM/Signal_" + String.valueOf(file_ct) + ".txt";
                    FileWriter fw = new FileWriter(filename, false);
                    BufferedWriter bw = new BufferedWriter(fw); //將BufferedWeiter與FileWrite物件做連結
                    bw.write(output);
                    bw.close();
                    output = "";
                    file_ct++;
                }catch(IOException e){}
            }
        });

    }

    //設計監聽式的觸發事件：1.當SENSOR讀取的數據變化的時候    2.當SENSOR的準確度發生變化的時候
    SensorEventListener myAccelerometerListener = new SensorEventListener(){
        public void onSensorChanged(SensorEvent sensorEvent){
            if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                if (aidx < 2000) {
                    A[aidx][0] = sensorEvent.values[0];
                    A[aidx][1] = sensorEvent.values[1];
                    A[aidx][2] = sensorEvent.values[2];

                    tv.setText("X axis" + "\t\t" + String.valueOf(A[aidx][0]));
                    tv1.setText("Y axis" + "\t\t" + String.valueOf(A[aidx][1]));
                    tv2.setText("Z axis" + "\t\t" + String.valueOf(A[aidx][2]));

                    output = output + String.valueOf(A[aidx][0]) + " " + String.valueOf(A[aidx][1]) + " " + String.valueOf(A[aidx][2]);
                    output = output + "\n";
                    aidx++;
                }
            }
        }

        public void onAccuracyChanged(Sensor sensor , int accuracy){}
    };


    //onPause時不啟動呼叫sensor，避免浪費資源
    public void onPause(){
        sm.unregisterListener(myAccelerometerListener);
        super.onPause();
    }
}
