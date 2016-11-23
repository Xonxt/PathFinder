package net.ucoz.talg.controller.Algorithms;

import net.ucoz.talg.model.Graph;
import net.ucoz.talg.model.GraphNode;

import java.util.LinkedList;

public interface PathFindingAlgorithm {

    /**
     * Возвращает путь от начально вершины до конечной.
     * @param mapGraph Ссылка на граф, лежащий в основе игровой карты.
     * @param startingPoint Начальная точка
     * @param endPoint Конечная точка
     * @return Список вершин, представляющих собой путь между вершинами.
     */
    LinkedList<GraphNode> getPath(Graph mapGraph, int startingPoint, int endPoint);

}
