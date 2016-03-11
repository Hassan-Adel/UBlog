package com.example.pc.blog;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Hassan on 7/8/2015.
 */
public class CustomAlertAdapter extends ArrayAdapter<AlertItem> {

    public boolean StringToBoolean(String x){
       if (x.equals("true"))
       {
           return true;
       }else
       {
           return false;
       }
    }

    public CustomAlertAdapter(Context context , AlertItem[] elements) {
        super(context,R.layout.custom_alert_row, elements);
    }
    /*public View getView(int ImagePathposition,int BlogTitleposition,int BlogTextposition, View convertView, ViewGroup parent)*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater TheInflater = LayoutInflater.from(getContext());
            View CustomView = TheInflater.inflate(R.layout.custom_alert_row, parent, false);

            String SingleAlertTitle = getItem(position).get_AlertTitle();
            String SingleAlertDate = getItem(position).get_AlertDay() + "/" + (getItem(position).get_AlertMonth()+1) + "/" + getItem(position).get_AlertYear();
            String SingleAlertTime = getItem(position).get_AlertHour() + ":" + getItem(position).get_AlertMinute();
            Boolean SingleIsActive = StringToBoolean(getItem(position).get_AlertIsActive());

            TextView TheAlertTitle = (TextView) CustomView.findViewById(R.id.TheTitle);
            TextView TheAlertDate = (TextView) CustomView.findViewById(R.id.TheDate);
            TextView TheAlertTime = (TextView) CustomView.findViewById(R.id.TheTime);
            Switch TheSwitch = (Switch) CustomView.findViewById(R.id.TheSwitch);

            TheAlertTitle.setText(SingleAlertTitle);

            TheAlertDate.setText(SingleAlertDate);

            TheAlertTime.setText(SingleAlertTime);

            TheSwitch.setChecked(SingleIsActive);
            return CustomView;
        }
        else return convertView;
    }
}

