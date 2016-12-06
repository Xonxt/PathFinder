package net.ucoz.talg.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;

public class GameMap extends JComponent {

    private MapCell[][] cellMap;

    private LinkedList<MapCell> movementPath;

    private Player player;

    private GameWindow parent;

    private double movementSpeed;

    private boolean isMoving = false;

    public GameMap(GameWindow parent) {
        this.parent = parent;

        movementPath = new LinkedList<>();

        this.setPreferredSize(new Dimension(600, 300));

        movementSpeed = 1;

        player = new Player();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (isMoving)
                    isMoving = false;
                else {

                    int endPoint = -1;
                    int startPoint = -1;

                    for (int i = 0; i < cellMap.length; i++) {
                        for (int j = 0; j < cellMap[i].length; j++) {
                            MapCell cell = cellMap[i][j];

                            int x = cell.getX() - cell.getWidth() / 2;
                            int y = cell.getY() - cell.getHeight() / 2;

                            Rectangle rect = new Rectangle(x, y, cell.getWidth(), cell.getHeight());

                            if (rect.contains(e.getX(), e.getY())) {
                                endPoint = cell.getIndex();
                            }
                        }

                        if (startPoint >= 0 && endPoint >= 0) break;
                    }
                    startPoint = player.getCurrentCell().getIndex();

                    isMoving = true;

                    movePlayer(parent.findPath(startPoint, endPoint));
                }
            }
        });
    }

    public void movePlayer(LinkedList<MapCell> path) {

        this.movementPath = path;

        new Thread(() -> {
            player.setMoving(true);

            Iterator<MapCell> iterator = movementPath.iterator();

            while(iterator.hasNext() && isMoving) {

                MapCell currentCell = player.getCurrentCell();
                MapCell targetCell  = iterator.next();

                int i1 = currentCell.getIndex() / cellMap[0].length;
                int i2 = targetCell.getIndex() / cellMap[0].length;
                int j1 = currentCell.getIndex() % cellMap[0].length;
                int j2 = targetCell.getIndex() % cellMap[0].length;

                if (Math.abs(i1-i2) > 1 || Math.abs(j1-j2) > 1)
                    break;

                if (currentCell.getIndex() == targetCell.getIndex())
                    continue;

                double
                        tx = targetCell.getX() - currentCell.getX(),
                        ty = targetCell.getY() - currentCell.getY(),
                        dist = Math.sqrt(tx*tx+ty*ty);

                double velX = (tx/dist) * movementSpeed;
                double velY = (ty/dist) * movementSpeed;

                while(dist > 1) {

                    try {
                        Thread.sleep(2);
                    }
                    catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    player.setX(player.getX() + velX);
                    player.setY(player.getY() + velY);

                    dist = Math.sqrt(Math.pow(player.getX() - targetCell.getX(), 2) + Math.pow(player.getY() - targetCell.getY(), 2));

                    repaint();
                }

                player.setCurrentCell(targetCell);
            }
            this.movementPath.clear();
            player.setMoving(false);
            isMoving = false;
        }).start();

    }

    public void setCellMap(MapCell[][] cellMap) {
        this.cellMap = cellMap;

        player.setCurrentCell(cellMap[0][0]);

        isMoving = false;

        player.setMoving(false);

        player.setVisible(true);
    }

    private void drawPlayer(Graphics2D g2) {

        if (player.isVisible()) {
            player.drawPlayer(g2);
        }
        else {
            MapCell cell = cellMap[0][0];
            player.setXY(cell.getX(), cell.getY());

            player.setVisible(true);
        }
    }

    private void drawCells(Graphics2D g2) {
        int width  = getSize().width;
        int height = getSize().height;

        if (cellMap != null) {

            int sizeY = height / cellMap.length;
            int sizeX = width / cellMap[0].length;

            for (int i = 0; i < cellMap.length; i++) {
                for (int j = 0; j < cellMap[i].length; j++) {

                    int x = j * sizeX;
                    int y = i * sizeY;

                    cellMap[i][j].setCoordinates(x + sizeX / 2, y + sizeY / 2);
                    cellMap[i][j].setSize(sizeX, sizeY);

                    g2.setPaint(cellMap[i][j].getColor());
                    g2.fill(new Rectangle2D.Double(x, y, sizeX, sizeY));

                    g2.setPaint(Color.DARK_GRAY);
                    g2.draw(new Rectangle2D.Double(x, y, sizeX, sizeY));

                }
            }
        }

    }

    private void drawPath(Graphics2D g2) {

        if( movementPath.size() > 0 ) {

            Iterator<MapCell> it = movementPath.iterator();

            GeneralPath polyline =
                    new GeneralPath(GeneralPath.WIND_EVEN_ODD, movementPath.size());

            int i = 0;
            while(it.hasNext()) {
                MapCell mCell = it.next();
                if (i++ == 0) {
                    polyline.moveTo(mCell.getX(), mCell.getY());
                }
                else {
                    polyline.lineTo(mCell.getX(), mCell.getY());
                }
            }

            g2.setPaint(Color.LIGHT_GRAY);
            g2.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));

            g2.draw(polyline);

        }

    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawCells(g2);

        drawPath(g2);

        if (!player.isMoving()) {
            MapCell cell = player.getCurrentCell();
            player.setXY(cell.getX(), cell.getY());
            player.setSize(cell.getWidth(), cell.getHeight());
        }

        drawPlayer(g2);

    }


}
