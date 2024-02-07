package com.example.grigoreadrianmaths.viewModels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grigoreadrianmaths.R;
import com.example.grigoreadrianmaths.dao.UserDAO;
import com.example.grigoreadrianmaths.database.ConexionDB;

import java.sql.Connection;
import java.sql.SQLException;

public class InfoMessageViewModel extends AppCompatActivity {
    private TextView userName,score;
    private Intent intent;
    private UserDAO userDAO;
    private Connection connection;
    private int cantidadDeVidas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_message);
        userName = findViewById(R.id.tv_username);
        score = findViewById(R.id.tv_score);
        String usuario = LoginViewModel.userTitle;
        userName.setText(usuario);
        connection = ConexionDB.initDBConnection();
        userDAO = new UserDAO(connection);
        cantidadDeVidas = getIntent().getIntExtra("vidas",5);

        loadScore();
    }


    public void loadMainMenu(View view) {
        intent = new Intent(InfoMessageViewModel.this, LevelSelectorViewModel.class);
        intent.putExtra("vidas", cantidadDeVidas);
        startActivity(intent);

    }

    public void loadScore() {
        String username =LoginViewModel.userTitle;
        try {
            int puntos = userDAO.loadScore(username);
            score.setText(String.valueOf(puntos));
        } catch (SQLException e) {
            Toast.makeText(this, "Error al actualizar puntos", Toast.LENGTH_SHORT).show();
        }
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