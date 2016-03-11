package com.example.pc.blog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Hassan on 7/26/2015.
 */
public class LoadImage extends AsyncTask<String, String, Bitmap> {
    ImageView img;
    Bitmap bitmap;
    Activity MyActivity;

    public LoadImage(Activity myActivity) {
        this.MyActivity = myActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public void Image(ImageView image) {
        img = image;
    }

    protected Bitmap doInBackground(String... args) {
        onPreExecute();
        System.gc();  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>GARBAGE COLLECTION !!
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

        } catch (Exception e) {
           // img.setBackground(MyActivity.getResources().getDrawable(R.drawable.no_image));
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap image) {

        if (image != null) {
            img.setImageBitmap(image);


        } else {
           // img.setBackground(MyActivity.getResources().getDrawable(R.drawable.no_image));
        }

    }

}
