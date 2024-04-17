package raven.main;

import raven.login.Login;
import raven.login.SignUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame {
    public Home() {
        // Set the title and size of the frame
        setTitle("Home");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create a menu with dropdown items
        JMenu menu1 = new JMenu("Acesso");
        JMenuItem menuItem1 = new JMenuItem("Cadastro");
        JMenuItem menuItem2 = new JMenuItem("Login");

        // Add action listeners to the menu items
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Sign Up page when "Cadastro" is clicked
                SignUp signUp = new SignUp();
                setContentPane(signUp);
                revalidate();
            }
        });

        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Login page when "Login" is clicked
                Login login = new Login();
                setContentPane(login);
                revalidate();
            }
        });

        menu1.add(menuItem1);
        menu1.add(menuItem2);

        // Create a button "About"
        JButton aboutButton = new JButton("Sobre");
        aboutButton.setBackground(UIManager.getColor("Menu.background"));
        aboutButton.setForeground(UIManager.getColor("Menu.foreground"));
        aboutButton.setBorderPainted(false);
        aboutButton.setFocusPainted(false);

        // Add action listener to the "About" button
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the action when "Sobre" button is clicked
                // For example, show a message dialog
                JOptionPane.showMessageDialog(Home.this, "Sobre button clicked!");
            }
        });

        // Add the menu and the "About" button to the menu bar
        menuBar.add(menu1);
        menuBar.add(aboutButton);

        // Set the menu bar to the frame
        setJMenuBar(menuBar);

        // Show the frame
        setVisible(true);
    }
}
