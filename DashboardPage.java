package pages;

import database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardPage extends JPanel {

    // ============================================================
    // VARIABLES
    // ============================================================

    String role;

    CardLayout cardLayout;

    JPanel contentPanel;

    JLabel totalEquipmentValue;
    JLabel totalQuantityValue;
    JLabel maintenanceValue;
    JLabel availableValue;

    JButton notificationButton;


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public DashboardPage(
            String role,
            CardLayout cardLayout,
            JPanel contentPanel
    ) {

        this.role = role;

        this.cardLayout = cardLayout;

        this.contentPanel = contentPanel;


        // ========================================================
        // PANEL SETTINGS
        // ========================================================

        setLayout(null);

        setBackground(
                AppTheme.BACKGROUND
        );


        // ========================================================
        // TITLE
        // ========================================================

        JLabel title =
                new JLabel(
                        "DASHBOARD"
                );

        title.setFont(
                AppTheme.TITLE_FONT
        );

        title.setForeground(
                AppTheme.TEXT
        );

        title.setBounds(
                60,
                35,
                500,
                50
        );

        add(title);


        // ========================================================
        // WELCOME
        // ========================================================

        JLabel welcome =
                new JLabel(
                        "Welcome to Medical Equipment Inventory System - "
                                + role
                );

        welcome.setFont(
                AppTheme.SUBTITLE_FONT
        );

        welcome.setForeground(
                AppTheme.TEXT
        );

        welcome.setBounds(
                60,
                90,
                700,
                30
        );

        add(welcome);


        // ========================================================
        // NOTIFICATION BELL
        // ========================================================

        notificationButton =
                new JButton();


        notificationButton.setIcon(
                new BellIcon(false)
        );


        notificationButton.setBounds(
                1135,
                30,
                55,
                55
        );


        notificationButton.setFocusPainted(
                false
        );


        notificationButton.setBorderPainted(
                false
        );


        notificationButton.setContentAreaFilled(
                false
        );


        notificationButton.setOpaque(
                false
        );


        notificationButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        notificationButton.setToolTipText(
                "Pending Equipment Requests"
        );


        add(notificationButton);


        // ========================================================
        // NOTIFICATION BUTTON ACTION
        // ========================================================

        notificationButton.addActionListener(
                e -> {

                    cardLayout.show(
                            contentPanel,
                            "Notifications"
                    );


                    refreshNotificationsPage();

                }
        );


        // ========================================================
        // DASHBOARD CARDS
        // ========================================================

        totalEquipmentValue =
                createCard(
                        "TOTAL EQUIPMENT",
                        60
                );


        totalQuantityValue =
                createCard(
                        "TOTAL QUANTITY",
                        300
                );


        maintenanceValue =
                createCard(
                        "UNDER MAINTENANCE",
                        540
                );


        availableValue =
                createCard(
                        "AVAILABLE QUANTITY",
                        780
                );


        // ========================================================
        // REFRESH BUTTON
        // ========================================================

        JButton refreshButton =
                new JButton(
                        "Refresh Dashboard"
                );


        refreshButton.setBounds(
                60,
                410,
                200,
                45
        );


        styleButton(
                refreshButton
        );


        add(refreshButton);


        refreshButton.addActionListener(
                e -> loadDashboard()
        );


        // ========================================================
        // LOAD DASHBOARD DATA
        // ========================================================

        loadDashboard();
    }


    // ============================================================
    // CREATE DASHBOARD CARD
    // ============================================================

    private JLabel createCard(
            String title,
            int x
    ) {

        JPanel card =
                new JPanel(null);


        card.setBackground(
                AppTheme.CARD_BACKGROUND
        );


        card.setBorder(
                BorderFactory.createLineBorder(
                        AppTheme.BORDER,
                        1
                )
        );


        card.setBounds(
                x,
                170,
                220,
                160
        );


        // --------------------------------------------------------
        // CARD TITLE
        // --------------------------------------------------------

        JLabel cardTitle =
                new JLabel(
                        title
                );


        cardTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );


        cardTitle.setForeground(
                AppTheme.TEXT
        );


        cardTitle.setBounds(
                15,
                25,
                200,
                30
        );


        card.add(
                cardTitle
        );


        // --------------------------------------------------------
        // CARD VALUE
        // --------------------------------------------------------

        JLabel value =
                new JLabel(
                        "0"
                );


        value.setFont(
                AppTheme.CARD_NUMBER_FONT
        );


        value.setForeground(
                AppTheme.PRIMARY
        );


        value.setBounds(
                15,
                70,
                180,
                55
        );


        card.add(
                value
        );


        add(card);


        return value;
    }


    // ============================================================
    // LOAD DASHBOARD
    // ============================================================

    public void loadDashboard() {

        try {

            Connection connection =
                    DBConnection.getConnection();


            if (connection == null) {

                return;

            }


            int totalEquipment = 0;

            int totalQuantity = 0;

            int underMaintenance = 0;

            int pendingRequests = 0;


            // ====================================================
            // TOTAL EQUIPMENT + TOTAL QUANTITY
            // ====================================================

            PreparedStatement statement =
                    connection.prepareStatement(
                            "SELECT COUNT(*) AS total_equipment, "
                                    + "COALESCE(SUM(quantity),0) AS total_quantity "
                                    + "FROM equipment"
                    );


            ResultSet result =
                    statement.executeQuery();


            if (result.next()) {

                totalEquipment =
                        result.getInt(
                                "total_equipment"
                        );


                totalQuantity =
                        result.getInt(
                                "total_quantity"
                        );

            }


            result.close();

            statement.close();


            // ====================================================
            // UNDER MAINTENANCE
            // ====================================================

            statement =
                    connection.prepareStatement(
                            "SELECT COALESCE("
                                    + "SUM(maintenance_quantity),0"
                                    + ") AS total "
                                    + "FROM maintenance "
                                    + "WHERE status != 'Completed'"
                    );


            result =
                    statement.executeQuery();


            if (result.next()) {

                underMaintenance =
                        result.getInt(
                                "total"
                        );

            }


            result.close();

            statement.close();


            // ====================================================
            // PENDING EQUIPMENT REQUESTS
            // ====================================================

            statement =
                    connection.prepareStatement(
                            "SELECT COUNT(*) AS total "
                                    + "FROM equipment_requests "
                                    + "WHERE status='Pending'"
                    );


            result =
                    statement.executeQuery();


            if (result.next()) {

                pendingRequests =
                        result.getInt(
                                "total"
                        );

            }


            result.close();

            statement.close();


            // ====================================================
            // AVAILABLE QUANTITY
            // ====================================================

            int availableQuantity =
                    totalQuantity
                            - underMaintenance;


            if (availableQuantity < 0) {

                availableQuantity = 0;

            }


            // ====================================================
            // UPDATE DASHBOARD VALUES
            // ====================================================

            totalEquipmentValue.setText(
                    String.valueOf(
                            totalEquipment
                    )
            );


            totalQuantityValue.setText(
                    String.valueOf(
                            totalQuantity
                    )
            );


            maintenanceValue.setText(
                    String.valueOf(
                            underMaintenance
                    )
            );


            availableValue.setText(
                    String.valueOf(
                            availableQuantity
                    )
            );


            // ====================================================
            // UPDATE NOTIFICATION BELL
            // ====================================================

            if (pendingRequests > 0) {

                notificationButton.setIcon(
                        new BellIcon(true)
                );

            } else {

                notificationButton.setIcon(
                        new BellIcon(false)
                );

            }


            notificationButton.repaint();


            connection.close();


        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading dashboard: "
                            + ex.getMessage()
            );

        }
    }


    // ============================================================
    // REFRESH NOTIFICATION PAGE
    // ============================================================

    private void refreshNotificationsPage() {

        for (
                Component component :
                contentPanel.getComponents()
        ) {

            if (
                    component instanceof
                            NotificationPage
            ) {

                (
                        (NotificationPage)
                                component
                ).loadNotifications();


                break;

            }

        }
    }


    // ============================================================
    // BUTTON STYLE
    // ============================================================

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


    // ============================================================
    // CUSTOM BELL ICON
    // ============================================================

    private static class BellIcon
            implements Icon {

        private final boolean showDot;


        BellIcon(
                boolean showDot
        ) {

            this.showDot =
                    showDot;

        }


        @Override
        public void paintIcon(
                Component component,
                Graphics graphics,
                int x,
                int y
        ) {

            Graphics2D g =
                    (Graphics2D)
                            graphics.create();


            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );


            // ====================================================
            // BELL
            // ====================================================

            g.setColor(
                    AppTheme.TEXT
            );


            // Bell body

            g.fillArc(
                    x + 15,
                    y + 10,
                    25,
                    28,
                    0,
                    180
            );


            g.fillRect(
                    x + 15,
                    y + 24,
                    25,
                    12
            );


            // Bell bottom

            g.fillOval(
                    x + 11,
                    y + 32,
                    33,
                    8
            );


            // Bell clapper

            g.fillOval(
                    x + 24,
                    y + 38,
                    7,
                    7
            );


            // ====================================================
            // RED NOTIFICATION DOT
            // ====================================================

            if (showDot) {

                g.setColor(
                        new Color(
                                220,
                                45,
                                45
                        )
                );


                g.fillOval(
                        x + 36,
                        y + 4,
                        12,
                        12
                );


                // Small white border

                g.setColor(
                        Color.WHITE
                );


                g.drawOval(
                        x + 36,
                        y + 4,
                        12,
                        12
                );

            }


            g.dispose();
        }


        @Override
        public int getIconWidth() {

            return 55;

        }


        @Override
        public int getIconHeight() {

            return 55;

        }
    }
}