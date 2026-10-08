package com.examenia.demo;

import com.examenia.demo.controladores.LoginControlador;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sistema de visión artificial");
        new LoginControlador(primaryStage).mostrar();
    }

    public static void main(String [] args) {
        launch(args);
    }
}


/*
 * Okey, lo que hay que entender es:
 * 1.- Se crea un proyecto con FX
 * 2.- Usamos las importaciones, en este caso para solo tener la pantalla se ocupa (Application, Scene, StackPane y Stage)
 * 3.- El main extendera de Application para tener claro con que trabajara
 * 4.- En el public static se asigara un launch(args), este sirve para tener una plantilla vacia que luego con start llenara
 * 5.- El @Override sirve para detectar errores al momento de usar el start (puede o no puede usarse)}
 * 6.- Se usan los constructores de Button, Scene y StacPane
 * 7.- primaryStage se utiliza para la creación de la window como tal
 * 8.- Scene es lo que va en el interior
 *
 * */