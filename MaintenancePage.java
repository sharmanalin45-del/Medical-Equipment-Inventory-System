package pages;

import javax.swing.*;
import java.awt.*;

public class MaintenancePage extends JPanel {

    public MaintenancePage(
            CardLayout cardLayout,
            JPanel contentPanel,
            String role
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
                        "MAINTENANCE MANAGEMENT"
                );

        title.setFont(
                AppTheme.TITLE_FONT
        );

        title.setForeground(
                AppTheme.TEXT
        );

        title.setBounds(
                290,
                70,
                600,
                50
        );

        add(title);


        // =========================
        // SUBTITLE
        // =========================

        JLabel subtitle =
                new JLabel(
                        "Manage equipment maintenance records"
                );

        subtitle.setFont(
                AppTheme.SUBTITLE_FONT
        );

        subtitle.setForeground(
                AppTheme.TEXT
        );

        subtitle.setBounds(
                340,
                125,
                500,
                30
        );

        add(subtitle);


        // =========================
        // BUTTONS
        // =========================

        JButton addButton =
                new JButton(
                        "Add Maintenance"
                );

        JButton viewButton =
                new JButton(
                        "View Maintenance"
                );

        JButton updateButton =
                new JButton(
                        "Update Maintenance"
                );

        JButton deleteButton =
                new JButton(
                        "Delete Maintenance"
                );


        addButton.setBounds(
                280,
                220,
                250,
                55
        );


        viewButton.setBounds(
                560,
                220,
                250,
                55
        );


        updateButton.setBounds(
                280,
                310,
                250,
                55
        );


        deleteButton.setBounds(
                560,
                310,
                250,
                55
        );


        // =========================
        // STAFF RESTRICTIONS
        // =========================

        if (
                role.equalsIgnoreCase(
                        "Staff"
                )
        ) {

            addButton.setEnabled(
                    false
            );

            updateButton.setEnabled(
                    false
            );

            deleteButton.setEnabled(
                    false
            );

        }


        // =========================
        // BUTTON THEME
        // =========================

        JButton[] buttons = {

                addButton,
                viewButton,
                updateButton,
                deleteButton

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


        // =========================
        // ADD BUTTONS
        // =========================

        add(addButton);

        add(viewButton);

        add(updateButton);

        add(deleteButton);


        // =========================
        // BUTTON ACTIONS
        // =========================

        addButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "addMaintenance"
                )
        );


        viewButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "viewMaintenance"
                )
        );


        updateButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "updateMaintenance"
                )
        );


        deleteButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "deleteMaintenance"
                )
        );

    }

}