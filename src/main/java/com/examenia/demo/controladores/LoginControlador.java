// Controlador del login: valida usuario y abre la ventana principal
package com.examenia.demo.controladores;

import com.examenia.demo.servicios.UsuarioServicio;
import com.examenia.demo.vistas.Alertas;
import com.examenia.demo.vistas.LoginVista;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LoginControlador {
    private final Stage stage;
    private final LoginVista vista;

    public LoginControlador(Stage stage) {
        this.stage = stage;
        vista = new LoginVista();

        vista.getBtnEntrar().setOnAction(e -> login());
        vista.getRegistro().setOnAction(e -> new RegistroControlador(stage).mostrar());
    }

    public void mostrar() {
        stage.setScene(vista.getEscena());
        stage.show();
    }

    private void login() {
        String usuario = vista.getTextoUsuario().getText().trim();
        String contra = vista.getTextoContra().getText();

        if (usuario.isEmpty() || contra.isEmpty()) {
            Alertas.advertencia("Campos Vacíos", "¡Advertencia! Falta escribir el usuario o la contraseña.");
            return;
        }

        try {
            int id = UsuarioServicio.login(usuario, contra);

            if (id == -1) {
                Alertas.error("Error de Autenticación", "Usuario o contraseña incorrectos. Inténtelo de nuevo.");
                return;
            }

            Alertas.informacion("Acceso Permitido", "¡Bienvenido al sistema, " + usuario + "!");
            new PrincipalControlador(stage, id).mostrar();
        } catch (SQLException ex) {
            Alertas.error("Error", "No se pudo conectar con la base de datos.\n" + ex.getMessage());
        }
    }
}
