package pages;

import database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DeleteMaintenancePage extends JPanel {

    private JTextField idField;


    public DeleteMaintenancePage(
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
                        "DELETE MAINTENANCE"
                );

        title.setFont(
                AppTheme.TITLE_FONT
        );

        title.setForeground(
                AppTheme.TEXT
        );

        title.setBounds(
                60,
                60,
                500,
                50
        );

        add(title);


        JLabel subtitle =
                new JLabel(
                        "Remove a maintenance record from the system"
                );

        subtitle.setFont(
                AppTheme.SUBTITLE_FONT
        );

        subtitle.setForeground(
                AppTheme.TEXT
        );

        subtitle.setBounds(
                60,
                110,
                600,
                30
        );

        add(subtitle);


        // =========================
        // DELETE FORM CARD
        // =========================

        JPanel formCard =
                new JPanel(
                        null
                );

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
                250,
                200,
                500,
                180
        );

        add(formCard);


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
                40,
                150,
                35
        );

        formCard.add(idLabel);


        idField =
                new JTextField();

        idField.setFont(
                AppTheme.FIELD_FONT
        );

        idField.setBounds(
                200,
                40,
                230,
                35
        );

        formCard.add(idField);


        JLabel warning =
                new JLabel(
                        "This action cannot be undone."
                );

        warning.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        warning.setForeground(
                AppTheme.TEXT
        );

        warning.setBounds(
                200,
                85,
                250,
                30
        );

        formCard.add(warning);


        // =========================
        // BUTTONS
        // =========================

        JButton deleteButton =
                new JButton(
                        "Delete Record"
                );

        JButton backButton =
                new JButton(
                        "Back"
                );


        styleButton(
                deleteButton
        );

        styleButton(
                backButton
        );


        deleteButton.setBounds(
                350,
                430,
                170,
                45
        );

        backButton.setBounds(
                540,
                430,
                140,
                45
        );


        add(deleteButton);

        add(backButton);


        // =========================
        // DELETE ACTION
        // =========================

        deleteButton.addActionListener(
                e -> deleteMaintenance()
        );


        // =========================
        // BACK ACTION
        // =========================

        backButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "Maintenance"
                )
        );

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
    // DELETE MAINTENANCE
    // =========================

    private void deleteMaintenance() {

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


            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to delete this maintenance record?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );


            if (
                    choice != JOptionPane.YES_OPTION
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

                            "DELETE FROM maintenance " +
                                    "WHERE maintenance_id=?"

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
                        "Maintenance Record Deleted Successfully!"
                );


                idField.setText(
                        ""
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
                    "Please enter a valid numeric Maintenance ID"
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