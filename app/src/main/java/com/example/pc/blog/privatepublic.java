package com.example.pc.blog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by pc on 7/5/2015.
 */
public class privatepublic extends Activity{

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar Act=getActionBar();
        Act.hide();
        setContentView(R.layout.privatepublic);
        System.gc();  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>GARBAGE COLLECTION !!

 }

    public void privateclicked(View view){
        Intent i = new Intent(privatepublic.this,tabs.class);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();


        //Intent intent = new Intent(this, HomeActivity.class);
        //startActivity(intent);

    }
    public void publicclicked(View view){

        //Toast.makeText(getApplicationContext(), "Not yet implemented", Toast.LENGTH_LONG).show();

        Intent i = new Intent(privatepublic.this, PublicSigninSignUp.class);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();
    }
    public  void testingReportsClicked(View view){
        Intent intent = new Intent(this, SimpleImageActivity.class);
        intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageListFragmentReports.INDEX);
        startActivity(intent);
        overridePendingTransition(0, 0);

    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    }
