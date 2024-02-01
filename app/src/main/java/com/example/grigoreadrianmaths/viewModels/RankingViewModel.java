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
import com.example.grigoreadrianmaths.models.AdapterPersonalizado;

public class RankingViewModel extends AppCompatActivity {
    private int[] logos = {R.drawable.bronze,R.drawable.silver,R.drawable.gold,R.drawable.platinum};
    String datos[] = new String[]{"Adrian", "Victor","Ruben","Bob","Jason"};

    private Intent intent;
    private GridView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        grid = findViewById(R.id.grid);
        AdapterPersonalizado adapter = new AdapterPersonalizado(this,datos);
        grid.setAdapter(adapter);
    }

    public void MainMenu(View view){
        intent = new Intent(RankingViewModel.this, LevelSelectorViewModel.class);
        startActivity(intent);
    }



}