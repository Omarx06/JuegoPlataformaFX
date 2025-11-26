package com.example.juegofxfull.juegofxfull;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        MenuInicial menu = new MenuInicial(stage);
        menu.mostrar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}





