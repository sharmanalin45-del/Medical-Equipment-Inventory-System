package pages;

import database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddEquipmentPage extends JPanel {

    JTextField idField;
    JTextField nameField;
    JTextField categoryField;
    JTextField quantityField;
    JTextField priceField;
    JTextField supplierField;


    public AddEquipmentPage(
            CardLayout cardLayout,
            JPanel contentPanel
    ) {

        setLayout(null);

        setBackground(
                AppTheme.BACKGROUND
        );


        // TITLE

        JLabel title =
                new JLabel("ADD EQUIPMENT");

        title.setFont(
                AppTheme.TITLE_FONT
        );

        title.setForeground(
                AppTheme.TEXT
        );

        title.setBounds(
                360,
                35,
                400,
                50
        );

        add(title);


        // SUBTITLE

        JLabel subtitle =
                new JLabel(
                        "Enter the details of the medical equipment"
                );

        subtitle.setFont(
                AppTheme.SUBTITLE_FONT
        );

        subtitle.setForeground(
                AppTheme.TEXT
        );

        subtitle.setBounds(
                300,
                90,
                600,
                30
        );

        add(subtitle);


        // FORM PANEL

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
                230,
                145,
                600,
                340
        );

        add(formPanel);


        // FIELDS

        idField =
                addField(
                        formPanel,
                        "Equipment ID:",
                        30
                );


        nameField =
                addField(
                        formPanel,
                        "Equipment Name:",
                        80
                );


        categoryField =
                addField(
                        formPanel,
                        "Category:",
                        130
                );


        quantityField =
                addField(
                        formPanel,
                        "Quantity:",
                        180
                );


        priceField =
                addField(
                        formPanel,
                        "Price:",
                        230
                );


        supplierField =
                addField(
                        formPanel,
                        "Supplier:",
                        280
                );


        // BUTTONS

        JButton addButton =
                new JButton(
                        "Add Equipment"
                );


        JButton backButton =
                new JButton(
                        "Back"
                );


        addButton.setBounds(
                370,
                520,
                170,
                45
        );


        backButton.setBounds(
                560,
                520,
                120,
                45
        );


        JButton[] buttons = {
                addButton,
                backButton
        };


        for (JButton button : buttons) {

            button.setBackground(
                    AppTheme.BUTTON
            );

            button.setForeground(
                    AppTheme.LIGHT_TEXT
            );

            button.setFont(
                    AppTheme.BUTTON_FONT
            );

            button.setFocusPainted(false);

            button.setBorderPainted(false);

            button.setCursor(
                    new Cursor(
                            Cursor.HAND_CURSOR
                    )
            );

        }


        add(addButton);

        add(backButton);


        // BUTTON ACTIONS

        addButton.addActionListener(
                e -> addEquipment()
        );


        backButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "Equipment"
                )
        );

    }


    // =========================
    // ADD FORM FIELD
    // =========================

    private JTextField addField(
            JPanel formPanel,
            String text,
            int y
    ) {

        JLabel label =
                new JLabel(text);

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
                160,
                30
        );

        formPanel.add(label);


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
                230,
                y,
                300,
                32
        );

        formPanel.add(field);


        return field;
    }


    // =========================
    // ADD EQUIPMENT DATABASE
    // =========================

    private void addEquipment() {

        try {

            if (
                    idField.getText().trim().isEmpty()
                            ||
                            nameField.getText().trim().isEmpty()
                            ||
                            categoryField.getText().trim().isEmpty()
                            ||
                            quantityField.getText().trim().isEmpty()
                            ||
                            priceField.getText().trim().isEmpty()
                            ||
                            supplierField.getText().trim().isEmpty()
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields"
                );

                return;

            }


            Connection connection =
                    DBConnection.getConnection();


            if (connection == null) {

                throw new Exception(
                        "Database connection failed"
                );

            }


            String query =
                    "INSERT INTO equipment VALUES (?, ?, ?, ?, ?, ?)";


            PreparedStatement statement =
                    connection.prepareStatement(
                            query
                    );


            statement.setInt(
                    1,
                    Integer.parseInt(
                            idField.getText().trim()
                    )
            );


            statement.setString(
                    2,
                    nameField.getText().trim()
            );


            statement.setString(
                    3,
                    categoryField.getText().trim()
            );


            statement.setInt(
                    4,
                    Integer.parseInt(
                            quantityField.getText().trim()
                    )
            );


            statement.setDouble(
                    5,
                    Double.parseDouble(
                            priceField.getText().trim()
                    )
            );


            statement.setString(
                    6,
                    supplierField.getText().trim()
            );


            statement.executeUpdate();


            connection.close();


            JOptionPane.showMessageDialog(
                    this,
                    "Equipment Added Successfully!"
            );


            idField.setText("");

            nameField.setText("");

            categoryField.setText("");

            quantityField.setText("");

            priceField.setText("");

            supplierField.setText("");


        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage()
            );

        }

    }

}