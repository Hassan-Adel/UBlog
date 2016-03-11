package com.example.pc.blog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;


public class CustomDialog extends Dialog implements View.OnClickListener {

public Activity mActivity;
public Dialog d;
public ImageButton AddAlert, AddBlog;



public CustomDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.mActivity = a;
        }

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom_dialog);
        AddAlert = (ImageButton) findViewById(R.id.AddAlert);
        AddAlert.setOnClickListener(this);
        AddBlog = (ImageButton) findViewById(R.id.AddBlog);
        AddBlog.setOnClickListener(this);


        }




        @Override
        public void onClick(View v) {
                if (v == AddAlert){
                    Intent i = new Intent(mActivity, AddAlertAgain.class);
                    mActivity.startActivity(i);
                        dismiss();}
                else {
                        Intent i = new Intent(mActivity, AddBlog.class);
                        mActivity.startActivity(i);
                        dismiss();
                }
        }

        }