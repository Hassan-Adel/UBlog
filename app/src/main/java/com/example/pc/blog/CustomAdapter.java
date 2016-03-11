package com.example.pc.blog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pc on 7/6/2015.
 */

public class CustomAdapter extends ArrayAdapter<BlogItem> {

    Typeface face=Typeface.createFromAsset(getContext().getAssets(),"fonts/remachinescript.ttf");
    //Typeface face=Typeface.createFromAsset(getContext().getAssets(),"fonts/brucum.ttf");

    public CustomAdapter(Context context , BlogItem[] elements) {
        super(context,R.layout.custom_row, elements);
    }
/*public View getView(int ImagePathposition,int BlogTitleposition,int BlogTextposition, View convertView, ViewGroup parent)*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater TheInflater = LayoutInflater.from(getContext());
            View CustomView = TheInflater.inflate(R.layout.custom_row, parent, false);

            String SingleImagePath = getItem(position).get_ImagePath();
            String SingleBlogTitle = getItem(position).get_BlogTitle();
            String SingleBlogText = getItem(position).get_BlogSentence();

            TextView TheTitle = (TextView) CustomView.findViewById(R.id.TheTitle);
            TextView TheBlogText = (TextView) CustomView.findViewById(R.id.TheBlogText);
            ImageView TheImage = (ImageView) CustomView.findViewById(R.id.TheImageView);

            RoundImage Round = new RoundImage();

            TheTitle.setText(SingleBlogTitle);
            TheTitle.setTypeface(face);

            TheBlogText.setText(SingleBlogText);

            //if no image in database (null) .. set a default image
            Drawable myDrawable = getContext().getResources().getDrawable(R.drawable.no_image_available);
            TheImage.setImageDrawable(myDrawable);

            if (SingleImagePath != null)
                TheImage.setImageBitmap(Round.getCircleBitmap(getResizedBitmap(BitmapFactory.decodeFile(SingleImagePath),150)));
            return CustomView;
        }
        else return convertView;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    ////-------------------------------------------------------------------------------------------/////

}

/*
if(SingleImagePath!=null)TheImage.setImageBitmap(BitmapFactory.decodeFile(SingleImagePath));
*/