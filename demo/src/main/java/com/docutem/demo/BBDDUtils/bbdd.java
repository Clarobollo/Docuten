package com.docutem.demo.BBDDUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.docutem.demo.EncryptUtils.Encryption;

@Component
public class bbdd {

    @Autowired
    Encryption encryption;

    private static String username;
    private static String password;
    private static String url;

    @Value("${bbdd.username}")
    public void setUsername(String username) {
        bbdd.username = username;
    }

    @Value("${bbdd.password}")
    public void setPassword(String password) {
        bbdd.password = password;
    }

    @Value("${bbdd.url}")
    public void setUrl(String url) {
        bbdd.url = url;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            throw e;
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    private boolean isUserValid(String usernameInput, String passwordInput) throws SQLException {
        Connection connection = getConnection();
        boolean isValid = false;

        String hashedPassword = encryption.hashPassword(passwordInput);

        try {
            String query = "SELECT COUNT(*) FROM users WHERE user = ? AND pass = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usernameInput);
            preparedStatement.setString(2, hashedPassword);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                isValid = true;
            }
        } catch (SQLException e) {
            System.err.println("Error al validar el usuario: " + e.getMessage());
            throw e;
        } finally {
            closeConnection(connection);
        }

        return isValid;
    }

    public void setKeys(String username, String pass, String privateKey, String publicKey) throws SQLException {
        Connection connection = getConnection();

        try {
            if (!isUserValid(username, pass)) {
                throw new SQLException("Usuario o contraseña incorrectos.");
            } else {
                String query = "UPDATE users SET private = ?, public = ? WHERE user = ? AND pass = ?";
                String hashedPassword = encryption.hashPassword(pass);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, privateKey);
                preparedStatement.setString(2, publicKey);
                preparedStatement.setString(3, username);
                preparedStatement.setString(4, hashedPassword);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar las claves: " + e.getMessage());
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

}
