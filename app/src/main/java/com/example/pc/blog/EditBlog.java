package com.example.pc.blog;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class EditBlog extends Activity {

    Bundle TabActivityoneData;
    String NoteId;
    String NoteTitle;
    String NoteText;
    String NoteImagePath;

    int RESULT_LOAD_IMG;
    String imgDecodableString;
    ImageView imageView;//id AddImage
    EditText TheTile;
    EditText TheBlogText;
    MyDBHandler DBHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_blog);

        //when you go bsck go to the first tab (0)
        tabs.obj = 0;


        TheTile=(EditText)findViewById(R.id.TheTitle);
        TheBlogText=(EditText)findViewById(R.id.TheBlogText);
        imageView=(ImageView)findViewById(R.id.AddImage);

        DBHandler = new MyDBHandler(this,null,null,1);
        TabActivityoneData=getIntent().getExtras();
        NoteId=TabActivityoneData.getString("NoteId");
        NoteTitle=TabActivityoneData.getString("NoteTitle");
        NoteText=TabActivityoneData.getString("NoteText");
        NoteImagePath=TabActivityoneData.getString("NoteImagePath");
        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||| ID = " + NoteId);
        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||| TITLE = " + NoteTitle);
        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||| TEXT = " + NoteText);
        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||| IMAGEPATH = " + NoteImagePath);

        Drawable myDrawable = getResources().getDrawable(R.drawable.image_add);
        imageView.setImageDrawable(myDrawable);
        if(NoteImagePath!=null)imageView.setImageBitmap(BitmapFactory.decodeFile(NoteImagePath));
        TheTile.setText(NoteTitle);
        TheBlogText.setText(NoteText);


    }






    ///////////////////////////////////////////////////////////////////////////////////////////////////


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



    //////////////////////////////////////////////////////////////////////////////////////////////////




    public void EditNoteClicked(View view){

        ///////////////////Validation//////////////////////
        if (TheTile.getText().toString().equals("")||TheTile.getText().toString().equals(null)) {
            TheTile.setError("Please Enter The Note Title");
        }

        else if (TheBlogText.getText().toString().equals("")||TheBlogText.getText().toString().equals(null)) {
            TheBlogText.setError("Please Enter The Note description ");
        }
        //////////////////////////////////////////////////
        else {
            String ImagePath = NoteImagePath;
            if (imgDecodableString != null) ImagePath = imgDecodableString;
            BlogItem blogItem = new BlogItem(TheTile.getText().toString(), TheBlogText.getText().toString(), ImagePath);
            DBHandler.updateblog(NoteId, TheTile.getText().toString(), TheBlogText.getText().toString(), ImagePath);

            finish();
            Intent i = new Intent(EditBlog.this, tabs.class);
            startActivity(i);
        }
        //PrintDatabase();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editnote, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DBHandler.deleteblog(NoteId);

        Intent i = new Intent(EditBlog.this,tabs.class);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();
        return  true;
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, tabs.class);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();
    }



}
