package com.examenia.demo.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CapasVista {
    private final Scene escena;
    private final ImageView imgOriginal;
    private final ImageView imgRojo;
    private final ImageView imgVerde;
    private final ImageView imgAzul;
    private final ImageView imgMagenta;
    private final ImageView imgAmarillo;
    private final ImageView imgCian;

    public CapasVista() {
        Label title = new Label("Separación en capas");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        imgOriginal = crearImagen();
        imgRojo = crearImagen();
        imgVerde = crearImagen();
        imgAzul = crearImagen();
        imgMagenta = crearImagen();
        imgAmarillo = crearImagen();
        imgCian = crearImagen();

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);

        grid.add(crearCelda("Imagen original", imgOriginal), 0, 0);
        grid.add(crearCelda("Canal rojo", imgRojo), 1, 0);
        grid.add(crearCelda("Canal verde", imgVerde), 2, 0);
        grid.add(crearCelda("Canal azul", imgAzul), 0, 1);
        grid.add(crearCelda("Canal magenta", imgMagenta), 1, 1);
        grid.add(crearCelda("Canal amarillo", imgAmarillo), 2, 1);
        grid.add(crearCelda("Canal cian", imgCian), 0, 2);

        VBox layout = new VBox(15, title, grid);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        escena = new Scene(layout, 680, 650);
    }

    private ImageView crearImagen() {
        ImageView imagen = new ImageView();
        imagen.setFitWidth(180);
        imagen.setFitHeight(140);
        imagen.setPreserveRatio(true);
        return imagen;
    }

    private VBox crearCelda(String texto, ImageView imagen) {
        StackPane marco = new StackPane(imagen);
        marco.setPrefSize(180, 140);
        marco.setStyle("-fx-border-color: #555; -fx-background-color: #eee;");

        VBox celda = new VBox(5, new Label(texto), marco);
        celda.setAlignment(Pos.CENTER);
        return celda;
    }

    public Scene getEscena() {return escena;}
    public ImageView getImgOriginal() {return imgOriginal;}
    public ImageView getImgRojo() {return imgRojo;}
    public ImageView getImgVerde() {return imgVerde;}
    public ImageView getImgAzul() {return imgAzul;}
    public ImageView getImgMagenta() {return imgMagenta;}
    public ImageView getImgAmarillo() {return imgAmarillo;}
    public ImageView getImgCian() {return imgCian;}
}
