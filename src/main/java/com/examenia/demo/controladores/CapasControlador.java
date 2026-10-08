package com.examenia.demo.controladores;

import com.examenia.demo.servicios.VisionCliente;
import com.examenia.demo.vistas.Alertas;
import com.examenia.demo.vistas.CapasVista;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

public class CapasControlador {

    private final Stage ventana;
    private final CapasVista vista;
    private final byte[] foto;

    public CapasControlador(byte[] foto) {
        this.foto = foto;
        vista = new CapasVista();
        vista.getImgOriginal().setImage(crearImagen(foto));

        ventana = new Stage();
        ventana.setTitle("Separación en capas");
        ventana.setScene(vista.getEscena());

        cargarCapas();
    }

    public void mostrar() {
        ventana.show();
    }

    private void cargarCapas() {
        try {
            Map<String, byte[]> capas = VisionCliente.separarCapas(foto);
            poner(vista.getImgRojo(), capas.get("rojo"));
            poner(vista.getImgVerde(), capas.get("verde"));
            poner(vista.getImgAzul(), capas.get("azul"));
            poner(vista.getImgMagenta(), capas.get("magenta"));
            poner(vista.getImgAmarillo(), capas.get("amarillo"));
            poner(vista.getImgCian(), capas.get("cian"));
        } catch (IOException ex) {
            Alertas.error("Separación en capas", VisionCliente.explicar(ex));
        }
    }

    private void poner(ImageView imageView, byte[] bytes) {
        if (bytes != null) {
            imageView.setImage(crearImagen(bytes));
        }
    }

    private Image crearImagen(byte[] bytes) {
        return new Image(new ByteArrayInputStream(bytes));
    }
}