package com.example.iamuser773.note;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by IamUser773 on 13/8/2558.
 */
public class Add_note extends AppCompatActivity {
    Button save;
    EditText title, des;
    NoteData AddNewNode;
    SQLiteDatabase Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkNote();

    }

    private void checkNote() {
        //  รับค่ามาใส่ตัวเเปร string
        title = (EditText) findViewById(R.id.bt_title);
        des = (EditText) findViewById(R.id.bt_des);
        save = (Button) findViewById(R.id.button);

        //เมื่อมีการกดที่ปุ่มเพิ่ม ทำการเช็คว่ามีข้อความอยู่ไหม
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ถ้าไม่มี ทำการเเจ้งว่าให้ใส่ข้อมูลให้ครบ
                if (title.length() <= 0 || des.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "กรุณาใส่ข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                } else {
                    //เเต่ถ้ามีข้อมูล

                    // ค่าcontext ที่ส่งไปให้เมธอด NoteData
                    AddNewNode = new NoteData(getApplicationContext());
                    Db = AddNewNode.getWritableDatabase();
                    // ทำการเก็บค่า
                    String Title = title.getText().toString();
                    String description = des.getText().toString();

                    //กำหนด ที่อยู่
                    Cursor mCursor = Db.rawQuery("SELECT * FROM " + NoteData.TABLE_NAME
                            + " WHERE " + NoteData.COL_TITLE + "='" + Title + "'"
                            + " AND " + NoteData.COL_DESCRIPTION + "='" + description + "'", null);

                    //เพิ่มข้อมูล
                    if (mCursor.getCount() == 0) {
                        Db.execSQL("INSERT INTO " + NoteData.TABLE_NAME + " (" + NoteData.COL_TITLE
                                + ", " + NoteData.COL_DESCRIPTION + ") VALUES ('" + Title + "', '" + description + "');");
                    }
                    Toast.makeText(getApplicationContext(), "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                    title.setText("");
                    des.setText("");

                    AddNewNode.close();
                    Db.close();
                    //ปิด Activity
                    finish();
                }
            }
        });


    }

}
