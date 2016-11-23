package net.ucoz.talg.controller;

import net.ucoz.talg.controller.Algorithms.PathFindingAlgorithm;
import net.ucoz.talg.model.GraphNode;
import net.ucoz.talg.view.GameWindow;
import net.ucoz.talg.view.MapCell;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Никита on 20.11.2016.
 */
public class MainController {
    private MapGenerator generator = new MapGenerator();

    private GameWindow gameWindow;

    // Алгоритм для нахождения пути от начальной точки до конечной
    private PathFindingAlgorithm algorithm;

    public MainController() {
        generator = new MapGenerator();

        generator.generateLayout(15, 10);

        gameWindow = new GameWindow(this, "Path Finder Game");

        gameWindow.setGenerator(generator);

        gameWindow.setGameMap(generator.getMapCells());

        gameWindow.setVisible(true);

    }

    public MainController(PathFindingAlgorithm algorithm) {
        this();

        this.algorithm = algorithm;
    }


    public LinkedList<MapCell> findPath(int startingPoint, int endPoint) {

        LinkedList<GraphNode> path = algorithm.getPath(generator.getGraph(), startingPoint, endPoint);

        LinkedList<MapCell> movePath = new LinkedList<>();

        Iterator<GraphNode> it = path.iterator();

        MapCell[][] map = generator.getMapCells();

        while(it.hasNext()) {

            int nodeIndex = it.next().getNodeIndex();

            int i = nodeIndex / map[0].length;
            int j = nodeIndex % map[0].length;

            movePath.add(map[i][j]);
        }

        return movePath;
    }
}
