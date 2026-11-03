package pages;

import database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class NotificationPage extends JPanel {

    CardLayout cardLayout;
    JPanel contentPanel;
    String role;

    JPanel notificationPanel;


    public NotificationPage(
            CardLayout cardLayout,
            JPanel contentPanel,
            String role
    ) {

        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        this.role = role;

        setLayout(null);

        setBackground(
                AppTheme.BACKGROUND
        );


        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel(
                        "NOTIFICATIONS"
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


        // =========================
        // SUBTITLE
        // =========================

        JLabel subtitle =
                new JLabel(
                        "Pending equipment requests"
                );

        subtitle.setFont(
                AppTheme.SUBTITLE_FONT
        );

        subtitle.setForeground(
                AppTheme.TEXT
        );

        subtitle.setBounds(
                60,
                90,
                500,
                30
        );

        add(subtitle);


        // =========================
        // NOTIFICATION PANEL
        // =========================

        notificationPanel =
                new JPanel();

        notificationPanel.setLayout(
                new BoxLayout(
                        notificationPanel,
                        BoxLayout.Y_AXIS
                )
        );

        notificationPanel.setBackground(
                AppTheme.BACKGROUND
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        notificationPanel
                );

        scrollPane.setBounds(
                60,
                140,
                1050,
                400
        );

        scrollPane.setBorder(
                null
        );

        scrollPane.getViewport().setBackground(
                AppTheme.BACKGROUND
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(
                        15
                );

        add(scrollPane);


        // =========================
        // REFRESH BUTTON
        // =========================

        JButton refreshButton =
                new JButton(
                        "Refresh"
                );

        refreshButton.setBounds(
                60,
                570,
                170,
                45
        );


        // =========================
        // BACK BUTTON
        // =========================

        JButton backButton =
                new JButton(
                        "Back"
                );

        backButton.setBounds(
                250,
                570,
                170,
                45
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

        }


        add(refreshButton);

        add(backButton);


        // =========================
        // BUTTON ACTIONS
        // =========================

        refreshButton.addActionListener(
                e -> loadNotifications()
        );


        backButton.addActionListener(

                e -> cardLayout.show(
                        contentPanel,
                        "Dashboard"
                )

        );


        loadNotifications();

    }


    // =========================
    // LOAD NOTIFICATIONS
    // =========================

    public void loadNotifications() {

        notificationPanel.removeAll();


        try {

            Connection connection =
                    DBConnection.getConnection();


            if (
                    connection == null
            ) {

                showMessage(
                        "Database connection failed",
                        Color.RED
                );

                return;

            }


            String query =
                    "SELECT request_id " +
                            "FROM equipment_requests " +
                            "WHERE status = 'Pending'";


            PreparedStatement statement =
                    connection.prepareStatement(
                            query
                    );


            ResultSet result =
                    statement.executeQuery();


            boolean found =
                    false;


            while (
                    result.next()
            ) {

                found =
                        true;


                int requestId =
                        result.getInt(
                                "request_id"
                        );


                addNotificationCard(
                        requestId
                );

            }


            if (
                    !found
            ) {

                showMessage(
                        "No pending equipment requests",
                        AppTheme.TEXT
                );

            }


            result.close();

            statement.close();

            connection.close();


        } catch (
                Exception e
        ) {

            e.printStackTrace();


            showMessage(
                    "Unable to load notifications",
                    Color.RED
            );

        }


        notificationPanel.revalidate();

        notificationPanel.repaint();

    }


    // =========================
    // NOTIFICATION CARD
    // =========================

    private void addNotificationCard(
            int requestId
    ) {

        JPanel card =
                new JPanel(
                        null
                );


        card.setBackground(
                AppTheme.CARD_BACKGROUND
        );


        card.setBorder(

                BorderFactory.createLineBorder(
                        AppTheme.BORDER,
                        1
                )

        );


        card.setPreferredSize(
                new Dimension(
                        950,
                        100
                )
        );


        card.setMaximumSize(
                new Dimension(
                        950,
                        100
                )
        );


        card.setMinimumSize(
                new Dimension(
                        950,
                        100
                )
        );


        // =========================
        // NOTIFICATION TITLE
        // =========================

        JLabel notificationTitle =
                new JLabel(
                        "Equipment Request"
                );


        notificationTitle.setFont(

                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )

        );


        notificationTitle.setForeground(
                AppTheme.PRIMARY
        );


        notificationTitle.setBounds(
                25,
                32,
                400,
                35
        );


        card.add(
                notificationTitle
        );


        // =========================
        // SEE DETAILS BUTTON
        // =========================

        JButton detailsButton =
                new JButton(
                        "See Details"
                );


        detailsButton.setBounds(
                760,
                27,
                150,
                45
        );


        detailsButton.setBackground(
                AppTheme.BUTTON
        );


        detailsButton.setForeground(
                AppTheme.LIGHT_TEXT
        );


        detailsButton.setFont(
                AppTheme.BUTTON_FONT
        );


        detailsButton.setFocusPainted(
                false
        );


        detailsButton.setBorderPainted(
                false
        );


        detailsButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        detailsButton.addActionListener(

                e -> showRequestDetails(
                        requestId
                )

        );


        card.add(
                detailsButton
        );


        notificationPanel.add(
                card
        );


        notificationPanel.add(
                Box.createVerticalStrut(
                        12
                )
        );

    }


    // =========================
    // SHOW REQUEST DETAILS
    // =========================

    private void showRequestDetails(
            int requestId
    ) {

        try {

            Connection connection =
                    DBConnection.getConnection();


            if (
                    connection == null
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Database connection failed"
                );

                return;

            }


            String query =
                    "SELECT * FROM equipment_requests " +
                            "WHERE request_id=?";


            PreparedStatement statement =
                    connection.prepareStatement(
                            query
                    );


            statement.setInt(
                    1,
                    requestId
            );


            ResultSet result =
                    statement.executeQuery();


            if (
                    result.next()
            ) {

                String equipmentName =
                        result.getString(
                                "equipment_name"
                        );


                int quantity =
                        result.getInt(
                                "requested_quantity"
                        );


                String requester =
                        result.getString(
                                "requester_name"
                        );


                String department =
                        result.getString(
                                "department"
                        );


                String requestDate =
                        result.getString(
                                "request_date"
                        );


                String purpose =
                        result.getString(
                                "purpose"
                        );


                showDetailsDialog(

                        requestId,
                        equipmentName,
                        quantity,
                        requester,
                        department,
                        requestDate,
                        purpose

                );

            }


            result.close();

            statement.close();

            connection.close();


        } catch (
                Exception e
        ) {

            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load request details"
            );

        }

    }


    // =========================
    // DETAILS DIALOG
    // =========================

    private void showDetailsDialog(

            int requestId,
            String equipmentName,
            int quantity,
            String requester,
            String department,
            String requestDate,
            String purpose

    ) {

        Window window =
                SwingUtilities.getWindowAncestor(
                        this
                );


        JDialog dialog =
                new JDialog(
                        window,
                        "Equipment Request Details",
                        Dialog.ModalityType.APPLICATION_MODAL
                );


        dialog.setLayout(
                null
        );


        dialog.getContentPane().setBackground(
                AppTheme.BACKGROUND
        );


        dialog.setSize(
                600,
                520
        );


        dialog.setLocationRelativeTo(
                this
        );


        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel(
                        "REQUEST DETAILS"
                );


        title.setFont(
                AppTheme.TITLE_FONT
        );


        title.setForeground(
                AppTheme.TEXT
        );


        title.setBounds(
                150,
                25,
                350,
                50
        );


        dialog.add(
                title
        );


        // =========================
        // DETAILS PANEL
        // =========================

        JPanel detailsPanel =
                new JPanel(
                        null
                );


        detailsPanel.setBackground(
                AppTheme.CARD_BACKGROUND
        );


        detailsPanel.setBorder(

                BorderFactory.createLineBorder(
                        AppTheme.BORDER,
                        1
                )

        );


        detailsPanel.setBounds(
                50,
                90,
                500,
                250
        );


        dialog.add(
                detailsPanel
        );


        addDetail(
                detailsPanel,
                "Equipment Name:",
                equipmentName,
                20
        );


        addDetail(
                detailsPanel,
                "Quantity:",
                String.valueOf(
                        quantity
                ),
                55
        );


        addDetail(
                detailsPanel,
                "Requester:",
                requester,
                90
        );


        addDetail(
                detailsPanel,
                "Department:",
                department,
                125
        );


        addDetail(
                detailsPanel,
                "Request Date:",
                requestDate,
                160
        );


        addDetail(
                detailsPanel,
                "Purpose:",
                purpose,
                195
        );


        // =========================
        // ACCEPT BUTTON
        // =========================

        JButton acceptButton =
                new JButton(
                        "Accept"
                );


        acceptButton.setBounds(
                110,
                380,
                160,
                45
        );


        // =========================
        // REFUSE BUTTON
        // =========================

        JButton refuseButton =
                new JButton(
                        "Refuse"
                );


        refuseButton.setBounds(
                330,
                380,
                160,
                45
        );


        JButton[] buttons = {

                acceptButton,
                refuseButton

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


        dialog.add(
                acceptButton
        );

        dialog.add(
                refuseButton
        );


        // =========================
        // ACCEPT ACTION
        // =========================

        acceptButton.addActionListener(

                e -> {

                    boolean updated =
                            updateRequestStatus(
                                    requestId,
                                    "Accepted"
                            );


                    if (
                            updated
                    ) {

                        dialog.dispose();

                        loadNotifications();

                    }

                }

        );


        // =========================
        // REFUSE ACTION
        // =========================

        refuseButton.addActionListener(

                e -> {

                    boolean updated =
                            updateRequestStatus(
                                    requestId,
                                    "Refused"
                            );


                    if (
                            updated
                    ) {

                        dialog.dispose();

                        loadNotifications();

                    }

                }

        );


        dialog.setVisible(
                true
        );

    }


    // =========================
    // ADD DETAIL
    // =========================

    private void addDetail(

            JPanel panel,
            String labelText,
            String value,
            int y

    ) {

        JLabel label =
                new JLabel(
                        labelText
                );


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
                20,
                y,
                150,
                25
        );


        panel.add(
                label
        );


        JLabel valueLabel =
                new JLabel(
                        value
                );


        valueLabel.setFont(

                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )

        );


        valueLabel.setForeground(
                AppTheme.TEXT
        );


        valueLabel.setBounds(
                180,
                y,
                280,
                25
        );


        panel.add(
                valueLabel
        );

    }


    // =========================
    // UPDATE REQUEST STATUS
    // =========================

    private boolean updateRequestStatus(

            int requestId,
            String status

    ) {

        try {

            Connection connection =
                    DBConnection.getConnection();


            if (
                    connection == null
            ) {

                return false;

            }


            String query =
                    "UPDATE equipment_requests " +
                            "SET status=? " +
                            "WHERE request_id=?";


            PreparedStatement statement =
                    connection.prepareStatement(
                            query
                    );


            statement.setString(
                    1,
                    status
            );


            statement.setInt(
                    2,
                    requestId
            );


            int rows =
                    statement.executeUpdate();


            statement.close();

            connection.close();


            if (
                    rows > 0
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Request " +
                                status +
                                " Successfully!"
                );


                return true;

            }


            return false;


        } catch (
                Exception e
        ) {

            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update request"
            );


            return false;

        }

    }


    // =========================
    // SHOW MESSAGE
    // =========================

    private void showMessage(

            String message,
            Color color

    ) {

        JLabel label =
                new JLabel(
                        message
                );


        label.setFont(
                AppTheme.SUBTITLE_FONT
        );


        label.setForeground(
                color
        );


        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        notificationPanel.add(
                label
        );

    }

}