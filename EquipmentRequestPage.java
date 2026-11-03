package pages;

import javax.swing.*;
import java.awt.*;

public class EquipmentRequestPage extends JPanel {

    private final CardLayout cardLayout;
    private final JPanel contentPanel;
    private final String role;

    public EquipmentRequestPage(
            CardLayout cardLayout,
            JPanel contentPanel,
            String role
    ) {

        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        this.role = role;

        setLayout(null);
        setBackground(AppTheme.BACKGROUND);

        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel("EQUIPMENT REQUEST MANAGEMENT");

        title.setFont(AppTheme.TITLE_FONT);
        title.setForeground(AppTheme.TEXT);

        // Width increased so complete title is visible
        title.setBounds(
                250,
                60,
                800,
                50
        );

        add(title);

        // =========================
        // SUBTITLE
        // =========================

        JLabel subtitle =
                new JLabel(
                        "Request and manage equipment requirements"
                );

        subtitle.setFont(AppTheme.SUBTITLE_FONT);
        subtitle.setForeground(AppTheme.TEXT);

        subtitle.setBounds(
                360,
                120,
                600,
                35
        );

        add(subtitle);

        // =========================
        // REQUEST EQUIPMENT BUTTON
        // =========================

        JButton requestButton =
                new JButton("Request Equipment");

        requestButton.setBounds(
                300,
                220,
                280,
                60
        );

        styleButton(requestButton);
        add(requestButton);

        // =========================
        // VIEW REQUESTS BUTTON
        // =========================

        JButton viewButton =
                new JButton("View Requests");

        viewButton.setBounds(
                610,
                220,
                280,
                60
        );

        styleButton(viewButton);
        add(viewButton);

        // =========================
        // BACK TO DASHBOARD
        // =========================

        JButton backButton =
                new JButton("Back to Dashboard");

        backButton.setBounds(
                455,
                330,
                280,
                60
        );

        styleButton(backButton);
        add(backButton);

        // =========================
        // REQUEST EQUIPMENT ACTION
        // =========================

        requestButton.addActionListener(e -> {

            cardLayout.show(
                    contentPanel,
                    "RequestEquipment"
            );

        });

        // =========================
        // VIEW REQUESTS ACTION
        // =========================

        viewButton.addActionListener(e -> {

            System.out.println("View Requests button clicked!");

            boolean found = false;

            for (Component component : contentPanel.getComponents()) {

                if (component instanceof ViewRequestsPage) {

                    ViewRequestsPage page =
                            (ViewRequestsPage) component;

                    cardLayout.show(contentPanel, "ViewRequests");

                    page.loadRequests();

                    found = true;
                    break;
                }
            }

            if (!found) {

                JOptionPane.showMessageDialog(
                        this,
                        "ViewRequestsPage is not registered in MainFrame!",
                        "Navigation Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // =========================
        // BACK ACTION
        // =========================

        backButton.addActionListener(e -> {

            cardLayout.show(
                    contentPanel,
                    "Dashboard"
            );

        });
    }

    // =====================================================
    // BUTTON STYLE
    // =====================================================

    private void styleButton(JButton button) {

        button.setBackground(
                AppTheme.BUTTON
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFont(
                AppTheme.BUTTON_FONT
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }
}