package com.examenia.demo.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PreprocesamientoVista {
    private final Scene escena;
    private final ImageView imgFoto;
    private final Button btnGris;
    private final Button btnHsv;
    private final Button btnNegativa;
    private final Button btnDestacarRojo;
    private final Button btnDestacarVerde;
    private final Button btnDestacarAzul;
    private final Button btnGamma;
    private final Slider sldGamma;
    private final Button btnSepararCapas;
    private final Button btnGuardar;

    public PreprocesamientoVista(Image imagen) {
        Label title = new Label("Preprocesamiento");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        imgFoto = new ImageView(imagen);
        imgFoto.setFitWidth(450);
        imgFoto.setFitHeight(350);
        imgFoto.setPreserveRatio(true);

        StackPane marcoFoto = new StackPane(imgFoto);
        marcoFoto.setPrefSize(450, 350);
        marcoFoto.setStyle("-fx-border-color: #555; -fx-border-width: 2; -fx-background-color: #eee;");

        btnGris = crearBoton("Gris", 85);
        btnHsv = crearBoton("HSV", 85);
        btnNegativa = crearBoton("Negativa", 85);
        HBox filtros = new HBox(8, btnGris, btnHsv, btnNegativa);

        btnDestacarRojo = crearBoton("Rojo", 85);
        btnDestacarVerde = crearBoton("Verde", 85);
        btnDestacarAzul = crearBoton("Azul", 85);
        HBox destacar = new HBox(8, btnDestacarRojo, btnDestacarVerde, btnDestacarAzul);

        // la barra va de 0 a 2 y empieza en 1 porque gamma = 1 deja la imagen igual
        btnGamma = crearBoton("Gamma", 85);
        sldGamma = new Slider(0, 2, 1);
        sldGamma.setShowTickMarks(true);
        sldGamma.setShowTickLabels(true);
        sldGamma.setMajorTickUnit(0.5);
        sldGamma.setPrefWidth(178);
        sldGamma.setDisable(true);
        HBox filaGamma = new HBox(8, btnGamma, sldGamma);
        filaGamma.setAlignment(Pos.CENTER_LEFT);

        Label valorGamma = new Label();
        valorGamma.textProperty().bind(sldGamma.valueProperty().asString("Valor: %.2f"));

        btnSepararCapas = crearBoton("Separar en capas", 271);
        btnGuardar = crearBoton("Guardar", 271);

        VBox panel = new VBox(8,
                crearSeccion("Filtros"), filtros,
                crearSeccion("Destacar color"), destacar,
                crearSeccion("Gamma"), filaGamma, valorGamma,
                crearSeccion("Acciones"), btnSepararCapas, btnGuardar);
        panel.setAlignment(Pos.CENTER_LEFT);

        HBox content = new HBox(30, marcoFoto, panel);
        content.setAlignment(Pos.CENTER);

        BorderPane layout = new BorderPane();
        layout.setTop(title);
        layout.setCenter(content);
        BorderPane.setAlignment(title, Pos.CENTER);
        layout.setPadding(new Insets(20));

        escena = new Scene(layout, 850, 520);
    }

    private Button crearBoton(String texto, double ancho) {
        Button boton = new Button(texto);
        boton.setPrefWidth(ancho);
        return boton;
    }

    private Label crearSeccion(String texto) {
        Label seccion = new Label(texto);
        seccion.setStyle("-fx-font-weight: bold; -fx-text-fill: #2E5A88;");
        seccion.setPadding(new Insets(6, 0, 0, 0));
        return seccion;
    }

    public Scene getEscena() {return escena;}
    public ImageView getImgFoto() {return imgFoto;}
    public Button getBtnGris() {return btnGris;}
    public Button getBtnHsv() {return btnHsv;}
    public Button getBtnNegativa() {return btnNegativa;}
    public Button getBtnDestacarRojo() {return btnDestacarRojo;}
    public Button getBtnDestacarVerde() {return btnDestacarVerde;}
    public Button getBtnDestacarAzul() {return btnDestacarAzul;}
    public Button getBtnGamma() {return btnGamma;}
    public Slider getSldGamma() {return sldGamma;}
    public Button getBtnSepararCapas() {return btnSepararCapas;}
    public Button getBtnGuardar() {return btnGuardar;}
}
