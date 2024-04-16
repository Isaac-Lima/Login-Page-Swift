package raven.main;

import javax.swing.*;

public class Home extends JFrame {
    public Home() {
        setTitle("Home");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();

        JMenu menu1 = new JMenu("Menu 1");
        JMenuItem menuItem1 = new JMenuItem("Item 1");
        JMenuItem menuItem2 = new JMenuItem("Item 2");
        menu1.add(menuItem1);
        menu1.add(menuItem2);

        JMenu menu2 = new JMenu("Menu 2");
        JMenuItem menuItem3 = new JMenuItem("Item 3");
        JMenuItem menuItem4 = new JMenuItem("Item 4");
        menu2.add(menuItem3);
        menu2.add(menuItem4);

        menuBar.add(menu1);
        menuBar.add(menu2);

        setJMenuBar(menuBar);
    }
}
