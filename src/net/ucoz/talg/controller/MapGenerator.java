package net.ucoz.talg.controller;

import net.ucoz.talg.model.Graph;
import net.ucoz.talg.view.MapCell;

import java.util.Random;

public class MapGenerator {

    private int[][] mapLayout;

    private MapCell[][] mapCells;

    private Graph mapGraph;

    public MapGenerator() {

    }

    public MapCell[][] getMapCells() {
        return mapCells;
    }

    public Graph getGraph() {
        return mapGraph;
    }

    private void generateCellMap(int[][] layout) {

        this.mapCells = new MapCell[layout.length][layout[0].length];

        int index = 0;
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[0].length; j++) {
                this.mapCells[i][j] = new MapCell(index++, layout[i][j] > 0);
            }
        }
    }

    private void generateGraph(MapCell[][] layout) {

        this.mapGraph = new Graph(layout.length * layout[0].length);

        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {

                mapGraph.setCellXY(layout[i][j].getIndex(), j, i);

                if (!layout[i][j].isTraversable())
                    continue;

                if (i - 1 >= 0 && layout[i-1][j].isTraversable())
                    mapGraph.addEdge(layout[i][j].getIndex(), layout[i-1][j].getIndex());

                if (i + 1 < layout.length && layout[i+1][j].isTraversable())
                    mapGraph.addEdge(layout[i][j].getIndex(), layout[i+1][j].getIndex());

                if (j - 1 >= 0 && layout[i][j-1].isTraversable())
                    mapGraph.addEdge(layout[i][j].getIndex(), layout[i][j-1].getIndex());

                if (j + 1 < layout[i].length && layout[i][j+1].isTraversable())
                    mapGraph.addEdge(layout[i][j].getIndex(), layout[i][j+1].getIndex());
            }
        }

    }

    public void generateLayout(int width, int height) {

        this.mapLayout = new int[height][width];

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                this.mapLayout[i][j] = 1;
            }
        }

        Random rand = new Random();

        int numberOfObstacles = 0;

        while (numberOfObstacles == 0)
            numberOfObstacles = rand.nextInt(width / 3);

        int step = width / (numberOfObstacles+1);

        for (int i = step; i < width - 1; i += width / numberOfObstacles) {
            int obstacleHeight = 0;

            while (obstacleHeight < (height / 3))
                obstacleHeight = rand.nextInt(height-1);

            int obstaclePosition = rand.nextInt(height);

            int blockHeight = 0;

            boolean decreased;
            while(obstacleHeight > 0) {
                decreased = false;
                if (obstaclePosition - blockHeight >= 0) {
                    if (this.mapLayout[obstaclePosition - blockHeight][i] == 1) {
                        this.mapLayout[obstaclePosition - blockHeight][i] = 0;
                        obstacleHeight--;
                        decreased = true;
                    }
                }
                if (obstaclePosition + blockHeight < height) {
                    if (this.mapLayout[obstaclePosition + blockHeight][i] == 1) {
                        this.mapLayout[obstaclePosition + blockHeight][i] = 0;
                        obstacleHeight--;
                        decreased = true;
                    }
                }
                if (decreased)
                    blockHeight++;
            }
        }

        generateCellMap(this.mapLayout);
        generateGraph(this.mapCells);
    }
}
