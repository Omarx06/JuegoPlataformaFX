package com.example.juegofxfull.juegofxfull;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;

public class Item {

    double x, y;
    Image sprite;

    public Item(double x, double y) {
        this.x = x;
        this.y = y;

        sprite = new Image(getClass().getResourceAsStream("/assets/images/item.png"));
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, 24, 24);
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(sprite, x, y, 24, 24);
    }
}





