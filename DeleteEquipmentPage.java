package pages;

import database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DeleteEquipmentPage extends JPanel {

    JTextField idField;

    public DeleteEquipmentPage(
            CardLayout cardLayout,
            JPanel contentPanel
    ) {

        setLayout(null);

        setBackground(AppTheme.BACKGROUND);


        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel("DELETE EQUIPMENT");

        title.setFont(AppTheme.TITLE_FONT);

        title.setForeground(AppTheme.TEXT);

        title.setBounds(
                350,
                80,
                500,
                50
        );

        add(title);


        JLabel subtitle =
                new JLabel(
                        "Remove equipment records from the inventory"
                );

        subtitle.setFont(AppTheme.SUBTITLE_FONT);

        subtitle.setForeground(AppTheme.TEXT);

        subtitle.setBounds(
                315,
                135,
                600,
                30
        );

        add(subtitle);


        // =========================
        // DELETE CARD
        // =========================

        JPanel deletePanel =
                new JPanel(null);

        deletePanel.setBackground(
                AppTheme.CARD_BACKGROUND
        );

        deletePanel.setBorder(
                BorderFactory.createLineBorder(
                        AppTheme.BORDER,
                        1
                )
        );

        deletePanel.setBounds(
                270,
                200,
                550,
                190
        );

        add(deletePanel);


        // =========================
        // EQUIPMENT ID
        // =========================

        JLabel idLabel =
                new JLabel("Equipment ID:");

        idLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        idLabel.setForeground(
                AppTheme.TEXT
        );

        idLabel.setBounds(
                50,
                40,
                140,
                35
        );

        deletePanel.add(idLabel);


        idField =
                new JTextField();

        idField.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        idField.setBounds(
                190,
                40,
                300,
                35
        );

        deletePanel.add(idField);


        // =========================
        // BUTTONS
        // =========================

        JButton deleteButton =
                new JButton(
                        "Delete Equipment"
                );


        JButton backButton =
                new JButton(
                        "Back"
                );


        deleteButton.setBounds(
                140,
                110,
                170,
                45
        );


        backButton.setBounds(
                330,
                110,
                100,
                45
        );


        JButton[] buttons = {

                deleteButton,
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


        deletePanel.add(
                deleteButton
        );

        deletePanel.add(
                backButton
        );


        // =========================
        // DELETE BUTTON ACTION
        // =========================

        deleteButton.addActionListener(
                e -> deleteEquipment()
        );


        // =========================
        // BACK BUTTON ACTION
        // =========================

        backButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "Equipment"
                )
        );

    }


    // =========================
    // DELETE EQUIPMENT METHOD
    // =========================

    private void deleteEquipment() {

        try {

            if (
                    idField.getText()
                            .trim()
                            .isEmpty()
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Equipment ID"
                );

                return;

            }


            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to delete this equipment?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );


            if (
                    choice !=
                            JOptionPane.YES_OPTION
            ) {

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
                            "DELETE FROM equipment WHERE id=?"
                    );


            statement.setInt(
                    1,
                    Integer.parseInt(
                            idField.getText()
                                    .trim()
                    )
            );


            int rows =
                    statement.executeUpdate();


            connection.close();


            if (
                    rows > 0
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Equipment Deleted Successfully!"
                );


                idField.setText(
                        ""
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Equipment Not Found"
                );

            }


        } catch (
                NumberFormatException ex
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Equipment ID"
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