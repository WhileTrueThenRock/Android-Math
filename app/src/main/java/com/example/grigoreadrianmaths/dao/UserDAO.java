package com.example.grigoreadrianmaths.dao;

import android.widget.Toast;

import com.example.grigoreadrianmaths.database.ConexionDB;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserDAO {
    private Connection connection;
    private ConexionDB conexionDB= new ConexionDB();

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    private boolean initDBConnection() {
        connection = conexionDB.initDBConnection();
        if (connection != null) {
            return true;
        }
        return false;
    }
        private boolean closeDBConnection() {
            try {
                connection.close();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    public boolean createDatabase(){ //Unique DB
        boolean createOK = false;
        if(!initDBConnection()){
           return createOK;
        }



            try {
                String query = "CREATE DATABASE maths"; //modificar la URL database
                PreparedStatement  statement = connection.prepareStatement(query);
                statement.execute();
                createOK = true;

            }catch (SQLException e){
                System.err.println("Error de sql al crear la base de datos: " + e.getMessage());
            }


        return createOK;
    }

    public boolean createTables() throws SQLException{ //tables
        boolean createOk = false;
        if(!initDBConnection()){
            return createOk;
        }
        createDatabase();

        try {
            String checkQuery = "SELECT 1 FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'users'";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
                ResultSet resultSet = checkStatement.executeQuery();
                if (!resultSet.next()) {
                    String query = "CREATE TABLE Users (\n" +
                            "                       id SERIAL PRIMARY KEY,\n" +
                            "                       nombre VARCHAR(50),\n" +
                            "                       apellidos VARCHAR(50),\n" +
                            "                       usuario VARCHAR(50) UNIQUE,\n" +
                            "                       password VARCHAR(50),\n" +
                            "                       points INTEGER DEFAULT 0,\n" +
                            "                       stars_lvl1 INTEGER DEFAULT 0,\n" +
                            "                       stars_lvl2 INTEGER DEFAULT 0,\n" +
                            "                       stars_lvl3 INTEGER DEFAULT 0,\n" +
                            "                       stars_lvl4 INTEGER DEFAULT 0,\n" +
                            "                       stars_lvl5 INTEGER DEFAULT 0,\n" +
                            "                       stars_lvl6 INTEGER DEFAULT 0,\n" +
                            "                       stars_lvl7 INTEGER DEFAULT 0,\n" +
                            "                       stars_lvl8 INTEGER DEFAULT 0,\n" +
                            "                       rankingpoints INTEGER DEFAULT 0,\n" +
                            "                       health INTEGER DEFAULT 5\n" +
                            ");";
                    PreparedStatement statement = connection.prepareStatement(query);
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0)
                        createOk = true;
                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            closeDBConnection();
        }

        return createOk;
    }


    public boolean createUser(String name,String surname,String username,String password) throws SQLException { //RegisterVM
        boolean createOk = false;
        if(!initDBConnection()){
            return createOk;
        }
        try {
            String query = "INSERT INTO users (nombre, apellidos, usuario, password) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, username);
            statement.setString(4, password);

            int rowsAffected = statement.executeUpdate();
            if(rowsAffected>0)
                createOk = true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeDBConnection();
        }

        return createOk;
    }

    public boolean login(String username, String password) throws SQLException { //LoginVM
        boolean login = false;

        if(!initDBConnection()){
            return login;
        }
        try {
            String query = "SELECT * FROM users WHERE usuario = ? AND password = ?";
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Si hay algún resultado, el login es exitoso
            if (resultSet.next()) {
                login = true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }   finally {
            closeDBConnection();
        }
        return login;
    }

    public boolean registerScore(String username, int newScore) throws SQLException{ //After each LVL I register the score
        boolean updateOk = false;

        if (!initDBConnection()) {
            return updateOk;
        }

        try {
            // Consulta de actualización
            String updateQuery = "UPDATE users SET points = points + ? WHERE usuario = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, newScore);
            updateStatement.setString(2, username);

            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected > 0) {
                // La actualización fue exitosa
                updateOk = true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeDBConnection();
        }

        return updateOk;
    }

    public int loadScore(String username) throws SQLException{ //Update after completion
        int score = 0;

        if (!initDBConnection()) {
            return score;
        }

        try {
            String query = "SELECT points FROM users WHERE usuario = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                score = resultSet.getInt("points");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeDBConnection();
        }

        return score;
    }



    public Map<String, Integer> getScoresAndUsernamesFromDatabase() throws SQLException { //RankingVM
        Map<String, Integer> scoresMap = new LinkedHashMap<>(); //Con HashMap nao funçona
        //Devuelve scoresMap en un orden aleatorio y al usar LinkedHashMap me mantiene el orden del query

        try {
            String query = "SELECT usuario, rankingpoints FROM users ORDER BY rankingpoints DESC LIMIT 4";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("usuario");
                int score = resultSet.getInt("rankingpoints");
                scoresMap.put(username, score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja la excepción adecuadamente en un entorno de producción
        }

        return scoresMap;
    }





    public boolean registerStars(String username, int level, int newScore) throws SQLException { //Register after each lvl completion
        boolean updateOk = false;

        if (!initDBConnection()) {
            return updateOk;
        }

        try {
            // Consulta de actualización para stars_lvl
            String updateQuery = "UPDATE users SET stars_lvl" + level + " = ? WHERE usuario = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, newScore);
            updateStatement.setString(2, username);

            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected > 0) {
                // La actualización fue exitosa
                updateOk = true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeDBConnection();
        }

        return updateOk;
    }

    public int loadStars(String username, int level){ //update after completion
        int stars = 0;

        if (!initDBConnection()) {
            return stars;
        }

        try {
            String query = "SELECT stars_lvl" + level + " FROM users WHERE usuario = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                stars = resultSet.getInt("stars_lvl" + level);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeDBConnection();
        }

        return stars;
    }


        public boolean resetProgress(String username) throws SQLException { //Start over
            if (!initDBConnection()) {
                return false;
            }

            try {
                // 1. Registrar la puntuación en el ranking
                int currentPoints = loadScore(username);
                registerScoreInRanking( currentPoints,username);


                String query = "UPDATE users SET points = 0, stars_lvl1 = 0, stars_lvl2 = 0, stars_lvl3 = 0, " +
                        "stars_lvl4 = 0, stars_lvl5 = 0, stars_lvl6 = 0, stars_lvl7 = 0, stars_lvl8 = 0 " +
                        "WHERE usuario = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, username);

                int rowsAffected = statement.executeUpdate();

                return rowsAffected > 0;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                closeDBConnection();
            }
        }

    public void registerScoreInRanking( int score,String username) { //Register points in DB
        if (!initDBConnection()) {
            return;
        }

        try {
            String query = "UPDATE users SET rankingPoints = ? WHERE usuario = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, score);
            statement.setString(2, username);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error"+e.getMessage());
        }
    }

    public boolean updateHealth(String username, int health) { //Update health after each Q
        boolean updateok = false;
        connection = conexionDB.initDBConnection();
        if (!initDBConnection()) {
            return updateok;
        }
        try {
            String query = "UPDATE users SET health = ? WHERE usuario = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, health);
            statement.setString(2, username);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            conexionDB.closeDBConnection(connection);
        }
    }

    public int getHealth(String username) {
        try {
            String query = "SELECT health FROM users WHERE usuario = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("health");
            } else {
                // Si no se encuentra el usuario, puedes devolver un valor predeterminado
                return 100; // o cualquier otro valor predeterminado
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 100; // o cualquier otro valor predeterminado
        }
    }

    public ArrayList<String> getUserNamesFromDatabase() throws SQLException {
        ArrayList<String> lista = new ArrayList<>();

        try {
            String query = "SELECT nombre FROM users ORDER BY points DESC LIMIT 4";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nombreUsuario = resultSet.getString("nombre");
                lista.add(nombreUsuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja la excepción adecuadamente en un entorno de producción
        }

        return lista;
    }



}
