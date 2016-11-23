package net.ucoz.talg.controller.Algorithms;

import net.ucoz.talg.model.AdjacencyListItem;
import net.ucoz.talg.model.Graph;
import net.ucoz.talg.model.GraphNode;
import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class AlgorithmBetter implements PathFindingAlgorithm {
    private Graph graph;
    private GraphNode[] nodes;
    private LinkedList<AdjacencyListItem>[] adjacencyList;

    @Override
    public LinkedList<GraphNode> getPath(Graph mapGraph, int startingPoint, int endPoint) {
        this.graph = mapGraph;
        this.nodes = this.graph.getNodes();
        this.adjacencyList = this.graph.getAdjacencyList();

        LinkedList<GraphNode> path = new LinkedList<>();

        getShortestPathBetweenTwoNodes(startingPoint, endPoint, path);

        return path;
    }

    /**
     * Найти кратчайший путь между указанными вершинами
     * @param indexNodeStart Начальная вершина
     * @param indexNodeFinish Конечная вершина
     */
    public void getShortestPathBetweenTwoNodes(int indexNodeStart, int indexNodeFinish, LinkedList<GraphNode> path) {

        // установим все вершины как непосещенные
        for (int i = 0; i < nodes.length; i++) {
            nodes[i].setVisited(false);
        }

        Random rand = new Random();

        path.add(nodes[indexNodeStart]);
        nodes[indexNodeStart].setVisited(true);

        int step = 0;

        int currentNodeIndex = indexNodeStart;

        while (currentNodeIndex != indexNodeFinish && step++ < 100) {

            double dist = getDistance(nodes[currentNodeIndex], nodes[indexNodeFinish]);
            int index = currentNodeIndex;

            Iterator<AdjacencyListItem> iterator = adjacencyList[currentNodeIndex].iterator();

            while(iterator.hasNext()) {
                AdjacencyListItem item = iterator.next();

                double newDist = getDistance(item.getNode(), nodes[indexNodeFinish]);
                if (newDist < dist) {
                    dist = newDist;
                    index = item.getNode().getNodeIndex();
                }
            }

            if (currentNodeIndex != index) {
                currentNodeIndex = index;
                nodes[currentNodeIndex].setVisited(true);
                path.addFirst(nodes[currentNodeIndex]);

            }
            else {
                int randIndx = rand.nextInt(adjacencyList[currentNodeIndex].size());

                while(adjacencyList[currentNodeIndex].get(randIndx).getNode().isVisited() && step++ < 100)
                    randIndx = rand.nextInt(adjacencyList[currentNodeIndex].size());

                currentNodeIndex = adjacencyList[currentNodeIndex].get(randIndx).getNode().getNodeIndex();
                nodes[currentNodeIndex].setVisited(true);

                path.addFirst(nodes[currentNodeIndex]);

            }

        }

    }

    private double getDistance(GraphNode A, GraphNode B) {

        return Math.sqrt( Math.pow(A.getCellX() - B.getCellX(), 2) + Math.pow(A.getCellY() - B.getCellY(), 2) );

    }
}
