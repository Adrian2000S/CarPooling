package com.example.carpooling;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.SQLException;
import  java.sql.Statement;
import java.lang.*;

public class SecondActivity extends AppCompatActivity{
    EditText nome, cognome, etemail, etpassword;
    Button registration;
    ProgressDialog progressDialog;
    ConnectionCLass connectionClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nome = (EditText) findViewById(R.id.et_name);
        cognome = (EditText) findViewById(R.id.et_surname);
        etemail = (EditText) findViewById(R.id.et_email);
        etpassword = (EditText) findViewById(R.id.et_password);
        registration = (Button) findViewById(R.id.btn_register2);

        connectionClass = new ConnectionCLass();
        progressDialog = new ProgressDialog(SecondActivity.this);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Doregister doregister = new Doregister(nome.getText().toString(), cognome.getText().toString(), etemail.getText().toString(), etpassword.getText().toString(), "", false );
                doregister.execute("");
            }
        });
    }

    public class Doregister extends AsyncTask<String, String, String>{

        String nomeStr;
        String cognomeStr;
        String emailStr ;
        String passStr ;
        String z ;
        boolean isSuccess;

        public Doregister(String nomeStr, String cognomeStr, String emailStr, String passStr, String z, boolean isSuccess){
            this.nomeStr=nomeStr;
            this.cognomeStr=cognomeStr;
            this.emailStr = emailStr;
            this.passStr = passStr;
            this.z=z;
            this.isSuccess= isSuccess;
        }

        @Override
        protected void onPreExecute(){
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            if (nomeStr.trim().equals("") || cognomeStr.trim().equals("") || emailStr.trim().equals("") || passStr.trim().equals("")) {
                z = "Inserire i dati richiesti...";
            }else{
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null){
                        z = "Controllare la connessione Internet";
                    }else{
                        String query = "INSERT INTO utente(nome, cognome, email, password) VALUES('"+nomeStr+"', '"+cognomeStr+"', '"+emailStr+"', '"+passStr+"')";
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate(query);
                        z = "Registrazione effettuata";
                        isSuccess=true;
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = "Eccezioni"+ex;
                }
                Intent i = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(i);
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s){
            if (isSuccess = true){
                Toast.makeText(getApplicationContext(), ""+z, Toast.LENGTH_LONG).show();
            }
            progressDialog.hide();
        }
    }
}

