package com.example.juegofxfull.juegofxfull;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;

import java.util.List;

public class Barril {

    public double x, y;
    public double velX = 80;
    public double velY = 0;

    private final double gravity = 500;

    private Image sprite;

    public Barril(double x, double y) {
        this.x = x;
        this.y = y;

        sprite = new Image(getClass().getResourceAsStream("/assets/images/barril.png"));
    }

    public void update(double dt, List<Plataforma> plataformas) {

        y += velY * dt;
        velY += gravity * dt;

        boolean onPlatform = false;

        for (Plataforma p : plataformas) {

            Rectangle2D pb = p.getBounds();
            Rectangle2D bb = getBounds();

            if (bb.intersects(pb)) {

                double bottom = y + 20;
                if (bottom - velY * dt <= p.getY() + 4) {
                    y = p.getY() - 20;

                    velY = 0;
                    onPlatform = true;

                    velX = (p.dir == Plataforma.Direction.RIGHT) ? 80 : -80;
                }
            }
        }

        if (onPlatform) {
            x += velX * dt;
        }
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, 20, 20);
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(sprite, x, y, 24, 24);
    }
}







