package com.example.pc.blog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by pc on 7/6/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";
//////////////////////////////// Blog Table ////////////////////////////////////
    public static final String TABLE_BLOGITEMS = "blogitems";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_BLOGTITLE = "blogtitle";
    public static final String COLUMN_BLOGSENTENCE = "blogsentence";
    public static final String COLUMN_IMAGEPATH = "imagepath";
//////////////////////////////// Alert Table ////////////////////////////////////
    public static final String TABLE_ALERTITEMS = "alertitems";
    public static final String COLUMN_ALERTID = "_id";
    public static final String COLUMN_ALERTTITLE = "alerttitle";
    public static final String COLUMN_DAY = "alertday";
    public static final String COLUMN_MONTH = "alertmonth";
    public static final String COLUMN_YEAR = "alertyear";
    public static final String COLUMN_HOUR = "alerthour";
    public static final String COLUMN_MINUTE = "alertminute";
    public static final String COLUMN_ISACTIVE = "alertisactive";






    //We need to pass database information along to superclass
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String query = "CREATE TABLE " + TABLE_BLOGITEMS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BLOGTITLE + " TEXT, " +
                COLUMN_BLOGSENTENCE + " TEXT, " +
                COLUMN_IMAGEPATH  + " TEXT " +
                ");";

        db.execSQL(query);


        String query1 = "CREATE TABLE " + TABLE_ALERTITEMS + "( " +
                COLUMN_ALERTID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ALERTTITLE + " TEXT, " +
                COLUMN_DAY + " INTEGER, " +
                COLUMN_MONTH + " INTEGER, " +
                COLUMN_YEAR  + " INTEGER, " +
                COLUMN_HOUR  + " INTEGER, " +
                COLUMN_MINUTE  + " INTEGER, " +
                COLUMN_ISACTIVE  + " TEXT " +
                ");";

        //System.out.println("QUERY : "+query1);
        db.execSQL(query1);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOGITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALERTITEMS);
        onCreate(db);
        onCreate(db);
    }

    //Add a new row to the database
    public void addProduct(BlogItem blogItem){
        ContentValues values = new ContentValues();

        System.out.println("Blog title DB" + blogItem.get_BlogTitle());
        System.out.println("Blog text DB" + blogItem.get_BlogSentence());
        System.out.println("Blog path DB " + blogItem.get_ImagePath());

        values.put(COLUMN_BLOGTITLE, blogItem.get_BlogTitle());
        values.put(COLUMN_BLOGSENTENCE, blogItem.get_BlogSentence());
        values.put(COLUMN_IMAGEPATH, blogItem.get_ImagePath());
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.isOpen();
            db.insert(TABLE_BLOGITEMS, null, values);
        }catch (Exception e){
            e.getMessage();
        }

        db.close();
        System.out.println(" YES YES  YES YES  YES YES  YES YES  YES YES  YES YES  YES YES  YES YES  YES YES ");
    }




    //Delete a product from the database
    public void deleteblog(String BlogID){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_BLOGITEMS + " WHERE " + COLUMN_ID + "=\"" + BlogID + "\";");
    }


    public void updateblog(String BlogID,String BlogTitle,String BlogText,String BlogImagePath){
        ContentValues values = new ContentValues();
            values.put(COLUMN_BLOGTITLE, BlogTitle);
            values.put(COLUMN_BLOGSENTENCE,BlogText);
            values.put(COLUMN_IMAGEPATH, BlogImagePath);
            SQLiteDatabase db = getWritableDatabase();
            db.update(TABLE_BLOGITEMS, values, COLUMN_ID + " =" + BlogID, null);

    }



    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BLOGITEMS + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("productname")) != null) {
                dbString += c.getString(c.getColumnIndex("productname"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }




    public ArrayList<String> getAllImagePath()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_BLOGITEMS, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COLUMN_IMAGEPATH)));
            res.moveToNext();
        }
        return array_list;
    }


    public ArrayList<String> getAllBlogTitles()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_BLOGITEMS, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COLUMN_BLOGTITLE)));
            res.moveToNext();
        }
        return array_list;
    }



    public ArrayList<String> getAllBlogText()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_BLOGITEMS+" ", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COLUMN_BLOGSENTENCE)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllIDs()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_BLOGITEMS+" ", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COLUMN_ID)));
            res.moveToNext();
        }
        return array_list;
    }


///////////////////////////////////////////////// ALERT /////////////////////////////////////////////////////


    public void CreateTables(SQLiteDatabase db){

        String query1 = "CREATE TABLE IF NOT EXISTS " + TABLE_ALERTITEMS + "( " +
                COLUMN_ALERTID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ALERTTITLE + " TEXT, " +
                COLUMN_DAY + " INTEGER, " +
                COLUMN_MONTH + " INTEGER, " +
                COLUMN_YEAR  + " INTEGER, " +
                COLUMN_HOUR  + " INTEGER, " +
                COLUMN_MINUTE  + " INTEGER, " +
                COLUMN_ISACTIVE  + " TEXT " +
                ");";

        System.out.println("QUERY : " + query1);
        db.execSQL(query1);


        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_BLOGITEMS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BLOGTITLE + " TEXT, " +
                COLUMN_BLOGSENTENCE + " TEXT, " +
                COLUMN_IMAGEPATH  + " TEXT " +
                ");";

        db.execSQL(query);

        db.close();

    }

    public void addAlert(AlertItem alertItem){

        ContentValues values = new ContentValues();

        values.put(COLUMN_ALERTTITLE, alertItem.get_AlertTitle());
        values.put(COLUMN_DAY, alertItem.get_AlertDay());
        values.put(COLUMN_MONTH, alertItem.get_AlertMonth());
        values.put(COLUMN_YEAR, alertItem.get_AlertYear());
        values.put(COLUMN_HOUR, alertItem.get_AlertHour());
        values.put(COLUMN_MINUTE, alertItem.get_AlertMinute());
        values.put(COLUMN_ISACTIVE, alertItem.get_AlertIsActive());

        SQLiteDatabase db = getWritableDatabase();
        //CreateTables(db);
        try {
            db.isOpen();
            db.insert(TABLE_ALERTITEMS, null, values);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error  :  "+e);
        }

        //db.close();

        /*
        System.out.println(" ALERT ADD  ALERT ADD  ALERT ADD  ALERT ADD  ALERT ADD  ALERT ADD  ALERT ADD  ALERT ADD ");


        AlertItem[] AI=getAllAlerts();
        for(int i=0;i<AI.length;i++){
            System.out.println("Title : "+AI[i].get_AlertTitle());
            System.out.println("Day : "+AI[i].get_AlertDay());
            System.out.println("Month : "+AI[i].get_AlertMonth());
            System.out.println("Year : "+AI[i].get_AlertYear());
            System.out.println("Hour : "+AI[i].get_AlertHour());
            System.out.println("Minute : "+AI[i].get_AlertMinute());
            System.out.println("__________________________________________________");

        }

        System.out.println(" ALERT ADDED  ALERT ADDED  ALERT ADDED  ALERT ADDED  ALERT ADDED  ALERT ADDED  ALERT ADDED ");

        */



    }
/*
        System.out.println("Blog title DB" + blogItem.get_BlogTitle());
        System.out.println("Blog text DB" + blogItem.get_BlogSentence());
        System.out.println("Blog path DB " + blogItem.get_ImagePath());
*/




    public AlertItem[] getAllAlerts()
    {

        //ArrayList<String> array_list_Alerts = new ArrayList<String>();
        ArrayList<String> array_list_IDs = new ArrayList<String>();
        ArrayList<String> array_list_Titles = new ArrayList<String>();
        ArrayList<String> array_list_Days = new ArrayList<String>();
        ArrayList<String> array_list_Months = new ArrayList<String>();
        ArrayList<String> array_list_Years = new ArrayList<String>();
        ArrayList<String> array_list_Hours = new ArrayList<String>();
        ArrayList<String> array_list_Minutes = new ArrayList<String>();
        ArrayList<String> array_list_IsActive = new ArrayList<String>();


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_ALERTITEMS, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list_IDs.add(res.getString(res.getColumnIndex(COLUMN_ALERTID)));
            array_list_Titles.add(res.getString(res.getColumnIndex(COLUMN_ALERTTITLE)));
            array_list_Days.add(res.getString(res.getColumnIndex(COLUMN_DAY)));
            array_list_Months.add(res.getString(res.getColumnIndex(COLUMN_MONTH)));
            array_list_Years.add(res.getString(res.getColumnIndex(COLUMN_YEAR)));
            array_list_Hours.add(res.getString(res.getColumnIndex(COLUMN_HOUR)));
            array_list_Minutes.add(res.getString(res.getColumnIndex(COLUMN_MINUTE)));
            array_list_IsActive.add(res.getString(res.getColumnIndex(COLUMN_ISACTIVE)));
            res.moveToNext();
        }

        //Construct Table Alerts
        AlertItem[] Elements = new AlertItem[array_list_IDs.size()];
        for(int i=0;i<array_list_IDs.size();i++){
            Elements[i]=new AlertItem(array_list_Titles.get(i).toString(),Integer.parseInt(array_list_Days.get(i)),Integer.parseInt(array_list_Months.get(i).toString()),
                    Integer.parseInt(array_list_Years.get(i).toString()),Integer.parseInt(array_list_Hours.get(i).toString()),Integer.parseInt(array_list_Minutes.get(i).toString()),
                    array_list_IsActive.get(i).toString());

            System.out.println("Alert "+i+"  Title = "+array_list_Titles.get(i));
            System.out.println("Alert "+i+" Date = "+array_list_Days.get(i) +"/"+ array_list_Months.get(i) +"/" +array_list_Years.get(i) ) ;
            System.out.println("Alert "+i+" Time = "+array_list_Hours.get(i) +":"+ array_list_Minutes.get(i));
            System.out.println("Alert "+i+" IsActive = "+array_list_IsActive.get(i) );
            System.out.println("___________________________________________________________________");

        }

        return Elements;
    }



    public void deletealert(String AlertID){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ALERTITEMS + " WHERE " + COLUMN_ALERTID + "=\"" + AlertID + "\";");
    }


    public void updateAlert(String AlertID, String AlertTitle, int AlertDay, int AlertMonth, int AlertYear, int AlertHour, int AlertMinute, String AlertIsActive){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ALERTTITLE, AlertTitle);
        values.put(COLUMN_DAY, AlertDay);
        values.put(COLUMN_MONTH, AlertMonth);
        values.put(COLUMN_YEAR, AlertYear);
        values.put(COLUMN_HOUR, AlertHour);
        values.put(COLUMN_MINUTE, AlertMinute);
        values.put(COLUMN_ISACTIVE, AlertIsActive);
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_ALERTITEMS, values, COLUMN_ALERTID + " =" + AlertID, null);
        System.out.println(TABLE_ALERTITEMS + values + COLUMN_ALERTID + " =" + AlertID);
    }


//////////////////////////////////////////////////////////////////////////

    public ArrayList<AlertItem> getArrayAlerts()
    {

        ArrayList<AlertItem> array_list_Alerts = new ArrayList<AlertItem>();
        ArrayList<String> array_list_IDs = new ArrayList<String>();
        ArrayList<String> array_list_Titles = new ArrayList<String>();
        ArrayList<String> array_list_Days = new ArrayList<String>();
        ArrayList<String> array_list_Months = new ArrayList<String>();
        ArrayList<String> array_list_Years = new ArrayList<String>();
        ArrayList<String> array_list_Hours = new ArrayList<String>();
        ArrayList<String> array_list_Minutes = new ArrayList<String>();
        ArrayList<String> array_list_IsActive = new ArrayList<String>();


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_ALERTITEMS, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list_IDs.add(res.getString(res.getColumnIndex(COLUMN_ALERTID)));
            array_list_Titles.add(res.getString(res.getColumnIndex(COLUMN_ALERTTITLE)));
            array_list_Days.add(res.getString(res.getColumnIndex(COLUMN_DAY)));
            array_list_Months.add(res.getString(res.getColumnIndex(COLUMN_MONTH)));
            array_list_Years.add(res.getString(res.getColumnIndex(COLUMN_YEAR)));
            array_list_Hours.add(res.getString(res.getColumnIndex(COLUMN_HOUR)));
            array_list_Minutes.add(res.getString(res.getColumnIndex(COLUMN_MINUTE)));
            array_list_IsActive.add(res.getString(res.getColumnIndex(COLUMN_ISACTIVE)));
            res.moveToNext();
        }

        //Construct Table Alerts

        for(int i=0;i<array_list_IDs.size();i++){
           array_list_Alerts.add(new AlertItem(array_list_Titles.get(i).toString(),Integer.parseInt(array_list_Days.get(i)),Integer.parseInt(array_list_Months.get(i).toString()),
                    Integer.parseInt(array_list_Years.get(i).toString()),Integer.parseInt(array_list_Hours.get(i).toString()),Integer.parseInt(array_list_Minutes.get(i).toString()),
                    array_list_IsActive.get(i).toString()));

            System.out.println("Alert "+i+"  Title = "+array_list_Titles.get(i));
            System.out.println("Alert "+i+" Date = "+array_list_Days.get(i) +"/"+ array_list_Months.get(i) +"/" +array_list_Years.get(i) ) ;
            System.out.println("Alert "+i+" Time = "+array_list_Hours.get(i) +":"+ array_list_Minutes.get(i));
            System.out.println("Alert "+i+" IsActive = "+array_list_IsActive.get(i) );
            System.out.println("___________________________________________________________________");

        }

        return array_list_Alerts;
    }

    //////////////////////////////////////////////////////////////////////////////////


    public ArrayList<String[]> getAllAsStrings()
    {

        ArrayList<AlertItem> array_list_Alerts = new ArrayList<AlertItem>();
        ArrayList<String> array_list_IDs = new ArrayList<String>();
        ArrayList<String> array_list_Titles = new ArrayList<String>();
        ArrayList<String> array_list_Days = new ArrayList<String>();
        ArrayList<String> array_list_Months = new ArrayList<String>();
        ArrayList<String> array_list_Years = new ArrayList<String>();
        ArrayList<String> array_list_Hours = new ArrayList<String>();
        ArrayList<String> array_list_Minutes = new ArrayList<String>();
        ArrayList<String> array_list_IsActive = new ArrayList<String>();
        ArrayList<String[]> array_list_Strings = new ArrayList<String[]>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_ALERTITEMS, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list_IDs.add(res.getString(res.getColumnIndex(COLUMN_ALERTID)));
            array_list_Titles.add(res.getString(res.getColumnIndex(COLUMN_ALERTTITLE)));
            array_list_Days.add(res.getString(res.getColumnIndex(COLUMN_DAY)));
            array_list_Months.add(res.getString(res.getColumnIndex(COLUMN_MONTH)));
            array_list_Years.add(res.getString(res.getColumnIndex(COLUMN_YEAR)));
            array_list_Hours.add(res.getString(res.getColumnIndex(COLUMN_HOUR)));
            array_list_Minutes.add(res.getString(res.getColumnIndex(COLUMN_MINUTE)));
            array_list_IsActive.add(res.getString(res.getColumnIndex(COLUMN_ISACTIVE)));
            res.moveToNext();
        }

        //Construct Table Alerts

        for(int i=0;i<array_list_IDs.size();i++){
            String[] s=new String[8];

            s[0]= array_list_IDs.get(i);
            s[1]= array_list_Titles.get(i);
            s[2]= array_list_Days.get(i);
            s[3]= array_list_Months.get(i);
            s[4]= array_list_Years.get(i);
            s[5]= array_list_Hours.get(i);
            s[6]= array_list_Minutes.get(i);
            s[7]= array_list_IsActive.get(i);

            array_list_Strings.add(s);

            System.out.println("Alert "+i+"  Title = "+array_list_Titles.get(i));
            System.out.println("Alert "+i+" Date = "+array_list_Days.get(i) +"/"+ array_list_Months.get(i) +"/" +array_list_Years.get(i) ) ;
            System.out.println("Alert "+i+" Time = "+array_list_Hours.get(i) +":"+ array_list_Minutes.get(i));
            System.out.println("Alert "+i+" IsActive = "+array_list_IsActive.get(i) );
            System.out.println("___________________________________________________________________");

        }

        return array_list_Strings;
    }


/////////////////////////////////////////////// Return ArrayList of Calenders ////////////////////////////////////

    public ArrayList<Calendar> getAllCalenders()
    {

        ArrayList<String[]> DateAndTime =getAllAsStrings();
        ArrayList<Calendar> Calerders = new ArrayList<Calendar>();


        for(int i=0;i<DateAndTime.size();i++){

            Calendar cal = Calendar.getInstance();

            cal.set(Integer.valueOf(DateAndTime.get(i)[4]),
                    Integer.valueOf(DateAndTime.get(i)[3]),
                    Integer.valueOf(DateAndTime.get(i)[2]),
                    Integer.valueOf(DateAndTime.get(i)[5]),
                    Integer.valueOf(DateAndTime.get(i)[6]),
                    00);

            Calerders.add(cal);

            System.out.println("Calender "+i);
            System.out.println("Alert "+i+" Date = "+DateAndTime.get(i)[2] +"/"+ DateAndTime.get(i)[3] +"/" +DateAndTime.get(i)[4] ) ;
            System.out.println("Alert "+i+" Time = "+DateAndTime.get(i)[5] +":"+ DateAndTime.get(i)[6]);
            System.out.println("___________________________________________________________________");

        }

        return Calerders;
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    public ArrayList<String> getAlertIsActive()
    {

        ArrayList<AlertItem> array_list_Alerts = new ArrayList<AlertItem>();
        ArrayList<String> array_list_IDs = new ArrayList<String>();
        ArrayList<String> array_list_Titles = new ArrayList<String>();
        ArrayList<String> array_list_Days = new ArrayList<String>();
        ArrayList<String> array_list_Months = new ArrayList<String>();
        ArrayList<String> array_list_Years = new ArrayList<String>();
        ArrayList<String> array_list_Hours = new ArrayList<String>();
        ArrayList<String> array_list_Minutes = new ArrayList<String>();
        ArrayList<String> array_list_IsActive = new ArrayList<String>();


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_ALERTITEMS, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list_IDs.add(res.getString(res.getColumnIndex(COLUMN_ALERTID)));
            array_list_Titles.add(res.getString(res.getColumnIndex(COLUMN_ALERTTITLE)));
            array_list_Days.add(res.getString(res.getColumnIndex(COLUMN_DAY)));
            array_list_Months.add(res.getString(res.getColumnIndex(COLUMN_MONTH)));
            array_list_Years.add(res.getString(res.getColumnIndex(COLUMN_YEAR)));
            array_list_Hours.add(res.getString(res.getColumnIndex(COLUMN_HOUR)));
            array_list_Minutes.add(res.getString(res.getColumnIndex(COLUMN_MINUTE)));
            array_list_IsActive.add(res.getString(res.getColumnIndex(COLUMN_ISACTIVE)));
            res.moveToNext();
        }

        //Construct Table Alerts

        for(int i=0;i<array_list_IDs.size();i++){
            array_list_Alerts.add(new AlertItem(array_list_Titles.get(i).toString(), Integer.parseInt(array_list_Days.get(i)), Integer.parseInt(array_list_Months.get(i).toString()),
                    Integer.parseInt(array_list_Years.get(i).toString()), Integer.parseInt(array_list_Hours.get(i).toString()), Integer.parseInt(array_list_Minutes.get(i).toString()),
                    array_list_IsActive.get(i).toString()));

            System.out.println("IS ACTIVE" +i);
            System.out.println("Alert "+i+" IsActive = "+array_list_IsActive.get(i) );
            System.out.println("___________________________________________________________________");

        }

        return array_list_IsActive;
    }



    public void ClearInvalidAlerts(){
        ArrayList<Calendar> Calenders=getAllCalenders();
        Calendar current = Calendar.getInstance();

        for (int i=0;i<Calenders.size();i++){

            if((Calenders.get(i)).compareTo(current) <= 0){
            //The set Date/Time already passed
                SQLiteDatabase db = getWritableDatabase();
                db.execSQL("DELETE FROM " + TABLE_ALERTITEMS + " WHERE " + COLUMN_YEAR + "=" + Calenders.get(i).get(Calendar.YEAR) + " AND " +
                                                                           COLUMN_MONTH + "=" + Calenders.get(i).get(Calendar.MONTH) + " AND " +
                                                                           COLUMN_DAY + "=" + Calenders.get(i).get(Calendar.DAY_OF_MONTH) + " AND " +
                                                                           COLUMN_HOUR + "=" + Calenders.get(i).get(Calendar.HOUR_OF_DAY) + " AND " +
                                                                           COLUMN_MINUTE + "=" + Calenders.get(i).get(Calendar.MINUTE) +" ;");
             }
        }

    }


    //////////////////////////////////////////////////////////////////////////////////

    public ArrayList<String> getCurrentAlertTitles()
    {

        ArrayList<String> array_list_Titles = new ArrayList<String>();
        Calendar current = Calendar.getInstance();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_ALERTITEMS + " WHERE " + COLUMN_YEAR + "=" + current.get(Calendar.YEAR) + " AND " +
                COLUMN_MONTH + "=" + current.get(Calendar.MONTH) + " AND " +
                COLUMN_DAY + "=" + current.get(Calendar.DAY_OF_MONTH) + " AND " +
                COLUMN_HOUR + "=" + current.get(Calendar.HOUR_OF_DAY) + " AND " +
                COLUMN_MINUTE + "=" + current.get(Calendar.MINUTE) +" ;", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list_Titles.add(res.getString(res.getColumnIndex(COLUMN_ALERTTITLE)));
            res.moveToNext();
        }

        for(int i=0;i<array_list_Titles.size();i++){

            System.out.println("Alert "+i+"  Title = "+array_list_Titles.get(i));
            System.out.println("___________________________________________________________________");

        }

        return array_list_Titles;
    }

//////////////////////////////////////////////////////////////////////////////////



    public void SnoozeCurrentAlerts(){
        ArrayList<Calendar> Calenders=getAllCalenders();
        Calendar current = Calendar.getInstance();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MINUTE, (current.get(Calendar.MINUTE) + 5));
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_ALERTITEMS, values, COLUMN_YEAR + "=" + current.get(Calendar.YEAR) + " AND " +
                COLUMN_MONTH + "=" + current.get(Calendar.MONTH) + " AND " +
                COLUMN_DAY + "=" + current.get(Calendar.DAY_OF_MONTH) + " AND " +
                COLUMN_HOUR + "=" + current.get(Calendar.HOUR_OF_DAY) + " AND " +
                COLUMN_MINUTE + "=" + current.get(Calendar.MINUTE), null);

        System.out.println("SnoozeCurrentAlerts Query : \n"+TABLE_ALERTITEMS + values + COLUMN_YEAR + "=" + current.get(Calendar.YEAR) + " AND " +
                COLUMN_MONTH + "=" + current.get(Calendar.MONTH) + " AND " +
                COLUMN_DAY + "=" + current.get(Calendar.DAY_OF_MONTH) + " AND " +
                COLUMN_HOUR + "=" + current.get(Calendar.HOUR_OF_DAY) + " AND " +
                COLUMN_MINUTE + "=" + current.get(Calendar.MINUTE));


    }



/////////////////////////////////////////////////////////////////////////////////



}



/*


    public ArrayList<String> getAlertIDs()
    {

        ArrayList<AlertItem> array_list_Alerts = new ArrayList<AlertItem>();
        ArrayList<String> array_list_IDs = new ArrayList<String>();
        ArrayList<String> array_list_Titles = new ArrayList<String>();
        ArrayList<String> array_list_Days = new ArrayList<String>();
        ArrayList<String> array_list_Months = new ArrayList<String>();
        ArrayList<String> array_list_Years = new ArrayList<String>();
        ArrayList<String> array_list_Hours = new ArrayList<String>();
        ArrayList<String> array_list_Minutes = new ArrayList<String>();
        ArrayList<String> array_list_IsActive = new ArrayList<String>();


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_ALERTITEMS, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list_IDs.add(res.getString(res.getColumnIndex(COLUMN_ALERTID)));
            array_list_Titles.add(res.getString(res.getColumnIndex(COLUMN_ALERTTITLE)));
            array_list_Days.add(res.getString(res.getColumnIndex(COLUMN_DAY)));
            array_list_Months.add(res.getString(res.getColumnIndex(COLUMN_MONTH)));
            array_list_Years.add(res.getString(res.getColumnIndex(COLUMN_YEAR)));
            array_list_Hours.add(res.getString(res.getColumnIndex(COLUMN_HOUR)));
            array_list_Minutes.add(res.getString(res.getColumnIndex(COLUMN_MINUTE)));
            array_list_IsActive.add(res.getString(res.getColumnIndex(COLUMN_ISACTIVE)));
            res.moveToNext();
        }

        //Construct Table Alerts

        for(int i=0;i<array_list_IDs.size();i++){
            array_list_Alerts.add(new AlertItem(array_list_Titles.get(i).toString(),Integer.parseInt(array_list_Days.get(i)),Integer.parseInt(array_list_Months.get(i).toString()),
                    Integer.parseInt(array_list_Years.get(i).toString()),Integer.parseInt(array_list_Hours.get(i).toString()),Integer.parseInt(array_list_Minutes.get(i).toString()),
                    array_list_IsActive.get(i).toString()));

            System.out.println("Alert "+i+"  Title = "+array_list_Titles.get(i));
            System.out.println("Alert "+i+" Date = "+array_list_Days.get(i) +"/"+ array_list_Months.get(i) +"/" +array_list_Years.get(i) ) ;
            System.out.println("Alert "+i+" Time = "+array_list_Hours.get(i) +":"+ array_list_Minutes.get(i));
            System.out.println("Alert "+i+" IsActive = "+array_list_IsActive.get(i) );
            System.out.println("___________________________________________________________________");

        }

        return array_list_IDs;
    }

    //////////////////////////////////////////////////////////////////////////////////

    public ArrayList<String> getAlertTitles()
    {

        ArrayList<AlertItem> array_list_Alerts = new ArrayList<AlertItem>();
        ArrayList<String> array_list_IDs = new ArrayList<String>();
        ArrayList<String> array_list_Titles = new ArrayList<String>();
        ArrayList<String> array_list_Days = new ArrayList<String>();
        ArrayList<String> array_list_Months = new ArrayList<String>();
        ArrayList<String> array_list_Years = new ArrayList<String>();
        ArrayList<String> array_list_Hours = new ArrayList<String>();
        ArrayList<String> array_list_Minutes = new ArrayList<String>();
        ArrayList<String> array_list_IsActive = new ArrayList<String>();


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_ALERTITEMS, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list_IDs.add(res.getString(res.getColumnIndex(COLUMN_ALERTID)));
            array_list_Titles.add(res.getString(res.getColumnIndex(COLUMN_ALERTTITLE)));
            array_list_Days.add(res.getString(res.getColumnIndex(COLUMN_DAY)));
            array_list_Months.add(res.getString(res.getColumnIndex(COLUMN_MONTH)));
            array_list_Years.add(res.getString(res.getColumnIndex(COLUMN_YEAR)));
            array_list_Hours.add(res.getString(res.getColumnIndex(COLUMN_HOUR)));
            array_list_Minutes.add(res.getString(res.getColumnIndex(COLUMN_MINUTE)));
            array_list_IsActive.add(res.getString(res.getColumnIndex(COLUMN_ISACTIVE)));
            res.moveToNext();
        }

        //Construct Table Alerts

        for(int i=0;i<array_list_IDs.size();i++){
            array_list_Alerts.add(new AlertItem(array_list_Titles.get(i).toString(),Integer.parseInt(array_list_Days.get(i)),Integer.parseInt(array_list_Months.get(i).toString()),
                    Integer.parseInt(array_list_Years.get(i).toString()),Integer.parseInt(array_list_Hours.get(i).toString()),Integer.parseInt(array_list_Minutes.get(i).toString()),
                    array_list_IsActive.get(i).toString()));

            System.out.println("Alert "+i+"  Title = "+array_list_Titles.get(i));
            System.out.println("Alert "+i+" Date = "+array_list_Days.get(i) +"/"+ array_list_Months.get(i) +"/" +array_list_Years.get(i) ) ;
            System.out.println("Alert "+i+" Time = "+array_list_Hours.get(i) +":"+ array_list_Minutes.get(i));
            System.out.println("Alert "+i+" IsActive = "+array_list_IsActive.get(i) );
            System.out.println("___________________________________________________________________");

        }

        return array_list_Titles;
    }

//////////////////////////////////////////////////////////////////////////////////

    public ArrayList<String> getAlertDays()
    {

        ArrayList<AlertItem> array_list_Alerts = new ArrayList<AlertItem>();
        ArrayList<String> array_list_IDs = new ArrayList<String>();
        ArrayList<String> array_list_Titles = new ArrayList<String>();
        ArrayList<String> array_list_Days = new ArrayList<String>();
        ArrayList<String> array_list_Months = new ArrayList<String>();
        ArrayList<String> array_list_Years = new ArrayList<String>();
        ArrayList<String> array_list_Hours = new ArrayList<String>();
        ArrayList<String> array_list_Minutes = new ArrayList<String>();
        ArrayList<String> array_list_IsActive = new ArrayList<String>();


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_ALERTITEMS, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list_IDs.add(res.getString(res.getColumnIndex(COLUMN_ALERTID)));
            array_list_Titles.add(res.getString(res.getColumnIndex(COLUMN_ALERTTITLE)));
            array_list_Days.add(res.getString(res.getColumnIndex(COLUMN_DAY)));
            array_list_Months.add(res.getString(res.getColumnIndex(COLUMN_MONTH)));
            array_list_Years.add(res.getString(res.getColumnIndex(COLUMN_YEAR)));
            array_list_Hours.add(res.getString(res.getColumnIndex(COLUMN_HOUR)));
            array_list_Minutes.add(res.getString(res.getColumnIndex(COLUMN_MINUTE)));
            array_list_IsActive.add(res.getString(res.getColumnIndex(COLUMN_ISACTIVE)));
            res.moveToNext();
        }

        //Construct Table Alerts

        for(int i=0;i<array_list_IDs.size();i++){
            array_list_Alerts.add(new AlertItem(array_list_Titles.get(i).toString(),Integer.parseInt(array_list_Days.get(i)),Integer.parseInt(array_list_Months.get(i).toString()),
                    Integer.parseInt(array_list_Years.get(i).toString()),Integer.parseInt(array_list_Hours.get(i).toString()),Integer.parseInt(array_list_Minutes.get(i).toString()),
                    array_list_IsActive.get(i).toString()));

            System.out.println("Alert "+i+"  Title = "+array_list_Titles.get(i));
            System.out.println("Alert "+i+" Date = "+array_list_Days.get(i) +"/"+ array_list_Months.get(i) +"/" +array_list_Years.get(i) ) ;
            System.out.println("Alert "+i+" Time = "+array_list_Hours.get(i) +":"+ array_list_Minutes.get(i));
            System.out.println("Alert "+i+" IsActive = "+array_list_IsActive.get(i) );
            System.out.println("___________________________________________________________________");

        }

        return array_list_Days;
    }

    //////////////////////////////////////////////////////////////////////////////////

    public ArrayList<String> getAlertMonths()
    {

        ArrayList<AlertItem> array_list_Alerts = new ArrayList<AlertItem>();
        ArrayList<String> array_list_IDs = new ArrayList<String>();
        ArrayList<String> array_list_Titles = new ArrayList<String>();
        ArrayList<String> array_list_Days = new ArrayList<String>();
        ArrayList<String> array_list_Months = new ArrayList<String>();
        ArrayList<String> array_list_Years = new ArrayList<String>();
        ArrayList<String> array_list_Hours = new ArrayList<String>();
        ArrayList<String> array_list_Minutes = new ArrayList<String>();
        ArrayList<String> array_list_IsActive = new ArrayList<String>();


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_ALERTITEMS, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list_IDs.add(res.getString(res.getColumnIndex(COLUMN_ALERTID)));
            array_list_Titles.add(res.getString(res.getColumnIndex(COLUMN_ALERTTITLE)));
            array_list_Days.add(res.getString(res.getColumnIndex(COLUMN_DAY)));
            array_list_Months.add(res.getString(res.getColumnIndex(COLUMN_MONTH)));
            array_list_Years.add(res.getString(res.getColumnIndex(COLUMN_YEAR)));
            array_list_Hours.add(res.getString(res.getColumnIndex(COLUMN_HOUR)));
            array_list_Minutes.add(res.getString(res.getColumnIndex(COLUMN_MINUTE)));
            array_list_IsActive.add(res.getString(res.getColumnIndex(COLUMN_ISACTIVE)));
            res.moveToNext();
        }

        //Construct Table Alerts

        for(int i=0;i<array_list_IDs.size();i++){
            array_list_Alerts.add(new AlertItem(array_list_Titles.get(i).toString(),Integer.parseInt(array_list_Days.get(i)),Integer.parseInt(array_list_Months.get(i).toString()),
                    Integer.parseInt(array_list_Years.get(i).toString()),Integer.parseInt(array_list_Hours.get(i).toString()),Integer.parseInt(array_list_Minutes.get(i).toString()),
                    array_list_IsActive.get(i).toString()));

            System.out.println("Alert "+i+"  Title = "+array_list_Titles.get(i));
            System.out.println("Alert "+i+" Date = "+array_list_Days.get(i) +"/"+ array_list_Months.get(i) +"/" +array_list_Years.get(i) ) ;
            System.out.println("Alert "+i+" Time = "+array_list_Hours.get(i) +":"+ array_list_Minutes.get(i));
            System.out.println("Alert "+i+" IsActive = "+array_list_IsActive.get(i) );
            System.out.println("___________________________________________________________________");

        }

        return array_list_Months;
    }

    //////////////////////////////////////////////////////////////////////////////////

    public ArrayList<String> getAlertYears()
    {

        ArrayList<AlertItem> array_list_Alerts = new ArrayList<AlertItem>();
        ArrayList<String> array_list_IDs = new ArrayList<String>();
        ArrayList<String> array_list_Titles = new ArrayList<String>();
        ArrayList<String> array_list_Days = new ArrayList<String>();
        ArrayList<String> array_list_Months = new ArrayList<String>();
        ArrayList<String> array_list_Years = new ArrayList<String>();
        ArrayList<String> array_list_Hours = new ArrayList<String>();
        ArrayList<String> array_list_Minutes = new ArrayList<String>();
        ArrayList<String> array_list_IsActive = new ArrayList<String>();


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_ALERTITEMS, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list_IDs.add(res.getString(res.getColumnIndex(COLUMN_ALERTID)));
            array_list_Titles.add(res.getString(res.getColumnIndex(COLUMN_ALERTTITLE)));
            array_list_Days.add(res.getString(res.getColumnIndex(COLUMN_DAY)));
            array_list_Months.add(res.getString(res.getColumnIndex(COLUMN_MONTH)));
            array_list_Years.add(res.getString(res.getColumnIndex(COLUMN_YEAR)));
            array_list_Hours.add(res.getString(res.getColumnIndex(COLUMN_HOUR)));
            array_list_Minutes.add(res.getString(res.getColumnIndex(COLUMN_MINUTE)));
            array_list_IsActive.add(res.getString(res.getColumnIndex(COLUMN_ISACTIVE)));
            res.moveToNext();
        }

        //Construct Table Alerts

        for(int i=0;i<array_list_IDs.size();i++){
            array_list_Alerts.add(new AlertItem(array_list_Titles.get(i).toString(),Integer.parseInt(array_list_Days.get(i)),Integer.parseInt(array_list_Months.get(i).toString()),
                    Integer.parseInt(array_list_Years.get(i).toString()),Integer.parseInt(array_list_Hours.get(i).toString()),Integer.parseInt(array_list_Minutes.get(i).toString()),
                    array_list_IsActive.get(i).toString()));

            System.out.println("Alert "+i+"  Title = "+array_list_Titles.get(i));
            System.out.println("Alert "+i+" Date = "+array_list_Days.get(i) +"/"+ array_list_Months.get(i) +"/" +array_list_Years.get(i) ) ;
            System.out.println("Alert "+i+" Time = "+array_list_Hours.get(i) +":"+ array_list_Minutes.get(i));
            System.out.println("Alert "+i+" IsActive = "+array_list_IsActive.get(i) );
            System.out.println("___________________________________________________________________");

        }

        return array_list_Years;
    }


    //////////////////////////////////////////////////////////////////////////////////

    public ArrayList<String> getAlertHours()
    {

        ArrayList<AlertItem> array_list_Alerts = new ArrayList<AlertItem>();
        ArrayList<String> array_list_IDs = new ArrayList<String>();
        ArrayList<String> array_list_Titles = new ArrayList<String>();
        ArrayList<String> array_list_Days = new ArrayList<String>();
        ArrayList<String> array_list_Months = new ArrayList<String>();
        ArrayList<String> array_list_Years = new ArrayList<String>();
        ArrayList<String> array_list_Hours = new ArrayList<String>();
        ArrayList<String> array_list_Minutes = new ArrayList<String>();
        ArrayList<String> array_list_IsActive = new ArrayList<String>();


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_ALERTITEMS, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list_IDs.add(res.getString(res.getColumnIndex(COLUMN_ALERTID)));
            array_list_Titles.add(res.getString(res.getColumnIndex(COLUMN_ALERTTITLE)));
            array_list_Days.add(res.getString(res.getColumnIndex(COLUMN_DAY)));
            array_list_Months.add(res.getString(res.getColumnIndex(COLUMN_MONTH)));
            array_list_Years.add(res.getString(res.getColumnIndex(COLUMN_YEAR)));
            array_list_Hours.add(res.getString(res.getColumnIndex(COLUMN_HOUR)));
            array_list_Minutes.add(res.getString(res.getColumnIndex(COLUMN_MINUTE)));
            array_list_IsActive.add(res.getString(res.getColumnIndex(COLUMN_ISACTIVE)));
            res.moveToNext();
        }

        //Construct Table Alerts

        for(int i=0;i<array_list_IDs.size();i++){
            array_list_Alerts.add(new AlertItem(array_list_Titles.get(i).toString(),Integer.parseInt(array_list_Days.get(i)),Integer.parseInt(array_list_Months.get(i).toString()),
                    Integer.parseInt(array_list_Years.get(i).toString()),Integer.parseInt(array_list_Hours.get(i).toString()),Integer.parseInt(array_list_Minutes.get(i).toString()),
                    array_list_IsActive.get(i).toString()));

            System.out.println("Alert "+i+"  Title = "+array_list_Titles.get(i));
            System.out.println("Alert "+i+" Date = "+array_list_Days.get(i) +"/"+ array_list_Months.get(i) +"/" +array_list_Years.get(i) ) ;
            System.out.println("Alert "+i+" Time = "+array_list_Hours.get(i) +":"+ array_list_Minutes.get(i));
            System.out.println("Alert "+i+" IsActive = "+array_list_IsActive.get(i) );
            System.out.println("___________________________________________________________________");

        }

        return array_list_Hours;
    }


    //////////////////////////////////////////////////////////////////////////////////

    public ArrayList<String> getAlertMinutes()
    {

        ArrayList<AlertItem> array_list_Alerts = new ArrayList<AlertItem>();
        ArrayList<String> array_list_IDs = new ArrayList<String>();
        ArrayList<String> array_list_Titles = new ArrayList<String>();
        ArrayList<String> array_list_Days = new ArrayList<String>();
        ArrayList<String> array_list_Months = new ArrayList<String>();
        ArrayList<String> array_list_Years = new ArrayList<String>();
        ArrayList<String> array_list_Hours = new ArrayList<String>();
        ArrayList<String> array_list_Minutes = new ArrayList<String>();
        ArrayList<String> array_list_IsActive = new ArrayList<String>();


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_ALERTITEMS, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list_IDs.add(res.getString(res.getColumnIndex(COLUMN_ALERTID)));
            array_list_Titles.add(res.getString(res.getColumnIndex(COLUMN_ALERTTITLE)));
            array_list_Days.add(res.getString(res.getColumnIndex(COLUMN_DAY)));
            array_list_Months.add(res.getString(res.getColumnIndex(COLUMN_MONTH)));
            array_list_Years.add(res.getString(res.getColumnIndex(COLUMN_YEAR)));
            array_list_Hours.add(res.getString(res.getColumnIndex(COLUMN_HOUR)));
            array_list_Minutes.add(res.getString(res.getColumnIndex(COLUMN_MINUTE)));
            array_list_IsActive.add(res.getString(res.getColumnIndex(COLUMN_ISACTIVE)));
            res.moveToNext();
        }

        //Construct Table Alerts

        for(int i=0;i<array_list_IDs.size();i++){
            array_list_Alerts.add(new AlertItem(array_list_Titles.get(i).toString(),Integer.parseInt(array_list_Days.get(i)),Integer.parseInt(array_list_Months.get(i).toString()),
                    Integer.parseInt(array_list_Years.get(i).toString()),Integer.parseInt(array_list_Hours.get(i).toString()),Integer.parseInt(array_list_Minutes.get(i).toString()),
                    array_list_IsActive.get(i).toString()));

            System.out.println("Alert "+i+"  Title = "+array_list_Titles.get(i));
            System.out.println("Alert "+i+" Date = "+array_list_Days.get(i) +"/"+ array_list_Months.get(i) +"/" +array_list_Years.get(i) ) ;
            System.out.println("Alert "+i+" Time = "+array_list_Hours.get(i) +":"+ array_list_Minutes.get(i));
            System.out.println("Alert "+i+" IsActive = "+array_list_IsActive.get(i) );
            System.out.println("___________________________________________________________________");

        }

        return array_list_Minutes;
    }


    //////////////////////////////////////////////////////////////////////////////////

    public ArrayList<String> getAlertIsActive()
    {

        ArrayList<AlertItem> array_list_Alerts = new ArrayList<AlertItem>();
        ArrayList<String> array_list_IDs = new ArrayList<String>();
        ArrayList<String> array_list_Titles = new ArrayList<String>();
        ArrayList<String> array_list_Days = new ArrayList<String>();
        ArrayList<String> array_list_Months = new ArrayList<String>();
        ArrayList<String> array_list_Years = new ArrayList<String>();
        ArrayList<String> array_list_Hours = new ArrayList<String>();
        ArrayList<String> array_list_Minutes = new ArrayList<String>();
        ArrayList<String> array_list_IsActive = new ArrayList<String>();


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_ALERTITEMS, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list_IDs.add(res.getString(res.getColumnIndex(COLUMN_ALERTID)));
            array_list_Titles.add(res.getString(res.getColumnIndex(COLUMN_ALERTTITLE)));
            array_list_Days.add(res.getString(res.getColumnIndex(COLUMN_DAY)));
            array_list_Months.add(res.getString(res.getColumnIndex(COLUMN_MONTH)));
            array_list_Years.add(res.getString(res.getColumnIndex(COLUMN_YEAR)));
            array_list_Hours.add(res.getString(res.getColumnIndex(COLUMN_HOUR)));
            array_list_Minutes.add(res.getString(res.getColumnIndex(COLUMN_MINUTE)));
            array_list_IsActive.add(res.getString(res.getColumnIndex(COLUMN_ISACTIVE)));
            res.moveToNext();
        }

        //Construct Table Alerts

        for(int i=0;i<array_list_IDs.size();i++){
            array_list_Alerts.add(new AlertItem(array_list_Titles.get(i).toString(),Integer.parseInt(array_list_Days.get(i)),Integer.parseInt(array_list_Months.get(i).toString()),
                    Integer.parseInt(array_list_Years.get(i).toString()),Integer.parseInt(array_list_Hours.get(i).toString()),Integer.parseInt(array_list_Minutes.get(i).toString()),
                    array_list_IsActive.get(i).toString()));

            System.out.println("Alert "+i+"  Title = "+array_list_Titles.get(i));
            System.out.println("Alert "+i+" Date = "+array_list_Days.get(i) +"/"+ array_list_Months.get(i) +"/" +array_list_Years.get(i) ) ;
            System.out.println("Alert "+i+" Time = "+array_list_Hours.get(i) +":"+ array_list_Minutes.get(i));
            System.out.println("Alert "+i+" IsActive = "+array_list_IsActive.get(i) );
            System.out.println("___________________________________________________________________");

        }

        return array_list_IsActive;
    }




 */



