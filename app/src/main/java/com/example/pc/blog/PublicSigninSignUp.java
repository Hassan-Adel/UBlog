package com.example.pc.blog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


public class PublicSigninSignUp extends Activity {


    ImageView BlogImage;
    RelativeLayout RelativeContainer;
    LinearLayout linearLayout,linearLayoutOrg;
    /////////////////////////////////////////////////PublicStuff/////////////////////////////////////////
    EditText TextFirstName,TextLastName,TextEmail,TextPassword,TextAddress,TextCollege,TextStudyYear,TextGender;
    String  UserImage="male.png";
    String Gender="male";
    Button male, female, gender_button,SigningButton;
    MyPHPHandler PHPHandler;
    Activity activity;

    ////////////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar Act=getActionBar();
        Act.hide();
        activity=this;
        setContentView(R.layout.activity_public_signin_sign_up);
        BlogImage=(ImageView)findViewById(R.id.BlogImage);
        BlogImage.setBackgroundResource(R.drawable.smallblog);
        RelativeContainer=(RelativeLayout)findViewById(R.id.RelativeContainer);
        //linearLayout=(LinearLayout)findViewById(R.id.LinearContainer);
        //PHPHandler = new MyPHPHandler(this, "signup");
    }


    ///////////////////Validation//////////////////////
    public boolean ValidateTextField(EditText editText,String warning){
        if (editText.getText().toString().equals("")||editText.getText().toString().equals(null)) {
            editText.setError("Please Enter "+warning);
            return false;
        }
        else return true;
    }



    public  void SigningUponClick(View view){
        PHPHandler = new MyPHPHandler(this, "signup");
        BlogImage.setBackgroundResource(R.drawable.bigblog);

        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View SignUpView=layoutInflater.inflate(R.layout.activity_public_sign_up,null);
        RelativeContainer.removeAllViews();
        RelativeContainer.addView(SignUpView);



        /////////////////////////////////////////////////////////////

        TextFirstName=(EditText)findViewById(R.id.TextFirstName);
        TextLastName=(EditText)findViewById(R.id.TextLastName);
        TextEmail=(EditText)findViewById(R.id.TextEmail);
        TextPassword=(EditText)findViewById(R.id.TextPassword);
        TextAddress=(EditText)findViewById(R.id.TextAddress);
        TextCollege=(EditText)findViewById(R.id.TextCollege);
        TextStudyYear=(EditText)findViewById(R.id.TextStudyYear);
        TextGender=(EditText)findViewById(R.id.TextGender);
        gender_button = (Button) findViewById(R.id.gender_button);
        SigningButton=(Button)findViewById(R.id.SignUpButton);
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
                if (!hasFocus) {
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


        SigningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                /////////////////////////////////////////Validation////////////////////////////////////////
                if (TextFirstName.getText().toString().equals("") || TextFirstName.getText().toString().equals(null)) {
                    TextFirstName.setError("Please Enter your first name");
                }
                else if (TextLastName.getText().toString().equals("") || TextLastName.getText().toString().equals(null)) {
                    TextLastName.setError("Please Enter your last name");
                }
                else if (Gender.equals("") || Gender.equals(null)) {
                    TextPassword.setError("Please Enter your gender");
                }
                else if (TextEmail.getText().toString().equals("") || TextEmail.getText().toString().equals(null)) {
                    TextEmail.setError("Please Enter your email");
                }
                else if (TextPassword.getText().toString().equals("") || TextPassword.getText().toString().equals(null)) {
                    TextPassword.setError("Please Enter your password");
                }
                else if (TextAddress.getText().toString().equals("") || TextAddress.getText().toString().equals(null)) {
                    TextAddress.setError("Please Enter your address");
                }
                else if (TextCollege.getText().toString().equals("") || TextCollege.getText().toString().equals(null)) {
                    TextCollege.setError("Please Enter your college");
                }
                else if (TextStudyYear.getText().toString().equals("") || TextStudyYear.getText().toString().equals(null)) {
                    TextStudyYear.setError("Please Enter your study year");
                }
                //////////////////////////////////////////////////////////////////////////////////////////
                else {
                    if(Gender=="female")UserImage="female.png";

                    PHPHandler.execute(TextFirstName.getText().toString(), TextLastName.getText().toString(), Gender,UserImage,
                    TextEmail.getText().toString(), TextPassword.getText().toString(), TextAddress.getText().toString(),
                    TextCollege.getText().toString(), TextStudyYear.getText().toString());
                }


            }
        });

    }


    public  void SigningInonClick(View view){
        //PHPHandler = new MyPHPHandler(this, "login");
        //PHPHandler.delegate = this;
        BlogImage.setBackgroundResource(R.drawable.smallblog);

        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View LogInView=layoutInflater.inflate(R.layout.activity_public_log_in,null);
        RelativeContainer.removeAllViews();
        RelativeContainer.addView(LogInView);

        ///////////////////////////////////////////////////////////////////////////////////////

        Button LogInButton=(Button)findViewById(R.id.LogInButton);
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText TextEmail, TextPassword;

                TextEmail = (EditText) findViewById(R.id.TextEmail);
                TextPassword = (EditText) findViewById(R.id.TextPassword);
                /////////////////////////////////////////Validation////////////////////////////////////////
                if (TextEmail.getText().toString().equals("") || TextEmail.getText().toString().equals(null)) {
                    TextEmail.setError("Please Enter The Note Title");
                } else if (TextPassword.getText().toString().equals("") || TextPassword.getText().toString().equals(null)) {
                    TextPassword.setError("Please Enter The Note description ");
                }

                //////////////////////////////////////////////////////////////////////////////////////////
                else {
                    if (TextEmail.getText().toString().equals("admin") && TextPassword.getText().toString().equals("admin")) {
                        Intent intent = new Intent(PublicSigninSignUp.this, SimpleImageActivity.class);
                        intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageListFragmentReports.INDEX);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                    else new MyPHPHandler(activity, "login").execute(TextEmail.getText().toString(), TextPassword.getText().toString());
            }
        }
        });

    }

    public void ForgotPasswordClicked(View view){
        Intent i = new Intent(PublicSigninSignUp.this, PublicForgotPassword.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
            Intent intent = new Intent(this, privatepublic.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
    }


}


