package com.example.justin.emotionmonitorv3;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;



import java.io.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyApp";
    String data;
    String error;

    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
    Date currentLocalTime = cal.getTime();
    DateFormat date = new SimpleDateFormat("HH:mm:ss");
    // you can get seconds by adding  "...:ss" to it
    String localTime = date.format(currentLocalTime);

    //methods
    String path =
            Environment.getExternalStorageDirectory() + File.separator  + "Emotion_Data";
    // Create the folder.
    File folder = new File(path);

    // Create the file.
    File file = new File(folder, "EmotionData.csv");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        vib.vibrate(500);
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
                data = (localTime + "," + "Happy" + "," + "\n");
                Toast.makeText(getApplicationContext(), "Thank you!", Toast.LENGTH_SHORT).show();
                try {
                    writeData();
                }catch(Exception e)
                {
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
                data = (localTime + "," + "Sad" + "," + "\n");
                Toast.makeText(getApplicationContext(), "Thank you!", Toast.LENGTH_SHORT).show();
                try {
                    writeData();
                }catch(Exception e)
                {
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
                data = (localTime + "," + "Mad" + "," + "\n" );
                Toast.makeText(getApplicationContext(), "Thank you!", Toast.LENGTH_SHORT).show();
                try {
                    writeData();
                }catch(Exception e)
                {
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
                data = (localTime + ","+ "I dont know" + "," + "\n");
                Toast.makeText(getApplicationContext(), "Thank you!", Toast.LENGTH_SHORT).show();
                try {
                    writeData();
                }catch(Exception e)
                {
                    Log.e("Exception", "File write failed: " + e.toString());
                    error = e.toString();
                    writeError();
                }


            }
        });
    }


    //writing to file
    public void writeData(){

        try {
            writeToFile(data);

        }catch(Exception e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }
    //writing to file
    public void writeError(){

        try {
            writeToFileError(error);

        }catch(Exception e)
        {
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
        try
        {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file, true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

             fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void writeToFileError(String data)
    {
        final File path =
                Environment.getExternalStoragePublicDirectory
                        (
                                Environment.DIRECTORY_DCIM + "/Errors/"
                        );

        if(!path.exists())
        {
            path.mkdirs();
        }

        File file = new File(path, "EmotionError.txt");
        try
        {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file, true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            // fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }



}
