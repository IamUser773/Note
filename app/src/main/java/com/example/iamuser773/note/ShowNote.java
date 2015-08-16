package com.example.iamuser773.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by IamUser773 on 14/8/2558.
 */
public class ShowNote extends AppCompatActivity {
    TextView title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_note);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title = (TextView) findViewById(R.id.title_show);
        description = (TextView) findViewById(R.id.des_show);

        //�Ѻ��ҷ�����������㹵�����
        String titleshow = getIntent().getExtras().getString("title");
        String Description = getIntent().getExtras().getString("description");
        //����set
        description.setText(Description);
        title.setText(titleshow);
    }
}
