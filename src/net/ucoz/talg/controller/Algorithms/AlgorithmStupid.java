package net.ucoz.talg.controller.Algorithms;

import net.ucoz.talg.model.AdjacencyListItem;
import net.ucoz.talg.model.Graph;
import net.ucoz.talg.model.GraphNode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class AlgorithmStupid implements PathFindingAlgorithm {

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
     * @param path Список вершин в пути
     */
    public void getShortestPathBetweenTwoNodes(int indexNodeStart, int indexNodeFinish, LinkedList<GraphNode> path) {

        // установим все вершины как непосещенные
        for (int i = 0; i < nodes.length; i++) {
            nodes[i].setVisited(false);
        }

        // установим начальную вершину как посещенную и добавим её в путь
        int currentIndex = indexNodeStart;
        GraphNode node = nodes[currentIndex];
        node.setVisited(true);
       // path.add(node);

        Random rand = new Random();

        int i = 0;

        // пока не достигли конечной точки или не сделали более 500 попыток
        while (currentIndex != indexNodeFinish && i++ < 500) {
            // сделать шаг на случайную вершину
            int randIndex = rand.nextInt(adjacencyList[currentIndex].size());

            AdjacencyListItem item = adjacencyList[currentIndex].get(randIndex);

            node = item.getNode();

            // если она еще не посещена, добавить в путь
            if (!node.isVisited()) {

                node.setVisited(true);

                currentIndex = node.getNodeIndex();

                path.add(node);
            }
        }
    }
}
