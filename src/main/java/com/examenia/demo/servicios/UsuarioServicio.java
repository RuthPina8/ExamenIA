package com.examenia.demo.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioServicio {

    public static int login(String usuario, String contra) throws SQLException {
        String sql = "SELECT id FROM usuarios WHERE usuario = ? AND contrasena = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, contra);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1;
    }

    public static boolean existeUsuario(String usuario) throws SQLException {
        String sql = "SELECT id FROM usuarios WHERE usuario = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public static void registrar(String nombre, String paterno, String materno, String usuario, String contra) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, apellido_paterno, apellido_materno, usuario, contrasena) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, paterno);
            ps.setString(3, materno);
            ps.setString(4, usuario);
            ps.setString(5, contra);
            ps.executeUpdate();
        }
    }
}
