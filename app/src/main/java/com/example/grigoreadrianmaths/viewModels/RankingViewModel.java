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

import com.example.grigoreadrianmaths.R;
import com.example.grigoreadrianmaths.dao.UserDAO;
import com.example.grigoreadrianmaths.database.ConexionDB;
import com.example.grigoreadrianmaths.models.AdapterPersonalizado;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class RankingViewModel extends AppCompatActivity {
    private int[] logos = {R.drawable.bronze,R.drawable.silver,R.drawable.gold,R.drawable.platinum};
    ArrayList<String> players = new ArrayList<String>();

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
        AdapterPersonalizado adapter = new AdapterPersonalizado(this,players);
        grid.setAdapter(adapter);


    }

    public void MainMenu(View view){
        intent = new Intent(RankingViewModel.this, LevelSelectorViewModel.class);
        startActivity(intent);
    }

    public void loadRankingScore(){
        try {
            players = userDAO.getUserNamesFromDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





}