package com.example.pc.blog;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;


public class ShowDialog extends Activity {

    MyDBHandler DBHandler;
    TextView AlertTitle;
    String Total="";
     MediaPlayer mp;

    public void PlayClip(Context context,String ClipName){


        if (mp.isPlaying()) {
            mp.stop();
        }

        try {
            mp.reset();
            AssetFileDescriptor afd;
            afd = context.getAssets().openFd(ClipName);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepare();
            mp.start();

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }	}


    public void PauseClip(Context context,String ClipName){

        try {
            mp.reset();
            AssetFileDescriptor afd;
            afd = context.getAssets().openFd(ClipName);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepare();
            mp.stop();

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mp= new MediaPlayer();
        PlayClip(this, "rin.mp3");
        setContentView(R.layout.activity_show_dialog);

        DBHandler = new MyDBHandler(this,null,null,1);
        AlertTitle=(TextView)findViewById(R.id.AlertTitle);
        for (int i=0;i<DBHandler.getCurrentAlertTitles().size();i++){
            Total+=DBHandler.getCurrentAlertTitles().get(i)+"\n";
        }

        AlertTitle.setText(Total);

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void setAlarm(ArrayList<Calendar> targetCal,ArrayList<String> IsActive) {


        AlarmManager alarmManager;
        ArrayList intentArray = new ArrayList<PendingIntent>();
        //for(int f=0;f<24;f++)

        for (int i = 0; i < targetCal.size();i++ ) {
            if(IsActive.get(i).equals("true")){Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
                PendingIntent pi = PendingIntent.getBroadcast(getBaseContext(), i, intent, 0);

                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.get(i).getTimeInMillis(), pi);

                intentArray.add(pi);}
            else {
                System.out.println("|||||||||||||||||||||||||||||||||||||||"+IsActive.get(i));
            }
        }

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void StopButtonClicked(View view){
        PauseClip(this,"rin.mp3");
        finish();
    }

    public void SnoozeButtonClicked(View view){
        DBHandler.SnoozeCurrentAlerts();
        setAlarm(DBHandler.getAllCalenders(), DBHandler.getAlertIsActive());
        finish();
    }




}
