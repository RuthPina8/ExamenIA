// Vista del formulario de registro de usuarios
package com.examenia.demo.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class RegistroVista {

    private final Scene escena;
    private final TextField nombre;
    private final TextField appellidoMaterno;
    private final TextField apellidoPaterno;
    private final TextField usuario;
    private final PasswordField contra;
    private final Button save;
    private final Hyperlink back;

    public RegistroVista() {
        Label title = new Label ("Nuevo Registro");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold");

        nombre = new TextField();
        apellidoPaterno = new TextField();
        appellidoMaterno = new TextField();
        usuario = new TextField();
        contra = new PasswordField();

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(12);
        form.setAlignment(Pos.CENTER);

        form.addRow(0, new Label("Nombre:"), nombre);
        form.addRow(1, new Label("Apellido Paterno:"), apellidoPaterno);
        form.addRow(2, new Label("Apellido Materno:"), appellidoMaterno);
        form.addRow(3, new Label("Nombre de Usuario:"), usuario);
        form.addRow(4, new Label("Contraseña:"), contra);

        save = new Button("Guardar");
        save.setPrefWidth(250);

        back = new Hyperlink("¿Ya tienes cuenta? Inicia sesion");

        VBox layout = new VBox(20, title, form, save, back);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

            escena = new Scene (layout, 450, 450);
    }

    public Scene getEscena() {
        return escena;
    }

    public TextField getNombre() {
        return nombre;
    }

    public TextField getApellidoPaterno() {
        return apellidoPaterno;
    }

    public TextField getAppellidoMaterno() {
        return appellidoMaterno;
    }

    public TextField getUsuario() {
        return usuario;
    }

    public PasswordField getContra() {
        return contra;
    }

    public Button getSave() {
        return save;
    }

    public Hyperlink getBack() {
        return back;
    }
}
