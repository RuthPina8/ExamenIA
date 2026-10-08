// Controlador de preprocesamiento: aplica filtros, gamma y guarda la imagen
package com.examenia.demo.controladores;

import com.examenia.demo.servicios.VisionCliente;
import com.examenia.demo.vistas.Alertas;
import com.examenia.demo.vistas.PreprocesamientoVista;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PreprocesamientoControlador {

    private final Stage ventana;
    private final PreprocesamientoVista vista;
    private final int idUsuario;
    private final byte[] original;
    private byte[] foto;
    private String tipo = "original";
    private double gamma = 1;
    private boolean gammaEnCurso = false;

    public PreprocesamientoControlador(byte[] original, int idUsuario) {
        this.original = original;
        this.idUsuario = idUsuario;
        foto = original;
        vista = new PreprocesamientoVista(new Image(new ByteArrayInputStream(original)));

        ventana = new Stage();
        ventana.setTitle("Preprocesamiento");
        ventana.setScene(vista.getEscena());

        vista.getBtnGris().setOnAction(e -> filtro("gris"));
        vista.getBtnHsv().setOnAction(e -> filtro("hsv"));
        vista.getBtnNegativa().setOnAction(e -> filtro("negativa"));
        vista.getBtnDestacarRojo().setOnAction(e -> filtro("rojo"));
        vista.getBtnDestacarVerde().setOnAction(e -> filtro("verde"));
        vista.getBtnDestacarAzul().setOnAction(e -> filtro("azul"));

        vista.getBtnGamma().setOnAction(e -> vista.getSldGamma().setDisable(false));
        vista.getSldGamma().valueProperty().addListener((obs, viejo, nuevo) -> aplicarGamma(nuevo.doubleValue()));

        vista.getBtnSepararCapas().setOnAction(e -> new CapasControlador(original).mostrar());
        vista.getBtnGuardar().setOnAction(e -> guardar());
    }

    public void mostrar() {
        ventana.show();
    }

    private void filtro(String tipo) {
        try {
            actualizarFoto(VisionCliente.filtro(tipo, original), tipo);
        } catch (IOException ex) {
            Alertas.error("Preprocesamiento", VisionCliente.explicar(ex));
        }
    }

    
    private void aplicarGamma(double valor) {
        gamma = valor;
        if (gammaEnCurso) {
            return;
        }
        gammaEnCurso = true;

        Thread hilo = new Thread(() -> {
            try {
                byte[] resultado = VisionCliente.gamma(original, valor);
                Platform.runLater(() -> {
                    actualizarFoto(resultado, "gamma");
                    terminarGamma(valor);
                });
            } catch (IOException ex) {
                Platform.runLater(() -> {
                    gammaEnCurso = false;
                    Alertas.error("Gamma", VisionCliente.explicar(ex));
                });
            }
        });
        hilo.setDaemon(true);
        hilo.start();
    }

    private void terminarGamma(double valorAplicado) {
        gammaEnCurso = false;
        if (gamma != valorAplicado) {
            aplicarGamma(gamma);
        }
    }

    private void guardar() {
        if (tipo.equals("original")) {
            Alertas.advertencia("Guardar", "Primero aplica algún preprocesamiento a la imagen.");
            return;
        }
        try {
            Double valorGamma = tipo.equals("gamma") ? gamma : null;
            VisionCliente.guardar(foto, idUsuario, tipo, valorGamma);
            Alertas.informacion("Guardar", "Imagen guardada en la base de datos.");
        } catch (IOException ex) {
            Alertas.error("Guardar", VisionCliente.explicar(ex));
        }
    }

    public void actualizarFoto(byte[] bytes, String tipo) {
        foto = bytes;
        this.tipo = tipo;
        vista.getImgFoto().setImage(new Image(new ByteArrayInputStream(bytes)));
    }
}