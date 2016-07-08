package com.shinecity.lko.customerpanal.ModelData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QServer on 6/2/2016.
 */
public class DbHelper extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "mydatabase";

    private static final String TABLE_DASHBOARD="dashboard";
    private static final String TABLE_ALLBOOKING="allbooking";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       String createTable= "create table "+ TABLE_DASHBOARD + "( dasbordid integer primary key autoincrement, dob TEXT  ,  memid TEXT, fathername TEXT, loginid TEXT, maritalstatus TEXT, Name TEXT, sex TEXT, title TEXT )";
        String createTable_allbooking= "create table "+ TABLE_ALLBOOKING + "( dasbordid integer primary key autoincrement, sn TEXT  ,  plotno TEXT, sitename TEXT, bsp TEXT )";

        db.execSQL(createTable);
        db.execSQL(createTable_allbooking);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DASHBOARD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALLBOOKING);

        onCreate(db);
    }


    // code to insert all  member
    public void insertMemberData(MemberDetailData memberDetailData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("dob", memberDetailData.getPanno());
        values.put("memid", memberDetailData.getMemid());
        values.put("fathername", memberDetailData.getFathername());
        values.put("loginid", memberDetailData.getLoginid());
        values.put("maritalstatus", memberDetailData.getMobileno());
        values.put("name", memberDetailData.getName());
        values.put("sex", memberDetailData.getEmail());
        values.put("title", memberDetailData.getTitle());



        // Inserting Row
        db.insert(TABLE_DASHBOARD, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public List<MemberDetailData> getAllMemberData() {
        List<MemberDetailData> buslistList = new ArrayList<MemberDetailData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DASHBOARD;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MemberDetailData addstudent = new MemberDetailData();
                //contact.setID(Integer.parseInt(cursor.getString(0)));
                addstudent.setPanno(cursor.getString(1));
                addstudent.setMemid(cursor.getString(2));
                addstudent.setFathername(cursor.getString(3));
                addstudent.setLoginid(cursor.getString(4));
                addstudent.setMobileno(cursor.getString(5));
                addstudent.setName(cursor.getString(6));
                addstudent.setEmail(cursor.getString(7));
                addstudent.setTitle(cursor.getString(8));

                // Adding contact to list
                buslistList.add(addstudent);
            } while (cursor.moveToNext());
        }
        // return contact list
        return buslistList;
    }

    public void deleteSingleRow()
    {
        SQLiteDatabase db = this.getWritableDatabase();
      //  db.execSQL("DELETE FROM " + TABLE_DASHBOARD + " WHERE " + KEY_EMAIL + "='" + value + "'");
        db.execSQL("DELETE FROM " + TABLE_DASHBOARD + " ;");
        db.close();
    }

    public void insertAllBookingData(AllBookingData allBookingData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("sn", allBookingData.getSnno());
        values.put("plotno", allBookingData.getPlotno());
        values.put("sitename", allBookingData.getSitename());
        values.put("bsp", allBookingData.getBsp());




        // Inserting Row
        db.insert(TABLE_ALLBOOKING, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public List<AllBookingData> getAllBookingData() {
        List<AllBookingData> buslistList = new ArrayList<AllBookingData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ALLBOOKING;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AllBookingData addstudent = new AllBookingData();
                //contact.setID(Integer.parseInt(cursor.getString(0)));
                addstudent.setSnno(cursor.getString(1));
                addstudent.setPlotno(cursor.getString(2));
                addstudent.setSitename(cursor.getString(3));
                addstudent.setBsp(cursor.getString(4));


                // Adding contact to list
                buslistList.add(addstudent);
            } while (cursor.moveToNext());
        }
        // return contact list
        return buslistList;
    }
}
