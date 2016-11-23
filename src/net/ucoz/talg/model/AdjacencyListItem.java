package net.ucoz.talg.model;

public class AdjacencyListItem {
    private GraphNode node;
    private double weight = 0;

    //private AdjacencyListItem next;

    public AdjacencyListItem(GraphNode node, double weight) {
        this.node = node;
        this.weight = weight;
    }

    public AdjacencyListItem(GraphNode node) {
        this(node, 0.0);
    }

    public GraphNode getNode() {
        return node;
    }

    public void setNode(GraphNode node) {
        this.node = node;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

   /* public AdjacencyListItem getNext() {
        return next;
    }

    public void setNext(AdjacencyListItem next) {
        this.next = next;
    }*/
}
