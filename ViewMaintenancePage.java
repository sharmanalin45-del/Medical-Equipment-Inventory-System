package pages;

import database.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class ViewMaintenancePage extends JPanel {

    DefaultTableModel model;
    JTable table;

    public ViewMaintenancePage(
            CardLayout cardLayout,
            JPanel contentPanel
    ) {

        setLayout(
                new BorderLayout()
        );

        setBackground(
                AppTheme.BACKGROUND
        );


        // =========================
        // TITLE PANEL
        // =========================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );

        topPanel.setBackground(
                AppTheme.BACKGROUND
        );

        topPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        40,
                        15,
                        40
                )
        );


        JLabel title =
                new JLabel(
                        "VIEW MAINTENANCE"
                );

        title.setFont(
                AppTheme.TITLE_FONT
        );

        title.setForeground(
                AppTheme.TEXT
        );


        JLabel subtitle =
                new JLabel(
                        "View all equipment maintenance records"
                );

        subtitle.setFont(
                AppTheme.SUBTITLE_FONT
        );

        subtitle.setForeground(
                AppTheme.TEXT
        );


        JPanel titleText =
                new JPanel(
                        new GridLayout(
                                2,
                                1
                        )
                );

        titleText.setBackground(
                AppTheme.BACKGROUND
        );

        titleText.add(
                title
        );

        titleText.add(
                subtitle
        );


        topPanel.add(
                titleText,
                BorderLayout.WEST
        );


        add(
                topPanel,
                BorderLayout.NORTH
        );


        // =========================
        // TABLE
        // =========================

        String[] columns = {

                "Maintenance ID",
                "Equipment ID",
                "Equipment Name",
                "Maintenance Quantity",
                "Maintenance Date",
                "Description",
                "Status"

        };


        model =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;

                    }

                };


        table =
                new JTable(
                        model
                );


        table.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );


        table.setRowHeight(
                32
        );


        table.setForeground(
                AppTheme.TEXT
        );


        table.setBackground(
                AppTheme.CARD_BACKGROUND
        );


        table.setSelectionBackground(
                AppTheme.BUTTON
        );


        table.setSelectionForeground(
                AppTheme.LIGHT_TEXT
        );


        JTableHeader header =
                table.getTableHeader();


        header.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );


        header.setBackground(
                AppTheme.PRIMARY
        );


        header.setForeground(
                AppTheme.LIGHT_TEXT
        );


        header.setReorderingAllowed(
                false
        );


        // =========================
        // TABLE COLUMN WIDTH
        // =========================

        table.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(110);

        table.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(100);

        table.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(160);

        table.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(160);

        table.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(140);

        table.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(250);

        table.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(120);


        JScrollPane scrollPane =
                new JScrollPane(
                        table
                );


        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        AppTheme.BORDER,
                        1
                )
        );


        JPanel tablePanel =
                new JPanel(
                        new BorderLayout()
                );


        tablePanel.setBackground(
                AppTheme.BACKGROUND
        );


        tablePanel.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        40,
                        15,
                        40
                )
        );


        tablePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        add(
                tablePanel,
                BorderLayout.CENTER
        );


        // =========================
        // BOTTOM BUTTON PANEL
        // =========================

        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                15
                        )
                );


        bottom.setBackground(
                AppTheme.BACKGROUND
        );


        JButton refreshButton =
                new JButton(
                        "Refresh"
                );


        JButton backButton =
                new JButton(
                        "Back"
                );


        JButton[] buttons = {

                refreshButton,
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

            button.setPreferredSize(
                    new Dimension(
                            140,
                            42
                    )
            );

        }


        bottom.add(
                refreshButton
        );

        bottom.add(
                backButton
        );


        add(
                bottom,
                BorderLayout.SOUTH
        );


        // =========================
        // BUTTON ACTIONS
        // =========================

        refreshButton.addActionListener(
                e -> loadMaintenance()
        );


        backButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "Maintenance"
                )
        );


        loadMaintenance();

    }


    // =========================
    // LOAD MAINTENANCE DATA
    // =========================

    public void loadMaintenance() {

        model.setRowCount(
                0
        );


        String query =
                "SELECT * FROM maintenance " +
                        "ORDER BY maintenance_id DESC";


        try (
                Connection connection =
                        DBConnection.getConnection()
        ) {

            if (
                    connection == null
            ) {

                throw new Exception(
                        "Database connection failed"
                );

            }


            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    query
                            );

                    ResultSet result =
                            statement.executeQuery()
            ) {

                while (
                        result.next()
                ) {

                    model.addRow(
                            new Object[]{

                                    result.getInt(
                                            "maintenance_id"
                                    ),

                                    result.getInt(
                                            "equipment_id"
                                    ),

                                    result.getString(
                                            "equipment_name"
                                    ),

                                    result.getInt(
                                            "maintenance_quantity"
                                    ),

                                    result.getString(
                                            "maintenance_date"
                                    ),

                                    result.getString(
                                            "description"
                                    ),

                                    result.getString(
                                            "status"
                                    )

                            }
                    );

                }

            }

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