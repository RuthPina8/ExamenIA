package com.examenia.demo.controladores;

import com.examenia.demo.servicios.VisionCliente;
import com.examenia.demo.vistas.Alertas;
import com.examenia.demo.vistas.VistaPrincipal;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PrincipalControlador {

    private final Stage stage;
    private final VistaPrincipal vista;
    private final int idUsuario;
    private byte[] foto;

    private Timeline video;
    private boolean pidiendoFrame = false;
    private byte[] ultimoFrame;

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

        stage.addEventHandler(WindowEvent.WINDOW_HIDDEN, e -> detenerCamara());
    }

    public void mostrar() {
        stage.setScene(vista.getEscena());
        stage.show();
    }

    private void buscarFoto() {
        detenerCamara();

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
        detenerCamara();
        foto = null;
        vista.getImgFoto().setImage(null);
        vista.getPreLoad().setDisable(true);
    }

    private void encenderCamara() {
        if (video != null) {
            return;
        }

        try {
            VisionCliente.encenderCamara();
        } catch (IOException ex) {
            Alertas.error("Cámara", VisionCliente.explicar(ex));
            return;
        }

        foto = null;
        ultimoFrame = null;
        vista.getPreLoad().setDisable(true);

        video = new Timeline(new KeyFrame(Duration.millis(100), e -> pedirFrame()));
        video.setCycleCount(Animation.INDEFINITE);
        video.play();
    }

    private void pedirFrame() {
        if (pidiendoFrame) {
            return;
        }
        pidiendoFrame = true;

        Thread hilo = new Thread(() -> {
            try {
                byte[] frame = VisionCliente.frame();
                Platform.runLater(() -> {
                    pidiendoFrame = false;
                    if (video != null) {
                        ultimoFrame = frame;
                        vista.getImgFoto().setImage(new Image(new ByteArrayInputStream(frame)));
                    }
                });
            } catch (IOException ex) {
                Platform.runLater(() -> pidiendoFrame = false);
            }
        });
        hilo.setDaemon(true);
        hilo.start();
    }

    private void tomarFoto() {
        if (video == null) {
            Alertas.advertencia("Cámara", "Primero enciende la cámara.");
            return;
        }

        detenerCamara();

        if (ultimoFrame == null) {
            Alertas.advertencia("Cámara", "La cámara todavía no había mandado ninguna imagen. Inténtalo de nuevo.");
            return;
        }
        ponerFoto(ultimoFrame);
    }

    private void detenerCamara() {
        if (video == null) {
            return;
        }
        video.stop();
        video = null;

        try {
            VisionCliente.apagarCamara();
        } catch (IOException ignored) {
        }
    }

    private void guardar() {
        if (foto == null) {
            Alertas.advertencia("Guardar", "Primero busca o toma una foto.");
            return;
        }

        try {
            VisionCliente.guardar(foto, idUsuario, "original", null);
            Alertas.informacion("Guardar", "Imagen guardada en la base de datos.");
        } catch (IOException ex) {
            Alertas.error("Guardar", VisionCliente.explicar(ex));
        }
    }

    public void ponerFoto(byte[] bytes) {
        foto = bytes;
        vista.getImgFoto().setImage(new Image(new ByteArrayInputStream(bytes)));
        vista.getPreLoad().setDisable(false);
    }
}