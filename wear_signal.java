package com.example.weiting.signal_wearable;

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
    Button bt_start, bt_stop;
    int aidx=0, bidx = 0, cidx = 0, didx = 0, eidx = 0, fidx = 0, ct = 0;
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
        bt_stop = (Button)findViewById(R.id.button2);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);


        sm = (SensorManager)getSystemService(SENSOR_SERVICE);//創立一個Sensormanager來取用感測器資料
        mAccelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//選取你要的感測器


        bt_start.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                //String filename;
                //設定參數進行註冊(sensorEventListener監聽器, 預設的sensor, 設定事件發生後傳送數值的頻率)
                if(ct==6)
                    ct=6;
                else
                    ct++;
                status.setText(String.valueOf(ct));
                sm.registerListener(myAccelerometerListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                try {
                    //filename = "/storage/emulated/0/Signal_" + String.valueOf(file_ct) + ".txt";
                    //FileWriter fw = new FileWriter(filename, false);
                    //BufferedWriter bw = new BufferedWriter(fw); //將BufferedWeiter與FileWrite物件做連結
                    if(ct==1) {
                        for (int i=0; i<aidx; i++) {
                            //output = output + String.valueOf(A[i][0]) + " " + String.valueOf(A[i][1]) + " " + String.valueOf(A[i][2]);
                            //output = output + "/n";
                            //bw.write(String.valueOf(A[i][0]) + " " + String.valueOf(A[i][1]) + " " + String.valueOf(A[i][2]));
                            //bw.newLine();
                        }
                    }
                    else if(ct==2){
                        for (int i=0; i<bidx; i++) {
                            //bw.write(String.valueOf(B[i][0]) + " " + String.valueOf(B[i][1]) + " " + String.valueOf(B[i][2]));
                            //bw.newLine();
                        }
                    }
                    else if(ct==3){
                        for (int i=0; i<cidx; i++) {
                        //bw.write(String.valueOf(C[i][0]) + " " + String.valueOf(C[i][1]) + " " + String.valueOf(C[i][2]));
                        //bw.newLine();
                    }
                    }
                    else if(ct==4){
                        for (int i=0; i<didx; i++) {
                            //bw.write(String.valueOf(D[i][0]) + " " + String.valueOf(D[i][1]) + " " + String.valueOf(D[i][2]));
                            //bw.newLine();
                        }
                    }
                    else if(ct==5){
                        for (int i=0; i<eidx; i++) {
                            //bw.write(String.valueOf(E[i][0]) + " " + String.valueOf(E[i][1]) + " " + String.valueOf(E[i][2]));
                            //bw.newLine();
                        }
                    }
                    else if(ct==6) {
                        for (int i = 0; i < fidx; i++) {
                            //bw.write(String.valueOf(F[i][0]) + " " + String.valueOf(F[i][1]) + " " + String.valueOf(F[i][2]));
                            //bw.newLine();
                        }
                    }
                    //bw.close();
                    file_ct++;
                } catch (Exception e){}

            }
        });

        bt_stop.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0){
                String filename;

                if (ct==6)
                    correct();
                sm.unregisterListener(myAccelerometerListener);
                try {
                    filename = "/storage/emulated/0/DCIM/Signal_" + String.valueOf(file_ct) + ".txt";
                    FileWriter fw = new FileWriter(filename, false);
                    BufferedWriter bw = new BufferedWriter(fw); //將BufferedWeiter與FileWrite物件做連結
                    bw.write(output);
                    bw.close();
                    output = "";
                }catch(IOException e){}
            }
        });

    }

    //設計監聽式的觸發事件：1.當SENSOR讀取的數據變化的時候    2.當SENSOR的準確度發生變化的時候
    SensorEventListener myAccelerometerListener = new SensorEventListener(){
        public void onSensorChanged(SensorEvent sensorEvent){
            if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                if (aidx < 2000 && ct==1) {
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
                else if (bidx < 2000 && ct==2){
                    B[bidx][0] = sensorEvent.values[0];
                    B[bidx][1] = sensorEvent.values[1];
                    B[bidx][2] = sensorEvent.values[2];

                    tv.setText("X axis" + "\t\t" + String.valueOf(B[bidx][0]));
                    tv1.setText("Y axis" + "\t\t" + String.valueOf(B[bidx][1]));
                    tv2.setText("Z axis" + "\t\t" + String.valueOf(B[bidx][2]));
                    output = output + String.valueOf(B[bidx][0]) + " " + String.valueOf(B[bidx][1]) + " " + String.valueOf(B[bidx][2]);
                    output = output + "\n";
                    bidx++;
                }
                else if (cidx < 2000 && ct==3){
                    C[cidx][0] = sensorEvent.values[0];
                    C[cidx][1] = sensorEvent.values[1];
                    C[cidx][2] = sensorEvent.values[2];

                    tv.setText("X axis" + "\t\t" + String.valueOf(C[cidx][0]));
                    tv1.setText("Y axis" + "\t\t" + String.valueOf(C[cidx][1]));
                    tv2.setText("Z axis" + "\t\t" + String.valueOf(C[cidx][2]));
                    output = output + String.valueOf(C[cidx][0]) + " " + String.valueOf(C[cidx][1]) + " " + String.valueOf(C[cidx][2]);
                    output = output + "\n";
                    cidx++;
                }
                else if (didx < 2000 && ct==4){
                    D[didx][0] = sensorEvent.values[0];
                    D[didx][1] = sensorEvent.values[1];
                    D[didx][2] = sensorEvent.values[2];

                    tv.setText("X axis" + "\t\t" + String.valueOf(D[didx][0]));
                    tv1.setText("Y axis" + "\t\t" + String.valueOf(D[didx][1]));
                    tv2.setText("Z axis" + "\t\t" + String.valueOf(D[didx][2]));
                    output = output + String.valueOf(D[didx][0]) + " " + String.valueOf(D[didx][1]) + " " + String.valueOf(D[didx][2]);
                    output = output + "\n";
                   didx++;
                }
                else if (eidx < 2000 && ct==5){
                    E[eidx][0] = sensorEvent.values[0];
                    E[eidx][1] = sensorEvent.values[1];
                    E[eidx][2] = sensorEvent.values[2];

                    tv.setText("X axis" + "\t\t" + String.valueOf(E[eidx][0]));
                    tv1.setText("Y axis" + "\t\t" + String.valueOf(E[eidx][1]));
                    tv2.setText("Z axis" + "\t\t" + String.valueOf(E[eidx][2]));
                    output = output + String.valueOf(E[eidx][0]) + " " + String.valueOf(E[eidx][1]) + " " + String.valueOf(E[eidx][2]);
                    output = output + "\n";
                    eidx++;
                }
                else if (fidx < 2000 && ct==6){
                    for(int i=0; i<2000; i++)
                        for(int j=0; j<3; j++)
                            F[i][j] = 0;
                    F[fidx][0] = sensorEvent.values[0];
                    F[fidx][1] = sensorEvent.values[1];
                    F[fidx][2] = sensorEvent.values[2];

                    tv.setText("X axis" + "\t\t" + String.valueOf(F[fidx][0]));
                    tv1.setText("Y axis" + "\t\t" + String.valueOf(F[fidx][1]));
                    tv2.setText("Z axis" + "\t\t" + String.valueOf(F[fidx][2]));
                    output = output + String.valueOf(F[fidx][0]) + " " + String.valueOf(F[fidx][1]) + " " + String.valueOf(F[fidx][2]);
                    output = output + "\n";
                    fidx++;
                }
            }
        }

        public void onAccuracyChanged(Sensor sensor , int accuracy){}
    };

    public float getDTW(float[][] data1, float[][] data2, float[][] dtw, int a, int b){
        float cost;
        for(int i=1; i<a; i++){
            dtw[i][0] = 2000;
        }
        for(int i=1; i<b; i++){
            dtw[0][i] = 2000;
        }
        dtw[0][0] = 0;
        for (int i=1;i<a; i++){
            for (int j=1; j<b; j++){
                cost = Math.abs(data1[i][0]-data2[j][0])+Math.abs(data1[i][1]-data2[j][1])+Math.abs(data1[i][2] - data2[j][2]);
                dtw[i][j] = cost + Math.min(dtw[i - 1][j - 1], Math.min(dtw[i - 1][j], dtw[i][j - 1]));
            }
        }
        return dtw[a-1][b-1];
    }


    public void correct(){
        float sum1, sum2;
        int p=0;
        String ans;
        sum1 = (getDTW(A, B, DTW, aidx, bidx) + getDTW(A, C, DTW, aidx, cidx) + getDTW(A, D, DTW, aidx, didx) + getDTW(A, E, DTW, aidx, eidx)
                + getDTW(B, C, DTW, bidx, cidx) + getDTW(B, D, DTW, bidx, didx) + getDTW(B, E, DTW, bidx, eidx)
                + getDTW(C, D, DTW, cidx, didx) + getDTW(C, E, DTW, cidx, eidx) + getDTW(D, E, DTW, didx, eidx))/10 + (aidx+bidx+cidx+didx+eidx)/5*15 ;
        status.setText(String.valueOf(sum1));
        sum2 = sum1 - 2*(aidx+bidx+cidx+didx+eidx)/5*15;

        if (getDTW(A, F, DTW, aidx, fidx)<sum1 && getDTW(A, F, DTW, aidx, fidx)>sum2)
            p++;
        if (getDTW(B, F, DTW, bidx, fidx)<sum1 && getDTW(B, F, DTW, bidx, fidx)>sum2)
            p++;
        if (getDTW(C, F, DTW, cidx, fidx)<sum1 && getDTW(C, F, DTW, cidx, fidx)>sum2)
            p++;
        if (getDTW(D, F, DTW, didx, fidx)<sum1 && getDTW(D, F, DTW, didx, fidx)>sum2)
            p++;
        if (getDTW(E, F, DTW, eidx, fidx)<sum1 && getDTW(E, F, DTW, eidx, fidx)>sum2)
            p++;

        if(p==5)
            ans = "correct";
        else
            ans = "wrong";
        status.setText(ans + ": " + String.valueOf(p));
    }

    //onPause時不啟動呼叫sensor，避免浪費資源
    public void onPause(){
        sm.unregisterListener(myAccelerometerListener);
        super.onPause();
    }
}
