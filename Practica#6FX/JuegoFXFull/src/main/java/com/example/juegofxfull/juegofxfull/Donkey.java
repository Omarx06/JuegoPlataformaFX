package com.example.juegofxfull.juegofxfull;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Donkey {

    double x, y;
    private Image sprite;

    public Donkey(double x, double y) {
        this.x = x;
        this.y = y;

        sprite = new Image(getClass().getResourceAsStream("/assets/images/donkey.png"));
    }

    public Barril lanzarBarril() {
        return new Barril(x + 40, y + 40);
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(sprite, x, y, 60, 60);
    }
}







