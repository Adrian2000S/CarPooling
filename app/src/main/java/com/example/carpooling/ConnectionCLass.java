package com.example.carpooling;

import android.os.StrictMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import android.os.StrictMode.ThreadPolicy;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.util.Log;

class ConnectionCLass {

    String classs = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://192.168.1.4/car_pooling";
    String user = "adrianstelea";
    String password = "AdrianStelea.2000";

    public Connection CONN(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;

        try {
            Class.forName(classs);
            conn = DriverManager.getConnection(url, user, password);
            conn = DriverManager.getConnection(ConnURL);
        } catch (ClassNotFoundException e) {
            Log.e("Errore", e.getMessage());
        }catch (SQLException se){
            Log.e("Errore", se.getMessage());
        }catch (Exception e){
            Log.e("Errore", e.getMessage());
        }
        return conn;
    }
}
