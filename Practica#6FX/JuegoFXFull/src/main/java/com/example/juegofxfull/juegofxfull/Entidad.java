package com.example.juegofxfull.juegofxfull;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entidad {
    protected double x, y, width, height;
    protected double velX = 0, velY = 0;

    public Entidad(double x, double y, double w, double h) {
        this.x = x; this.y = y; this.width = w; this.height = h;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);
    }

    public abstract void update(double delta);
    public abstract void draw(javafx.scene.canvas.GraphicsContext gc);
}




