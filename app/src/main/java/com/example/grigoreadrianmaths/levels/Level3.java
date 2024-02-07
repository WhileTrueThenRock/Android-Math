package com.example.grigoreadrianmaths.levels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grigoreadrianmaths.R;
import com.example.grigoreadrianmaths.dao.UserDAO;
import com.example.grigoreadrianmaths.database.ConexionDB;
import com.example.grigoreadrianmaths.viewModels.GameOverViewModel;
import com.example.grigoreadrianmaths.viewModels.LevelSelectorViewModel;
import com.example.grigoreadrianmaths.viewModels.LoginViewModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Level3 extends AppCompatActivity {
    private ImageView bt1,bt2,bt3;
    private TextView userName,totalQuestions, pregunta,score;
    private Intent intent;
    private static int numeroDePreguntas = 0;
    public static int aciertos = 0;
    private ArrayList preguntas = new ArrayList<>();
    private String preguntaActualText;
    private Random random = new Random();
    private int preguntaAleatoria;
    private MediaPlayer mediaPlayer;
    private UserDAO userDAO;
    private Connection connection;
    public static int vidas;
    private ImageView healthBar;
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3);
        userName = findViewById(R.id.tv_username);
        totalQuestions = findViewById(R.id.tv_totalPreguntas);
        pregunta = findViewById(R.id.tv_pregunta1);
        healthBar = findViewById(R.id.healthBar);
        score = findViewById(R.id.tv_score);
        relativeLayout = findViewById(R.id.myRelative_layout);
        String username =LoginViewModel.userTitle;
        userName.setText(username);
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);

        connection = ConexionDB.initDBConnection();
        userDAO = new UserDAO(connection);
        vidas = userDAO.getHealth(LoginViewModel.userTitle);
        numeroDePreguntas = 0;
        aciertos = 0;
        preguntas.clear();

        preguntas.add("¿Cuánto pesará un ladrillo y medio si sabemos que un ladrillo pesa un kilo más medio ladrillo?");
        preguntas.add("¿Cuántos días tardará un caracol en llegar arriba de un palo de 7m si cada día sube 3m, pero en la noche se resbala 2m?");
        preguntas.add("Si un triángulo equilátero, un círculo y un cuadrado tienen la misma área, ¿cuál de ellos tiene un perímetro mayor?");


        if(vidas == 4)
            healthBar.setImageResource(R.drawable.life_75);
        else if(vidas == 3)
            healthBar.setImageResource(R.drawable.life_50);
        else if(vidas == 2)
            healthBar.setImageResource(R.drawable.life_25);
        else if(vidas == 1)
            healthBar.setImageResource(R.drawable.life_5);
        else if(vidas == 0)
            healthBar.setImageResource(R.drawable.life_0);

        loadQuestions();
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

    public void loadQuestions() {
        preguntaAleatoria = random.nextInt(preguntas.size());
        preguntaActualText = (String) preguntas.get(preguntaAleatoria);
        pregunta.setText(preguntaActualText);

        if(preguntaActualText.contains("ladrillo")){
            relativeLayout.setBackgroundResource(R.drawable.background3);
            bt1.setImageResource(R.drawable.kg2);
            bt2.setImageResource(R.drawable.kg3);
            bt3.setImageResource(R.drawable.kg4);
        }
        else if(preguntaActualText.contains("caracol")){
            relativeLayout.setBackgroundResource(R.drawable.background4);
            bt1.setImageResource(R.drawable.days7);
            bt2.setImageResource(R.drawable.days6);
            bt3.setImageResource(R.drawable.days5);

        }
        else if(preguntaActualText.contains("cuadrado")){
            relativeLayout.setBackgroundResource(R.drawable.background2);
            bt1.setImageResource(R.drawable.square);
            bt2.setImageResource(R.drawable.triangle);
            bt3.setImageResource(R.drawable.cicrcle);
        }

        preguntas.remove(preguntaAleatoria); // Eliminar la pregunta mostrada de la lista
    }

    public void nextQuestionlvl2(View view) {
        try {
            numeroDePreguntas++;
            String numero= String.valueOf(numeroDePreguntas+1);
            if(numeroDePreguntas<3)
                totalQuestions.setText(numero);

            if (preguntaActualText.contains("ladrillo") && view.getId() == R.id.bt2) {
                aciertos++;
                if (vidas < 5)
                    vidas++;
                correct();
                updateScoreLvl3();
                loadScore();

            } else if (preguntaActualText.contains("caracol") && view.getId() == R.id.bt1) { //Cambio de 1 a 3!
                aciertos++;
                if (vidas < 5)
                    vidas++;
                correct();
                updateScoreLvl3();
                loadScore();

            }  else if (preguntaActualText.contains("cuadrado") && view.getId() == R.id.bt2) {
                aciertos++;
                if (vidas < 5)
                    vidas++;
                correct();
                updateScoreLvl3();
                loadScore();
            }
            else{
                vidas--;
                incorrect();
            }

            if(numeroDePreguntas==3){
                intent = new Intent(Level3.this, LevelSelectorViewModel.class);
                //intent.putExtra("vidas", vidas);
                userDAO.updateHealth(LoginViewModel.userTitle, vidas);
                startActivity(intent);
            }


            if(vidas >= 5)
                healthBar.setImageResource(R.drawable.life_100);
            else if(vidas == 4)
                healthBar.setImageResource(R.drawable.life_75);
            else if(vidas == 3)
                healthBar.setImageResource(R.drawable.life_50);
            else if(vidas == 2)
                healthBar.setImageResource(R.drawable.life_25);
            else if(vidas == 1)
                healthBar.setImageResource(R.drawable.life_5);
            else if(vidas == 0) {
                healthBar.setImageResource(R.drawable.life_0);
                gameOver();
                resetProgress();
                intent = new Intent(Level3.this, GameOverViewModel.class);
                startActivity(intent);
            }

            updateStarsLvl3();
            loadQuestions();

        }catch (Exception e){
        }

    }

    public void resetProgress(){
        String username =LoginViewModel.userTitle;
        try {
            if(userDAO.resetProgress(username)){
                Toast.makeText(this, "Has perdido", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void gameOver(){
        mediaPlayer = MediaPlayer.create(this,R.raw.game_over);
        mediaPlayer.start();
    }

    public void correct(){
        mediaPlayer = MediaPlayer.create(this,R.raw.correct);
        mediaPlayer.start();
    }

    public void incorrect(){
        mediaPlayer = MediaPlayer.create(this,R.raw.nope);
        mediaPlayer.start();
    }

    public void updateScoreLvl3(){
        int score =1;
        String username = LoginViewModel.userTitle;
        try {
            if(userDAO.registerScore(username,score)){
            }
            else{
                Toast.makeText(this, "Nada que actualizar", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStarsLvl3(){
        int score = aciertos;
        String username =LoginViewModel.userTitle;
        try {
            if(userDAO.registerStars(username,3,score)){
            }
            else{
                Toast.makeText(this, "Nada que actualizar", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
}