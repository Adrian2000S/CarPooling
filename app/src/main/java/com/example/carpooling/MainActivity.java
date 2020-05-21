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
            DoLogin dologin = new DoLogin(email.getText().toString(), password.getText().toString(), "", false);
            dologin.execute();
            }
        });
    }

    public class DoLogin extends AsyncTask<String, String, String> {
        String emailStr;
        String passStr;
        String z;
        boolean isSuccess;

        public DoLogin(String emailStr, String passStr, String z, boolean isSuccess) {
            this.emailStr = emailStr;
            this.passStr = passStr;
            this.z = z;
            this.isSuccess = isSuccess;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            if (emailStr.trim().equals("") || passStr.trim().equals("")) {
                z = "Inserire i dati richiesti...";
            } else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Controllare la connessione Internet";
                    } else {
                        String query = "SELECT utente.email, utente.password FROM utente WHERE utente.email ="+emailStr+" AND utente.password="+passStr+"";
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate(query);
                        z = "Login effettuato";
                        isSuccess = true;
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = "Eccezioni" + ex;
                }
                startActivity(new Intent(MainActivity.this, PrenotationActivity.class));
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            if (isSuccess = true) {
                Toast.makeText(getApplicationContext(), "" + z, Toast.LENGTH_LONG).show();
            }
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


