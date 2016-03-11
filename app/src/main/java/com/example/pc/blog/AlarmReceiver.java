package com.example.pc.blog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {


	@Override
	public void onReceive(Context arg0, Intent arg1) {

		Toast.makeText(arg0, "Alarm received!", Toast.LENGTH_SHORT).show();


		Intent mIntent = new Intent(arg0,ShowDialog.class); //Same as above two lines
		mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		arg0.startActivity(mIntent);


	}





}
