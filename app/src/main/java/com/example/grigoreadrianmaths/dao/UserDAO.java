package com.example.grigoreadrianmaths.dao;

import android.widget.Toast;

import com.example.grigoreadrianmaths.database.ConexionDB;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public boolean createUser(String name,String surname,String username,String password) throws SQLException {
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

    public boolean login(String username, String password) throws SQLException {
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

    public boolean registerScore(String username, int newScore) throws SQLException{
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

    public int loadScore(String username) throws SQLException{
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

    public boolean registerStars(String username, int level, int newScore) throws SQLException {
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

    public int loadStars(String username, int level){
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



        public boolean resetProgress(String username) throws SQLException {
            if (!initDBConnection()) {
                return false;
            }

            try {
                // 1. Registrar la puntuación en el ranking
                int currentPoints = loadScore(username);
                registerScoreInRanking(username, currentPoints);


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

    private void registerScoreInRanking(String username, int score) {
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
            throw new RuntimeException(e);
        }
    }





}
