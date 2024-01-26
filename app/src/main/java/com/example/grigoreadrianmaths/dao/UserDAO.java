package com.example.grigoreadrianmaths.dao;

import com.example.grigoreadrianmaths.database.ConexionDB;

import java.sql.Connection;
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


}
