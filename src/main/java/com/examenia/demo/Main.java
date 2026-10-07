package com.examenia.demo;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<ActionEvent>{

    Button button;
    Button buton2Stay;

    public static void main(String [] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Title of the window");
        button = new Button();
        button.setText("Click on me");
        button.setOnAction(this);

        buton2Stay = new Button();
        buton2Stay.setText("Hiiiii, im button two");
        buton2Stay.setOnAction(this);

        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        layout.getChildren().add(buton2Stay);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() ==button) {
            System.out.println("HAHHAHAAHH");
        }

        if (event.getSource() ==buton2Stay) {
            System.out.println("Im stayiiiiiiiiiiiiiiiiiing");
        }
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