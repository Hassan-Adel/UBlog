package com.example.pc.blog;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class PublicForgotPassword extends Activity {

    EditText TextEnterEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);ActionBar Act=getActionBar();
        Act.hide();

        setContentView(R.layout.activity_public_forgot_password);
        TextEnterEmail=(EditText)findViewById(R.id.TextEnterEmail);
    }


    public void SendEmailButtononClick(View view){

    }
}
