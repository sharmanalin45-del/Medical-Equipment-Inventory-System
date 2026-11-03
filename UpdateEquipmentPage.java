package pages;

import database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdateEquipmentPage extends JPanel {

    JTextField idField;
    JTextField nameField;
    JTextField categoryField;
    JTextField quantityField;
    JTextField priceField;
    JTextField supplierField;

    public UpdateEquipmentPage(
            CardLayout cardLayout,
            JPanel contentPanel
    ) {

        setLayout(null);

        setBackground(AppTheme.BACKGROUND);


        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel("UPDATE EQUIPMENT");

        title.setFont(AppTheme.TITLE_FONT);

        title.setForeground(AppTheme.TEXT);

        title.setBounds(
                350,
                35,
                500,
                50
        );

        add(title);


        JLabel subtitle =
                new JLabel(
                        "Search and update equipment details"
                );

        subtitle.setFont(AppTheme.SUBTITLE_FONT);

        subtitle.setForeground(AppTheme.TEXT);

        subtitle.setBounds(
                340,
                90,
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
                220,
                145,
                650,
                350
        );

        add(formPanel);


        // =========================
        // EQUIPMENT ID
        // =========================

        JLabel idLabel =
                new JLabel("Equipment ID:");

        idLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        idLabel.setForeground(
                AppTheme.TEXT
        );

        idLabel.setBounds(
                50,
                30,
                150,
                30
        );

        formPanel.add(idLabel);


        idField =
                new JTextField();

        idField.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        idField.setBounds(
                220,
                30,
                200,
                32
        );

        formPanel.add(idField);


        JButton searchButton =
                new JButton("Search");

        searchButton.setBounds(
                440,
                30,
                120,
                32
        );

        searchButton.setBackground(
                AppTheme.BUTTON
        );

        searchButton.setForeground(
                AppTheme.LIGHT_TEXT
        );

        searchButton.setFont(
                AppTheme.BUTTON_FONT
        );

        searchButton.setFocusPainted(false);

        searchButton.setBorderPainted(false);

        searchButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        formPanel.add(searchButton);


        // =========================
        // FORM FIELDS
        // =========================

        nameField =
                addField(
                        formPanel,
                        "Equipment Name:",
                        85
                );


        categoryField =
                addField(
                        formPanel,
                        "Category:",
                        135
                );


        quantityField =
                addField(
                        formPanel,
                        "Quantity:",
                        185
                );


        priceField =
                addField(
                        formPanel,
                        "Price:",
                        235
                );


        supplierField =
                addField(
                        formPanel,
                        "Supplier:",
                        285
                );


        // =========================
        // BUTTONS
        // =========================

        JButton updateButton =
                new JButton(
                        "Update Equipment"
                );


        JButton backButton =
                new JButton("Back");


        updateButton.setBounds(
                360,
                530,
                180,
                45
        );


        backButton.setBounds(
                560,
                530,
                120,
                45
        );


        JButton[] buttons = {

                updateButton,
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


        add(updateButton);

        add(backButton);


        // =========================
        // BUTTON ACTIONS
        // =========================

        searchButton.addActionListener(
                e -> searchEquipment()
        );


        updateButton.addActionListener(
                e -> updateEquipment()
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
                220,
                y,
                340,
                32
        );

        formPanel.add(field);


        return field;
    }


    // =========================
    // SEARCH EQUIPMENT
    // =========================

    private void searchEquipment() {

        try {

            if (idField.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Equipment ID"
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


            PreparedStatement statement =
                    connection.prepareStatement(
                            "SELECT * FROM equipment WHERE id=?"
                    );


            statement.setInt(
                    1,
                    Integer.parseInt(
                            idField.getText().trim()
                    )
            );


            ResultSet result =
                    statement.executeQuery();


            if (result.next()) {

                nameField.setText(
                        result.getString("name")
                );


                categoryField.setText(
                        result.getString("category")
                );


                quantityField.setText(
                        String.valueOf(
                                result.getInt("quantity")
                        )
                );


                priceField.setText(
                        String.valueOf(
                                result.getDouble("price")
                        )
                );


                supplierField.setText(
                        result.getString("supplier")
                );


            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Equipment Not Found"
                );

            }


            connection.close();


        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Equipment ID must be a number"
            );


        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage()
            );

        }

    }


    // =========================
    // UPDATE EQUIPMENT
    // =========================

    private void updateEquipment() {

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
                    "UPDATE equipment " +
                            "SET name=?, category=?, quantity=?, price=?, supplier=? " +
                            "WHERE id=?";


            PreparedStatement statement =
                    connection.prepareStatement(
                            query
                    );


            statement.setString(
                    1,
                    nameField.getText().trim()
            );


            statement.setString(
                    2,
                    categoryField.getText().trim()
            );


            statement.setInt(
                    3,
                    Integer.parseInt(
                            quantityField.getText().trim()
                    )
            );


            statement.setDouble(
                    4,
                    Double.parseDouble(
                            priceField.getText().trim()
                    )
            );


            statement.setString(
                    5,
                    supplierField.getText().trim()
            );


            statement.setInt(
                    6,
                    Integer.parseInt(
                            idField.getText().trim()
                    )
            );


            int rows =
                    statement.executeUpdate();


            connection.close();


            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Equipment Updated Successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Equipment Not Found"
                );

            }


        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "ID, Quantity and Price must contain valid numbers"
            );


        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage()
            );

        }

    }

}