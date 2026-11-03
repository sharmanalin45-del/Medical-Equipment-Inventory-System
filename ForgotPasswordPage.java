package pages;

import database.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ForgotPasswordPage extends JFrame {

    public ForgotPasswordPage() {
        setTitle("Medical Equipment Inventory System - Forgot Password");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel title = new JLabel("RESET PASSWORD");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(160, 40, 250, 30);
        add(title);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(90, 120, 100, 30);
        add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(200, 120, 200, 30);
        add(usernameField);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setBounds(90, 170, 100, 30);
        add(newPasswordLabel);

        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setBounds(200, 170, 200, 30);
        add(newPasswordField);

        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setBounds(70, 220, 130, 30);
        add(confirmLabel);

        JPasswordField confirmField = new JPasswordField();
        confirmField.setBounds(200, 220, 200, 30);
        add(confirmField);

        JButton resetButton = new JButton("Reset Password");
        resetButton.setBounds(100, 300, 140, 40);
        add(resetButton);

        JButton backButton = new JButton("Back to Login");
        backButton.setBounds(260, 300, 130, 40);
        add(backButton);

        resetButton.addActionListener(e -> {
            try {
                String username = usernameField.getText().trim();
                String newPassword = new String(newPasswordField.getPassword());
                String confirm = new String(confirmField.getPassword());

                if (username.isEmpty() || newPassword.isEmpty() || confirm.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields");
                    return;
                }

                if (!newPassword.equals(confirm)) {
                    JOptionPane.showMessageDialog(this, "Passwords do not match");
                    return;
                }

                Connection connection = DBConnection.getConnection();
                if (connection == null) throw new Exception("Database connection failed");

                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE users SET password=? WHERE username=?");
                statement.setString(1, newPassword);
                statement.setString(2, username);

                int rows = statement.executeUpdate();
                connection.close();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Password Reset Successfully!");
                    dispose();
                    new LoginPage();
                } else {
                    JOptionPane.showMessageDialog(this, "Username not found");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new LoginPage();
        });

        setVisible(true);
    }
}
