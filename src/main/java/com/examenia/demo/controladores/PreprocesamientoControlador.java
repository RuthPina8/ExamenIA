package com.examenia.demo.controladores;

import com.examenia.demo.vistas.Alertas;
import com.examenia.demo.vistas.PreprocesamientoVista;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;

public class PreprocesamientoControlador {

    private final Stage ventana;
    private final PreprocesamientoVista vista;
    private final int idUsuario;
    private final byte[] original;
    private byte[] foto;
    private String tipo = "original";
    private double gamma = 1;

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

    // TODO API: POST /filtros/{tipo} o /filtros/destacar/{color} con la foto original
    private void filtro(String tipo) {
        Alertas.informacion("Preprocesamiento", "El filtro " + tipo + " está pendiente de conectar con la API.");
    }

    // TODO API: POST /filtros/gamma?valor=
    private void aplicarGamma(double valor) {
        gamma = valor;
    }

    // TODO API: POST /imagenes?usuario_id=idUsuario&tipo=tipo (y valor_gamma si es gamma)
    private void guardar() {
        if (tipo.equals("original")) {
            Alertas.advertencia("Guardar", "Primero aplica algún preprocesamiento a la imagen.");
            return;
        }
        Alertas.informacion("Guardar", "Pendiente de conectar con la API de Python.");
    }

    // Se llama cuando la API regresa la imagen ya procesada
    public void actualizarFoto(byte[] bytes, String tipo) {
        foto = bytes;
        this.tipo = tipo;
        vista.getImgFoto().setImage(new Image(new ByteArrayInputStream(bytes)));
    }
}
