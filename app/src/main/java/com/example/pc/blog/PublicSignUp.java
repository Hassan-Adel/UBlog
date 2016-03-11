package com.example.pc.blog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import org.w3c.dom.Text;


public class PublicSignUp extends Activity {


    EditText TextFirstName,TextLastName,TextEmail,TextPassword,TextAddress,TextCollege,TextStudyYear,TextGender;
    Spinner dropdown;
    String Gender="male";
    Button male, female, gender_button,signup;
    MyPHPHandler PHPHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_sign_up);

        TextFirstName=(EditText)findViewById(R.id.TextFirstName);
        TextLastName=(EditText)findViewById(R.id.TextLastName);
        TextEmail=(EditText)findViewById(R.id.TextEmail);
        TextPassword=(EditText)findViewById(R.id.TextPassword);
        TextAddress=(EditText)findViewById(R.id.TextAddress);
        TextCollege=(EditText)findViewById(R.id.TextCollege);
        TextStudyYear=(EditText)findViewById(R.id.TextStudyYear);
        TextGender=(EditText)findViewById(R.id.TextGender);
        gender_button = (Button) findViewById(R.id.gender_button);
        male = (Button) findViewById(R.id.male_gender);
        female = (Button) findViewById(R.id.female_gender);

        male.setVisibility(View.GONE);
        female.setVisibility(View.GONE);
        gender_button.bringToFront();



        gender_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setVisibility(View.VISIBLE);
                male.bringToFront();
                female.bringToFront();
                female.setVisibility(View.VISIBLE);
            }
        });

        TextGender.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    male.setVisibility(View.GONE);
                    female.setVisibility(View.GONE);
                }
            }
        });
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextGender.setText("male");
                Gender = "male";
                male.setVisibility(View.GONE);
                female.setVisibility(View.GONE);
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextGender.setText("female");
                Gender = "female";
                male.setVisibility(View.GONE);
                female.setVisibility(View.GONE);
            }
        });

    }



    public void SignUpButtononClicked(View view){


        PHPHandler = new MyPHPHandler(this,"signup");

        PHPHandler.execute(TextFirstName.getText().toString(), TextLastName.getText().toString(),Gender,
                TextEmail.getText().toString(),TextPassword.getText().toString(),TextAddress.getText().toString(),
                TextCollege.getText().toString(),TextStudyYear.getText().toString());

        Intent i = new Intent(PublicSignUp.this, PublicAddImage.class);
        startActivity(i);

    }

}
