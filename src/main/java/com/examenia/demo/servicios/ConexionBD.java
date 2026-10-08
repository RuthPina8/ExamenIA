// Conexión JDBC a PostgreSQL usando los datos del .env
package com.examenia.demo.servicios;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String URL = "jdbc:postgresql://" + dotenv.get("DB_HOST") + ":" + dotenv.get("DB_PORT") + "/" + dotenv.get("DB_NAME");
    private static final String USUARIO = dotenv.get("DB_USER");
    private static final String CONTRA = dotenv.get("DB_PASSWORD");

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRA);
    }
}
