package com.examenia.demo.controladores;

import com.examenia.demo.servicios.UsuarioServicio;
import com.examenia.demo.vistas.Alertas;
import com.examenia.demo.vistas.RegistroVista;
import javafx.stage.Stage;

import java.sql.SQLException;

public class RegistroControlador {

    private final Stage stage;
    private final RegistroVista vista;

    public RegistroControlador(Stage stage) {
        this.stage = stage;
        vista = new RegistroVista();

        vista.getSave().setOnAction(e -> guardar());
        vista.getBack().setOnAction(e -> regresar());
    }

    public void mostrar() {
        stage.setScene(vista.getEscena());
        stage.show();
    }

    private void guardar() {
        String nombre = vista.getNombre().getText().trim();
        String paterno = vista.getApellidoPaterno().getText().trim();
        String materno = vista.getAppellidoMaterno().getText().trim();
        String usuario = vista.getUsuario().getText().trim();
        String contra = vista.getContra().getText();

        if (nombre.isEmpty() || paterno.isEmpty() || materno.isEmpty() || usuario.isEmpty() || contra.isEmpty()) {
            Alertas.advertencia("Campos Vacíos", "¡Advertencia! Llena todos los campos.");
            return;
        }

        try {
            if (UsuarioServicio.existeUsuario(usuario)) {
                Alertas.error("Usuario existente", "El usuario " + usuario + " ya está registrado, prueba con otro.");
                return;
            }

            UsuarioServicio.registrar(nombre, paterno, materno, usuario, contra);
            Alertas.informacion("Registro", "Usuario registrado correctamente.");
            regresar();
        } catch (SQLException ex) {
            Alertas.error("Error", "No se pudo guardar el registro.\n" + ex.getMessage());
        }
    }

    private void regresar() {
        new LoginControlador(stage).mostrar();
    }
}
