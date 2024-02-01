package com.example.grigoreadrianmaths.viewModels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.grigoreadrianmaths.R;

public class RankingViewModel extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
    }

    public void MainMenu(View view){
        intent = new Intent(RankingViewModel.this, LevelSelectorViewModel.class);
        startActivity(intent);
    }

}