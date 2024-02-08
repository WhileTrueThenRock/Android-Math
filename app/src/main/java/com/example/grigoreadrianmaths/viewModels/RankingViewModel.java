package com.example.grigoreadrianmaths.viewModels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grigoreadrianmaths.R;
import com.example.grigoreadrianmaths.dao.UserDAO;
import com.example.grigoreadrianmaths.database.ConexionDB;
import com.example.grigoreadrianmaths.models.AdapterPersonalizado;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RankingViewModel extends AppCompatActivity {
    private int[] logos = {R.drawable.bronze,R.drawable.silver,R.drawable.gold,R.drawable.platinum};
    ArrayList<String> players = new ArrayList<String>();

    private int [] scores;

    private Intent intent;
    private GridView grid;
    private UserDAO userDAO;
    private Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        connection = ConexionDB.initDBConnection();
        userDAO = new UserDAO(connection);
        grid = findViewById(R.id.grid);
        loadRankingScore();


    }

    public void MainMenu(View view){
        intent = new Intent(RankingViewModel.this, LevelSelectorViewModel.class);
        startActivity(intent);
    }

    public void loadRankingScore() {
        try {
            Map<String, Integer> scoresMap = userDAO.getScoresAndUsernamesFromDatabase();
            players = new ArrayList<>(scoresMap.keySet());
            scores = new int[players.size()];

            for (int i = 0; i < players.size(); i++) {
                scores[i] = scoresMap.get(players.get(i));
            }
            AdapterPersonalizado adapter = new AdapterPersonalizado(this,players,scores);
            grid.setAdapter(adapter);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public void loadScore() {
        String username =LoginViewModel.userTitle;
        try {
            int puntos = userDAO.loadScore(username);
            userDAO.registerScoreInRanking(puntos,username);
        } catch (SQLException e) {
            Toast.makeText(this, "Error al actualizar puntos", Toast.LENGTH_SHORT).show();
        }
    }





}