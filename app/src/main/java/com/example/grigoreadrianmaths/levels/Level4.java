package com.example.grigoreadrianmaths.levels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Level4 extends AppCompatActivity {
    private Button nextQuestion;
    private EditText et_respuesta;
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
        setContentView(R.layout.activity_level4);
        userName = findViewById(R.id.tv_username);
        nextQuestion = findViewById(R.id.bt_nextQuestion);
        totalQuestions = findViewById(R.id.tv_totalPreguntas);
        pregunta = findViewById(R.id.tv_pregunta1);
        healthBar = findViewById(R.id.healthBar);
        score = findViewById(R.id.tv_score);
        et_respuesta = findViewById(R.id.et_respuesta);
        relativeLayout = findViewById(R.id.myRelative_layout);
        String username = LoginViewModel.userTitle;
        userName.setText(username);

        connection = ConexionDB.initDBConnection();
        userDAO = new UserDAO(connection);
        vidas = userDAO.getHealth(LoginViewModel.userTitle);
        numeroDePreguntas = 0;
        aciertos = 0;
        preguntas.clear();

        preguntas.add("¿Cuántos cuadrados puedes llegar a contar?"); //40
        preguntas.add("¿Cuántos animales ves en la foto?"); //11
        preguntas.add("Resuelve el problema: "); //39


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

        if(preguntaActualText.contains("cuadrados")){
            relativeLayout.setBackgroundResource(R.drawable.background7_1);
        }
        else if(preguntaActualText.contains("animales")){
            relativeLayout.setBackgroundResource(R.drawable.background7_2);
        }
        else if(preguntaActualText.contains("problema")){
            relativeLayout.setBackgroundResource(R.drawable.background7_3);

        }

        preguntas.remove(preguntaAleatoria); // Eliminar la pregunta mostrada de la lista
    }

    public void nextQuestionlvl2(View view) {
        if(TextUtils.isEmpty(et_respuesta.getText().toString())){
            Toast.makeText(this, "Debes introducir una respuesta", Toast.LENGTH_SHORT).show();
            return;
        }
        int resultado = Integer.parseInt(et_respuesta.getText().toString());
        try {
            numeroDePreguntas++;
            String numero= String.valueOf(numeroDePreguntas+1);
            if(numeroDePreguntas<3)
                totalQuestions.setText(numero);

            if(numeroDePreguntas==2)
                nextQuestion.setText("Finalizar");


            if (preguntaActualText.contains("cuadrados") && resultado==40) {
                aciertos++;
                if (vidas < 5)
                    vidas++;
                correct();
                updateScoreLvl4();
                loadScore();

            } else if (preguntaActualText.contains("animales") && resultado==11) {
                aciertos++;
                if (vidas < 5)
                    vidas++;
                correct();
                updateScoreLvl4();
                loadScore();

            }  else if (preguntaActualText.contains("problema") && resultado==39) {
                aciertos++;
                if (vidas < 5)
                    vidas++;
                correct();
                updateScoreLvl4();
                loadScore();
            }
            else{
                vidas--;
                incorrect();
            }

            if(numeroDePreguntas==3){
                intent = new Intent(Level4.this, LevelSelectorViewModel.class);
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
                intent = new Intent(Level4.this, GameOverViewModel.class);
                startActivity(intent);
            }

            updateStarsLvl4();
            loadQuestions();
            et_respuesta.setText("");

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

    public void updateScoreLvl4(){
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

    public void updateStarsLvl4(){
        int score = aciertos;
        String username =LoginViewModel.userTitle;
        try {
            if(userDAO.registerStars(username,4,score)){
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