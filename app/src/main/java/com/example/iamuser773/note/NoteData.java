package com.example.iamuser773.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by IamUser773 on 14/8/2558.
 */
public class NoteData extends SQLiteOpenHelper{

    // ชื่อฐานข้อมูล เเล เวอชั่น
    private static final String DB_NAME = "MyNote";
    private static final int VERSION = 1;
    //ชื่อ คอลัม
    public static final String TABLE_NAME = "Note";
    public static final String COL_TITLE = "Title";
    public static final String COL_DESCRIPTION = "Description";

// เมธดรับค่า context เพื่อเรียกใช้งาน
    public NoteData(Context context){
        super(context,DB_NAME,null,VERSION);
    }

    // เมธอดสร้างฐานข้อมูล
    @Override
    public void onCreate(SQLiteDatabase db) {
        //คำสั่งสร้างฐานข้อมูล
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT, " + COL_DESCRIPTION + " TEXT);");

    }


    // เมธอด อัพเกรดฐานข้อมูล
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }


}
