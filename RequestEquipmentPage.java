
package pages;

import database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;

public class RequestEquipmentPage extends JPanel {

    JTextField equipmentIdField;
    JTextField equipmentNameField;
    JTextField quantityField;
    JTextField requesterNameField;
    JTextField departmentField;
    JTextField requestDateField;
    JTextField purposeField;

    public RequestEquipmentPage(
            CardLayout cardLayout,
            JPanel contentPanel,
            String role
    ) {

        setLayout(null);
        setBackground(AppTheme.BACKGROUND);

        // =========================
        // TITLE
        // =========================

        JLabel title = new JLabel("REQUEST EQUIPMENT");

        title.setFont(AppTheme.TITLE_FONT);
        title.setForeground(AppTheme.TEXT);
        title.setBounds(350, 30, 500, 50);

        add(title);

        // =========================
        // SUBTITLE
        // =========================

        JLabel subtitle = new JLabel(
                "Fill the details to request medical equipment"
        );

        subtitle.setFont(AppTheme.SUBTITLE_FONT);
        subtitle.setForeground(AppTheme.TEXT);
        subtitle.setBounds(300, 85, 600, 30);

        add(subtitle);

        // =========================
        // FORM PANEL
        // =========================

        JPanel formPanel = new JPanel(null);

        formPanel.setBackground(AppTheme.CARD_BACKGROUND);

        formPanel.setBorder(
                BorderFactory.createLineBorder(
                        AppTheme.BORDER, 1
                )
        );

        formPanel.setBounds(220, 130, 650, 390);

        add(formPanel);

        // =========================
        // FORM FIELDS
        // =========================

        equipmentIdField =
                addField(formPanel, "Equipment ID:", 20);

        equipmentNameField =
                addField(formPanel, "Equipment Name:", 70);

        equipmentNameField.setEditable(false);

        quantityField =
                addField(formPanel, "Requested Quantity:", 120);

        requesterNameField =
                addField(formPanel, "Requester Name:", 170);

        departmentField =
                addField(formPanel, "Department:", 220);

        requestDateField =
                addField(formPanel, "Request Date:", 270);

        purposeField =
                addField(formPanel, "Purpose:", 320);

        // =========================
        // AUTO LOAD EQUIPMENT NAME
        // =========================

        equipmentIdField.addFocusListener(
                new FocusAdapter() {

                    @Override
                    public void focusLost(FocusEvent e) {
                        loadEquipmentName();
                    }
                }
        );

        // =========================
        // BUTTONS
        // =========================

        JButton submitButton =
                new JButton("Submit Request");

        JButton backButton =
                new JButton("Back");

        submitButton.setBounds(360, 550, 180, 45);
        backButton.setBounds(560, 550, 120, 45);

        JButton[] buttons = {
                submitButton,
                backButton
        };

        for (JButton button : buttons) {

            button.setBackground(AppTheme.BUTTON);
            button.setForeground(AppTheme.LIGHT_TEXT);
            button.setFont(AppTheme.BUTTON_FONT);

            button.setFocusPainted(false);
            button.setBorderPainted(false);

            button.setCursor(
                    new Cursor(Cursor.HAND_CURSOR)
            );
        }

        add(submitButton);
        add(backButton);

        // =========================
        // BUTTON ACTIONS
        // =========================

        submitButton.addActionListener(
                e -> submitRequest()
        );

        backButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "EquipmentRequest"
                )
        );
    }

    // =========================================
    // ADD FORM FIELD
    // =========================================

    private JTextField addField(
            JPanel formPanel,
            String text,
            int y
    ) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        label.setForeground(AppTheme.TEXT);
        label.setBounds(50, y, 170, 30);

        formPanel.add(label);

        JTextField field = new JTextField();

        field.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        field.setBounds(230, y, 350, 32);

        formPanel.add(field);

        return field;
    }

    // =========================================
    // LOAD EQUIPMENT NAME
    // =========================================

    private void loadEquipmentName() {

        String idText =
                equipmentIdField.getText().trim();

        if (idText.isEmpty()) {
            equipmentNameField.setText("");
            return;
        }

        try {

            int equipmentId =
                    Integer.parseInt(idText);

            try (
                    Connection connection =
                            DBConnection.getConnection();

                    PreparedStatement statement =
                            connection.prepareStatement(
                                    "SELECT name FROM equipment WHERE id=?"
                            )
            ) {

                statement.setInt(1, equipmentId);

                try (ResultSet result =
                             statement.executeQuery()) {

                    if (result.next()) {

                        equipmentNameField.setText(
                                result.getString("name")
                        );

                    } else {

                        equipmentNameField.setText("");

                        JOptionPane.showMessageDialog(
                                this,
                                "Equipment ID Not Found!"
                        );
                    }
                }
            }

        } catch (NumberFormatException e) {

            equipmentNameField.setText("");

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Equipment ID"
            );

        } catch (Exception e) {

            equipmentNameField.setText("");

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + e.getMessage()
            );
        }
    }

    // =========================================
    // CHECK EQUIPMENT STOCK
    // =========================================

    private int getAvailableStock(
            Connection connection,
            int equipmentId
    ) throws SQLException {

        String query =
                "SELECT quantity FROM equipment WHERE id=?";

        try (
                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            statement.setInt(1, equipmentId);

            try (ResultSet result =
                         statement.executeQuery()) {

                if (result.next()) {

                    return result.getInt("quantity");
                }

                throw new SQLException(
                        "Equipment ID not found."
                );
            }
        }
    }

    // =========================================
    // CALCULATE TOTAL PENDING REQUESTS
    // =========================================

    private int getPendingQuantity(
            Connection connection,
            int equipmentId
    ) throws SQLException {

        String query =
                "SELECT COALESCE(SUM(requested_quantity), 0) " +
                        "FROM equipment_requests " +
                        "WHERE equipment_id=? AND status='Pending'";

        try (
                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            statement.setInt(1, equipmentId);

            try (ResultSet result =
                         statement.executeQuery()) {

                if (result.next()) {

                    return result.getInt(1);
                }

                return 0;
            }
        }
    }

    // =========================================
    // SUBMIT REQUEST
    // =========================================

    private void submitRequest() {

        if (
                equipmentIdField.getText().trim().isEmpty()
                        ||
                        quantityField.getText().trim().isEmpty()
                        ||
                        requesterNameField.getText().trim().isEmpty()
                        ||
                        departmentField.getText().trim().isEmpty()
                        ||
                        requestDateField.getText().trim().isEmpty()
                        ||
                        purposeField.getText().trim().isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields"
            );

            return;
        }

        Connection connection = null;

        try {

            int equipmentId =
                    Integer.parseInt(
                            equipmentIdField.getText().trim()
                    );

            int requestedQuantity =
                    Integer.parseInt(
                            quantityField.getText().trim()
                    );

            if (requestedQuantity <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Requested Quantity must be greater than 0"
                );

                return;
            }

            connection = DBConnection.getConnection();

            if (connection == null) {

                throw new Exception(
                        "Database connection failed"
                );
            }

            // =================================
            // VERIFY EQUIPMENT
            // =================================

            String equipmentQuery =
                    "SELECT name FROM equipment WHERE id=?";

            String equipmentName;

            try (
                    PreparedStatement equipmentStatement =
                            connection.prepareStatement(
                                    equipmentQuery
                            )
            ) {

                equipmentStatement.setInt(
                        1, equipmentId
                );

                try (
                        ResultSet equipmentResult =
                                equipmentStatement.executeQuery()
                ) {

                    if (!equipmentResult.next()) {

                        equipmentNameField.setText("");

                        JOptionPane.showMessageDialog(
                                this,
                                "Equipment ID Not Found!"
                        );

                        return;
                    }

                    equipmentName =
                            equipmentResult.getString("name");
                }
            }

            equipmentNameField.setText(equipmentName);

            // =================================
            // SMART CONFLICT DETECTION
            // =================================

            int availableStock =
                    getAvailableStock(
                            connection,
                            equipmentId
                    );

            int pendingQuantity =
                    getPendingQuantity(
                            connection,
                            equipmentId
                    );

            int totalRequired =
                    pendingQuantity + requestedQuantity;

            boolean conflict =
                    totalRequired > availableStock;

            // =================================
            // SHOW CONFLICT WARNING
            // =================================

            if (conflict) {

                int shortage =
                        totalRequired - availableStock;

                String warning =
                        "⚠ EQUIPMENT STOCK CONFLICT DETECTED!\n\n" +

                                "Equipment: " + equipmentName + "\n" +

                                "Available Stock: " +
                                availableStock + "\n" +

                                "Already Pending: " +
                                pendingQuantity + "\n" +

                                "Your Requested Quantity: " +
                                requestedQuantity + "\n" +

                                "Total Required: " +
                                totalRequired + "\n" +

                                "Shortage: " + shortage + "\n\n" +

                                "The requested quantity exceeds " +
                                "available equipment stock.\n\n" +

                                "Do you still want to submit " +
                                "this request for admin review?";

                int choice =
                        JOptionPane.showConfirmDialog(
                                this,
                                warning,
                                "Smart Conflict Detection",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE
                        );

                if (choice != JOptionPane.YES_OPTION) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Request submission cancelled."
                    );

                    return;
                }
            }

            // =================================
            // INSERT REQUEST INTO DATABASE
            // =================================

            String query =
                    "INSERT INTO equipment_requests " +
                            "(equipment_id, equipment_name, requested_quantity, " +
                            "requester_name, department, request_date, purpose) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(query)
            ) {

                statement.setInt(1, equipmentId);

                statement.setString(2, equipmentName);

                statement.setInt(
                        3, requestedQuantity
                );

                statement.setString(
                        4,
                        requesterNameField.getText().trim()
                );

                statement.setString(
                        5,
                        departmentField.getText().trim()
                );

                statement.setString(
                        6,
                        requestDateField.getText().trim()
                );

                statement.setString(
                        7,
                        purposeField.getText().trim()
                );

                statement.executeUpdate();
            }

            // =================================
            // SUCCESS MESSAGE
            // =================================

            JOptionPane.showMessageDialog(
                    this,
                    conflict
                            ? "Request submitted successfully!\n" +
                            "Admin has been alerted about the stock conflict."
                            : "Equipment Request Submitted Successfully!"
            );

            // =================================
            // CLEAR FIELDS
            // =================================

            equipmentIdField.setText("");
            equipmentNameField.setText("");
            quantityField.setText("");
            requesterNameField.setText("");
            departmentField.setText("");
            requestDateField.setText("");
            purposeField.setText("");

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Equipment ID and Quantity must be valid numbers"
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } finally {

            try {

                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException ex) {

                ex.printStackTrace();
            }
        }
    }
}
