package com.example.grigoreadrianmaths.levels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grigoreadrianmaths.R;
import com.example.grigoreadrianmaths.dao.UserDAO;
import com.example.grigoreadrianmaths.database.ConexionDB;
import com.example.grigoreadrianmaths.viewModels.GameOverViewModel;
import com.example.grigoreadrianmaths.viewModels.LevelSelectorViewModel;
import com.example.grigoreadrianmaths.viewModels.LoginViewModel;
import com.example.grigoreadrianmaths.viewModels.RankingViewModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

    public class Level1 extends AppCompatActivity {
    private RadioButton r1,r2,r3;
    private Button nextQuestion;
    private TextView userName,totalQuestions, pregunta,score;
    private Intent intent;
    private static int numeroDePreguntas = 0;
    public static int aciertos = 0;
    public static int vidas ;
    private ImageView healthBar;
    private ArrayList preguntas = new ArrayList<>();
    private ArrayList respuestasGranja = new ArrayList<>();
    private ArrayList respuestasFiguras = new ArrayList<>();
    private ArrayList respuestasEdad = new ArrayList<>();
    private Random random = new Random();
    private int preguntaAleatoria;
    private String preguntaActualText;
    private MediaPlayer mediaPlayer;
    private UserDAO userDAO;
    private Connection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);
        userName = findViewById(R.id.tv_username);
        nextQuestion = findViewById(R.id.bt_nextQuestion);
        totalQuestions = findViewById(R.id.tv_totalPreguntas);
        pregunta = findViewById(R.id.tv_pregunta1);
        score = findViewById(R.id.tv_score);
        r1 = findViewById(R.id.respuesta1);
        r2 = findViewById(R.id.respuesta2);
        r3 = findViewById(R.id.respuesta3);
        healthBar = findViewById(R.id.healthBar);
        String username =LoginViewModel.userTitle;
        userName.setText(username);
        connection = ConexionDB.initDBConnection();
        userDAO = new UserDAO(connection);

        vidas = userDAO.getHealth(LoginViewModel.userTitle);
        numeroDePreguntas = 0;
        aciertos = 0;
        preguntas.clear();
        respuestasGranja.clear();
        respuestasFiguras.clear();
        respuestasEdad.clear();
        preguntas.add("Dispones de 5 conejos, 40 cerdos y 20 caballos en una granja. Si deseas llamar caballos a los cerdos, ¿cuál es el número total de caballos que tienes?");
        preguntas.add("¿Cuál es una figura geométrica que no tiene 4 ni 5 lados, sino la mitad de 6?");
        preguntas.add("Si Pedro es dos veces más viejo que Ana, y la suma de sus edades es 33, ¿cuántos años tiene cada uno?");

        respuestasGranja.add("60 caballos");
        respuestasGranja.add("20 caballos"); //correcto
        respuestasGranja.add("40 caballos");

        respuestasFiguras.add("Cuadrado");
        respuestasFiguras.add("Hexágono");
        respuestasFiguras.add("Triángulo"); //correcto

        respuestasEdad.add("Ana tiene 11 y Pedro 22"); //correcto
        respuestasEdad.add("Pedro tiene 11 y Ana 22");
        respuestasEdad.add("Ana tiene 21 y Pedro 11");

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
            if(preguntaActualText.contains("granja")){
                String radio1 = (String) respuestasGranja.get(0);
                String radio2 = (String) respuestasGranja.get(1);
                String radio3 = (String) respuestasGranja.get(2);
                r1.setText(radio1);
                r2.setText(radio2);
                r3.setText(radio3);

            }
            else if(preguntaActualText.contains("sino la mitad de 6?")){
                String radio1 = (String) respuestasFiguras.get(0);
                String radio2 = (String) respuestasFiguras.get(1);
                String radio3 = (String) respuestasFiguras.get(2);
                r1.setText(radio1);
                r2.setText(radio2);
                r3.setText(radio3);

            }
            else if(preguntaActualText.contains("edades")){
                String radio1 = (String) respuestasEdad.get(0);
                String radio2 = (String) respuestasEdad.get(1);
                String radio3 = (String) respuestasEdad.get(2);
                r1.setText(radio1);
                r2.setText(radio2);
                r3.setText(radio3);

            }

            preguntas.remove(preguntaAleatoria); // Eliminar la pregunta mostrada de la lista

    }


    public void nextQuestion(View view) {
        if(!r1.isChecked() && !r2.isChecked() && !r3.isChecked()){
            Toast.makeText(this, "Selecciona al menos una opcion", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            numeroDePreguntas++;
            String numero= String.valueOf(numeroDePreguntas+1);
            if(numeroDePreguntas<3)
                totalQuestions.setText(numero);


            if(numeroDePreguntas==2){
                nextQuestion.setText("Finalizar");

            }



        if (preguntaActualText.contains("granja") && r2.isChecked()) {
            aciertos++;
            if (vidas < 5)
                vidas++;
            correct();
            updateScoreLvl1();
            loadScore();
        } else if (preguntaActualText.contains("sino la mitad de 6?") && r3.isChecked()) {
            aciertos++;
            if (vidas < 5)
                vidas++;
            correct();
            updateScoreLvl1();
            loadScore();
        }  else if (preguntaActualText.contains("edades") && r1.isChecked()) {
            aciertos++;
            if (vidas < 5)
                vidas++;
            correct();
            updateScoreLvl1();
            loadScore();
        }
        else{
            vidas--;
            incorrect();
        }
            if(numeroDePreguntas==3){
                intent = new Intent(Level1.this, LevelSelectorViewModel.class);
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
            else if(vidas == 1) {
                healthBar.setImageResource(R.drawable.life_5);
            }else if(vidas == 0){
                healthBar.setImageResource(R.drawable.life_0);
                gameOver();
                resetProgress();
                intent = new Intent(Level1.this, GameOverViewModel.class);
                startActivity(intent);
            }




        updateStarsLvl1();
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

    public void updateScoreLvl1(){
        String username =LoginViewModel.userTitle;
        int score =1;

        try {
            if(userDAO.registerScore(username,score)){
            }
            else{
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStarsLvl1(){
        int score = aciertos;
        String username =LoginViewModel.userTitle;
        try {
            if(userDAO.registerStars(username,1,score)){
            }
            else{
                Toast.makeText(this, "No se ha podido actualizar", Toast.LENGTH_SHORT).show();
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