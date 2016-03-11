package com.example.pc.blog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by pc on 7/5/2015.
 */
public class TabActivity2 extends Activity {

    MyDBHandler DBHandler;
    ArrayList<String[]> TheAlertsInfo = new ArrayList<String[]>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabactivity2);

        DBHandler = new MyDBHandler(this,null,null,1);
        TheAlertsInfo=DBHandler.getAllAsStrings();
        final AlertItem[] Elements=DBHandler.getAllAlerts();

        ListAdapter TheAdapter=new CustomAlertAdapter(this,Elements);
        ListView TheListView = (ListView) findViewById(R.id.TheListV);
        TheListView.setAdapter(TheAdapter);
        System.out.println("hhhhhh");
        TheListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        System.out.println("\n AlertId : "+ TheAlertsInfo.get(position)[0]+
                        "\n AlertTitle : "+TheAlertsInfo.get(position)[1]+
                        "\n AlertDay : "+TheAlertsInfo.get(position)[2]+
                        "\n AlertMonth : "+TheAlertsInfo.get(position)[3]+
                        "\n AlertYear : "+TheAlertsInfo.get(position)[4]+
                        "\n AlertHour : "+TheAlertsInfo.get(position)[5]+
                        "\n AlertMinute : "+TheAlertsInfo.get(position)[6]+
                        "\n AlertIsActive : "+TheAlertsInfo.get(position)[7]);

                        Intent i = new Intent(TabActivity2.this,EditAlertAgain.class);

                        i.putExtra("AlertId",TheAlertsInfo.get(position)[0]);
                        i.putExtra("AlertTitle",TheAlertsInfo.get(position)[1]);
                        i.putExtra("AlertDay",TheAlertsInfo.get(position)[2]);
                        i.putExtra("AlertMonth",TheAlertsInfo.get(position)[3]);
                        i.putExtra("AlertYear",TheAlertsInfo.get(position)[4]);
                        i.putExtra("AlertHour",TheAlertsInfo.get(position)[5]);
                        i.putExtra("AlertMinute",TheAlertsInfo.get(position)[6]);
                        i.putExtra("AlertIsActive",TheAlertsInfo.get(position)[7]);
                        startActivity(i);

                    }
                }
        );



    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, privatepublic.class);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();
    }

}
