package com.example.juegofxfull.juegofxfull;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.geometry.Rectangle2D;

public class Plataforma {

    public enum Direction { LEFT, RIGHT }

    double x, y, w, h;
    Direction dir;

    public Plataforma(double x, double y, double w, double h, Direction dir) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.dir = dir;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, w, h);
    }

    public Direction getDirection() { return dir; }

    public double getY() { return y; }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.DARKRED);
        gc.fillRect(x, y, w, h);
    }
}







