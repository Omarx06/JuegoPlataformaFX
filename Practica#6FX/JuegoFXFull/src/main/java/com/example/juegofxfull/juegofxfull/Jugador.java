package com.example.juegofxfull.juegofxfull;

import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class Jugador {

    double x, y;
    double width, height;

    double velX = 0;
    double velY = 0;

    private final double speed = 120;
    private final double gravity = 450;
    private final double jumpPower = -260;

    private boolean enSuelo = false;
    private boolean enEscalera = false;

    private Image sprite;

    public Jugador(double x, double y, double w, double h) {
        this.x = x; this.y = y;
        this.width = w; this.height = h;

        sprite = new Image(getClass().getResourceAsStream("/assets/images/player.png"));
    }

    public void moverIzquierda() {
        x -= speed * 0.016;
    }

    public void moverDerecha() {
        x += speed * 0.016;
    }

    public void subir() {
        y -= speed * 0.016;
    }

    public void bajar() {
        y += speed * 0.016;
    }

    public void saltar() {
        velY = jumpPower;
    }

    public void aplicarGravedad() {
        velY += gravity * 0.016;
        y += velY * 0.016;
    }

    public void aterrizar(Plataforma p) {
        y = p.getY() - height;
        velY = 0;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);
    }

    public boolean isEnSuelo() { return enSuelo; }
    public void setEnSuelo(boolean b) { enSuelo = b; }

    public void setEnEscalera(boolean e) { enEscalera = e; }

    public void draw(GraphicsContext gc) {
        gc.drawImage(sprite, x, y, width, height);
    }
}







