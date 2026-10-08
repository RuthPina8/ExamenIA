package com.examenia.demo.controladores;

import com.examenia.demo.vistas.Alertas;
import com.examenia.demo.vistas.VistaPrincipal;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PrincipalControlador {

    private final Stage stage;
    private final VistaPrincipal vista;
    private final int idUsuario;
    private byte[] foto;

    public PrincipalControlador(Stage stage, int idUsuario) {
        this.stage = stage;
        this.idUsuario = idUsuario;
        vista = new VistaPrincipal();

        vista.getSearchPhoto().setOnAction(e -> buscarFoto());
        vista.getClean().setOnAction(e -> limpiar());
        vista.getTurnOn().setOnAction(e -> encenderCamara());
        vista.getPhoto().setOnAction(e -> tomarFoto());
        vista.getSave().setOnAction(e -> guardar());
        vista.getPreLoad().setOnAction(e -> new PreprocesamientoControlador(foto, idUsuario).mostrar());
    }

    public void mostrar() {
        stage.setScene(vista.getEscena());
        stage.show();
    }

    private void buscarFoto() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Buscar foto");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));

        File archivo = chooser.showOpenDialog(stage);
        if (archivo == null) {
            return;
        }

        try {
            ponerFoto(Files.readAllBytes(archivo.toPath()));
        } catch (IOException ex) {
            Alertas.error("Error", "No se pudo abrir la imagen.");
        }
    }

    private void limpiar() {
        foto = null;
        vista.getImgFoto().setImage(null);
        vista.getPreLoad().setDisable(true);
    }

    // TODO API: GET /camara/cuadro (ver docs/API.md)
    private void encenderCamara() {
        Alertas.informacion("Cámara", "Pendiente de conectar con la API de Python.");
    }

    // TODO API: tomar el ultimo cuadro de la camara y mandarlo a ponerFoto()
    private void tomarFoto() {
        Alertas.informacion("Cámara", "Pendiente de conectar con la API de Python.");
    }

    // TODO API: POST /imagenes?usuario_id=idUsuario&tipo=original
    private void guardar() {
        if (foto == null) {
            Alertas.advertencia("Guardar", "Primero busca o toma una foto.");
            return;
        }
        Alertas.informacion("Guardar", "Pendiente de conectar con la API de Python.");
    }

    public void ponerFoto(byte[] bytes) {
        foto = bytes;
        vista.getImgFoto().setImage(new Image(new ByteArrayInputStream(bytes)));
        vista.getPreLoad().setDisable(false);
    }
}
