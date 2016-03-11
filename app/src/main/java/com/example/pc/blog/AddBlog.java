package com.example.pc.blog;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;


public class AddBlog extends Activity {


    int RESULT_LOAD_IMG;
    String imgDecodableString;
    EditText TheTile;
    EditText TheBlogText;
    MyDBHandler DBHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog);

        //when you go bsck go to the second tab ()
        tabs.obj = 0;

        TheTile=(EditText)findViewById(R.id.TheTitle);
        TheBlogText=(EditText)findViewById(R.id.TheBlogText);
        DBHandler = new MyDBHandler(this,null,null,1);
    }


    public void AddImageClicked(View view){
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.AddImage);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }






    public void AddNoteClicked(View view){

        ///////////////////Validation//////////////////////
        if (TheTile.getText().toString().equals("")||TheTile.getText().toString().equals(null)) {
            TheTile.setError("Please Enter The Note Title");
        }

        else if (TheBlogText.getText().toString().equals("")||TheBlogText.getText().toString().equals(null)) {
            TheBlogText.setError("Please Enter The Note description ");
        }
        //////////////////////////////////////////////////

        else {
            BlogItem blogItem = new BlogItem(TheTile.getText().toString(), TheBlogText.getText().toString(), imgDecodableString);
            DBHandler.addProduct(blogItem);

            finish();
            Intent i = new Intent(AddBlog.this, tabs.class);
            startActivity(i);
            overridePendingTransition(0, 0);

        }
        //PrintDatabase();
    }


    public void PrintDatabase(){
        String dbString=DBHandler.databaseToString();
        TheBlogText.setText(dbString);
        TheTile.setText("");
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_blog, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, tabs.class);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();
    }

}
