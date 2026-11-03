package pages;

import database.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginPage extends JFrame {

    public LoginPage() {
        setTitle("Medical Equipment Inventory System - Login");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);

        JLabel title = new JLabel("MEDICAL EQUIPMENT INVENTORY SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(70, 40, 400, 30);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(100, 120, 100, 30);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(200, 120, 180, 30);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(100, 170, 100, 30);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(200, 170, 180, 30);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(190, 230, 120, 35);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(100, 300, 120, 35);

        JButton forgotPasswordButton = new JButton("Forgot Password");
        forgotPasswordButton.setBounds(260, 300, 150, 35);

        loginButton.addActionListener(e -> {
            try {
                Connection connection = DBConnection.getConnection();
                if (connection == null) throw new Exception("Database connection failed");

                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM users WHERE username=? AND password=?");
                statement.setString(1, usernameField.getText().trim());
                statement.setString(2, new String(passwordField.getPassword()));

                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    String role = result.getString("role");
                    connection.close();

                    JOptionPane.showMessageDialog(this, "Login Successful!\nRole: " + role);
                    dispose();
                    new MainFrame(role);
                } else {
                    connection.close();
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        signUpButton.addActionListener(e -> {
            new SignUpPage();
            dispose();
        });

        forgotPasswordButton.addActionListener(e -> {
            new ForgotPasswordPage();
            dispose();
        });

        panel.add(title);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(signUpButton);
        panel.add(forgotPasswordButton);

        add(panel);
        setVisible(true);
    }
}
