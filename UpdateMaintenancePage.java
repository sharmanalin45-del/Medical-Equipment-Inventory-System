package pages;

import database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;

public class UpdateMaintenancePage extends JPanel {

    JTextField idField;
    JTextField equipmentIdField;
    JTextField equipmentNameField;
    JTextField quantityField;
    JTextField dateField;

    JComboBox<String> statusBox;

    JTextArea descriptionArea;


    public UpdateMaintenancePage(
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
                        "UPDATE MAINTENANCE"
                );

        title.setFont(
                AppTheme.TITLE_FONT
        );

        title.setForeground(
                AppTheme.TEXT
        );

        title.setBounds(
                60,
                30,
                500,
                50
        );

        add(title);


        JLabel subtitle =
                new JLabel(
                        "Search and update maintenance records"
                );

        subtitle.setFont(
                AppTheme.SUBTITLE_FONT
        );

        subtitle.setForeground(
                AppTheme.TEXT
        );

        subtitle.setBounds(
                60,
                80,
                500,
                30
        );

        add(subtitle);


        // =========================
        // MAIN FORM CARD
        // =========================

        JPanel formCard =
                new JPanel();

        formCard.setLayout(null);

        formCard.setBackground(
                AppTheme.CARD_BACKGROUND
        );

        formCard.setBorder(
                BorderFactory.createLineBorder(
                        AppTheme.BORDER,
                        1
                )
        );

        formCard.setBounds(
                180,
                130,
                650,
                430
        );

        add(formCard);


        // =========================
        // MAINTENANCE ID
        // =========================

        JLabel idLabel =
                new JLabel(
                        "Maintenance ID"
                );

        idLabel.setFont(
                AppTheme.LABEL_FONT
        );

        idLabel.setForeground(
                AppTheme.TEXT
        );

        idLabel.setBounds(
                50,
                35,
                150,
                30
        );

        formCard.add(idLabel);


        idField =
                new JTextField();

        idField.setFont(
                AppTheme.FIELD_FONT
        );

        idField.setBounds(
                210,
                35,
                200,
                35
        );

        formCard.add(idField);


        JButton searchButton =
                new JButton(
                        "Search"
                );

        styleButton(
                searchButton
        );

        searchButton.setBounds(
                430,
                35,
                140,
                35
        );

        formCard.add(searchButton);


        // =========================
        // EQUIPMENT ID
        // =========================

        equipmentIdField =
                addField(
                        formCard,
                        "Equipment ID",
                        90
                );


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
        // EQUIPMENT NAME
        // =========================

        equipmentNameField =
                addField(
                        formCard,
                        "Equipment Name",
                        140
                );


        equipmentNameField.setEditable(
                false
        );


        // =========================
        // MAINTENANCE QUANTITY
        // =========================

        quantityField =
                addField(
                        formCard,
                        "Maintenance Quantity",
                        190
                );


        // =========================
        // STATUS
        // =========================

        JLabel statusLabel =
                new JLabel(
                        "Status"
                );

        statusLabel.setFont(
                AppTheme.LABEL_FONT
        );

        statusLabel.setForeground(
                AppTheme.TEXT
        );

        statusLabel.setBounds(
                50,
                240,
                150,
                30
        );

        formCard.add(statusLabel);


        statusBox =
                new JComboBox<>(
                        new String[]{
                                "Pending",
                                "In Progress",
                                "Completed"
                        }
                );

        statusBox.setFont(
                AppTheme.FIELD_FONT
        );

        statusBox.setBounds(
                210,
                240,
                360,
                35
        );

        formCard.add(statusBox);


        // =========================
        // MAINTENANCE DATE
        // =========================

        dateField =
                addField(
                        formCard,
                        "Maintenance Date",
                        290
                );


        // =========================
        // DESCRIPTION
        // =========================

        JLabel descriptionLabel =
                new JLabel(
                        "Description"
                );

        descriptionLabel.setFont(
                AppTheme.LABEL_FONT
        );

        descriptionLabel.setForeground(
                AppTheme.TEXT
        );

        descriptionLabel.setBounds(
                50,
                340,
                150,
                30
        );

        formCard.add(descriptionLabel);


        descriptionArea =
                new JTextArea();

        descriptionArea.setFont(
                AppTheme.FIELD_FONT
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
                210,
                340,
                360,
                70
        );

        formCard.add(scrollPane);


        // =========================
        // BUTTONS
        // =========================

        JButton updateButton =
                new JButton(
                        "Update Record"
                );

        JButton backButton =
                new JButton(
                        "Back"
                );


        styleButton(
                updateButton
        );

        styleButton(
                backButton
        );


        updateButton.setBounds(
                320,
                590,
                170,
                45
        );

        backButton.setBounds(
                510,
                590,
                140,
                45
        );


        add(updateButton);

        add(backButton);


        // =========================
        // BUTTON ACTIONS
        // =========================

        searchButton.addActionListener(
                e -> searchMaintenance()
        );


        updateButton.addActionListener(
                e -> updateMaintenance()
        );


        backButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "Maintenance"
                )
        );

    }


    // =========================
    // ADD FORM FIELD
    // =========================

    private JTextField addField(
            JPanel panel,
            String text,
            int y
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                AppTheme.LABEL_FONT
        );

        label.setForeground(
                AppTheme.TEXT
        );

        label.setBounds(
                50,
                y,
                160,
                30
        );

        panel.add(label);


        JTextField field =
                new JTextField();

        field.setFont(
                AppTheme.FIELD_FONT
        );

        field.setBounds(
                210,
                y,
                360,
                35
        );

        panel.add(field);


        return field;

    }


    // =========================
    // BUTTON STYLE
    // =========================

    private void styleButton(
            JButton button
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
    // SEARCH MAINTENANCE
    // =========================

    private void searchMaintenance() {

        try {

            if (
                    idField.getText()
                            .trim()
                            .isEmpty()
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Maintenance ID"
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


            PreparedStatement statement =
                    connection.prepareStatement(

                            "SELECT * FROM maintenance " +
                                    "WHERE maintenance_id=?"

                    );


            statement.setInt(
                    1,
                    Integer.parseInt(
                            idField.getText()
                                    .trim()
                    )
            );


            ResultSet result =
                    statement.executeQuery();


            if (
                    result.next()
            ) {

                equipmentIdField.setText(
                        String.valueOf(
                                result.getInt(
                                        "equipment_id"
                                )
                        )
                );


                equipmentNameField.setText(
                        result.getString(
                                "equipment_name"
                        )
                );


                quantityField.setText(
                        String.valueOf(
                                result.getInt(
                                        "maintenance_quantity"
                                )
                        )
                );


                statusBox.setSelectedItem(
                        result.getString(
                                "status"
                        )
                );


                dateField.setText(
                        result.getString(
                                "maintenance_date"
                        )
                );


                descriptionArea.setText(
                        result.getString(
                                "description"
                        )
                );


            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Maintenance ID Not Found!"
                );

            }


            connection.close();


        } catch (
                NumberFormatException ex
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Maintenance ID"
            );

        } catch (
                Exception ex
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " +
                            ex.getMessage()
            );

        }

    }


    // =========================
    // UPDATE MAINTENANCE
    // =========================

    private void updateMaintenance() {

        try {

            if (
                    idField.getText()
                            .trim()
                            .isEmpty()
                            ||
                            equipmentIdField.getText()
                                    .trim()
                                    .isEmpty()
                            ||
                            quantityField.getText()
                                    .trim()
                                    .isEmpty()
                            ||
                            dateField.getText()
                                    .trim()
                                    .isEmpty()
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all required fields"
                );

                return;

            }


            int maintenanceId =
                    Integer.parseInt(
                            idField.getText()
                                    .trim()
                    );


            int equipmentId =
                    Integer.parseInt(
                            equipmentIdField.getText()
                                    .trim()
                    );


            int maintenanceQuantity =
                    Integer.parseInt(
                            quantityField.getText()
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


            equipmentNameField.setText(
                    equipmentName
            );


            // =========================
            // CHECK OTHER ACTIVE
            // MAINTENANCE RECORDS
            // =========================

            String maintenanceQuery =
                    "SELECT COALESCE(" +
                            "SUM(maintenance_quantity), 0" +
                            ") AS under_maintenance " +
                            "FROM maintenance " +
                            "WHERE equipment_id=? " +
                            "AND status != 'Completed' " +
                            "AND maintenance_id != ?";


            PreparedStatement maintenanceStatement =
                    connection.prepareStatement(
                            maintenanceQuery
                    );


            maintenanceStatement.setInt(
                    1,
                    equipmentId
            );


            maintenanceStatement.setInt(
                    2,
                    maintenanceId
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
            // AVAILABLE QUANTITY
            // =========================

            int availableQuantity =
                    totalQuantity
                            - underMaintenance;


            if (
                    availableQuantity < 0
            ) {

                availableQuantity = 0;

            }


            String selectedStatus =
                    statusBox
                            .getSelectedItem()
                            .toString();


            // =========================
            // VALIDATE QUANTITY
            // ONLY FOR ACTIVE STATUS
            // =========================

            if (
                    !selectedStatus.equals(
                            "Completed"
                    )
                            &&
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
            // UPDATE MAINTENANCE
            // =========================

            String query =
                    "UPDATE maintenance SET " +
                            "equipment_id=?, " +
                            "equipment_name=?, " +
                            "maintenance_quantity=?, " +
                            "status=?, " +
                            "maintenance_date=?, " +
                            "description=? " +
                            "WHERE maintenance_id=?";


            PreparedStatement statement =
                    connection.prepareStatement(
                            query
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
                    selectedStatus
            );


            statement.setString(
                    5,
                    dateField.getText()
                            .trim()
            );


            statement.setString(
                    6,
                    descriptionArea.getText()
                            .trim()
            );


            statement.setInt(
                    7,
                    maintenanceId
            );


            int rows =
                    statement.executeUpdate();


            connection.close();


            if (
                    rows > 0
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Maintenance Record Updated Successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Maintenance ID Not Found!"
                );

            }


        } catch (
                NumberFormatException ex
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric values"
            );

        } catch (
                Exception ex
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " +
                            ex.getMessage()
            );

        }

    }

}