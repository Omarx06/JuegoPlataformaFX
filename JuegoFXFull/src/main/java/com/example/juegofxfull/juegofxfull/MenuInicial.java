package com.example.juegofxfull.juegofxfull;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuInicial {
    private final Stage stage;
    public MenuInicial(Stage stage) { this.stage = stage; }

    public void mostrar() {
        Label title = new Label("Donkey-Style: Caverna");
        title.setStyle("-fx-font-size:22px; -fx-text-fill: white;");

        Button nuevo = new Button("Nuevo Juego");
        Button cargar = new Button("Cargar Partida");
        Button salir = new Button("Salir");

        nuevo.setOnAction(e -> {
            Game g = new Game(800, 600);
            g.iniciar(stage);
        });

        cargar.setOnAction(e -> {
            Game g = new Game(800, 600);
            g.iniciar(stage);
        });

        salir.setOnAction(e -> stage.close());

        VBox root = new VBox(14, title, nuevo, cargar, salir);
        root.setStyle("-fx-background-color: linear-gradient(#0b0f14, #101522); -fx-padding:40;");
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Donkey-Style - Menú");
        stage.show();
    }
}

