package com.example.grigoreadrianmaths.levels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
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

public class Level6 extends AppCompatActivity {
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
    public static int vidas = 5;
    private ImageView healthBar;
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level6);
        userName = findViewById(R.id.tv_username);
        totalQuestions = findViewById(R.id.tv_totalPreguntas);
        pregunta = findViewById(R.id.tv_pregunta1);
        healthBar = findViewById(R.id.healthBar);
        score = findViewById(R.id.tv_score);
        relativeLayout = findViewById(R.id.myRelative_layout);
        String username = LoginViewModel.userTitle;
        userName.setText(username);
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);

        connection = ConexionDB.initDBConnection();
        userDAO = new UserDAO(connection);

        numeroDePreguntas = 0;
        aciertos = 0;
        preguntas.clear();

        preguntas.add("Completa la siguiente secuencia numérica");
        preguntas.add("Indica cuál es el número tapado por el coche");
        preguntas.add("Indica el número primo");


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
    public void loadQuestions() {
        preguntaAleatoria = random.nextInt(preguntas.size());
        preguntaActualText = (String) preguntas.get(preguntaAleatoria);
        pregunta.setText(preguntaActualText);

        if(preguntaActualText.contains("secuencia")){
            relativeLayout.setBackgroundResource(R.drawable.background9_1);
            bt1.setImageResource(R.drawable.parking1);
            bt2.setImageResource(R.drawable.parking2);
            bt3.setImageResource(R.drawable.parking3);
        }
        else if(preguntaActualText.contains("coche")){
            relativeLayout.setBackgroundResource(R.drawable.background9_2);
            bt1.setImageResource(R.drawable.parking5);
            bt2.setImageResource(R.drawable.parking4);
            bt3.setImageResource(R.drawable.parking6);

        }
        else if(preguntaActualText.contains("primo")){
            relativeLayout.setBackgroundResource(R.drawable.background9_3);
            bt1.setImageResource(R.drawable.primo2);
            bt2.setImageResource(R.drawable.primo3);
            bt3.setImageResource(R.drawable.primo1);
        }

        preguntas.remove(preguntaAleatoria); // Eliminar la pregunta mostrada de la lista
    }

    public void nextQuestionlvl2(View view) {
        try {
            numeroDePreguntas++;
            String numero= String.valueOf(numeroDePreguntas+1);
            if(numeroDePreguntas<3)
                totalQuestions.setText(numero);


            if (preguntaActualText.contains("secuencia") && view.getId() == R.id.bt1) {
                aciertos++;
                if (vidas < 5)
                    vidas++;
                correct();
                updateScoreLvl6();
                loadScore();

            } else if (preguntaActualText.contains("coche") && view.getId() == R.id.bt2) {
                aciertos++;
                if (vidas < 5)
                    vidas++;
                correct();
                updateScoreLvl6();
                loadScore();

            }  else if (preguntaActualText.contains("primo") && view.getId() == R.id.bt3) {
                aciertos++;
                if (vidas < 5)
                    vidas++;
                correct();
                updateScoreLvl6();
                loadScore();
            }
            else{
                vidas--;
                incorrect();
            }

            if(numeroDePreguntas==3){
                intent = new Intent(Level6.this, LevelSelectorViewModel.class);
                intent.putExtra("vidas", vidas);
                //intent.putExtra("nivel", 1);
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
                intent = new Intent(Level6.this, GameOverViewModel.class);
                startActivity(intent);
            }

            updateStarsLvl6();
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

    public void updateScoreLvl6(){
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

    public void updateStarsLvl6(){
        int score = aciertos;
        String username =LoginViewModel.userTitle;
        try {
            if(userDAO.registerStars(username,6,score)){
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