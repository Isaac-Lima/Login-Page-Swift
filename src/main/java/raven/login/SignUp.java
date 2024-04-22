package raven.login;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUp extends JPanel {

    // initializing variables
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JTextField txtCPF;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JComboBox<String> userTypeComboBox;
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
        txtCPF = new JTextField();
        txtPassword = new JPasswordField();
        txtConfirmPassword = new JPasswordField();
        userTypeComboBox = new JComboBox<>(new String[]{"Admin", "Cliente"});
        cmdRegister = new JButton("Cadastrar");

        // Validating passwords, if match the user is directioned to Section 2
        cmdRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emptyField = validateFields();

                if (!emptyField.isEmpty()) {
                    JOptionPane.showMessageDialog(SignUp.this,
                            "O campo \"" + emptyField + "\" está inválido, por favor preencha-o corretamente.",
                            "Campo inválido",
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
                    // Se todos os campos estiverem preenchidos corretamente, então envie os dados para o banco de dados
                    sendDataToDatabase();
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
        txtCPF.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Digite seu CPF");
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
        panel.add(new JLabel("CPF"), "gapy 8");
        panel.add(txtCPF);
        panel.add(new JLabel("Senha"), "gapy 8");
        panel.add(txtPassword);
        panel.add(new JLabel("Confirmar senha"), "gapy 8");
        panel.add(txtConfirmPassword);
        panel.add(new JLabel("Tipo de Usuário"), "gapy 8");
        panel.add(userTypeComboBox);
        panel.add(cmdRegister, "gapy 10");

        // Adding the panel to the SignUp panel
        add(panel);
    }

    // Method to validate if fields are empty and return which field is empty
    private String validateFields() {
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String CPF = txtCPF.getText();
        String password = String.valueOf(txtPassword.getPassword());
        String confirmPassword = String.valueOf(txtConfirmPassword.getPassword());
        String emptyField = "";

        if (username.isEmpty()) {
            emptyField = "Usuário";
        } else if (email.isEmpty()) {
            emptyField = "Email";
        } else if (CPF.isEmpty()){
            emptyField = "CPF";
        } else if (!validateCPF(CPF)) {
            emptyField = "CPF";
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

    // Method to validate if the CPF format is valid
    private boolean validateCPF(String cpf) {
        // Removes any non-digit characters from the CPF
        cpf = cpf.replaceAll("[^\\d]", "");

        // Checks if the CPF has 11 digits
        if (cpf.length() != 11) {
            return false;
        }

        // Validates the CPF using the algorithm
        int[] numbers = new int[11];
        for (int i = 0; i < 11; i++) {
            numbers[i] = Character.getNumericValue(cpf.charAt(i));
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += numbers[i] * (10 - i);
        }

        int remainder = sum % 11;
        int expectedDigit1 = (remainder < 2) ? 0 : (11 - remainder);

        if (numbers[9] != expectedDigit1) {
            return false;
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += numbers[i] * (11 - i);
        }

        remainder = sum % 11;
        int expectedDigit2 = (remainder < 2) ? 0 : (11 - remainder);

        return numbers[10] == expectedDigit2;
    }

    // Method to send data to the database
    private void sendDataToDatabase() {
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String CPF = txtCPF.getText();
        String password = String.valueOf(txtPassword.getPassword());
        String profile = (String) userTypeComboBox.getSelectedItem();

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5434/gestaoDeProdutos", "postgres", "aluno");

            String query = "INSERT INTO usuario (username, email, CPF, profile, password) VALUES (?, ? , ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, CPF);
            preparedStatement.setString(4, profile);
            preparedStatement.setString(5, password);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Dados enviados com sucesso!");
            System.out.println("Dados enviados com sucesso!");
            System.out.println("Volte sempre!");

            preparedStatement.close();
            connection.close();
            Login loginPage = new Login();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SignUp.this);
            frame.setContentPane(loginPage);
            frame.revalidate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao enviar dados para o banco de dados.");
        }
    }
}