package com.example.pc.blog;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;


public class PublicTabs extends TabActivity {

    static int obj=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_tabs);



        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

        TabHost.TabSpec tab1 = tabHost.newTabSpec("FirstTab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("SeconTab");

        tab1.setIndicator("Log In");
        tab1.setContent(new Intent(this, PublicLogIn.class));

        tab2.setIndicator("Sign Up");
        tab2.setContent(new Intent(this, PublicSignUp.class));


        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

        tabHost.setCurrentTab(obj);

    }









}
