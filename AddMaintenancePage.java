package pages;

import database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;

public class AddMaintenancePage extends JPanel {

    JTextField equipmentIdField;
    JTextField equipmentNameField;
    JTextField quantityField;
    JTextField dateField;

    JComboBox<String> statusBox;

    JTextArea descriptionArea;


    public AddMaintenancePage(
            CardLayout cardLayout,
            JPanel contentPanel
    ) {

        setLayout(null);

        setBackground(
                AppTheme.BACKGROUND
        );


        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel(
                        "ADD MAINTENANCE RECORD"
                );

        title.setFont(
                AppTheme.TITLE_FONT
        );

        title.setForeground(
                AppTheme.TEXT
        );

        title.setBounds(
                300,
                25,
                600,
                50
        );

        add(title);


        JLabel subtitle =
                new JLabel(
                        "Add a new equipment maintenance record"
                );

        subtitle.setFont(
                AppTheme.SUBTITLE_FONT
        );

        subtitle.setForeground(
                AppTheme.TEXT
        );

        subtitle.setBounds(
                335,
                80,
                500,
                30
        );

        add(subtitle);


        // =========================
        // FORM PANEL
        // =========================

        JPanel formPanel =
                new JPanel(null);

        formPanel.setBackground(
                AppTheme.CARD_BACKGROUND
        );

        formPanel.setBorder(
                BorderFactory.createLineBorder(
                        AppTheme.BORDER,
                        1
                )
        );

        formPanel.setBounds(
                210,
                125,
                700,
                390
        );

        add(formPanel);


        // =========================
        // FORM FIELDS
        // =========================

        equipmentIdField =
                addField(
                        formPanel,
                        "Equipment ID:",
                        30
                );


        equipmentNameField =
                addField(
                        formPanel,
                        "Equipment Name:",
                        80
                );


        // Equipment name will be loaded automatically
        equipmentNameField.setEditable(
                false
        );


        quantityField =
                addField(
                        formPanel,
                        "Maintenance Quantity:",
                        130
                );


        // =========================
        // AUTO LOAD EQUIPMENT NAME
        // =========================

        equipmentIdField.addFocusListener(
                new FocusAdapter() {

                    @Override
                    public void focusLost(
                            FocusEvent e
                    ) {

                        loadEquipmentName();

                    }

                }
        );


        // =========================
        // STATUS
        // =========================

        JLabel statusLabel =
                new JLabel(
                        "Status:"
                );

        statusLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        statusLabel.setForeground(
                AppTheme.TEXT
        );

        statusLabel.setBounds(
                50,
                180,
                180,
                30
        );

        formPanel.add(
                statusLabel
        );


        statusBox =
                new JComboBox<>(
                        new String[]{
                                "Pending",
                                "In Progress",
                                "Completed"
                        }
                );

        statusBox.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        statusBox.setBounds(
                250,
                180,
                350,
                32
        );

        formPanel.add(
                statusBox
        );


        // =========================
        // DATE
        // =========================

        dateField =
                addField(
                        formPanel,
                        "Maintenance Date:",
                        230
                );


        // =========================
        // DESCRIPTION
        // =========================

        JLabel descriptionLabel =
                new JLabel(
                        "Description:"
                );

        descriptionLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        descriptionLabel.setForeground(
                AppTheme.TEXT
        );

        descriptionLabel.setBounds(
                50,
                280,
                180,
                30
        );

        formPanel.add(
                descriptionLabel
        );


        descriptionArea =
                new JTextArea();

        descriptionArea.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        descriptionArea.setLineWrap(
                true
        );

        descriptionArea.setWrapStyleWord(
                true
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        descriptionArea
                );

        scrollPane.setBounds(
                250,
                280,
                350,
                75
        );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        AppTheme.BORDER,
                        1
                )
        );

        formPanel.add(
                scrollPane
        );


        // =========================
        // BUTTONS
        // =========================

        JButton addButton =
                new JButton(
                        "Add Maintenance"
                );


        JButton backButton =
                new JButton(
                        "Back"
                );


        addButton.setBounds(
                350,
                545,
                180,
                45
        );


        backButton.setBounds(
                550,
                545,
                120,
                45
        );


        JButton[] buttons = {

                addButton,
                backButton

        };


        for (
                JButton button : buttons
        ) {

            button.setBackground(
                    AppTheme.BUTTON
            );

            button.setForeground(
                    AppTheme.LIGHT_TEXT
            );

            button.setFont(
                    AppTheme.BUTTON_FONT
            );

            button.setFocusPainted(
                    false
            );

            button.setBorderPainted(
                    false
            );

            button.setCursor(
                    new Cursor(
                            Cursor.HAND_CURSOR
                    )
            );

        }


        add(
                addButton
        );

        add(
                backButton
        );


        // =========================
        // BUTTON ACTIONS
        // =========================

        addButton.addActionListener(
                e -> addMaintenance()
        );


        backButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "Maintenance"
                )
        );

    }


    // =========================
    // ADD TEXT FIELD
    // =========================

    private JTextField addField(
            JPanel formPanel,
            String text,
            int y
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        label.setForeground(
                AppTheme.TEXT
        );

        label.setBounds(
                50,
                y,
                180,
                30
        );

        formPanel.add(
                label
        );


        JTextField field =
                new JTextField();

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        field.setBounds(
                250,
                y,
                350,
                32
        );

        formPanel.add(
                field
        );


        return field;
    }


    // =========================
    // LOAD EQUIPMENT NAME
    // =========================

    private void loadEquipmentName() {

        try {

            String idText =
                    equipmentIdField
                            .getText()
                            .trim();


            if (
                    idText.isEmpty()
            ) {

                equipmentNameField.setText(
                        ""
                );

                return;

            }


            int equipmentId =
                    Integer.parseInt(
                            idText
                    );


            Connection connection =
                    DBConnection.getConnection();


            if (
                    connection == null
            ) {

                return;

            }


            String query =
                    "SELECT name FROM equipment WHERE id=?";


            PreparedStatement statement =
                    connection.prepareStatement(
                            query
                    );


            statement.setInt(
                    1,
                    equipmentId
            );


            ResultSet result =
                    statement.executeQuery();


            if (
                    result.next()
            ) {

                equipmentNameField.setText(
                        result.getString(
                                "name"
                        )
                );

            } else {

                equipmentNameField.setText(
                        ""
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Equipment ID Not Found!"
                );

            }


            connection.close();


        } catch (
                NumberFormatException e
        ) {

            equipmentNameField.setText(
                    ""
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Equipment ID"
            );


        } catch (
                Exception e
        ) {

            equipmentNameField.setText(
                    ""
            );

        }

    }


    // =========================
    // ADD MAINTENANCE
    // =========================

    private void addMaintenance() {

        try {

            // =========================
            // REQUIRED FIELD VALIDATION
            // =========================

            if (
                    equipmentIdField
                            .getText()
                            .trim()
                            .isEmpty()

                            ||

                            quantityField
                                    .getText()
                                    .trim()
                                    .isEmpty()

                            ||

                            dateField
                                    .getText()
                                    .trim()
                                    .isEmpty()
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all required fields"
                );

                return;

            }


            int equipmentId =
                    Integer.parseInt(
                            equipmentIdField
                                    .getText()
                                    .trim()
                    );


            int maintenanceQuantity =
                    Integer.parseInt(
                            quantityField
                                    .getText()
                                    .trim()
                    );


            if (
                    maintenanceQuantity <= 0
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Maintenance Quantity must be greater than 0"
                );

                return;

            }


            Connection connection =
                    DBConnection.getConnection();


            if (
                    connection == null
            ) {

                throw new Exception(
                        "Database connection failed"
                );

            }


            // =========================
            // CHECK EQUIPMENT
            // =========================

            String equipmentQuery =
                    "SELECT name, quantity " +
                            "FROM equipment " +
                            "WHERE id=?";


            PreparedStatement equipmentStatement =
                    connection.prepareStatement(
                            equipmentQuery
                    );


            equipmentStatement.setInt(
                    1,
                    equipmentId
            );


            ResultSet equipmentResult =
                    equipmentStatement.executeQuery();


            if (
                    !equipmentResult.next()
            ) {

                connection.close();

                equipmentNameField.setText(
                        ""
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Equipment ID Not Found!"
                );

                return;

            }


            String equipmentName =
                    equipmentResult.getString(
                            "name"
                    );


            int totalQuantity =
                    equipmentResult.getInt(
                            "quantity"
                    );


            // Keep the displayed name synchronized
            equipmentNameField.setText(
                    equipmentName
            );


            // =========================
            // CALCULATE EQUIPMENT
            // ALREADY UNDER MAINTENANCE
            // =========================

            String maintenanceQuery =
                    "SELECT COALESCE(" +
                            "SUM(maintenance_quantity), 0" +
                            ") AS under_maintenance " +
                            "FROM maintenance " +
                            "WHERE equipment_id=? " +
                            "AND status != 'Completed'";


            PreparedStatement maintenanceStatement =
                    connection.prepareStatement(
                            maintenanceQuery
                    );


            maintenanceStatement.setInt(
                    1,
                    equipmentId
            );


            ResultSet maintenanceResult =
                    maintenanceStatement.executeQuery();


            int underMaintenance =
                    0;


            if (
                    maintenanceResult.next()
            ) {

                underMaintenance =
                        maintenanceResult.getInt(
                                "under_maintenance"
                        );

            }


            // =========================
            // CALCULATE AVAILABLE
            // =========================

            int availableQuantity =
                    totalQuantity
                            - underMaintenance;


            if (
                    availableQuantity < 0
            ) {

                availableQuantity = 0;

            }


            // =========================
            // VALIDATE MAINTENANCE
            // QUANTITY
            // =========================

            if (
                    maintenanceQuantity
                            > availableQuantity
            ) {

                connection.close();

                JOptionPane.showMessageDialog(
                        this,
                        "Only "
                                + availableQuantity
                                + " equipment units are currently available."
                );

                return;

            }


            // =========================
            // INSERT MAINTENANCE
            // =========================

            String insertQuery =
                    "INSERT INTO maintenance " +
                            "(equipment_id, equipment_name, " +
                            "maintenance_quantity, status, " +
                            "maintenance_date, description) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";


            PreparedStatement statement =
                    connection.prepareStatement(
                            insertQuery
                    );


            statement.setInt(
                    1,
                    equipmentId
            );


            statement.setString(
                    2,
                    equipmentName
            );


            statement.setInt(
                    3,
                    maintenanceQuantity
            );


            statement.setString(
                    4,
                    statusBox
                            .getSelectedItem()
                            .toString()
            );


            statement.setString(
                    5,
                    dateField
                            .getText()
                            .trim()
            );


            statement.setString(
                    6,
                    descriptionArea
                            .getText()
                            .trim()
            );


            statement.executeUpdate();


            connection.close();


            // =========================
            // SUCCESS
            // =========================

            JOptionPane.showMessageDialog(
                    this,
                    "Maintenance Record Added Successfully!"
            );


            equipmentIdField.setText(
                    ""
            );

            equipmentNameField.setText(
                    ""
            );

            quantityField.setText(
                    ""
            );

            dateField.setText(
                    ""
            );

            descriptionArea.setText(
                    ""
            );

            statusBox.setSelectedIndex(
                    0
            );


        } catch (
                NumberFormatException ex
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric values for Equipment ID and Quantity"
            );


        } catch (
                Exception ex
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: "
                            + ex.getMessage()
            );

        }

    }

}