package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by GL on 2017-03-27.
 */
public class ResidentInterface extends JFrame {

    private final int WIDTH = 800;
    private final int HEIGHT = 650;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu searchMenu = new JMenu("Search");
    private JMenu optionsMenu = new JMenu("Options");
    private JMenuItem residenceMenu = new JMenuItem("Search Residences");
    private JMenuItem roomTypeMenu = new JMenuItem("Search Room Types");
    private JMenuItem roomTypeAdvanceMenu = new JMenuItem("Room Type Advance");
    private JMenuItem changeUserMenu = new JMenuItem("Change User");
    private JMenuItem exitMenu = new JMenuItem("Exit");

    public ResidentInterface() {
        super("Residence Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        final JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        searchMenu.setMargin(new Insets(0, 20, 0, 20));
        searchMenu.add(residenceMenu);
        searchMenu.add(roomTypeMenu);
        searchMenu.add(roomTypeAdvanceMenu);
        optionsMenu.setMargin(new Insets(0, 20, 0, 20));
        optionsMenu.add(changeUserMenu);
        optionsMenu.add(exitMenu);
        menuBar.add(searchMenu);
        menuBar.add(optionsMenu);
        setJMenuBar(menuBar);

        residenceMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(new ResidenceSearch(WIDTH, HEIGHT));
                mainPanel.validate();
                mainPanel.repaint();
            }
        });

        roomTypeMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(new RoomTypeSearch(WIDTH, HEIGHT));
                mainPanel.validate();
                mainPanel.repaint();
            }
        });

        roomTypeAdvanceMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(new RoomTypeAdvanceSearch(WIDTH, HEIGHT));
                mainPanel.validate();
                mainPanel.repaint();
            }
        });

        changeUserMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StartInterface().setVisible(true);
                dispose();
            }
        });

        exitMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }
}