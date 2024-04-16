package raven.main;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import raven.login.Login;
import raven.login.SignUp;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame{
    public Application(){
        init();
    }
    public void init(){
        setTitle("Swing UI Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1200, 700));
        setLocationRelativeTo(null);
        setContentPane(new Login());
    }

    public static void main(String[] args){
        FlatRobotoMonoFont.install(); // Font Page
        FlatMacDarkLaf.registerCustomDefaultsSource("raven.themes");

        UIManager.put("defaultFont" , new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13)); // set font, size...
        FlatMacDarkLaf.setup(); // Background color
        EventQueue.invokeLater(() -> new Application().setVisible(true));
    }
}
