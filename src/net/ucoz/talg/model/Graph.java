package net.ucoz.talg.model;

import sun.awt.image.ImageWatched;

import java.util.Iterator;
import java.util.LinkedList;

public class Graph {

    // Матрица смежности для графа
    private double[][] adjacencyMatrix;

    // Массив списков смежности для графа
    private LinkedList<AdjacencyListItem>[] adjacencyList;

    // Массив вершин графа
    private GraphNode[] nodes;

    public void setCellXY(int index, int cellX, int cellY) {
        nodes[index].setCellXY(cellX, cellY);
    }

    /**
     * Создать граф из указанного числа вершин, но без ребер
     * @param numberOfVertices Число вершин в графе
     */
    public Graph(int numberOfVertices) {
        // создать заданное количество вершин
        createNodes(numberOfVertices);

        // создать пустую матрицу смежности и список смежности
        adjacencyMatrix = new double[numberOfVertices][];
        adjacencyList = new LinkedList[numberOfVertices];

        for (int i = 0; i < numberOfVertices; i++) {
            adjacencyMatrix[i] = new double[numberOfVertices];
            adjacencyList[i] = new LinkedList<>();
        }
    }

    /**
     * Создать новый граф из матрицы смежности
     * @param adjacencyMatrix Матрица смежности в виде массива типа double размером N x N
     */
    public Graph(double[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;

        createNodes(adjacencyMatrix.length);

        // конвертировать данную матрицу в смежности в список смежности
        convertMatrixToList();
    }

    /**
     * Создать новый граф из списка смежности
     * @param adjacencyList Ссылка на массмв списков смежности
     */
    public Graph(LinkedList<AdjacencyListItem>[] adjacencyList) {

        this.adjacencyList = adjacencyList;

        createNodes(adjacencyList.length);

        // конвертировать данный список смежности в матрицу смежности
        convertListToMatrix();
    }

    /**
     * Создаёт набор вершин в зависимости от данной матрицы или списка смежности
     * @param numberOfNodes Количество вершин
     */
    private void createNodes(int numberOfNodes) {
        this.nodes = new GraphNode[numberOfNodes];

        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new GraphNode(i);
        }
    }

    /**
     * Добавляет в граф новое ребро
     * @param nodeIndexA Исходящая вершина
     * @param nodeIndexB Входящая вершина
     * @param weight Вес ребра
     */
    public void addEdge(int nodeIndexA, int nodeIndexB, double weight) {
        if(nodeIndexA >= nodes.length || nodeIndexB >= nodes.length)
            return;

        if (adjacencyMatrix[nodeIndexA][nodeIndexB] == 0) {
            adjacencyMatrix[nodeIndexA][nodeIndexB] = weight;

            adjacencyList[nodeIndexA].add(new AdjacencyListItem(nodes[nodeIndexB], weight));
        }
    }

    /**
     * Добавляет в граф новое ребро
     * @param nodeIndexA Исходящая вершина
     * @param nodeIndexB Входящая вершина
     */
    public void addEdge(int nodeIndexA, int nodeIndexB) {
        addEdge(nodeIndexA, nodeIndexB, 1);
    }

    /**
     * Добавляет в граф новое ребро
     * @param nodeIndexA Исходящая вершина
     * @param nodeIndexB Входящая вершина
     * @param weight Вес ребра
     * @param twoDirection Если true, это ребро двунаправленное
     */
    public void addEdge(int nodeIndexA, int nodeIndexB, double weight, boolean twoDirection) {
        if (twoDirection) {
            addEdge(nodeIndexA, nodeIndexB, weight);
            addEdge(nodeIndexB, nodeIndexA, weight);
        }
        else {
            addEdge(nodeIndexA, nodeIndexB, weight);
        }
    }

    /**
     * Удаляет ребро из графа
     * @param nodeIndexA Исходящая вершина
     * @param nodeIndexB Входящая вершина
     */
    public void removeEdge(int nodeIndexA, int nodeIndexB) {
        if(nodeIndexA >= nodes.length || nodeIndexB >= nodes.length)
            return;

        adjacencyMatrix[nodeIndexA][nodeIndexB] = 0;

        Iterator<AdjacencyListItem> iterator = adjacencyList[nodeIndexA].iterator();

        while(iterator.hasNext()) {
            AdjacencyListItem item = iterator.next();

            if(item.getNode().getNodeIndex() == nodeIndexB)
                adjacencyList[nodeIndexA].remove();
        }

    }

    /**
     * Удаляет ребро из графа
     * @param nodeIndexA Исходящая вершина
     * @param nodeIndexB Входящая вершина
     * @param twoDirection Если true, это ребро двунаправленное
     */
    public void removeEdge(int nodeIndexA, int nodeIndexB, boolean twoDirection) {
        if (twoDirection) {
            removeEdge(nodeIndexA, nodeIndexB);
            removeEdge(nodeIndexB, nodeIndexA);
        }
        else {
            removeEdge(nodeIndexA, nodeIndexB);
        }
    }

    /**
     * Сгенерировать список смежности из матрицы смежности
     */
    private void convertMatrixToList() {

        if (nodes.length == 0 || nodes == null) {
            return;
        }
        adjacencyList = new LinkedList[adjacencyMatrix.length];

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            adjacencyList[i] = new LinkedList<>();
            for (int j = 0; j < adjacencyMatrix.length; j++) {

                if (adjacencyMatrix[i][j] != 0) {
                    adjacencyList[i].add(new AdjacencyListItem(nodes[j], adjacencyMatrix[i][j]));
                }
            }
        }
    }

    /**
     * Сгенерировать матрицу смежности из списка смежности
     */
    private void convertListToMatrix() {

        if (nodes.length == 0 || nodes == null) {
            return;
        }

        adjacencyMatrix = new double[adjacencyList.length][];

        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyMatrix[i] = new double[adjacencyList.length];

            Iterator<AdjacencyListItem> iterator = adjacencyList[i].iterator();
            while(iterator.hasNext()) {
                AdjacencyListItem item = iterator.next();

                adjacencyMatrix[i][item.getNode().getNodeIndex()] = item.getWeight();
            }
        }
    }

    public void printAdjacencyList() {
        // Проходим по массиву списков смежности
        for (int j = 0; j < adjacencyList.length; j++) {
            System.out.print("node " + (j) + ": ");

            // Устанавливаем "итератор" в самое начало списка j
            Iterator<AdjacencyListItem> iterator = adjacencyList[j].iterator();

            // Пока в текущем списке еще есть элементы
            while(iterator.hasNext()) {
                // Получить следующий элемент в списке
                AdjacencyListItem item = iterator.next();
                System.out.print("(" + (item.getNode().getNodeIndex()) + " : " + item.getWeight() + ") -> ");
            }

            System.out.println("");
        }
    }

    /**
     * Распечатать матрицу смежности
     */
    public void printAdjacencyMatrix() {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                System.out.print(adjacencyMatrix[i][j] + "\t");
            }
            System.out.println();
        }
    }


    public double[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public LinkedList<AdjacencyListItem>[] getAdjacencyList() {
        return adjacencyList;
    }

    public GraphNode[] getNodes() {
        return nodes;
    }
}
