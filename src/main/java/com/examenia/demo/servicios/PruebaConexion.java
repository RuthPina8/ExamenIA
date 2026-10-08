// Programa de prueba para verificar la conexión a la base de datos
package com.examenia.demo.servicios;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {

    public static void main(String[] args) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            System.out.println("Conexión exitosa a la base: " + conexion.getCatalog());
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }
}