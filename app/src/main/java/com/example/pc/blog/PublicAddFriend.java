package com.example.pc.blog;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;


public class PublicAddFriend extends Activity {

    EditText TextFriendId;
    public String UserID;
    SharedPreferences sharedPref;
    MyPHPHandler PHPHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar Act=this.getActionBar();
        Act.hide();
        setContentView(R.layout.activity_public_add_friend);

        sharedPref = this.getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        UserID= sharedPref.getString("ID", "");
        
        TextFriendId=(EditText)findViewById(R.id.TextFriendId);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SimpleImageActivity.class);
        intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageListFragmentFriends.INDEX);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }


    public void AddFriendButtononClicked(View view) {
        if (TextFriendId.getText().toString().equals(UserID))
            Toast.makeText(this, "You can't add yourself !!", Toast.LENGTH_LONG).show();
        else{
            PHPHandler = new MyPHPHandler(this, "fetch_user");
        PHPHandler.execute(TextFriendId.getText().toString());
         }
            }


}
/*

PHPHandler = new MyPHPHandler(this,"add_friend");
        PHPHandler.execute(UserEmail,TextFriendId.getText().toString());


 */