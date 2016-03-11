package com.example.pc.blog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class PublicLogIn extends Activity {

    EditText TextEmail,TextPassword;
    MyPHPHandler PHPHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_log_in);
        TextEmail=(EditText)findViewById(R.id.TextEmail);
        TextPassword=(EditText)findViewById(R.id.TextPassword);

    }

    public void LogInButtononClicked(View view){

        PHPHandler = new MyPHPHandler(this,"login");
        PHPHandler.execute(TextEmail.getText().toString(), TextPassword.getText().toString());


    }





}
