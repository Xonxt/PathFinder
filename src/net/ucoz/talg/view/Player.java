package net.ucoz.talg.view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Никита on 20.11.2016.
 */
public class Player {

    private double x;
    private double y;

    private int width;
    private int height;

    private boolean visible;
    private boolean moving;

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public MapCell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(MapCell currentCell) {
        this.currentCell = currentCell;
    }

    private MapCell currentCell;

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    BufferedImage image;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Player() {
        try {
            image = ImageIO.read(new File("invader.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.width = 1;
        this.height = 1;

        visible = false;
        moving = false;

    }

    public Player(double x, double y) {
        this();

        this.x = x;
        this.y = y;
    }

    public void drawPlayer(Graphics2D g2) {
        int x = (int)getX() - width / 2;
        int y = (int)getY() - height / 2;

        g2.drawImage(image, x, y, width, height, null);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

