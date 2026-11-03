package pages;

import database.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SignUpPage extends JFrame {

    public SignUpPage() {
        setTitle("Medical Equipment Inventory System - Sign Up");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel title = new JLabel("CREATE NEW ACCOUNT");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(130, 40, 300, 30);
        add(title);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(90, 120, 100, 30);
        add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(200, 120, 200, 30);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(90, 170, 100, 30);
        add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(200, 170, 200, 30);
        add(passwordField);

        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setBounds(70, 220, 130, 30);
        add(confirmLabel);

        JPasswordField confirmField = new JPasswordField();
        confirmField.setBounds(200, 220, 200, 30);
        add(confirmField);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(120, 300, 120, 40);
        add(signUpButton);

        JButton backButton = new JButton("Back to Login");
        backButton.setBounds(260, 300, 130, 40);
        add(backButton);

        signUpButton.addActionListener(e -> {
            try {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());
                String confirm = new String(confirmField.getPassword());

                if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields");
                    return;
                }

                if (!password.equals(confirm)) {
                    JOptionPane.showMessageDialog(this, "Passwords do not match");
                    return;
                }

                Connection connection = DBConnection.getConnection();
                if (connection == null) throw new Exception("Database connection failed");

                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO users (username, password, role) VALUES (?, ?, ?)");
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setString(3, "Staff");
                statement.executeUpdate();
                connection.close();

                JOptionPane.showMessageDialog(this, "Account Created Successfully!");
                dispose();
                new LoginPage();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Username already exists or error occurred: " + ex.getMessage());
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new LoginPage();
        });

        setVisible(true);
    }
}
