package net.ucoz.talg.view;

import net.ucoz.talg.controller.Main;
import net.ucoz.talg.controller.MainController;
import net.ucoz.talg.controller.MapGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;

public class GameWindow extends JFrame {
    private GameMap gameMap;

    private MapGenerator generator;

    private MainController controller;

    private BigButton buttonGenerate;

    public GameWindow(MainController controller, String title) throws HeadlessException {
        super(title);

        this.controller = controller;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        this.add(setButtonPanel(), BorderLayout.NORTH);

        gameMap = new GameMap(this);

        this.add(gameMap, BorderLayout.CENTER);

        this.pack();

        //this.setVisible(true);
    }

    public LinkedList<MapCell> findPath(int startingPoint, int endPoint) {
        return controller.findPath(startingPoint, endPoint);
    }

    public void setGenerator(MapGenerator generator) {
        this.generator = generator;
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    private JPanel setButtonPanel() {

        JPanel buttonPanel = new JPanel(true);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        buttonGenerate = new BigButton("Новая карта");

        buttonGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random rand = new Random();

                int width = java.util.concurrent.ThreadLocalRandom.current().nextInt(10, 20);
                int height = width * 2 / 3;

                generator.generateLayout(width, height);
                setGameMap(generator.getMapCells());
                repaint();
            }
        });

        buttonPanel.add(buttonGenerate);

        return buttonPanel;
    }

    public void setGameMap(MapCell[][] mapCells) {
        this.gameMap.setCellMap(mapCells);
    }

}
