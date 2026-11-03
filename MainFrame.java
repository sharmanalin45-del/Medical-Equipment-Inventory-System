
package pages;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    // ============================================================
    // VARIABLES
    // ============================================================

    private CardLayout cardLayout;
    private JPanel contentPanel;

    private String role;


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public MainFrame(String role) {

        this.role = role;

        // ========================================================
        // FRAME SETTINGS
        // ========================================================

        setTitle("Medical Equipment Inventory System");

        setSize(1200, 700);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());


        // ========================================================
        // MAIN PANEL
        // ========================================================

        JPanel mainPanel = new JPanel(new BorderLayout());


        // ========================================================
        // SIDEBAR
        // ========================================================

        JPanel sidebar = new JPanel(null);

        sidebar.setPreferredSize(
                new Dimension(220, 700)
        );

        sidebar.setBackground(
                AppTheme.PRIMARY
        );


        // ========================================================
        // SIDEBAR TITLE
        // ========================================================

        JLabel title = new JLabel("MEDICAL");

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        title.setBounds(
                40,
                25,
                160,
                35
        );

        sidebar.add(title);


        // ========================================================
        // SIDEBAR SUBTITLE
        // ========================================================

        JLabel subtitle =
                new JLabel("EQUIPMENT SYSTEM");

        subtitle.setForeground(
                new Color(
                        200,
                        220,
                        215
                )
        );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        subtitle.setBounds(
                40,
                55,
                170,
                30
        );

        sidebar.add(subtitle);


        // ========================================================
        // SIDEBAR BUTTONS
        // ========================================================

        JButton dashboardButton =
                createSidebarButton(
                        "Dashboard",
                        130
                );


        JButton equipmentButton =
                createSidebarButton(
                        "Equipment",
                        190
                );


        JButton maintenanceButton =
                createSidebarButton(
                        "Maintenance",
                        250
                );


        JButton requestButton =
                createSidebarButton(
                        "Equipment Request",
                        310
                );


        JButton logoutButton =
                createSidebarButton(
                        "Logout",
                        570
                );


        // ========================================================
        // ADD BUTTONS TO SIDEBAR
        // ========================================================

        sidebar.add(dashboardButton);

        sidebar.add(equipmentButton);

        sidebar.add(maintenanceButton);

        sidebar.add(requestButton);

        sidebar.add(logoutButton);


        // ========================================================
        // CARD LAYOUT
        // ========================================================

        cardLayout =
                new CardLayout();


        contentPanel =
                new JPanel(cardLayout);


        contentPanel.setBackground(
                AppTheme.BACKGROUND
        );


        // ========================================================
        // DASHBOARD PAGE
        // ========================================================

        DashboardPage dashboardPage =
                new DashboardPage(
                        role,
                        cardLayout,
                        contentPanel
                );


        contentPanel.add(
                dashboardPage,
                "Dashboard"
        );


        // ========================================================
        // EQUIPMENT PAGE
        // ========================================================

        EquipmentPanel equipmentPanel =
                new EquipmentPanel(
                        cardLayout,
                        contentPanel,
                        role
                );


        contentPanel.add(
                equipmentPanel,
                "Equipment"
        );


        // ========================================================
        // ADD EQUIPMENT PAGE
        // ========================================================

        AddEquipmentPage addEquipmentPage =
                new AddEquipmentPage(
                        cardLayout,
                        contentPanel
                );


        contentPanel.add(
                addEquipmentPage,
                "addEquipment"
        );


        // ========================================================
        // VIEW EQUIPMENT PAGE
        // ========================================================

        ViewEquipmentPage viewEquipmentPage =
                new ViewEquipmentPage(
                        cardLayout,
                        contentPanel
                );


        contentPanel.add(
                viewEquipmentPage,
                "viewEquipment"
        );


        // ========================================================
        // UPDATE EQUIPMENT PAGE
        // ========================================================

        UpdateEquipmentPage updateEquipmentPage =
                new UpdateEquipmentPage(
                        cardLayout,
                        contentPanel
                );


        contentPanel.add(
                updateEquipmentPage,
                "updateEquipment"
        );


        // ========================================================
        // DELETE EQUIPMENT PAGE
        // ========================================================

        DeleteEquipmentPage deleteEquipmentPage =
                new DeleteEquipmentPage(
                        cardLayout,
                        contentPanel
                );


        contentPanel.add(
                deleteEquipmentPage,
                "deleteEquipment"
        );


        // ========================================================
        // MAINTENANCE PAGE
        // ========================================================

        MaintenancePage maintenancePage =
                new MaintenancePage(
                        cardLayout,
                        contentPanel,
                        role
                );


        contentPanel.add(
                maintenancePage,
                "Maintenance"
        );


        // ========================================================
        // ADD MAINTENANCE PAGE
        // ========================================================

        AddMaintenancePage addMaintenancePage =
                new AddMaintenancePage(
                        cardLayout,
                        contentPanel
                );


        contentPanel.add(
                addMaintenancePage,
                "addMaintenance"
        );


        // ========================================================
        // VIEW MAINTENANCE PAGE
        // ========================================================

        ViewMaintenancePage viewMaintenancePage =
                new ViewMaintenancePage(
                        cardLayout,
                        contentPanel
                );


        contentPanel.add(
                viewMaintenancePage,
                "viewMaintenance"
        );


        // ========================================================
        // UPDATE MAINTENANCE PAGE
        // ========================================================

        UpdateMaintenancePage updateMaintenancePage =
                new UpdateMaintenancePage(
                        cardLayout,
                        contentPanel
                );


        contentPanel.add(
                updateMaintenancePage,
                "updateMaintenance"
        );


        // ========================================================
        // DELETE MAINTENANCE PAGE
        // ========================================================

        DeleteMaintenancePage deleteMaintenancePage =
                new DeleteMaintenancePage(
                        cardLayout,
                        contentPanel
                );


        contentPanel.add(
                deleteMaintenancePage,
                "deleteMaintenance"
        );


        // ========================================================
        // EQUIPMENT REQUEST PAGE
        // ========================================================

        EquipmentRequestPage equipmentRequestPage =
                new EquipmentRequestPage(
                        cardLayout,
                        contentPanel,
                        role
                );


        contentPanel.add(
                equipmentRequestPage,
                "EquipmentRequest"
        );


        // ========================================================
        // VIEW REQUESTS PAGE (FIXED)
        // ========================================================

        ViewRequestsPage viewRequestsPage =
                new ViewRequestsPage(
                        cardLayout,
                        contentPanel,
                        role
                );


        contentPanel.add(
                viewRequestsPage,
                "ViewRequests"
        );


        // ========================================================
        // REQUEST EQUIPMENT PAGE
        // ========================================================

        RequestEquipmentPage requestEquipmentPage =
                new RequestEquipmentPage(
                        cardLayout,
                        contentPanel,
                        role
                );


        contentPanel.add(
                requestEquipmentPage,
                "RequestEquipment"
        );


        // ========================================================
        // NOTIFICATION PAGE
        // ========================================================

        NotificationPage notificationPage =
                new NotificationPage(
                        cardLayout,
                        contentPanel,
                        role
                );


        contentPanel.add(
                notificationPage,
                "Notifications"
        );


        // ========================================================
        // DASHBOARD BUTTON
        // ========================================================

        dashboardButton.addActionListener(
                e -> {

                    cardLayout.show(
                            contentPanel,
                            "Dashboard"
                    );

                }
        );


        // ========================================================
        // EQUIPMENT BUTTON
        // ========================================================

        equipmentButton.addActionListener(
                e -> {

                    cardLayout.show(
                            contentPanel,
                            "Equipment"
                    );

                }
        );


        // ========================================================
        // MAINTENANCE BUTTON
        // ========================================================

        maintenanceButton.addActionListener(
                e -> {

                    cardLayout.show(
                            contentPanel,
                            "Maintenance"
                    );

                }
        );


        // ========================================================
        // EQUIPMENT REQUEST BUTTON
        // ========================================================

        requestButton.addActionListener(
                e -> {

                    cardLayout.show(
                            contentPanel,
                            "EquipmentRequest"
                    );

                }
        );


        // ========================================================
        // LOGOUT BUTTON
        // ========================================================

        logoutButton.addActionListener(
                e -> {

                    int choice =
                            JOptionPane.showConfirmDialog(
                                    this,
                                    "Are you sure you want to logout?",
                                    "Logout",
                                    JOptionPane.YES_NO_OPTION
                            );


                    if (
                            choice ==
                                    JOptionPane.YES_OPTION
                    ) {

                        dispose();

                        new LoginPage();

                    }

                }
        );


        // ========================================================
        // ADD SIDEBAR + CONTENT
        // ========================================================

        mainPanel.add(
                sidebar,
                BorderLayout.WEST
        );


        mainPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );


        add(mainPanel);


        // ========================================================
        // SHOW DASHBOARD FIRST
        // ========================================================

        cardLayout.show(
                contentPanel,
                "Dashboard"
        );


        // ========================================================
        // SHOW FRAME
        // ========================================================

        setVisible(true);
    }


    // ============================================================
    // CREATE SIDEBAR BUTTON
    // ============================================================

    private JButton createSidebarButton(
            String text,
            int y
    ) {

        JButton button =
                new JButton(text);


        button.setBounds(
                20,
                y,
                180,
                45
        );


        button.setBackground(
                AppTheme.BUTTON
        );


        button.setForeground(
                Color.WHITE
        );


        button.setFocusPainted(false);

        button.setBorderPainted(false);


        button.setFont(
                AppTheme.BUTTON_FONT
        );


        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        return button;
    }
}
