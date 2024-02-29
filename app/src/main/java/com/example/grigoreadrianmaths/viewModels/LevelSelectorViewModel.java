package com.example.grigoreadrianmaths.viewModels;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.grigoreadrianmaths.R;
import com.example.grigoreadrianmaths.dao.UserDAO;
import com.example.grigoreadrianmaths.database.ConexionDB;
import com.example.grigoreadrianmaths.levels.Level1;
import com.example.grigoreadrianmaths.levels.Level2;
import com.example.grigoreadrianmaths.levels.Level3;
import com.example.grigoreadrianmaths.levels.Level4;
import com.example.grigoreadrianmaths.levels.Level5;
import com.example.grigoreadrianmaths.levels.Level6;
import com.example.grigoreadrianmaths.levels.Level7;
import com.example.grigoreadrianmaths.levels.Level8;

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
    private Level5 level5;
    private Level6 level6;
    private Level7 level7;
    private Level8 level8;
    private ImageButton lvl1,lvl2,lvl3,lvl4,lvl5,lvl6,lvl7,lvl8;
    private ImageView trophy,healthBar;
    private Menu menu;
    private MediaPlayer mediaPlayer;
    private ConstraintLayout constraintLayout;
    private boolean lvl1Enabled = true;
    private boolean lvl2Enabled = true;
    private boolean lvl3Enabled = true;
    private boolean lvl4Enabled = true;
    private boolean lvl5Enabled = true;
    private boolean lvl6Enabled = true;
    private boolean lvl7Enabled = true;
    private boolean lvl8Enabled = true;

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
        constraintLayout = findViewById(R.id.myConstraintLayout);
        score = findViewById(R.id.tv_score);
        healthBar = findViewById(R.id.healthBar);

        lvl2.setEnabled(false);
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
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.pi_logo);

        int estadoDeSalud = userDAO.getHealth(LoginViewModel.userTitle); //Actualizamos el nivel de vida
         //cantidadDeVidas = getIntent().getIntExtra("vidas",5);
        updateHealth(estadoDeSalud);

        loadStars();
        loadScore();
    }

    @Override
    public void onBackPressed() {
        if (false) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Usa los botones de la App!", Toast.LENGTH_SHORT).show();
        }
    }


    public void updateHealth(int vidas) {
        int drawableId;

        switch (vidas) {
            case 5:
                drawableId = R.drawable.life_100;
                break;
            case 4:
                drawableId = R.drawable.life_75;
                break;
            case 3:
                drawableId = R.drawable.life_50;
                break;
            case 2:
                drawableId = R.drawable.life_25;
                break;
            case 1:
                drawableId = R.drawable.life_5;
                break;
            case 0:
            default:
                drawableId = R.drawable.life_0;
                break;
        }
        healthBar.setImageResource(drawableId);
    }


    public void resetProgress(){
        String username =LoginViewModel.userTitle;
        try {
            if(userDAO.resetProgress(username)){
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void loadStars(){
        String username = LoginViewModel.userTitle;
        try {
            for (int nivel = 1; nivel <= 8; nivel++) {
                int estrellas = userDAO.loadStars(username, nivel);
                //-------------------NIVEL1---------------------
                if (estrellas == 1 && nivel == 1) {
                    lvl1.setImageResource(R.drawable.lvl1_1);
                    lvl2.setEnabled(true);
                    lvl2.setImageResource(R.drawable.lvl2);
                    //lvl1.setEnabled(false);
                    lvl1Enabled = false;
                } else if (estrellas == 2 && nivel == 1) {
                    lvl1.setImageResource(R.drawable.lvl1_2);
                    lvl2.setEnabled(true);
                    lvl2.setImageResource(R.drawable.lvl2);
                    //lvl1.setEnabled(false);
                    lvl1Enabled = false;
                } else if (estrellas == 3 && nivel == 1) {
                    lvl1.setImageResource(R.drawable.lvl1_3);
                    lvl2.setEnabled(true);
                    lvl2.setImageResource(R.drawable.lvl2);
                    lvl1Enabled = false;
                }//-------------------NIVEL2---------------------
                else if (estrellas == 1 && nivel == 2) {
                    lvl2.setImageResource(R.drawable.lvl2_1);
                    lvl3.setEnabled(true);
                    lvl3.setImageResource(R.drawable.lvl3);
                    lvl2Enabled = false;
                    constraintLayout.setBackgroundResource(R.drawable.bg_bronze);
                } else if (estrellas == 2 && nivel == 2) {
                    lvl2.setImageResource(R.drawable.lvl2_2);
                    lvl3.setEnabled(true);
                    lvl3.setImageResource(R.drawable.lvl3);
                    lvl2Enabled = false;
                    constraintLayout.setBackgroundResource(R.drawable.bg_bronze);
                } else if (estrellas == 3 && nivel == 2) {
                    lvl2.setImageResource(R.drawable.lvl2_3);
                    lvl3.setEnabled(true);
                    lvl3.setImageResource(R.drawable.lvl3);
                    lvl2Enabled = false;
                    constraintLayout.setBackgroundResource(R.drawable.bg_bronze);
                }//-------------------NIVEL3---------------------
                else if (estrellas == 1 && nivel == 3) {
                    lvl3.setImageResource(R.drawable.lvl3_1);
                } else if (estrellas == 2 && nivel == 3) {
                    lvl3.setImageResource(R.drawable.lvl3_2);
                    lvl4.setEnabled(true);
                    lvl4.setImageResource(R.drawable.lvl4);
                    lvl3Enabled = false;
                } else if (estrellas == 3 && nivel == 3) {
                    lvl3.setImageResource(R.drawable.lvl3_3);
                    lvl4.setEnabled(true);
                    lvl4.setImageResource(R.drawable.lvl4);
                    lvl3Enabled = false;
                }//-------------------NIVEL4---------------------
                else if (estrellas == 1 && nivel == 4) {
                    lvl4.setImageResource(R.drawable.lvl4_1);
                } else if (estrellas == 2 && nivel == 4) {
                    lvl4.setImageResource(R.drawable.lvl4_2);
                    lvl5.setEnabled(true);
                    lvl5.setImageResource(R.drawable.lvl5);
                    lvl4Enabled = false;
                    constraintLayout.setBackgroundResource(R.drawable.bg_silver);
                } else if (estrellas == 3 && nivel == 4) {
                    lvl4.setImageResource(R.drawable.lvl4_3);
                    lvl5.setEnabled(true);
                    lvl5.setImageResource(R.drawable.lvl5);
                    lvl4Enabled = false;
                    constraintLayout.setBackgroundResource(R.drawable.bg_silver);
                }//-------------------NIVEL5---------------------
                else if (estrellas == 1 && nivel == 5) {
                    lvl5.setImageResource(R.drawable.lvl5_1);
                } else if (estrellas == 2 && nivel == 5) {
                    lvl5.setImageResource(R.drawable.lvl5_2);
                    lvl6.setEnabled(true);
                    lvl6.setImageResource(R.drawable.lvl6);
                    lvl5Enabled = false;
                } else if (estrellas == 3 && nivel == 5) {
                    lvl5.setImageResource(R.drawable.lvl5_3);
                    lvl6.setEnabled(true);
                    lvl6.setImageResource(R.drawable.lvl6);
                    lvl5Enabled = false;
                }//-------------------NIVEL6---------------------
                else if (estrellas == 1 && nivel == 6) {
                    lvl6.setImageResource(R.drawable.lvl6_1);
                } else if (estrellas == 2 && nivel == 6) {
                    lvl6.setImageResource(R.drawable.lvl6_2);
                } else if (estrellas == 3 && nivel == 6) {
                    lvl6.setImageResource(R.drawable.lvl6_3);
                    lvl7.setEnabled(true);
                    lvl7.setImageResource(R.drawable.lvl7);
                    lvl6Enabled = false;
                    constraintLayout.setBackgroundResource(R.drawable.bg_gold);
                }//-------------------NIVEl7---------------------
                else if (estrellas == 1 && nivel == 7) {
                    lvl7.setImageResource(R.drawable.lvl7_1);
                } else if (estrellas == 2 && nivel == 7) {
                    lvl7.setImageResource(R.drawable.lvl7_2);
                } else if (estrellas == 3 && nivel == 7) {
                    lvl7.setImageResource(R.drawable.lvl7_3);
                    lvl8.setEnabled(true);
                    lvl8.setImageResource(R.drawable.lvl8);
                    lvl7Enabled = false;
                }//-------------------NIVEL8---------------------
                else if (estrellas == 1 && nivel == 8) {
                    lvl8.setImageResource(R.drawable.lvl8_1);
                    lvl8Enabled = false;
                } else if (estrellas == 2 && nivel == 8) {
                    lvl8.setImageResource(R.drawable.lvl8_2);
                    lvl8Enabled = false;
                } else if (estrellas == 3 && nivel == 8) {
                    lvl8.setImageResource(R.drawable.lvl8_3);
                    lvl8Enabled = false;
                    constraintLayout.setBackgroundResource(R.drawable.bg_platinum);
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error al actualizar imagen", Toast.LENGTH_SHORT).show();
        }
    }



    public void loadScore() {
        String username =LoginViewModel.userTitle;
        try {
            int puntos = userDAO.loadScore(username);
            userDAO.registerScoreInRanking(puntos,username);
            score.setText(String.valueOf(puntos));
        } catch (SQLException e) {
            Toast.makeText(this, "Error al actualizar puntos", Toast.LENGTH_SHORT).show();
        }
    }



    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Estás seguro de que quieres borrar todo tu progreso? [Incluido Ranking]");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Usuario hizo clic en "Sí", realiza la acción de reseteo de progreso
                resetProgress();
                intent = new Intent(LevelSelectorViewModel.this, LevelSelectorViewModel.class);
                startActivity(intent);
                healthBar.setImageResource(R.drawable.life_100);
                userDAO.updateHealth(LoginViewModel.userTitle,5);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Usuario hizo clic en "No", cierra el diálogo sin hacer nada
                dialog.dismiss();
            }
        });

        // Mostrar el cuadro de diálogo de confirmación
        builder.create().show();
    }



    public void loadLvl1(View view){
        if(lvl1Enabled==false){
            intent = new Intent(LevelSelectorViewModel.this, InfoMessageViewModel.class);
            startActivity(intent);
        }
        else{
            intent = new Intent(LevelSelectorViewModel.this, Level1.class);
            startActivity(intent);
        }


    }

    public void loadLvl2(View view){
        if(lvl2Enabled==false){
            intent = new Intent(LevelSelectorViewModel.this, InfoMessageViewModel.class);
            startActivity(intent);
        }
        else{
            intent = new Intent(LevelSelectorViewModel.this, Level2.class);
            startActivity(intent);
        }
    }
    public void loadLvl3(View view){
        if(lvl3Enabled==false){
            intent = new Intent(LevelSelectorViewModel.this, InfoMessageViewModel.class);
            startActivity(intent);
        }
        else{
            intent = new Intent(LevelSelectorViewModel.this, Level3.class);
            startActivity(intent);
        }
    }
    public void loadLvl4(View view){
        if(lvl4Enabled==false){
            intent = new Intent(LevelSelectorViewModel.this, InfoMessageViewModel.class);
            startActivity(intent);
        }
        else{
            intent = new Intent(LevelSelectorViewModel.this, Level4.class);
            startActivity(intent);
        }
    }
    public void loadLvl5(View view){
        if(lvl5Enabled==false){
            intent = new Intent(LevelSelectorViewModel.this, InfoMessageViewModel.class);
            startActivity(intent);
        }
        else{
            intent = new Intent(LevelSelectorViewModel.this, Level5.class);
            startActivity(intent);
        }
    }
    public void loadLvl6(View view){
        if(lvl6Enabled==false){
            intent = new Intent(LevelSelectorViewModel.this, InfoMessageViewModel.class);
            startActivity(intent);
        }
        else{
            intent = new Intent(LevelSelectorViewModel.this, Level6.class);
            startActivity(intent);
        }
    }
    public void loadLvl7(View view){
        if(lvl7Enabled==false){
            intent = new Intent(LevelSelectorViewModel.this, InfoMessageViewModel.class);
            startActivity(intent);
        }
        else{
            intent = new Intent(LevelSelectorViewModel.this, Level7.class);
            startActivity(intent);
        }
    }
    public void loadLvl8(View view){
        intent = new Intent(LevelSelectorViewModel.this, Level8.class);
        if(lvl8Enabled==false){
            intent = new Intent(LevelSelectorViewModel.this, InfoMessageViewModel.class);
            startActivity(intent);
        }
        else{
            intent = new Intent(LevelSelectorViewModel.this, Level8.class);
            startActivity(intent);
        }
    }

    public void logOut(View view){
        intent = new Intent(LevelSelectorViewModel.this, LoginViewModel.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.mimenu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.item1) {
            intent = new Intent(LevelSelectorViewModel.this, RankingViewModel.class);
            startActivity(intent);
        } else if(item.getItemId() == R.id.item2) {
            showConfirmationDialog();
        }
        return true;
    }
}