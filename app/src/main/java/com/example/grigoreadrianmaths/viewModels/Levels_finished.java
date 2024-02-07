package com.example.grigoreadrianmaths.viewModels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.grigoreadrianmaths.R;

public class Levels_finished extends AppCompatActivity {
private ImageView fireworks;
private TextView username;
private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_finished);
        fireworks = findViewById(R.id.img_fireworks);
        username = findViewById(R.id.tv_username);
        String usuario = LoginViewModel.userTitle;
        username.setText(usuario);

        Glide.with(this)
                .asGif()
                .load(R.drawable.giphy)
                .into(fireworks);
    }

    public void loadRankings(View view) {
        intent = new Intent(Levels_finished.this, RankingViewModel.class);
        startActivity(intent);
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