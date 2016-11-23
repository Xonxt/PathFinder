package net.ucoz.talg.model;

/**
 * Created by Никита on 20.11.2016.
 */
public class GraphEdge {
    // Индекс исходящей вершины
    private int nodeIndexOut;

    // Индекс входящей вершины
    private int nodeIndexIn;

    // Вес ребра
    private double edgeWeight;

    /**
     * Создать новое ребро
     * @param nodeIndexOut Индекс исходящей вершины
     * @param nodeIndexIn Индекс входящей вершины
     * @param edgeWeight Вес ребра
     */
    public GraphEdge(int nodeIndexOut, int nodeIndexIn, double edgeWeight) {
        this.nodeIndexOut = nodeIndexOut;
        this.nodeIndexIn = nodeIndexIn;
        this.edgeWeight = edgeWeight;
    }

    public int getNodeIndexOut() {
        return nodeIndexOut;
    }

    public int getNodeIndexIn() {
        return nodeIndexIn;
    }

    public double getEdgeWeight() {
        return edgeWeight;
    }
}
