package pages;

import database.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class ViewEquipmentPage extends JPanel {

    DefaultTableModel model;
    JTable table;

    public ViewEquipmentPage(
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
                        "VIEW EQUIPMENT"
                );

        title.setFont(
                AppTheme.TITLE_FONT
        );

        title.setForeground(
                AppTheme.TEXT
        );


        JLabel subtitle =
                new JLabel(
                        "View and manage all medical equipment records"
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

                "ID",
                "Equipment Name",
                "Category",
                "Total Quantity",
                "Under Maintenance",
                "Available Quantity",
                "Price",
                "Supplier"

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
                .setPreferredWidth(70);

        table.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(180);

        table.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(140);

        table.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(120);

        table.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(140);

        table.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(130);

        table.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(100);

        table.getColumnModel()
                .getColumn(7)
                .setPreferredWidth(150);


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
                e -> loadEquipment()
        );


        backButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "Equipment"
                )
        );


        loadEquipment();

    }


    // =========================
    // LOAD EQUIPMENT DATA
    // =========================

    public void loadEquipment() {

        model.setRowCount(
                0
        );


        String query =

                "SELECT e.id, e.name, e.category, " +
                        "e.quantity, e.price, e.supplier, " +

                        "COALESCE(SUM(CASE " +
                        "WHEN m.status IN ('Pending', 'In Progress') " +
                        "THEN m.maintenance_quantity " +
                        "ELSE 0 END), 0) AS under_maintenance " +

                        "FROM equipment e " +

                        "LEFT JOIN maintenance m " +
                        "ON e.id = m.equipment_id " +

                        "GROUP BY e.id, e.name, e.category, " +
                        "e.quantity, e.price, e.supplier " +

                        "ORDER BY e.id DESC";


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

                    int quantity =
                            result.getInt(
                                    "quantity"
                            );


                    int under =
                            result.getInt(
                                    "under_maintenance"
                            );


                    int available =
                            quantity - under;


                    if (
                            available < 0
                    ) {

                        available = 0;

                    }


                    model.addRow(
                            new Object[]{

                                    result.getInt(
                                            "id"
                                    ),

                                    result.getString(
                                            "name"
                                    ),

                                    result.getString(
                                            "category"
                                    ),

                                    quantity,

                                    under,

                                    available,

                                    result.getDouble(
                                            "price"
                                    ),

                                    result.getString(
                                            "supplier"
                                    )

                            }
                    );

                }

            }

        } catch (
                Exception e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " +
                            e.getMessage()
            );

        }

    }

}