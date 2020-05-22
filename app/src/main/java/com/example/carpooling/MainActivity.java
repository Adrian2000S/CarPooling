package com.example.carpooling;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.ResultSet;
import  java.sql.Statement;
import  java.lang.*;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button login, register;
    ProgressDialog progressDialog;
    ConnectionCLass connectionClass;
    private static final int REQUEST_CODE = 1;
    final String TAGR = "Result";
    final String TAG = "MainActivity";
    String em, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register);

        connectionClass = new ConnectionCLass();
        progressDialog = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Dologin dologin = new Dologin(email.getText().toString(), password.getText().toString(), "", false, "", "");
            dologin.execute();
            }
        });
    }

    public class Dologin extends AsyncTask<String, String, String> {
        String emailStr;
        String passStr;
        String z;
        boolean isSuccess;
        String em, pass;

        public Dologin(String emailStr, String passStr, String z, boolean isSuccess, String em, String pass) {
            this.emailStr= emailStr;
            this.passStr= passStr;
            this.z = z;
            this.isSuccess = isSuccess;
            this.em = em;
            this.pass = pass;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            if (emailStr.trim().equals("") || passStr.trim().equals("")) {
                isSuccess=false;
                z = "Inserire i dati richiesti...";
            } else {
                try {
                    Connection connect = connectionClass.CONN();
                    if (connect == null) {
                        isSuccess=false;
                        z = "Controllare la connessione Internet";
                    } else {
                        String querySelect = "SELECT utente.email, utente.password FROM utente WHERE utente.email ='"+emailStr+"' AND utente.password='"+passStr+"'";
                        Statement sttmnt = connect.createStatement();
                        ResultSet rs = sttmnt.executeQuery(querySelect);
                        while(rs.next()){
                            em = rs.getString(1);
                            pass = rs.getString(2);
                            if(em.equals(emailStr) && pass.equals(passStr)){
                                isSuccess = true;
                                z = "Login effettuato";
                                Intent in = new Intent(MainActivity.this, MapsActivity.class);
                                startActivity(in);
                            }else{
                                isSuccess = false;
                                z = "Login non eseguito";
                                startActivity(new Intent(MainActivity.this, MainActivity.class));
                            }
                        }
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = "Eccezioni" + ex;
                }

            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();
            progressDialog.hide();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==REQUEST_CODE)&&(resultCode == Activity.RESULT_OK)){
            String message = data.getStringExtra("MESSAGE");
            String msg = data.getStringExtra("MSG");
            email.setText(message);
            password.setText(msg);
        } else if(resultCode == Activity.RESULT_CANCELED ) {
            Toast toa = Toast.makeText(getApplicationContext(), "Operazione annullata!", Toast.LENGTH_LONG);
            toa.show();
        }
        Log.d(TAGR, "siamo dentro al metodo onResult");
    }
}


