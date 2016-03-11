package com.example.pc.blog;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class EditAlertAgain extends Activity {

    Bundle TimeBun;
    Bundle DateBun;
    Bundle TabActivitytwoData;
    String AlertId;
    String AlertTitle;
    String AlertDay;
    String AlertMonth;
    String AlertYear;
    String AlertHour;
    String AlertMinute;
    String AlertIsActive;


    EditText TheTitle,TheTimeString,TheDateString;

    MyDBHandler DBHandler;
    Switch TheSwitchh;
    int day ;
    int month;
    int yearr;
    int hour;
    int minutee;
    String state="false";

    ImageButton BtnSTPD,BtnSDPD;
    static final int DIALOG_ID=0;
    static final int DIALOG_IDD=1;

    public boolean StringToBoolean(String x){
        if (x.equals("true"))
        {
            return true;
        }else
        {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alert_again);

        //when you go bsck go to the second tab ()
        tabs.obj = 1;

        TheTitle=(EditText)findViewById(R.id.TheTitle);
        TheTimeString=(EditText)findViewById(R.id.TheTimeString);
        TheDateString=(EditText)findViewById(R.id.TheDateString);
        TheSwitchh=(Switch)findViewById(R.id.TheSwitchh);




        DBHandler = new MyDBHandler(this,null,null,1);
        TabActivitytwoData=getIntent().getExtras();
        AlertId=TabActivitytwoData.getString("AlertId");
        AlertTitle=TabActivitytwoData.getString("AlertTitle");
        AlertDay=TabActivitytwoData.getString("AlertDay");
        AlertMonth=TabActivitytwoData.getString("AlertMonth");
        AlertYear=TabActivitytwoData.getString("AlertYear");
        AlertHour=TabActivitytwoData.getString("AlertHour");
        AlertMinute=TabActivitytwoData.getString("AlertMinute");
        AlertIsActive=TabActivitytwoData.getString("AlertIsActive");

        yearr=Integer.valueOf(AlertYear);
        month=Integer.valueOf(AlertMonth);
        day=Integer.valueOf(AlertDay);
        hour=Integer.valueOf(AlertHour);
        minutee=Integer.valueOf(AlertMinute);


        TheTitle.setText(AlertTitle);
        TheTimeString.setText(AlertHour +" : "+AlertMinute);
        TheDateString.setText(AlertDay +" / "+AlertMonth+" / "+AlertYear);
        TheSwitchh.setChecked(StringToBoolean(AlertIsActive));


        TheSwitchh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    state = "true";
                } else {
                    state = "false";
                }
                System.out.println("|||||||||||||||| state =" + state);
            }


        });


        ShowTimePickerDialog();
        ShowDatePickerDialog();

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
    public void ShowTimePickerDialog(){
        BtnSTPD=(ImageButton)findViewById(R.id.Addtime);
        BtnSTPD.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == DIALOG_ID)return new TimePickerDialog(EditAlertAgain.this,KTimePickerListner,Integer.valueOf(AlertHour),Integer.valueOf(AlertMinute),false);
        else if(id == DIALOG_IDD)return new DatePickerDialog(EditAlertAgain.this,KDatePickerListner,Integer.valueOf(AlertYear),Integer.valueOf(AlertMonth),Integer.valueOf(AlertDay));
        else return null;
    }


    protected  TimePickerDialog.OnTimeSetListener KTimePickerListner = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            minutee = minute;
            Toast.makeText(EditAlertAgain.this, hour + " : " + minutee, Toast.LENGTH_SHORT).show();
            TheTimeString.setText(hour +" : "+minutee);
        }
    };

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void ShowDatePickerDialog(){
        BtnSDPD=(ImageButton)findViewById(R.id.AddDate);
        BtnSDPD.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_IDD);
                    }
                }
        );
    }

    protected DatePickerDialog.OnDateSetListener KDatePickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            yearr = year;
            month = monthOfYear;
            day = dayOfMonth;
            Toast.makeText(EditAlertAgain.this,day +" / "+(month+1)+" / "+yearr,Toast.LENGTH_SHORT).show();
            TheDateString.setText(day +" / "+(month+1)+" / "+yearr);
        }
    };



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




    public void EditAlertClicked(View view){

        ///////////////////Validation//////////////////////

        Calendar current = Calendar.getInstance();

        Calendar cal = Calendar.getInstance();
        cal.set(yearr,
                month,
                day,
                hour,
                minutee,
                00);

        if(cal.compareTo(current) <= 0){
            //The set Date/Time already passed
            Toast.makeText(getApplicationContext(),
                    "Invalid Date/Time",
                    Toast.LENGTH_SHORT).show();
        }


        else if (TheTitle.getText().toString().equals("")||TheTitle.getText().toString().equals(null)) {
            TheTitle.setError("Please Enter The Alert Title");
        }
        else {
            System.out.println("TTTTitle : " + TheTitle.getText().toString());
            System.out.println("Day : " + day);
            System.out.println("Month : " + month);
            System.out.println("Year : " + yearr);
            System.out.println("Hour : " + hour);
            System.out.println("Minute : " + minutee);
            System.out.println("State : " + state);

            //AlertItem alertItem = new AlertItem(TheTitle.getText().toString(),day,month,year,hour,minute,state);
            DBHandler.updateAlert(AlertId, TheTitle.getText().toString(), day, month, yearr, hour, minutee, state);
            setAlarm(DBHandler.getAllCalenders(),DBHandler.getAlertIsActive());
            finish();
            Intent i = new Intent(EditAlertAgain.this, tabs.class);
            startActivity(i);
            overridePendingTransition(0, 0);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editnote, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DBHandler.deletealert(AlertId);

        Intent i = new Intent(EditAlertAgain.this,tabs.class);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();
        return  true;
    }



    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, tabs.class);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();
    }



}


    /*
        day = datePicker.getDayOfMonth();
        month = datePicker.getMonth() + 1;
        year = datePicker.getYear();
        hour   = timePicker.getCurrentHour();
        minute = timePicker.getCurrentMinute();


            private void setAlarm(ArrayList<Calendar> targetCal,ArrayList<String> IsActive) {


        AlarmManager alarmManager;
        ArrayList intentArray = new ArrayList<PendingIntent>();
        //for(int f=0;f<24;f++)

        for (int i = 0; i < targetCal.size();i++ ) {
            if(IsActive.get(i)=="false")continue;
            else {
                Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
                PendingIntent pi = PendingIntent.getBroadcast(getBaseContext(), i, intent, 0);

                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.get(i).getTimeInMillis(), pi);

                intentArray.add(pi);
            }
        }

    }





        */