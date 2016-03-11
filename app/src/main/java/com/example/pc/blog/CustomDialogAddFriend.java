package com.example.pc.blog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;


public class CustomDialogAddFriend extends Dialog implements View.OnClickListener {

    public Activity mActivity;
    TextView TextDialogFriendName,TextDialogFriendId;
    MyPHPHandler PHPHandler;
    String UserEmail;
    SharedPreferences sharedPref;
    ImageButton AddFriendDialogButton;
    ImageView AddThisUser;
    String[] parts;
    String ReturnedData;


    public CustomDialogAddFriend(Activity a,String returnedData) {
        super(a);
        // TODO Auto-generated constructor stub
        this.mActivity = a;
        this.ReturnedData=returnedData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom_dialog_add_friend);
        TextDialogFriendName=(TextView)findViewById(R.id.TextFriendName);
        TextDialogFriendId=(TextView)findViewById(R.id.TextFriendId);
        AddThisUser=(ImageView)findViewById(R.id.AddThisUser);
        AddFriendDialogButton = (ImageButton) findViewById(R.id.AddFriend);
        parts = ReturnedData.split("\\#");
        TextDialogFriendName.setText(parts[0]);
        TextDialogFriendId.setText(parts[1]);

            String UserImageURL= MyPHPHandler.Online_URL+"uploadedimages/"+parts[2];
            LoadImage LoadPostImage = new LoadImage(mActivity);
            LoadPostImage.Image(AddThisUser);
            LoadPostImage.execute(UserImageURL);

        sharedPref = mActivity.getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        UserEmail= sharedPref.getString("Email","");


        AddFriendDialogButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PHPHandler = new MyPHPHandler(mActivity, "add_friend");
                        PHPHandler.execute(UserEmail, parts[1]);
                        dismiss();

                    }
                }
        );


    }




    @Override
    public void onClick(View v) {
        if (v == AddFriendDialogButton){
            Intent i = new Intent(mActivity, AddAlertAgain.class);
            mActivity.startActivity(i);
            dismiss();}

    }

}