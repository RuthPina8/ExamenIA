package com.examenia.demo.controladores;

import com.examenia.demo.vistas.CapasVista;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;

public class CapasControlador {

    private final Stage ventana;
    private final CapasVista vista;
    private final byte[] foto;

    public CapasControlador(byte[] foto) {
        this.foto = foto;
        vista = new CapasVista();
        vista.getImgOriginal().setImage(new Image(new ByteArrayInputStream(foto)));

        ventana = new Stage();
        ventana.setTitle("Separación en capas");
        ventana.setScene(vista.getEscena());

        // TODO API: POST /capas/{canal} para rojo, verde, azul, magenta, amarillo y cian
    }

    public void mostrar() {
        ventana.show();
    }
}
