package com.example.juegofxfull.juegofxfull;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.geometry.Rectangle2D;

public class Escalera {
    double x, y, w, h;

    public Escalera(double x, double y, double w, double h) {
        this.x = x; this.y = y;
        this.w = w; this.h = h;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, w, h);
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BURLYWOOD);
        gc.fillRect(x, y, w, h);
    }
}







