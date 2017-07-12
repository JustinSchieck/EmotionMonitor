package com.example.justin.emotionmonitorv3;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.os.PowerManager;


import java.io.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyApp";
    String data;
    String error;

    //Vibration and PowerManager
    PowerManager.WakeLock wl;




      //Timer Varilbales
//    private Timer timer = new Timer();
//    TimerTask timerTask;
//    int m_interval = 30000; // 5 seconds by default, can be changed later
//
//    Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
        final Vibrator vib = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        vib.vibrate(1000);
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");

        wl.acquire();

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                vib.vibrate(1000);
                PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
                wakeLock.acquire();
            }
        }, 0, 900000);
    }

    public void addListenerOnButton() {

        //Wire up the button
        //get the butt
        ImageButton HappyBtn = (ImageButton) findViewById(R.id.happyBtn);
        ImageButton SadBtn = (ImageButton) findViewById(R.id.sadBtn);
        ImageButton MadBtn = (ImageButton) findViewById(R.id.madBtn);
        ImageButton MehBtn = (ImageButton) findViewById(R.id.mehBtn);


        //set the button
        HappyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i(TAG, "Happy");



                Calendar c = Calendar.getInstance();
                int Seconds = c.get(Calendar.SECOND);
                int Minutes = c.get(Calendar.MINUTE);
                int Hours = c.get(Calendar.HOUR);

                String Sec = String.valueOf(Seconds);
                String Min = String.valueOf(Minutes);
                String Hour = String.valueOf(Hours);

                String localTime = Hour + ":" + Min + ":" + Sec;

                data = (localTime + "," + "Happy" + "," + "\n");
                Toast.makeText(getApplicationContext(), "Thank you!", Toast.LENGTH_SHORT).show();
                try {
                    writeData();
                } catch (Exception e) {
                    Log.e("Exception", "File write failed: " + e.toString());
                    error = e.toString();
                    writeError();
                }
            }
        });
        SadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i(TAG, "Sad");

                Calendar c = Calendar.getInstance();
                int Seconds = c.get(Calendar.SECOND);
                int Minutes = c.get(Calendar.MINUTE);
                int Hours = c.get(Calendar.HOUR);

                String Sec = String.valueOf(Seconds);
                String Min = String.valueOf(Minutes);
                String Hour = String.valueOf(Hours);

                String localTime = Hour + ":" + Min + ":" + Sec;

                data = (localTime + "," + "Sad" + "," + "\n");
                Toast.makeText(getApplicationContext(), "Thank you!", Toast.LENGTH_SHORT).show();
                try {
                    writeData();
                } catch (Exception e) {
                    Log.e("Exception", "File write failed: " + e.toString());
                    error = e.toString();
                    writeError();
                }
            }
        });
        MadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i(TAG, "Mad");

                Calendar c = Calendar.getInstance();
                int Seconds = c.get(Calendar.SECOND);
                int Minutes = c.get(Calendar.MINUTE);
                int Hours = c.get(Calendar.HOUR);

                String Sec = String.valueOf(Seconds);
                String Min = String.valueOf(Minutes);
                String Hour = String.valueOf(Hours);

                String localTime = Hour + ":" + Min + ":" + Sec;

                data = (localTime + "," + "Mad" + "," + "\n");
                Toast.makeText(getApplicationContext(), "Thank you!", Toast.LENGTH_SHORT).show();
                try {
                    writeData();
                } catch (Exception e) {
                    Log.e("Exception", "File write failed: " + e.toString());
                    error = e.toString();
                    writeError();
                }

            }
        });
        MehBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i(TAG, "IDK");

                Calendar c = Calendar.getInstance();
                int Seconds = c.get(Calendar.SECOND);
                int Minutes = c.get(Calendar.MINUTE);
                int Hours = c.get(Calendar.HOUR);

                String Sec = String.valueOf(Seconds);
                String Min = String.valueOf(Minutes);
                String Hour = String.valueOf(Hours);

                String localTime = Hour + ":" + Min + ":" + Sec;

                data = (localTime + "," + "I dont know" + "," + "\n");
                Toast.makeText(getApplicationContext(), "Thank you!", Toast.LENGTH_SHORT).show();
                try {
                    writeData();
                } catch (Exception e) {
                    Log.e("Exception", "File write failed: " + e.toString());
                    error = e.toString();
                    writeError();
                }


            }
        });
    }


    //writing to file
    public void writeData() {

        try {
            writeToFile(data);

        } catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }

    //writing to file
    public void writeError() {

        try {
            writeToFileError(error);

        } catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }

    public void writeToFile(String data) {
        final File path =
                Environment.getExternalStoragePublicDirectory
                        (
                                Environment.DIRECTORY_DCIM + "/Emotion_Data/"
                        );

        if (!path.exists()) {
            path.mkdirs();
        }

        File file = new File(path, "Emotion.csv");
        try {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file, true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void writeToFileError(String data) {
        final File path =
                Environment.getExternalStoragePublicDirectory
                        (
                                Environment.DIRECTORY_DCIM + "/Errors/"
                        );

        if (!path.exists()) {
            path.mkdirs();
        }

        File file = new File(path, "EmotionError.txt");
        try {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file, true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            // fOut.flush();
            fOut.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


}
