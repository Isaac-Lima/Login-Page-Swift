package raven.login;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.main.Section2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp extends JPanel {

    // initializing variables
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JButton cmdRegister;

    public SignUp() {
        init();
    }

    private void init() {
        // Setting the layout for the panel
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        // Initializing components
        txtUsername = new JTextField();
        txtEmail = new JTextField();
        txtPassword = new JPasswordField();
        txtConfirmPassword = new JPasswordField();
        cmdRegister = new JButton("Cadastrar");

        // Validating passwords, if match the user is directioned to Section 2
        cmdRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emptyField = validateFields();

                if (!emptyField.isEmpty()) {
                    JOptionPane.showMessageDialog(SignUp.this,
                            "O campo \"" + emptyField + "\" está vazio, por favor preencha-o.",
                            "Campo Vazio",
                            JOptionPane.WARNING_MESSAGE);
                } else if (!validatePasswordChars()) {
                    JOptionPane.showMessageDialog(SignUp.this,
                            "A senha deve conter pelo menos um número, uma letra e um caractere especial.",
                            "Erro de Senha",
                            JOptionPane.ERROR_MESSAGE);
                } else if (!validatePasswordsMatch()) {
                    JOptionPane.showMessageDialog(SignUp.this,
                            "Os campos de senha não coincidem.",
                            "Senhas Divergentes",
                            JOptionPane.ERROR_MESSAGE);
                } else if (!validateEmail(txtEmail.getText())) {
                    JOptionPane.showMessageDialog(SignUp.this,
                            "Por favor, insira um email válido.",
                            "Email Inválido",
                            JOptionPane.ERROR_MESSAGE);
                } else if (String.valueOf(txtPassword.getPassword()).length() < 8) {
                    JOptionPane.showMessageDialog(SignUp.this,
                            "A senha deve ter pelo menos 8 caracteres.",
                            "Senha Curta",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Login loginPage = new Login();
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SignUp.this);
                    frame.setContentPane(loginPage);
                    frame.revalidate();
                }
            }
        });



        // Creating a panel with MigLayout for organizing components
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,250:280"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");

        // Setting properties for password fields to show reveal buttons
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");
        txtConfirmPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");

        // Setting properties for register button
        cmdRegister.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        // Setting placeholder text for text fields
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Digite seu usuário");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Digite seu email");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Digite sua senha");
        txtConfirmPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Confirme sua senha");

        // Creating labels for title and description
        JLabel lbTitle = new JLabel("Cadastro de Usuário");
        JLabel description = new JLabel("Por favor cadastre-se");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        // Adding components to the panel
        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Usuário"), "gapy 8");
        panel.add(txtUsername);
        panel.add(new JLabel("Email"), "gapy 8");
        panel.add(txtEmail);
        panel.add(new JLabel("Senha"), "gapy 8");
        panel.add(txtPassword);
        panel.add(new JLabel("Confirmar senha"), "gapy 8");
        panel.add(txtConfirmPassword);
        panel.add(cmdRegister, "gapy 10");

        // Adding the panel to the SignUp panel
        add(panel);
    }

    // Method to validate if fields are empty and return which field is empty
    private String validateFields() {
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = String.valueOf(txtPassword.getPassword());
        String confirmPassword = String.valueOf(txtConfirmPassword.getPassword());
        String emptyField = "";

        if (username.isEmpty()) {
            emptyField = "Usuário";
        } else if (email.isEmpty()) {
            emptyField = "Email";
        } else if (password.isEmpty()) {
            emptyField = "Senha";
        } else if (confirmPassword.isEmpty()) {
            emptyField = "Confirmar senha";
        }

        return emptyField;
    }

    // Method to validate if the email format is valid
    private boolean validateEmail(String email) {
        // Regular expression for validating email format
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*(?:\\.[a-zA-Z]{2,7})$";
        return email.matches(regex);
    }

    // method to validate if the password has number, letter, and special character
    private boolean validatePasswordChars() {
        String password = String.valueOf(txtPassword.getPassword());
        boolean hasNumber = false;
        boolean hasLetter = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }

        return hasNumber && hasLetter && hasSpecialChar;
    }

    // method to validate if the fields password and confirm password match
    private boolean validatePasswordsMatch() {
        String password = String.valueOf(txtPassword.getPassword());
        String confirmPassword = String.valueOf(txtConfirmPassword.getPassword());

        return password.equals(confirmPassword);
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
}
