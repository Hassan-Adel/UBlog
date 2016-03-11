package com.example.pc.blog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by pc on 7/5/2015.
 */
public class TabActivity1 extends Activity {

    MyDBHandler DBHandler;
    ArrayList<String> ImagePaths = new ArrayList<String>();
    ArrayList<String> BlogTitles = new ArrayList<String>();
    ArrayList<String> TheBlogTexts = new ArrayList<String>();
    ArrayList<String> TheBlogIDs = new ArrayList<String>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabactivity1);
        DBHandler = new MyDBHandler(this,null,null,1);

        TheBlogIDs=DBHandler.getAllIDs();
        BlogTitles=DBHandler.getAllBlogTitles();
        TheBlogTexts=DBHandler.getAllBlogText();
        ImagePaths=DBHandler.getAllImagePath();
        System.out.println("SIZE = " + BlogTitles.size());
        BlogItem[] Elements = new BlogItem[BlogTitles.size()];


        for(int i=0;i<BlogTitles.size();i++){
            Elements[i]=new BlogItem(BlogTitles.get(i),TheBlogTexts.get(i),ImagePaths.get(i));
            System.out.println("Blog "+i+" Note Title = "+BlogTitles.get(i));
            System.out.println("Blog "+i+" Note Text = "+TheBlogTexts.get(i));
            System.out.println("Blog "+i+" Note Path = "+ImagePaths.get(i));
        }

        //String [] Foods={"Bacon","Beef","Tuna","Candy","Meatball","Potato"};
        ListAdapter TheAdapter=new CustomAdapter(this,Elements);
        ListView TheListView = (ListView) findViewById(R.id.TheListView);
        TheListView.setAdapter(TheAdapter);

        TheListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent i = new Intent(TabActivity1.this,EditBlog.class);
                        i.putExtra("NoteId",TheBlogIDs.get(position));
                        i.putExtra("NoteTitle",BlogTitles.get(position));
                        i.putExtra("NoteText",TheBlogTexts.get(position));
                        i.putExtra("NoteImagePath",ImagePaths.get(position));
                        startActivity(i);
                        /*
                        Intent myIntent = new Intent(mycurentActivity.this, secondActivity.class);
                        myIntent.putExtra("key", myEditText.Text.toString();
                        startActivity(myIntent);
                        */

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

