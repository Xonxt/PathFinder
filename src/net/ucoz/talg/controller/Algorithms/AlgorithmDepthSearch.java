package net.ucoz.talg.controller.Algorithms;

import net.ucoz.talg.model.AdjacencyListItem;
import net.ucoz.talg.model.Graph;
import net.ucoz.talg.model.GraphNode;

import java.util.Iterator;
import java.util.LinkedList;

public class AlgorithmDepthSearch implements PathFindingAlgorithm {

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
     * @param path Спсиок вершин в пути (в обратном порядке)
     */
    public void getShortestPathBetweenTwoNodes(int indexNodeStart, int indexNodeFinish, LinkedList<GraphNode> path) {

        // установим все вершины как непосещенный
        for (int i = 0; i < nodes.length; i++) {
            nodes[i].setVisited(false);
        }

        if(recursivePathSearch(indexNodeStart, indexNodeFinish, path)){
            path.add(nodes[indexNodeStart]);
        }
    }

    private boolean recursivePathSearch(int indexNodeStart, int indexNodeFinish, LinkedList<GraphNode> path) {

        // установить текущую вершину как посещеннкю
        nodes[indexNodeStart].setVisited(true);

        // начать итерировать по смежным вершинам
        Iterator<AdjacencyListItem> iterator = adjacencyList[indexNodeStart].iterator();

        // пока есть смежные вершины...
        while(iterator.hasNext()) {

            AdjacencyListItem item = iterator.next();

            // если вершина еще не посещена ...
            if (!item.getNode().isVisited()) {
                // ... если попали в искомую финальную вершину ...
                if (item.getNode().getNodeIndex() == indexNodeFinish) {
                    // ... добавить её в путь
                    path.add(item.getNode());

                    // достигли конца рекурсивных вызовов
                    return true;
                }
                // ... если рекурсивный вызов "обхода" для следующей вершины возвращает true, то мы движемся с конца, найдя кратчайший путь
                if (recursivePathSearch(item.getNode().getNodeIndex(), indexNodeFinish, path)) {
                    // ... если мы уже нашли путь, то добавить текущую вершину в путь
                    path.add(item.getNode());

                    // вернуть true дальше по стеку вызовов
                    return true;
                }
            }
        }
        return false;
    }
}
