package com.example.grigoreadrianmaths.viewModels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grigoreadrianmaths.R;
import com.example.grigoreadrianmaths.dao.UserDAO;
import com.example.grigoreadrianmaths.database.ConexionDB;
import com.example.grigoreadrianmaths.levels.Level1;
import com.example.grigoreadrianmaths.levels.Level2;
import com.example.grigoreadrianmaths.levels.Level3;
import com.example.grigoreadrianmaths.levels.Level4;

import java.sql.Connection;
import java.sql.SQLException;

public class LevelSelectorViewModel extends AppCompatActivity {
    private UserDAO userDAO;
    String username = LoginViewModel.userTitle;
    private Connection connection;
    private TextView usuario,score;
    private Intent intent;
    private LoginViewModel loginViewModel = new LoginViewModel();
    private Level1 level1 = new Level1();
    private Level2 level2;
    private Level3 level3;
    private Level4 level4;
    private ImageButton lvl1,lvl2,lvl3,lvl4,lvl5,lvl6,lvl7,lvl8;
    private ImageView trophy,healthBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_selector);
        lvl1 = findViewById(R.id.bt_lvl1);
        lvl2 = findViewById(R.id.bt_lvl2);
        lvl3 = findViewById(R.id.bt_lvl3);
        lvl4 = findViewById(R.id.bt_lvl4);
        lvl5 = findViewById(R.id.bt_lvl5);
        lvl6 = findViewById(R.id.bt_lvl6);
        lvl7 = findViewById(R.id.bt_lvl7);
        lvl8 = findViewById(R.id.bt_lvl8);
        //trophy = findViewById(R.id.trophies);
        score = findViewById(R.id.tv_score);
        healthBar = findViewById(R.id.healthBar);

        lvl2.setEnabled(true);
        lvl3.setEnabled(false);
        lvl4.setEnabled(false);
        lvl5.setEnabled(false);
        lvl6.setEnabled(false);
        lvl7.setEnabled(false);
        lvl8.setEnabled(false);

        connection = ConexionDB.initDBConnection();
        userDAO = new UserDAO(connection);
        usuario = findViewById(R.id.tv_username);
        username = LoginViewModel.userTitle;
        usuario.setText(username);

        updateHealth();
        loadStars();
        loadScore();
    }

    public void loadStars(){
        String username =LoginViewModel.userTitle;
        try {
            for (int nivel = 1; nivel <= 6; nivel++) {
                int estrellas = userDAO.loadStars(username, nivel);
                if (estrellas == 1 && nivel == 1) {
                    lvl1.setImageResource(R.drawable.lvl1_1);
                    lvl2.setEnabled(true);
                    lvl2.setImageResource(R.drawable.lvl2);
                } else if (estrellas == 2 && nivel == 1) {
                    lvl1.setImageResource(R.drawable.lvl1_2);
                    lvl2.setEnabled(true);
                    lvl2.setImageResource(R.drawable.lvl2);
                } else if (estrellas == 3 && nivel == 1) {
                    lvl1.setImageResource(R.drawable.lvl1_3);
                    lvl2.setEnabled(true);
                    lvl2.setImageResource(R.drawable.lvl2);
                    trophy.setImageResource(R.drawable.bronze_trophy);
                } else if (estrellas == 1 && nivel == 2) {
                    lvl2.setImageResource(R.drawable.lvl2_1);
                    lvl3.setEnabled(true);
                    lvl3.setImageResource(R.drawable.lvl3);
                } else if (estrellas == 2 && nivel == 2) {
                    lvl2.setImageResource(R.drawable.lvl2_2);
                    lvl3.setEnabled(true);
                    lvl3.setImageResource(R.drawable.lvl3);
                } else if (estrellas == 3 && nivel == 2) {
                    lvl2.setImageResource(R.drawable.lvl2_3);
                    lvl3.setEnabled(true);
                    lvl3.setImageResource(R.drawable.lvl3);
                } else if (estrellas == 1 && nivel == 3) {
                    lvl3.setImageResource(R.drawable.lvl3_1);
                    lvl4.setEnabled(true);
                    lvl4.setImageResource(R.drawable.lvl4);
                } else if (estrellas == 2 && nivel == 3) {
                    lvl3.setImageResource(R.drawable.lvl3_2);
                    lvl4.setEnabled(true);
                    lvl4.setImageResource(R.drawable.lvl4);
                } else if (estrellas == 3 && nivel == 3) {
                    lvl3.setImageResource(R.drawable.lvl3_3);
                    lvl4.setEnabled(true);
                    lvl4.setImageResource(R.drawable.lvl4);
                } else if (estrellas == 1 && nivel == 4) {
                   lvl4.setImageResource(R.drawable.lvl4_1);
                    lvl5.setEnabled(true);
                    lvl5.setImageResource(R.drawable.lvl5);
                } else if (estrellas == 2 && nivel == 4) {
                    lvl4.setImageResource(R.drawable.lvl4_2);
                    lvl5.setEnabled(true);
                    lvl5.setImageResource(R.drawable.lvl5);
                } else if (estrellas == 3 && nivel == 4) {
                    lvl4.setImageResource(R.drawable.lvl4_3);
                    lvl5.setEnabled(true);
                    lvl5.setImageResource(R.drawable.lvl5);
                } else if (estrellas == 1 && nivel == 5) {
                    lvl5.setImageResource(R.drawable.lvl5_1);
                    lvl6.setEnabled(true);
                    lvl6.setImageResource(R.drawable.lvl6);
                } else if (estrellas == 2 && nivel == 5) {
                    lvl5.setImageResource(R.drawable.lvl5_2);
                    lvl6.setEnabled(true);
                    lvl6.setImageResource(R.drawable.lvl6);
                } else if (estrellas == 3 && nivel == 5) {
                    lvl5.setImageResource(R.drawable.lvl5_3);
                    lvl6.setEnabled(true);
                    lvl6.setImageResource(R.drawable.lvl6);
                } else if (estrellas == 1 && nivel == 6) {
                    lvl6.setImageResource(R.drawable.lvl6_1);
                } else if (estrellas == 2 && nivel == 6) {
                    lvl6.setImageResource(R.drawable.lvl6_2);
                } else if (estrellas == 3 && nivel == 6) {
                    lvl6.setImageResource(R.drawable.lvl6_3);
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error al actualizar imagen", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateHealth(){
        if(Level1.vidas >= 5)
            healthBar.setImageResource(R.drawable.life_100);
        else if(Level1.vidas == 4)
            healthBar.setImageResource(R.drawable.life_75);
        else if(Level1.vidas == 3)
            healthBar.setImageResource(R.drawable.life_50);
        else if(Level1.vidas == 2)
            healthBar.setImageResource(R.drawable.life_25);
        else if(Level1.vidas == 1)
            healthBar.setImageResource(R.drawable.life_5);
        else if(Level1.vidas == 0)
            healthBar.setImageResource(R.drawable.life_0);
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





    public void loadLvl1(View view){
        intent = new Intent(LevelSelectorViewModel.this, Level1.class);
        startActivity(intent);
    }

    public void loadLvl2(View view){
        intent = new Intent(LevelSelectorViewModel.this, Level2.class);
        startActivity(intent);
    }
    public void loadLvl3(View view){
        intent = new Intent(LevelSelectorViewModel.this, Level3.class);
        startActivity(intent);
    }
    public void loadLvl4(View view){
        intent = new Intent(LevelSelectorViewModel.this, Level4.class);
        startActivity(intent);
    }

    public void logOut(View view){
        lvl2.setEnabled(false);

        if(lvl2.isEnabled()){
            this.finish();
        }
        else{
            Toast.makeText(this, "Todavía no has desbloqueado el nivel", Toast.LENGTH_SHORT).show();
        }
    }
}