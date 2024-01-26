package com.example.grigoreadrianmaths.database;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static Connection conexion = null;
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://192.168.20.61:5432/Pasajeros";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public static Connection initDBConnection(){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conexion;
    }

    public static void closeDBConnection(Connection conexion){
        try {
            conexion.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
