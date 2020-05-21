package com.example.carpooling;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.SQLException;
import  java.sql.Statement;
import  java.lang.*;

class PrenotationActivity extends AppCompatActivity {

    String TAG = "Prenotation";
    TextView datas;
    Button home;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prenotation);
        home = (Button) findViewById(R.id.btn_home);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        datas = (TextView) findViewById(R.id.tv_yourdata);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(PrenotationActivity.this, MainActivity.class);
                in.putExtra("MESSAGE", "Nome");
                in.putExtra("MSG", "Password");
                setResult(Activity.RESULT_OK, in);
                finish();
            }
        });
        Log.d(TAG, "onCreate");

        datas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
