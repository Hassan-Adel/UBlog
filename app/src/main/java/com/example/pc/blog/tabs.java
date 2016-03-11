package com.example.pc.blog;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;

import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TabHost;


public class tabs extends TabActivity {
    static int obj =0 ;
    MyDBHandler DBHandler;

//--------------------------------------------------Animation Stuff--------------------------------------------------//
    ImageButton GreenAdd,GreenAddNote,GreenAddAlert;

    boolean Myanimation=false;
//-------------------------------------------------------------------------------------------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar Act=getActionBar();
        Act.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        DBHandler = new MyDBHandler(this,null,null,1);
        DBHandler.ClearInvalidAlerts();
        DBHandler.ClearInvalidAlerts();

//--------------------------------------------------Animation Stuff--------------------------------------------------//
        GreenAdd=(ImageButton)findViewById(R.id.imageButton1);
        GreenAddNote=(ImageButton)findViewById(R.id.imageButton2);
        GreenAddAlert=(ImageButton)findViewById(R.id.imageButton3);
        GreenAddNote.setVisibility(View.GONE);
        GreenAddAlert.setVisibility(View.GONE);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;
        final int height = size.y;


//--------------------------------------------------Tabs Stuff----------------------------------------------------//

        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

        TabHost.TabSpec tab1 = tabHost.newTabSpec("FirstTab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("SeconTab");

        tab1.setIndicator("Notes");
        tab1.setContent(new Intent(this, TabActivity1.class));

        tab2.setIndicator("Alerts");
        tab2.setContent(new Intent(this, TabActivity2.class));


        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

        tabHost.setCurrentTab(obj);

//-------------------------------------------CLick Listeners------------------------------------------------//180,90
        // button click event
        GreenAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start the animation
                if (!Myanimation) {

                    GreenAddNote.setVisibility(View.VISIBLE);
                    GreenAddAlert.setVisibility(View.VISIBLE);

                    ObjectAnimator mover = ObjectAnimator.ofFloat(GreenAddNote, "translationY", 0, (int)(-height*0.24));
                    mover.setDuration(300);
                    mover.start();
                    mover = ObjectAnimator.ofFloat(GreenAddAlert, "translationY", 0, (int)(-height*0.12));
                    mover.setDuration(300);
                    mover.start();


                    Myanimation = true;
                } else {
                    ObjectAnimator mover = ObjectAnimator.ofFloat(GreenAddNote, "translationY", (int)(-height*0.24), 0);
                    mover.setDuration(300);
                    mover.start();
                    mover = ObjectAnimator.ofFloat(GreenAddAlert, "translationY", (int)(-height*0.12), 0);
                    mover.setDuration(300);
                    mover.start();
                    Myanimation = false;
                }
            }
        });

        GreenAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tabs.this, AddBlog.class);
                startActivity(i);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        GreenAddAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tabs.this, AddAlertAgain.class);
                startActivity(i);
                overridePendingTransition(0, 0);
                finish();
            }
        });

//-------------------------------------------------------------------------------------------------------------------//





    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, privatepublic.class);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();
    }
}


     /*
        CustomDialog start_dialog = new CustomDialog(this);
        start_dialog.show();
        */


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_test, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final  CustomDialog dialog = new CustomDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialog.setContentView(R.layout.activity_custom_dialog);
        dialog.show();

        return  true;
    }



    public void AddClicked(View view){
       Intent i = new Intent(tabs.this,Splash.class);
   }
    */