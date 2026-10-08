// Vista de la pantalla de inicio de sesión
package com.examenia.demo.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginVista {
    private final Scene escena;
    private final TextField textoUsuario;
    private final PasswordField textoContra;
    private final Button btnEntrar;
    private final Hyperlink registro;

    public LoginVista() {
        Label title = new Label("Inicio de sesión");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold");

        textoUsuario = new TextField();
        textoUsuario.setPromptText("Usuario");
        textoUsuario.setMaxWidth(250);

        textoContra = new PasswordField();
        textoContra.setPromptText("Contraseña");
        textoContra.setMaxWidth(250);

        btnEntrar = new Button("Iniciar Sesión");
        btnEntrar.setPrefWidth(250);

        registro = new Hyperlink("¿No tienes cuenta? Registrate");

        VBox layout = new VBox(15, title, textoUsuario, textoContra,btnEntrar, registro);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        escena = new Scene(layout, 400, 350);
    }

    public Scene getEscena() {return escena;}
    public TextField getTextoUsuario() {return textoUsuario;}
    public PasswordField getTextoContra() {return textoContra;}
    public Button getBtnEntrar() {return btnEntrar;}

    public Hyperlink getRegistro() {
        return registro;
    }
}
