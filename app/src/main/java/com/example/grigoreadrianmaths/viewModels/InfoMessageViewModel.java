package com.example.grigoreadrianmaths.viewModels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grigoreadrianmaths.R;

public class InfoMessageViewModel extends AppCompatActivity {
    private TextView userName,score;
    private ImageView healthBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_message);
        userName = findViewById(R.id.tv_username);
        score = findViewById(R.id.tv_score);
        healthBar = findViewById(R.id.healthBar);
        String usuario = LoginViewModel.userTitle;
        userName.setText(usuario);
    }
}