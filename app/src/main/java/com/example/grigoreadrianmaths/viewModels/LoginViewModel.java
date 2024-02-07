package com.example.grigoreadrianmaths.viewModels;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grigoreadrianmaths.R;
import com.example.grigoreadrianmaths.dao.UserDAO;
import com.example.grigoreadrianmaths.database.ConexionDB;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginViewModel extends AppCompatActivity {
    private EditText name,surname,username,password,confirmPassword;
    private UserDAO userDAO;
    private Connection connection;
    private Intent intent;
    public static String userTitle;
    private boolean shouldAllowBack = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        connection = ConexionDB.initDBConnection();
        userDAO = new UserDAO(connection);
        createDatabaseAndTables();
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);

    }

    @Override
    public void onBackPressed() {
        if (false) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Usa los botones de la App!", Toast.LENGTH_SHORT).show();
        }
    }

    public void createDatabaseAndTables(){
        try {
            userDAO.createTables();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void login(View view) {
        String nombre = username.getText().toString();
        String passwd = password.getText().toString();
        userTitle = nombre;
        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(passwd)) {
            Toast.makeText(this, "Te faltan campos por rellenar!", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            if (userDAO.login(nombre, passwd)) {
                intent = new Intent(LoginViewModel.this, LevelSelectorViewModel.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e) {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
        }
    }

    public void loadRegisterPage(View view){
        intent = new Intent(LoginViewModel.this, RegisterViewModel.class);
        startActivity(intent);
    }
}