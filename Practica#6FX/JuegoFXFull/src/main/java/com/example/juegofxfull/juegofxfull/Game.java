package com.example.juegofxfull.juegofxfull;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

import java.io.*;
import java.util.*;

public class Game {

    private final int width, height;
    private final Canvas canvas;
    private final GraphicsContext gc;

    private Jugador jugador;
    private Donkey donkey;
    private final List<Plataforma> plataformas = new ArrayList<>();
    private final List<Escalera> escaleras = new ArrayList<>();
    private final List<Barril> barriles = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();

    private final Set<KeyCode> keys = new HashSet<>();
    private AnimationTimer loop;

    private Image background;

    private boolean gameOver = false;
    private int puntaje = 0;

    private boolean mostrarGuardado = false;
    private double guardadoTimer = 0;

    public Game(int width, int height, boolean cargar) {
        this.width = width;
        this.height = height;

        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();

        loadResources();
        initLevel();

        if (cargar) {
            puntaje = cargarProgreso();
        }

        createLoop();
    }


    private void loadResources() {
        try {
            background = new Image(getClass().getResourceAsStream("/assets/images/background.png"));
        } catch (Exception e) {
            System.out.println("Sin fondo, usando color solido");
            background = null;
        }
    }

    private void initLevel() {
        plataformas.clear();
        escaleras.clear();
        barriles.clear();
        items.clear();
        puntaje = 0;

        plataformas.add(new Plataforma(80, 240, 500, 18, Plataforma.Direction.RIGHT));
        plataformas.add(new Plataforma(180, 340, 580, 18, Plataforma.Direction.LEFT));
        plataformas.add(new Plataforma(60, 440, 620, 18, Plataforma.Direction.RIGHT));
        plataformas.add(new Plataforma(200, 515, 580, 18, Plataforma.Direction.LEFT));

        plataformas.add(new Plataforma(0, 560, 800, 60, Plataforma.Direction.RIGHT)); // suelo

        escaleras.add(new Escalera(520, 240, 40, 100));
        escaleras.add(new Escalera(300, 340, 40, 100));
        escaleras.add(new Escalera(500, 440, 40, 75));
        escaleras.add(new Escalera(350, 515, 40, 60));

        jugador = new Jugador(100, 520, 40, 50);
        donkey = new Donkey(100, 170);

        items.add(new Item(350, 200));
        items.add(new Item(700, 300));
        items.add(new Item(150, 400));
        items.add(new Item(600, 480));
    }

    private double spawnTimer = 0;
    private final double spawnInterval = 2.2;

    private void createLoop() {
        loop = new AnimationTimer() {
            private long lastTime = 0;
            @Override
            public void handle(long now) {
                if (lastTime == 0) lastTime = now;
                double delta = (now - lastTime) / 1e9;
                update(delta);
                render();
                lastTime = now;
            }
        };
    }

    public void iniciar(Stage stage) {
        Scene scene = new Scene(new StackPane(canvas));
        setupInput(scene);
        stage.setScene(scene);
        stage.setTitle("Donkey Kong Style");
        stage.show();
        loop.start();
    }

    private void setupInput(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> keys.add(e.getCode()));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> keys.remove(e.getCode()));
    }

    private void update(double dt) {

        if (gameOver) {
            if (keys.contains(KeyCode.R)) {
                reiniciar();
            }
        }



        boolean left  = keys.contains(KeyCode.LEFT)  || keys.contains(KeyCode.A);
        boolean right = keys.contains(KeyCode.RIGHT) || keys.contains(KeyCode.D);
        boolean up    = keys.contains(KeyCode.UP)    || keys.contains(KeyCode.W);
        boolean down  = keys.contains(KeyCode.DOWN)  || keys.contains(KeyCode.S);
        boolean jump  = keys.contains(KeyCode.SPACE);

        if (keys.contains(KeyCode.Q)) guardarProgreso();

        // DETECCIÓN ESCALERA
        boolean enEscalera = false;
        for (Escalera esc : escaleras) {
            if (jugador.getBounds().intersects(esc.getBounds())) {
                enEscalera = true;
                break;
            }
        }

        if (enEscalera && (up || down)) {
            jugador.setEnEscalera(true);
            if (up) jugador.subir();
            else if (down) jugador.bajar();
        } else {
            jugador.setEnEscalera(false);
            if (left) jugador.moverIzquierda();
            if (right) jugador.moverDerecha();
            if (jump && jugador.isEnSuelo()) jugador.saltar();
            jugador.aplicarGravedad();
        }

        // COLISIONES CON PLATAFORMAS
        boolean enSuelo = false;
        for (Plataforma p : plataformas) {
            if (jugador.getBounds().intersects(p.getBounds())) {

                double pjBottom = jugador.y + jugador.height;
                double pTop = p.getY();

                if (pjBottom - jugador.velY <= pTop + 6) {
                    jugador.aterrizar(p);
                    enSuelo = true;
                }
            }
        }
        jugador.setEnSuelo(enSuelo);

        // SPAWN BARRILES
        spawnTimer += dt;
        if (spawnTimer >= spawnInterval) {
            spawnTimer = 0;
            barriles.add(donkey.lanzarBarril());
        }

        // UPDATE BARRILES
        Iterator<Barril> it = barriles.iterator();
        while (it.hasNext()) {
            Barril b = it.next();
            b.update(dt, plataformas);

            if (b.getBounds().intersects(jugador.getBounds())) gameOver = true;
            if (b.x < -200 || b.x > width + 200) it.remove();
        }

        // COLISIONES ITEMS
        Iterator<Item> itItems = items.iterator();
        while (itItems.hasNext()) {
            Item item = itItems.next();
            if (jugador.getBounds().intersects(item.getBounds())) {
                puntaje += 100;
                itItems.remove();
            }
        }
    }

    private void reiniciar() {
        gameOver = false;
        spawnTimer = 0;

        initLevel();
    }

    private void render() {

        if (background != null)
            gc.drawImage(background, 0, 0, width, height);
        else {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, width, height);
        }

        for (Plataforma p : plataformas) p.draw(gc);
        for (Escalera e : escaleras) e.draw(gc);
        for (Item it : items) it.draw(gc);
        donkey.draw(gc);
        for (Barril b : barriles) b.draw(gc);
        jugador.draw(gc);

        // HUD
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(20));
        gc.fillText("Puntaje: " + puntaje, 15, 30);
        gc.fillText("Teclas: Flechas o WASD | Q: Guardar", 15, 55);

        if (mostrarGuardado)
            gc.fillText(" ¡Guardado!", width - 140, 30);

        if (gameOver) {
            gc.setFill(Color.color(0,0,0,0.7));
            gc.fillRect(0,0,width,height);

            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(40));
            gc.fillText("¡GAME OVER!", width/2 - 150, height/2);
            gc.setFont(Font.font(20));
            gc.fillText("Presiona R para reiniciar", width/2 - 140, height/2 + 40);

        }
    }

    private void guardarProgreso() {
        File carpeta = new File("datos");
        if (!carpeta.exists()) carpeta.mkdirs();

        try {
            FileWriter fw = new FileWriter("datos/progreso.txt");
            fw.write(String.valueOf(puntaje));
            fw.close();
            mostrarGuardado = true;
            guardadoTimer = 2;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int cargarProgreso() {
        try {
            File f = new File("datos/progreso.txt");
            if (!f.exists()) return 0;

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            br.close();

            return Integer.parseInt(line);
        } catch (Exception e) {
            return 0;
        }
    }

}










