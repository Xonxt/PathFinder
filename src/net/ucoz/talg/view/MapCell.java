package net.ucoz.talg.view;

import java.awt.*;

public class MapCell {
    private Color cellColor;

    final private Color grassColor = new Color(60, 160, 80);
    final private Color obstacleColor = new Color(70, 40, 10);

    private int x;
    private int y;
    private int width;
    private int height;

    private int index;

    private boolean traversable;

    public Color getColor() {
        return this.cellColor;
    }

    public void setColor(Color color) {
        this.cellColor = color;
    }

    public MapCell(int index, boolean traversable) {
        this.index = index;
        this.traversable = traversable;

        if (traversable) {
            cellColor = grassColor;
        }
        else {
            cellColor = obstacleColor;
        }

        setCoordinates(0, 0);
        setSize(0, 0);
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getIndex() {
        return index;
    }

    public int getX() {

        return x;
    }

    public int getY() {
        return y;
    }

    public Color getCellColor() {
        return cellColor;
    }

    public void setCellColor(Color cellColor) {
        this.cellColor = cellColor;
    }

    public boolean isTraversable() {
        return traversable;
    }

    public void setTraversable(boolean traversable) {
        this.traversable = traversable;
    }
}
