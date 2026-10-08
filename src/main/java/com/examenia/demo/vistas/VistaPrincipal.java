// Vista principal: visor de foto y botones de cámara/foto
package com.examenia.demo.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class VistaPrincipal {
    private final Scene escena;
    private final ImageView imgFoto;
    private final Button searchPhoto;
    private final Button clean;
    private final Button turnOn;
    private final Button photo;
    private final Button save;
    private final Button preLoad;

    public VistaPrincipal () {
        Label title = new Label("Sistema de Visión Artificial");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        imgFoto = new ImageView();
        imgFoto.setFitWidth(400);
        imgFoto.setFitHeight(300);
        imgFoto.setPreserveRatio(true);

        StackPane marcoFoto = new StackPane(imgFoto);
        marcoFoto.setPrefSize(400, 300);
        marcoFoto.setStyle("-fx-border-color: #555; -fx-border-width: 2; -fx-background-color: #eee;");

        searchPhoto = crearBoton("Buscar foto");
        clean = crearBoton("Limpiar");
        turnOn = crearBoton("Encender Camara");
        photo = crearBoton("Tomar foto");
        save = crearBoton("Guardar");
        preLoad = crearBoton("Preprocesamiento");
        preLoad.setDisable(true);

        VBox buttons = new VBox(12, searchPhoto, clean, turnOn, photo,save, preLoad);
        buttons.setAlignment(Pos.CENTER);

        HBox content = new HBox(30, marcoFoto, buttons);
        content.setAlignment(Pos.CENTER);

        BorderPane layout = new BorderPane();
        layout.setTop(title);
        layout.setCenter(content);
        BorderPane.setAlignment(title, Pos.CENTER);
        layout.setPadding(new Insets(20));

        escena = new Scene(layout, 750, 450);
    }

    private Button crearBoton(String texto) {
        Button boton = new Button(texto);
        boton.setPrefWidth(180);
        return boton;
    }

    public Scene getEscena() {
        return escena;
    }

    public ImageView getImgFoto() {
        return imgFoto;
    }

    public Button getSearchPhoto() {
        return searchPhoto;
    }

    public Button getClean() {
        return clean;
    }
    public Button getTurnOn() {
        return turnOn;
    }

    public Button getPhoto() {
        return photo;
    }

    public Button getSave() {
        return save;
    }

    public Button getPreLoad() {
        return preLoad;
    }
}
