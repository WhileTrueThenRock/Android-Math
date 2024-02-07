package com.example.grigoreadrianmaths.viewModels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.grigoreadrianmaths.R;
import com.example.grigoreadrianmaths.dao.UserDAO;
import com.example.grigoreadrianmaths.database.ConexionDB;

import java.sql.Connection;
import java.sql.SQLException;

public class GameOverViewModel extends AppCompatActivity {
    private UserDAO userDAO;
    String username = LoginViewModel.userTitle;
    private Connection connection;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        connection = ConexionDB.initDBConnection();
        userDAO = new UserDAO(connection);
    }

    public void resetProgress(View view){
        String username =LoginViewModel.userTitle;
                intent = new Intent(GameOverViewModel.this, LevelSelectorViewModel.class);
                startActivity(intent);
                userDAO.updateHealth(LoginViewModel.userTitle,5);
    }


    @Override
    public void onBackPressed() {
        if (false) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Usa los botones de la App!", Toast.LENGTH_SHORT).show();
        }
    }
}