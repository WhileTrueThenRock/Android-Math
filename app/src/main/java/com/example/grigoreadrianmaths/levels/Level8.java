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
import com.example.grigoreadrianmaths.viewModels.LevelSelectorViewModel;
import com.example.grigoreadrianmaths.viewModels.LoginViewModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Level8 extends AppCompatActivity {
    private ImageView bt1,bt2;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level8);
        userName = findViewById(R.id.tv_username);
        totalQuestions = findViewById(R.id.tv_totalPreguntas);
        pregunta = findViewById(R.id.tv_pregunta1);
        healthBar = findViewById(R.id.healthBar);
        score = findViewById(R.id.tv_score);
        String username = LoginViewModel.userTitle;
        userName.setText(username);
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);

        connection = ConexionDB.initDBConnection();
        userDAO = new UserDAO(connection);

        numeroDePreguntas = 0;
        aciertos = 0;
        preguntas.clear();

        preguntas.add("¿Cuál de los 2 Trapecios es el Isósceles?");
        preguntas.add("¿Cuál de los 2 es un Trapezoide?");
        preguntas.add("¿Cuál de los 2 es un Romboide?");

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

        if(preguntaActualText.contains("Isósceles")){
            bt1.setImageResource(R.drawable.trapecio);
            bt2.setImageResource(R.drawable.trapecio_isosceles);
        }
        else if(preguntaActualText.contains("Trapezoide")){
            bt1.setImageResource(R.drawable.trapezoide);
            bt2.setImageResource(R.drawable.trapecio);

        }
        else if(preguntaActualText.contains("Romboide")){
            bt1.setImageResource(R.drawable.romboide);
            bt2.setImageResource(R.drawable.rombo);
        }

        preguntas.remove(preguntaAleatoria); // Eliminar la pregunta mostrada de la lista
    }

    public void nextQuestionlvl2(View view) {
        try {
            numeroDePreguntas++;
            String numero= String.valueOf(numeroDePreguntas+1);
            if(numeroDePreguntas<3)
                totalQuestions.setText(numero);


            if(numeroDePreguntas==3){
                intent = new Intent(Level8.this, LevelSelectorViewModel.class);
                startActivity(intent);
            }

            if (preguntaActualText.contains("Isósceles") && view.getId() == R.id.bt2) {
                aciertos++;
                if (vidas < 5)
                    vidas++;
                correct();
                updateScoreLvl8();
                loadScore();
                Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show();

            } else if (preguntaActualText.contains("Trapezoide") && view.getId() == R.id.bt1) {
                aciertos++;
                if (vidas < 5)
                    vidas++;
                correct();
                updateScoreLvl8();
                loadScore();
                Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show();

            }  else if (preguntaActualText.contains("Romboide") && view.getId() == R.id.bt1) {
                aciertos++;
                if (vidas < 5)
                    vidas++;
                correct();
                updateScoreLvl8();
                loadScore();
                Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show();
            }
            else{
                vidas--;
                incorrect();
                Toast.makeText(this, "total vidas: "+vidas, Toast.LENGTH_SHORT).show();
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
            }

            updateStarsLvl8();
            loadQuestions();

        }catch (Exception e){
        }

    }
    public void correct(){
        mediaPlayer = MediaPlayer.create(this,R.raw.correct);
        mediaPlayer.start();
    }

    public void incorrect(){
        mediaPlayer = MediaPlayer.create(this,R.raw.nope);
        mediaPlayer.start();
    }

    public void updateScoreLvl8(){
        int score =1;
        String username = LoginViewModel.userTitle;
        try {
            if(userDAO.registerScore(username,score)){
                Toast.makeText(this, "puntuacion registrada", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Nada que actualizar", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStarsLvl8(){
        int score = aciertos;
        String username =LoginViewModel.userTitle;
        try {
            if(userDAO.registerStars(username,8,score)){
                Toast.makeText(this, "puntuacion registrada", Toast.LENGTH_SHORT).show();
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