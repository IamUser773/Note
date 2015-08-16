package com.example.iamuser773.note;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ListView note_list;
    Toolbar toolbar;
    NoteData listnote;
    SQLiteDatabase Db;
    Cursor mCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        note_list = (ListView) findViewById(R.id.list_note);


    }

    //ใช้เมธอดonResume เพื่อเวลาที่ลบหรือเพิ่มข้อมูลจะได้โชในlistView ทันที ขอบคุณพี่Artit Kiuwilai
    @Override
    protected void onResume() {
        super.onResume();
        listview_note();
    }

    //เมธอดนี้ทำหน้าที่ดึงข้อมูลจากคอลัมtitle ทั่งหมดมาเก็บไว้ที่ Arraylist
    private void listview_note() {
        listnote = new NoteData(this);
        Db = listnote.getReadableDatabase();
        mCursor = Db.rawQuery("SELECT * FROM " + NoteData.TABLE_NAME, null);
        ArrayList<String> add_note = new ArrayList<String>();
        //ให้cursorดึงข้อมูลตั่งเเต่คอลัมเเรก จนถึงคอลัมสุดท้าย
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            add_note.add(mCursor.getString(mCursor.getColumnIndex(NoteData.COL_TITLE)));
            mCursor.moveToNext();
        }

        //เอาข้อมูลมา set ในAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_list_item_1, add_note);
        adapter.notifyDataSetChanged();
        note_list.setAdapter(adapter);
        //เมื่อมี่การคลิก ให้ cursor รับตำเเหน่งที่คลิกเเละไปดึงข้อมูลจากคอลัม มาเก็บไว้ในString เเละIntent ไปอีกActivity
        note_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCursor.moveToPosition(position);
                String title = mCursor.getString(mCursor.getColumnIndex(NoteData.COL_TITLE));
                String description = mCursor.getString(mCursor.getColumnIndex(NoteData.COL_DESCRIPTION));
                Intent i = new Intent(getApplicationContext(), ShowNote.class);
                i.putExtra("title", title);
                i.putExtra("description", description);
                startActivity(i);
            }
        });
        //เมื่อมีการคลิกค้าง ให้ทำการสร้างAlertdialog เเละกำหนดปุ่มกด
        note_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mCursor.moveToPosition(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("คุณต้องการทำอะไร");
                //สร้าง ปุ่มลบข้อมูล
                dialog.setPositiveButton("ลบข้อมูล", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = mCursor.getString(mCursor.getColumnIndex(NoteData.COL_TITLE));
                        String description = mCursor.getString(mCursor.getColumnIndex(NoteData.COL_DESCRIPTION));
                        Db.execSQL("DELETE FROM " + NoteData.TABLE_NAME + " WHERE " + NoteData.COL_TITLE + "='" + title + "'" + " AND " + NoteData.COL_DESCRIPTION + "='" + description + "';");
                        onResume();

                    }
                });
                dialog.show();


                return true;
            }


        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.bar) {
            Intent intent = new Intent(this, Add_note.class);
            startActivity(intent);
        }
        if (id == R.id.refresh) {
            recreate();
        }

        return super.onOptionsItemSelected(item);
    }


}
