package com.example.pc.blog;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.Display;
import android.view.Window;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pc on 7/13/2015.
 */
public class MyPHPHandler extends AsyncTask<String,Void,String> {

    public static final String Online_URL="http://hassanadel.bugs3.com/Blog/";
    //public static final String Online_URL="http://hassanadel.byethost33.com/Blog/";
    public AsyncResponse delegate=null;
    Activity MyActivity;
    ArrayList<String> SentStrings;
    String Function;
    String Returned="";
    //flag 0 means get and 1 means post.(By default it is get.)

/*
    public MyPHPHandler(Context context,String function) {
        this.Function = function;
        this.MyContext=context;
    }
*/
public MyPHPHandler(Activity activity,String function) {
    this.Function = function;
    this.MyActivity=activity;
    //this.MyContext=context;
}

    protected void onPreExecute(){

    }

    //By Get
    @Override
    protected String doInBackground(String... arg0) {
        try{
            SentStrings= new ArrayList<String>(Arrays.asList(arg0));
            String link = null, data = null;
            if (Function == "signup") {
                String firstname = (String) arg0[0];
                String secondname = (String) arg0[1];
                String gender = (String) arg0[2];
                String userimage=(String) arg0[3];
                String email = (String) arg0[4];
                String password = (String) arg0[5];
                String address = (String) arg0[6];
                String college = (String) arg0[7];
                String studyyear = (String) arg0[8];

                link = Online_URL+"PHPSQLConnection.php";
                data = URLEncoder.encode("firstname", "UTF-8") + "=" + URLEncoder.encode(firstname, "UTF-8");
                data += "&" + URLEncoder.encode("lastname", "UTF-8") + "=" + URLEncoder.encode(secondname, "UTF-8");
                data += "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8");
                data += "&" + URLEncoder.encode("userimage", "UTF-8") + "=" + URLEncoder.encode(userimage, "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                data += "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8");
                data += "&" + URLEncoder.encode("college", "UTF-8") + "=" + URLEncoder.encode(college, "UTF-8");
                data += "&" + URLEncoder.encode("studyyear", "UTF-8") + "=" + URLEncoder.encode(studyyear, "UTF-8");
            }
            else if (Function == "login") {
                String email = (String) arg0[0];
                String password = (String) arg0[1];

                //link = Online_URL+"Blog_Login.php";
                link = "http://hassanadel.byethost33.com/Blog/Blog_Login.php";
                data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                 }
            else if (Function == "retrieve_email") {
                String email = (String) arg0[0];

                link = Online_URL+"db_mail.php";
                data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");

            }
            else if (Function == "update_user_image") {
                String email = (String) arg0[0];
                String image = (String) arg0[1];

                link = Online_URL+"db_edit_user_image.php";
                data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                data += "&" + URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(image, "UTF-8");

            }
            else if (Function == "add_post") {
                String email = (String) arg0[0];
                String image = (String) arg0[1];
                String title = (String) arg0[2];
                String date = (String) arg0[3];
                String time = (String) arg0[4];

                link = Online_URL+"db_add_post.php";
                data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                data += "&" + URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(image, "UTF-8");
                data += "&" + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8");
                data += "&" + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
                data += "&" + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8");

            }
            else if (Function == "add_friend") {
                String email = (String) arg0[0];
                String friendid = (String) arg0[1];
                link = Online_URL+"db_add_friend.php";
                data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                data += "&" + URLEncoder.encode("friendid", "UTF-8") + "=" + URLEncoder.encode(friendid, "UTF-8");

            }
            else if (Function == "print_friends") {
                String email = (String) arg0[0];
                link = Online_URL+"db_print_friends.php";
                data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");

            }
            else if (Function == "print_friend_requests") {
                String userid = (String) arg0[0];
                link = Online_URL+"db_print_friend_requests.php";
                data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8");

            }
            else if (Function == "print_posts") {
                String userid = (String) arg0[0];
                link = Online_URL+"db_print_posts.php";
                data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8");

            }
            else if (Function == "fetch_user") {
                String userid = (String) arg0[0];
                link = Online_URL+"db_fetch_user.php";
                data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8");

            }
            else if (Function == "drop_user") {
                String userid = (String) arg0[0];
                link = Online_URL+"dp_drop_user.php";
                data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8");

            }
            else if (Function == "accept_friend") {
                String userid = (String) arg0[0];
                String friendid = (String) arg0[1];
                link = Online_URL+"db_accept_friend.php";
                data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8");
                data += "&" + URLEncoder.encode("friendid", "UTF-8") + "=" + URLEncoder.encode(friendid, "UTF-8");

            }
            else if (Function == "delete_friend") {
                String userid = (String) arg0[0];
                String friendid = (String) arg0[1];
                link = Online_URL+"db_delete_friend.php";
                data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8");
                data += "&" + URLEncoder.encode("friendid", "UTF-8") + "=" + URLEncoder.encode(friendid, "UTF-8");

            }
            else if (Function == "delete_friend_request") {
                String userid = (String) arg0[0];
                String friendid = (String) arg0[1];
                link = Online_URL+"db_delete_friend.php";
                data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8");
                data += "&" + URLEncoder.encode("friendid", "UTF-8") + "=" + URLEncoder.encode(friendid, "UTF-8");

            }
            else if (Function == "report_friend") {
                String userid = (String) arg0[0];
                String reportedid = (String) arg0[1];
                String reportreason = (String) arg0[2];
                link = Online_URL+"db_report_friend.php";
                data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8");
                data += "&" + URLEncoder.encode("reportedid", "UTF-8") + "=" + URLEncoder.encode(reportedid, "UTF-8");
                data += "&" + URLEncoder.encode("reportreason", "UTF-8") + "=" + URLEncoder.encode(reportreason, "UTF-8");

            }else if (Function == "db_update_friend_request_status") {
                String userid = (String) arg0[0];
                link = Online_URL+"db_update_friend_request_status.php";
                data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8");

            }
            else if (Function == "print_all_reports") {
                String userid = (String) arg0[0];
                link = Online_URL+"db_print_all_reports.php";
                data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8");

            }
            System.out.println("data "+data);
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write( data );
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                sb.append(line);
                break;
            }
            System.out.println("sb " + sb.toString());
            return sb.toString();
        }
        catch(Exception e){
            return new String("Exception: " + e.getMessage());

            /////////////////////Another way to implement connection timeout before Post Excute
                        /*
            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                public void run() {
                    Toast.makeText(MyActivity, "Your message to main thread", Toast.LENGTH_SHORT).show();
                }
            });
            */
        }
    }


    public ArrayList<String> getSentStrings() {
        return SentStrings;
    }

    public String getReturned() {
        return Returned;
    }

    public void setReturned(String returned) {
        Returned = returned;
    }

    @Override
    protected void onPostExecute(String result){
        if(result.contains("Exception: failed to connect"))Toast.makeText(MyActivity, "Connection Time Out !!!", Toast.LENGTH_SHORT).show();
        else if (result.contains("Exception"))Toast.makeText(MyActivity, "Something went wrong !!!", Toast.LENGTH_SHORT).show();
        else if (result.equals("")||result.equals(null)||result.equals(" "))Toast.makeText(MyActivity, "Server Error!!!", Toast.LENGTH_SHORT).show();
        else {

            if (Function.equals("signup")) {

                if (result.contains("SignupComplete")) {
                    System.out.println("Connected !!!!");
                    Returned = result;
                    String[] parts = result.split("\\#");

                    SharedPreferences sharedPref = MyActivity.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                    //  SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext);

                    SharedPreferences.Editor editor = sharedPref.edit();

                    sharedPref.edit().clear();
                    sharedPref.edit().commit();
                    editor.putString("ID", parts[1]);
                    editor.putString("FirstName", SentStrings.get(0));
                    editor.putString("LastName", SentStrings.get(1));
                    editor.putString("Gender", SentStrings.get(2));
                    editor.putString("Email", SentStrings.get(4));
                    editor.putString("Password", SentStrings.get(5));
                    editor.putString("Address", SentStrings.get(6));
                    editor.putString("College", SentStrings.get(7));
                    editor.putString("StudyYear", SentStrings.get(8));
                    editor.apply();
                    ////////////////////////////////////////////////////////////////////////////////////
                    SharedPreferences sharedPrefer = MyActivity.getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editors = sharedPrefer.edit();
                    sharedPrefer.edit().clear();
                    sharedPrefer.edit().commit();
                    editors.putString("ID", parts[1]);
                    editors.putString("Email", SentStrings.get(4));
                    editors.putString("Password", SentStrings.get(5));
                    editors.apply();


                    //Toast.makeText(privatepublic, "Saved !!", Toast.LENGTH_SHORT).show();
                    System.out.println("_________________________________________________________");

                    sharedPref = MyActivity.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

                    String id = sharedPref.getString("ID", "");
                    String firstname = sharedPref.getString("FirstName", "");
                    String lastname = sharedPref.getString("LastName", "");
                    String gender = sharedPref.getString("Gender", "");
                    String email = sharedPref.getString("Email", "");
                    String PW = sharedPref.getString("Password", "");
                    String addr = sharedPref.getString("Address", "");
                    String college = sharedPref.getString("College", "");
                    String studyyear = sharedPref.getString("StudyYear", "");

                    System.out.println(id + "\n" + firstname + "\n" + lastname + "\n" + gender + "\n" + email + "\n" + PW
                            + "\n" + addr + "\n" + college + "\n" + studyyear);

                    Intent intent = new Intent(MyActivity, PublicAddImage.class);
                    MyActivity.startActivity(intent);
                    MyActivity.overridePendingTransition(0, 0);
                    MyActivity.finish();

                } else
                    Toast.makeText(MyActivity, "This Email Has Been Used Before !!", Toast.LENGTH_LONG).show();
            }
            ////////////////////////////////////////////////////////////////////////////////////////////
            else if (Function.equals("login")) {
                if (result.contains("LoginComplete")) {
                    System.out.println("Login Successful !!");
                    Returned = result;
                    String[] parts = result.split("\\#");
                    SharedPreferences sharedPref = MyActivity.getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    sharedPref.edit().clear();
                    sharedPref.edit().commit();
                    editor.putString("ID", parts[1]);
                    editor.putString("Email", SentStrings.get(0));
                    editor.putString("Password", SentStrings.get(1));
                    editor.apply();
                    System.out.println("PARTS 1  :  " + parts[1]);

                    Intent intent = new Intent(MyActivity, SimpleImageActivity.class);
                    intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageListFragmentPosts.INDEX);
                    MyActivity.overridePendingTransition(0, 0);
                    MyActivity.startActivity(intent);
                    MyActivity.finish();


                } else {
                    Toast.makeText(MyActivity, "Wrong Email/Password", Toast.LENGTH_LONG).show();
                    }
            }
            //////////////////////////////////////////////////////////////////////////////////////////
            else if (Function.equals("update_user_image")) {
                System.out.println(result);
            } else if (Function.equals("add_post")) {
                Intent intent = new Intent(MyActivity, SimpleImageActivity.class);
                intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageListFragmentPosts.INDEX);
                MyActivity.startActivity(intent);
            } else if (Function.equals("print_friends")) {
                if (result.contains("0 results"))
                    Toast.makeText(MyActivity, "You have no friends yet", Toast.LENGTH_LONG).show();
                else {
                    delegate.processFinish(result);
                }
            } else if (Function.equals("print_friend_requests")) {
                if (result.equals("0 results"))
                    Toast.makeText(MyActivity, "You have no friends yet", Toast.LENGTH_LONG).show();
                else {
                    delegate.processFinish(result);
                }
            } else if (Function.equals("print_posts")) {
                if (result.contains("0 results"))
                    Toast.makeText(MyActivity, "You have no posts yet", Toast.LENGTH_LONG).show();
                else {
                    delegate.processFinish(result);
                }
            } else if (Function.equals("fetch_user")) {
                if (result.contains("0 results"))
                    Toast.makeText(MyActivity, "Wrong ID", Toast.LENGTH_LONG).show();
                else {
                    final CustomDialogAddFriend dialog = new CustomDialogAddFriend(MyActivity, result);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.show();
                }
            } else if (Function.equals("report_friend")) {

            }
            else if (Function.equals("drop_user")) {
                if(result.contains("delete"))Toast.makeText(MyActivity, "User deleted from database", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MyActivity, SimpleImageActivity.class);
                intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageListFragmentReports.INDEX);
                MyActivity.startActivity(intent);
                MyActivity.overridePendingTransition(0, 0);
                MyActivity.finish();
            }else if (Function.equals("delete_friend")) {
                Intent intent = new Intent(MyActivity, SimpleImageActivity.class);
                intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageListFragmentFriends.INDEX);
                ImageLoader.getInstance().clearMemoryCache();
                ImageLoader.getInstance().clearDiskCache();
                MyActivity.startActivity(intent);
                MyActivity.overridePendingTransition(0, 0);
                MyActivity.finish();
            } else if (Function.equals("delete_friend_request")) {
                Intent intent = new Intent(MyActivity, SimpleImageActivity.class);
                intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageListFragmentFriendRequests.INDEX);
                ImageLoader.getInstance().clearMemoryCache();
                ImageLoader.getInstance().clearDiskCache();
                MyActivity.startActivity(intent);
                MyActivity.overridePendingTransition(0, 0);
                MyActivity.finish();
            } else if (Function.equals("print_all_reports")) {
                if (result.equals("0 results"))
                    Toast.makeText(MyActivity, "No reports yet", Toast.LENGTH_LONG).show();
                else {
                    delegate.processFinish(result);
                }
            }else if (Function.equals("accept_friend")) {
                Intent intent = new Intent(MyActivity, SimpleImageActivity.class);
                intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageListFragmentFriendRequests.INDEX);
                ImageLoader.getInstance().clearMemoryCache();
                ImageLoader.getInstance().clearDiskCache();
                MyActivity.startActivity(intent);
                MyActivity.overridePendingTransition(0, 0);
                MyActivity.finish();
            }
        }
        System.out.println("||||||||||||||||||||||||||||||||||||| RESULT : " + result);
    }
}


//String[] parts = s.split("\\."); // String array, each element is text between dots









/*


                SharedPreferences sharedPref = MyContext.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                sharedPref.edit().clear();
                sharedPref.edit().commit();
                editor.putString("Email",SentStrings.get(0));
                editor.putString("Password",SentStrings.get(1));
                editor.apply();


   */

