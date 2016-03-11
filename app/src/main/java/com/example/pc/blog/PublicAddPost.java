package com.example.pc.blog;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;


public class PublicAddPost extends Activity {

    int test;
    Upload upload;
    ProgressDialog prgDialog;
    String encodedString;
    RequestParams params = new RequestParams();
    String imgPath, fileName="none";
    Bitmap bitmap;
    private static final int RESULT_LOAD_IMG = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private Uri mImageCaptureUri;

    ///////////////////////////////////////////////////
    MyPHPHandler PHPHandler;
    ImageButton TakePhoto,Choosephoto;
    String UserEmail;
    SharedPreferences sharedPref;
    ///////////////////////////////////////////////////
    EditText TextThePost;
    String Date, Time;

//201507200224
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar Act=getActionBar();
        Act.hide();
        setContentView(R.layout.activity_public_add_post);
        TextThePost=(EditText)findViewById(R.id.TextThePost);

        Calendar now = Calendar.getInstance();
        System.out.println("||||||||||||| month"+now.get(Calendar.MONTH));

        Date=now.get(Calendar.YEAR)+"/"+now.get(Calendar.MONTH)+"/"+now.get(Calendar.DAY_OF_MONTH);
        Time=now.get(Calendar.HOUR_OF_DAY)+":"+now.get(Calendar.MINUTE);

        ////////////////////////////////////////////////////////////
        prgDialog = new ProgressDialog(this);
        // Set Cancelable as False
        prgDialog.setCancelable(false);

        ////////////////////////////////////////////////////////

        sharedPref = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        UserEmail= sharedPref.getString("Email","");


    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////////////////////////////////////////////

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    // When Image is selected from Gallery

    @Override
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
                imgPath = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.UploadIcon);
                System.gc();  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>GARBAGE COLLECTION !!
                // Set the Image in ImageView
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgPath));
                // Get the Image's file name
                String fileNameSegments[] = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];

                System.out.println("Image Name in Function : "+fileName);
                // Put file name in Async Http Post Param which will used in Php web app
                params.put("filename", fileName);

               // uploadImagee();  ////////////////////////////////////////////////////////////

            }
            else if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
                //Get The photo

                String[] projection = { MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(mImageCaptureUri, projection, null, null, null);
                int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String capturedImageFilePath = cursor.getString(column_index_data);
                System.out.println("||||||||||||||||  Path  :  "+capturedImageFilePath);

                imgPath=capturedImageFilePath;

                ImageView imgView = (ImageView) findViewById(R.id.UploadIcon);
                System.gc();  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>GARBAGE COLLECTION !!
                // Set the Image in ImageView
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgPath));
                // Get the Image's file name
                String fileNameSegments[] = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];

                System.out.println("Image Name in Function : "+fileName);
                // Put file name in Async Http Post Param which will used in Php web app
                params.put("filename", fileName);

             //   uploadImagee();  ////////////////////////////////////////////////////////////

            }
            else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }



    // When Upload button is clicked
    public void uploadImage(View v) {
        // When Image is selected from Gallery
        if (imgPath != null && !imgPath.isEmpty()) {
            prgDialog.setMessage("Converting Image to Binary Data");
            prgDialog.show();
            // Convert image to String using Base64
            encodeImagetoString();
            // When Image is not selected from Gallery
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "You must select image from gallery before you try to upload",
                    Toast.LENGTH_LONG).show();
        }
    }

    // When Upload button is clicked
    public void uploadImagee() {
        // When Image is selected from Gallery
        if (imgPath != null && !imgPath.isEmpty()) {
            prgDialog.setMessage("Converting Image to Binary Data");
            prgDialog.show();
            // Convert image to String using Base64
            encodeImagetoString();
            // When Image is not selected from Gallery
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "You must select image from gallery before you try to upload",
                    Toast.LENGTH_LONG).show();
        }
    }

    // AsyncTask - To convert Image to String
    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            };

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                System.gc();  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>GARBAGE COLLECTION !!
                bitmap = BitmapFactory.decodeFile(imgPath,
                        options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                //bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                //getResizedBitmap(bitmap, 50).compress(Bitmap.CompressFormat.PNG, 50, stream);
                getResizedBitmap(bitmap, 150).compress(Bitmap.CompressFormat.PNG, 70, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                prgDialog.setMessage("Calling Upload");
                // Put converted Image string into Async Http Post param
                params.put("image", encodedString);
                // Trigger Image upload
                triggerImageUpload();
            }
        }.execute(null, null, null);
    }

    public void triggerImageUpload() {
        makeHTTPCall();
    }

    // http://192.168.2.4:9000/imgupload/upload_image.php
    // http://192.168.2.4:9999/ImageUploadWebApp/uploadimg.jsp
    // Make Http call to upload Image to Php server
    public void makeHTTPCall() {
        prgDialog.setMessage("Invoking Php");
        AsyncHttpClient client = new AsyncHttpClient();
        // Don't forget to change the IP address to your LAN address. Port no as well.
        client.post(MyPHPHandler.Online_URL + "db_upload.php",
                params, new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        System.out.println(response);
                        String[] parts = response.split("\\#");
                        Toast.makeText(getApplicationContext(), parts[0], Toast.LENGTH_LONG).show();

                        finish();
                        System.gc();  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>GARBAGE COLLECTION !!
                        System.out.println("|||||| IN Succsess GC IS Called");
                    }

                    // When the response returned by REST has Http
                    // response code other than '200' such as '404',
                    // '500' or '403' etc
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error Occured \n Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not running\n HTTP Status code : "
                                            + statusCode, Toast.LENGTH_LONG)
                                    .show();
                        }
                        finish();
                        System.gc();  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>GARBAGE COLLECTION !!
                    }
                });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();



        // Dismiss the progress bar when application is closed
        if (prgDialog != null) {
            prgDialog.dismiss();
        }
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


    ////---------------------------------------------Camera------------------------------------------------/////

    private boolean haveCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }


    //Launching The Camera
    public void LaunchCamera(View view){

        //Make intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Take picture and pass results along onActivityResult
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

    }

    //Launching The Camera and take pic
    public void LaunchCameraPic(View view){

        String fileName = "temp.jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        mImageCaptureUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

    }

    ////----------------------------------------------------------------------------------------------------////



    public  void UploadIcononClick(View view){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View dialogview=layoutInflater.inflate(R.layout.activity_custom_dialog_upload_image,null);
        dialog.setContentView(dialogview);
        TakePhoto=(ImageButton)dialogview.findViewById(R.id.TakePhoto);
        Choosephoto=(ImageButton)dialogview.findViewById(R.id.ChoosePhoto);

        //Disable the button if the user has no camera
        if(!haveCamera())TakePhoto.setEnabled(false);

        //dialog.setContentView(R.layout.activity_custom_dialog);
        dialog.show();

        TakePhoto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Take photo");
                        LaunchCameraPic(v);
                        dialog.dismiss();
                    }
                }
        );

        Choosephoto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Choose photo");
                        loadImagefromGallery(v);
                        System.out.println("Image Name in Call : " + fileName);
                        //uploadImage(v);
                        dialog.dismiss();

                    }
                }
        );


    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




    public void PostButtononClicked(View view){

        System.out.println("Image Name in Post Call : " + fileName);
        ////////////Update Database////////////////
        PHPHandler = new MyPHPHandler(this,"add_post");
        PHPHandler.execute(UserEmail, fileName, TextThePost.getText().toString(), Date, Time);
        ////////////////////////////////////////
        uploadImagee();

}


    @Override
    public void onBackPressed() {
            Intent intent = new Intent(this, SimpleImageActivity.class);
            intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageListFragmentPosts.INDEX);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
    }



}
/*
in onDestroy
        /////
        try {
            trimCache(this);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /////
 /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onStop(){
        super.onStop();
    }
/*
    //Fires after the OnStop() state
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            trimCache(this);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

public static void trimCache(Context context) {
    try {
        File dir = context.getCacheDir();
        if (dir != null && dir.isDirectory()) {
            deleteDir(dir);
        }
    } catch (Exception e) {
        // TODO: handle exception
    }
}

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }
/////////////////////////////////////////////////////////////////////////////////////////////////


 */
