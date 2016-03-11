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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.transform.Source;


public class AddAlertAgain extends Activity {

    DatePicker datePicker;
    EditText TheTitle,TheTimeString,TheDateString;
    TimePicker timePicker;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alert_again);

        //when you go bsck go to the second tab ()
        tabs.obj = 1;


        TheTitle=(EditText)findViewById(R.id.TheTitle);
        TheTimeString=(EditText)findViewById(R.id.TheTimeString);
        TheDateString=(EditText)findViewById(R.id.TheDateString);
        TheSwitchh=(Switch)findViewById(R.id.TheSwitchh);

        DBHandler = new MyDBHandler(this,null,null,1);

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
        Calendar now = Calendar.getInstance();
        if(id == DIALOG_ID)return new TimePickerDialog(AddAlertAgain.this,KTimePickerListner,now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false);
        else if(id == DIALOG_IDD)return new DatePickerDialog(AddAlertAgain.this,KDatePickerListner,now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
        else return null;
    }


    protected  TimePickerDialog.OnTimeSetListener KTimePickerListner = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            minutee = minute;
            Toast.makeText(AddAlertAgain.this,hour +" : "+minutee,Toast.LENGTH_SHORT).show();
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
        Toast.makeText(AddAlertAgain.this,day +" / "+(month+1)+" / "+yearr,Toast.LENGTH_SHORT).show();
        TheDateString.setText(day +" / "+(month+1)+" / "+yearr);
    }
};



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






    public void AddAlertClicked(View view){

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

        else if (TheTimeString.getText().toString().equals("")||TheTimeString.getText().toString().equals(null)) {
            TheTimeString.setError("Please Enter The Alet Time");
        }

        else if (TheDateString.getText().toString().equals("")||TheDateString.getText().toString().equals(null)) {
            TheDateString.setError("Please Enter The Alet Date");
        }


        else {
            System.out.println("Title : " + TheTitle.getText().toString());
            System.out.println("Day : " + day);
            System.out.println("Month : " + month);
            System.out.println("Year : " + yearr);
            System.out.println("Hour : " + hour);
            System.out.println("Minute : " + minutee);
            System.out.println("State : " + state);
            AlertItem alertItem = new AlertItem(TheTitle.getText().toString(), day, month, yearr, hour, minutee, state);
            DBHandler.addAlert(alertItem);
            setAlarm(DBHandler.getAllCalenders(),DBHandler.getAlertIsActive());
            finish();
            Intent i = new Intent(AddAlertAgain.this, tabs.class);
            startActivity(i);
            overridePendingTransition(0, 0);
        }
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
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        day = datePicker.getDayOfMonth();
        month = datePicker.getMonth() + 1;
        year = datePicker.getYear();

        timePicker = (TimePicker)findViewById(R.id.timePicker);
        hour   = timePicker.getCurrentHour();
        minute = timePicker.getCurrentMinute();
        */