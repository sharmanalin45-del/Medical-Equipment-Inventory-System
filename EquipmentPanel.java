package pages;

import javax.swing.*;
import java.awt.*;

public class EquipmentPanel extends JPanel {

    public EquipmentPanel(
            CardLayout cardLayout,
            JPanel contentPanel,
            String role
    ) {

        setLayout(null);

        setBackground(AppTheme.BACKGROUND);


        JLabel title =
                new JLabel("EQUIPMENT MANAGEMENT");

        title.setFont(AppTheme.TITLE_FONT);

        title.setForeground(AppTheme.TEXT);

        title.setBounds(
                300,
                80,
                600,
                50
        );

        add(title);


        JLabel subtitle =
                new JLabel(
                        "Manage medical equipment records"
                );

        subtitle.setFont(
                AppTheme.SUBTITLE_FONT
        );

        subtitle.setForeground(
                AppTheme.TEXT
        );

        subtitle.setBounds(
                360,
                135,
                500,
                30
        );

        add(subtitle);


        JButton addButton =
                new JButton("Add Equipment");

        JButton viewButton =
                new JButton("View Equipment");

        JButton updateButton =
                new JButton("Update Equipment");

        JButton deleteButton =
                new JButton("Delete Equipment");


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


        JButton[] buttons = {
                addButton,
                viewButton,
                updateButton,
                deleteButton
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


        // STAFF PERMISSIONS

        if (role.equalsIgnoreCase("Staff")) {

            addButton.setEnabled(false);

            updateButton.setEnabled(false);

            deleteButton.setEnabled(false);

        }


        add(addButton);

        add(viewButton);

        add(updateButton);

        add(deleteButton);


        // BUTTON NAVIGATION

        addButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "addEquipment"
                )
        );


        viewButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "viewEquipment"
                )
        );


        updateButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "updateEquipment"
                )
        );


        deleteButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "deleteEquipment"
                )
        );

    }
}