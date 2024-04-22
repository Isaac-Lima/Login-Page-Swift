package raven.login;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.main.Home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JPanel {

    // initializing variables
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chRememberMe;
    private JButton cmdLogin;

    public Login() {
        init();
    }

    private void init() {
        // Setting the layout for the panel
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        // Initializing components
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        chRememberMe = new JCheckBox("Lembrar-me");
        cmdLogin = new JButton("Login");

        // Validating passwords, if match the user is directioned to Section 2
        cmdLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add the validation using bd
                String email = txtUsername.getText();
                String password = new String(txtPassword.getPassword());
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this, "Por favor, preencha todos os campos.", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
                } else {
                    if (verificarLogin(email, password)) {
                        Home homePage = new Home();
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Login.this);
                        frame.setContentPane(homePage);
                        frame.revalidate();
                    } else {
                        JOptionPane.showMessageDialog(Login.this, "Email ou senha incorretos. Por favor, tente novamente.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });



        // Creating a panel with MigLayout for organizing components
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,250:280"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");


        // Setting properties for login button
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        // Setting properties for password fields to show reveal buttons
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");

        // Setting placeholder text for text fields
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Digite seu email");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Digite sua senha");

        // Creating labels for title and description
        JLabel lbTitle = new JLabel("Bem-Vindo de Volta !");
        JLabel description = new JLabel("Por favor faça o login");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        // Adding components to the panel
        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Email"), "gapy 8");
        panel.add(txtUsername);
        panel.add(new JLabel("Senha"), "gapy 8");
        panel.add(txtPassword);
        panel.add(chRememberMe, "grow 0");
        panel.add(cmdLogin, "gapy 10");
        panel.add(createSignupLabel(), "gapy 10");

        // Adding the panel to the Login panel
        add(panel);
    }

    // Method to create the signup label
    private Component createSignupLabel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        JButton cmdRegister = new JButton("<html><a href=\"#\">Cadastre-se</a></html>");
        cmdRegister.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:3,3,3,3");
        cmdRegister.setContentAreaFilled(false);
        cmdRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel label = new JLabel("Não tem uma conta ?");
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");
        panel.add(label);
        panel.add(cmdRegister);
        return panel;
    }

    // Método para verificar o login
    private boolean verificarLogin(String email, String password) {
        String url = "jdbc:postgresql://localhost:5434/gestaoDeProdutos";
        String usuario = "postgres";
        String senhaBD = "aluno";

        try (Connection conn = DriverManager.getConnection(url, usuario, senhaBD)) {
            String sql = "SELECT * FROM usuario WHERE email = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next(); // Se encontrar um resultado, o login é válido
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    };
}