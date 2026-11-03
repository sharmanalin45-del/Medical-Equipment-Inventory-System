
package pages;

import database.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import java.awt.*;
import java.sql.*;
import java.util.Collections;

public class ViewRequestsPage extends JPanel {

    private final CardLayout cardLayout;
    private final JPanel contentPanel;
    private final String role;

    private DefaultTableModel model;
    private JTable table;

    public ViewRequestsPage(
            CardLayout cardLayout,
            JPanel contentPanel,
            String role
    ) {

        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        this.role = role;

        setLayout(new BorderLayout(10, 10));
        setBackground(AppTheme.BACKGROUND);

        // =========================
        // TITLE
        // =========================

        JLabel title = new JLabel(
                "VIEW EQUIPMENT REQUESTS",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(AppTheme.TEXT);

        title.setBorder(
                BorderFactory.createEmptyBorder(20, 10, 20, 10)
        );

        add(title, BorderLayout.NORTH);

        // =========================
        // TABLE COLUMNS
        // =========================

        String[] columns = {
                "Request ID",
                "Equipment ID",
                "Equipment Name",
                "Quantity",
                "Requester",
                "Department",
                "Request Date",
                "Purpose",
                "Status",
                "Priority Score",
                "Priority Level",
                "Conflict",
                "Smart Recommendation"
        };

        model = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {

                if (column == 0 || column == 1 ||
                        column == 3 || column == 9) {
                    return Integer.class;
                }

                return String.class;
            }
        };

        // =========================
        // CREATE TABLE
        // =========================

        table = new JTable(model);

        table.setRowHeight(30);

        table.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        table.getTableHeader().setReorderingAllowed(false);

        table.getTableHeader().setBackground(
                AppTheme.BUTTON
        );

        table.getTableHeader().setForeground(
                Color.WHITE
        );

        // IMPORTANT: Fit columns according to available space
        table.setAutoResizeMode(
                JTable.AUTO_RESIZE_ALL_COLUMNS
        );

        table.setAutoCreateRowSorter(true);

        // =========================
        // COLUMN WIDTHS
        // =========================

        table.getColumnModel().getColumn(0).setPreferredWidth(65);
        table.getColumnModel().getColumn(1).setPreferredWidth(75);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        table.getColumnModel().getColumn(7).setPreferredWidth(130);
        table.getColumnModel().getColumn(8).setPreferredWidth(85);
        table.getColumnModel().getColumn(9).setPreferredWidth(95);
        table.getColumnModel().getColumn(10).setPreferredWidth(95);
        table.getColumnModel().getColumn(11).setPreferredWidth(75);
        table.getColumnModel().getColumn(12).setPreferredWidth(250);

        // =========================
        // PRIORITY COLOR RENDERER
        // =========================

        table.setDefaultRenderer(
                Object.class,
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column
                    ) {

                        Component component =
                                super.getTableCellRendererComponent(
                                        table,
                                        value,
                                        isSelected,
                                        hasFocus,
                                        row,
                                        column
                                );

                        int modelRow =
                                table.convertRowIndexToModel(row);

                        String priority =
                                String.valueOf(
                                        model.getValueAt(modelRow, 10)
                                );

                        String conflict =
                                String.valueOf(
                                        model.getValueAt(modelRow, 11)
                                );

                        // =========================
                        // PRIORITY COLORS
                        // =========================

                        if (!isSelected) {

                            if (priority.equals("HIGH")) {

                                component.setBackground(
                                        new Color(255, 220, 220)
                                );

                                component.setForeground(
                                        new Color(180, 0, 0)
                                );

                            } else if (priority.equals("MEDIUM")) {

                                component.setBackground(
                                        new Color(255, 239, 205)
                                );

                                component.setForeground(
                                        new Color(180, 100, 0)
                                );

                            } else {

                                component.setBackground(
                                        new Color(220, 245, 225)
                                );

                                component.setForeground(
                                        new Color(0, 110, 45)
                                );
                            }
                        }

                        // =========================
                        // CONFLICT HIGHLIGHT
                        // =========================

                        if (conflict.equals("YES")
                                && column == 11
                                && !isSelected) {

                            component.setBackground(
                                    new Color(200, 0, 0)
                            );

                            component.setForeground(
                                    Color.WHITE
                            );
                        }

                        // =========================
                        // SCORE BOLD
                        // =========================

                        if (column == 9) {

                            component.setFont(
                                    new Font(
                                            "Arial",
                                            Font.BOLD,
                                            13
                                    )
                            );

                        } else {

                            component.setFont(
                                    new Font(
                                            "Arial",
                                            Font.PLAIN,
                                            12
                                    )
                            );
                        }

                        return component;
                    }
                }
        );

        // =========================
        // SCROLL PANE
        // =========================

        JScrollPane scrollPane =
                new JScrollPane(table);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        add(scrollPane, BorderLayout.CENTER);

        // =========================
        // BOTTOM PANEL
        // =========================

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                20,
                                15
                        )
                );

        bottomPanel.setBackground(
                AppTheme.BACKGROUND
        );

        JButton refreshButton =
                new JButton("Refresh");

        JButton backButton =
                new JButton("Back");

        styleButton(refreshButton);
        styleButton(backButton);

        bottomPanel.add(refreshButton);
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // =========================
        // BUTTON ACTIONS
        // =========================

        refreshButton.addActionListener(e -> {
            loadRequests();
        });

        backButton.addActionListener(e -> {

            cardLayout.show(
                    contentPanel,
                    "EquipmentRequest"
            );
        });

        // =========================
        // INITIAL DATA LOAD
        // =========================

        SwingUtilities.invokeLater(
                this::loadRequests
        );
    }

    // =====================================================
    // LOAD REQUESTS FROM DATABASE
    // =====================================================

    public void loadRequests() {

        if (model == null) {
            return;
        }

        model.setRowCount(0);

        String query =
                "SELECT request_id, equipment_id, equipment_name, " +
                        "requested_quantity, requester_name, department, " +
                        "request_date, purpose, status " +
                        "FROM equipment_requests " +
                        "ORDER BY request_id DESC";

        try (Connection connection =
                     DBConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(query);

             ResultSet result =
                     statement.executeQuery()) {

            while (result.next()) {

                int equipmentId =
                        result.getInt("equipment_id");

                int requestedQuantity =
                        result.getInt("requested_quantity");

                String equipmentName =
                        result.getString("equipment_name");

                String department =
                        result.getString("department");

                String purpose =
                        result.getString("purpose");

                String requestDate =
                        result.getString("request_date");

                String status =
                        result.getString("status");

                String requesterName =
                        result.getString("requester_name");

                // =================================
                // AVAILABLE EQUIPMENT QUANTITY
                // =================================

                int availableQuantity = 0;

                String stockQuery =
                        "SELECT quantity FROM equipment WHERE id = ?";

                try (PreparedStatement stockStatement =
                             connection.prepareStatement(stockQuery)) {

                    stockStatement.setInt(1, equipmentId);

                    try (ResultSet stockResult =
                                 stockStatement.executeQuery()) {

                        if (stockResult.next()) {

                            availableQuantity =
                                    stockResult.getInt("quantity");
                        }
                    }
                }

                // =================================
                // PENDING REQUEST QUANTITY
                // =================================

                int pendingQuantity = 0;

                String pendingQuery =
                        "SELECT COALESCE(SUM(requested_quantity), 0) " +
                                "FROM equipment_requests " +
                                "WHERE equipment_id = ? AND status = 'Pending'";

                try (PreparedStatement pendingStatement =
                             connection.prepareStatement(pendingQuery)) {

                    pendingStatement.setInt(1, equipmentId);

                    try (ResultSet pendingResult =
                                 pendingStatement.executeQuery()) {

                        if (pendingResult.next()) {

                            pendingQuantity =
                                    pendingResult.getInt(1);
                        }
                    }
                }

                // =================================
                // WAITING DAYS
                // =================================

                int waitingDays = 0;

                try {

                    java.time.LocalDate requestDateValue =
                            java.time.LocalDate.parse(requestDate);

                    waitingDays =
                            (int) java.time.temporal.ChronoUnit
                                    .DAYS.between(
                                            requestDateValue,
                                            java.time.LocalDate.now()
                                    );

                } catch (Exception ignored) {

                    waitingDays = 0;
                }

                // =================================
                // SMART PRIORITY SCORE
                // =================================

                int score =
                        SmartRequestAnalyzer.calculatePriority(
                                department,
                                purpose,
                                equipmentName,
                                waitingDays
                        );

                String priority =
                        SmartRequestAnalyzer.getPriorityLevel(
                                score
                        );

                // =================================
                // CONFLICT DETECTION
                // =================================

                boolean conflict =
                        SmartRequestAnalyzer.hasConflict(
                                availableQuantity,
                                pendingQuantity
                        );

                String conflictStatus =
                        conflict ? "YES" : "NO";

                // =================================
                // SMART RECOMMENDATION
                // =================================

                String recommendation =
                        SmartRequestAnalyzer.getRecommendation(
                                score,
                                conflict
                        );

                // =================================
                // ADD TABLE ROW
                // =================================

                model.addRow(new Object[]{

                        result.getInt("request_id"),
                        equipmentId,
                        equipmentName,
                        requestedQuantity,
                        requesterName,
                        department,
                        requestDate,
                        purpose,
                        status,
                        score,
                        priority,
                        conflictStatus,
                        recommendation

                });
            }

            // =================================
            // SORT BY PRIORITY SCORE
            // HIGHEST FIRST
            // =================================

            if (table.getRowCount() > 0) {

                table.getRowSorter().setSortKeys(
                        Collections.singletonList(
                                new RowSorter.SortKey(
                                        9,
                                        SortOrder.DESCENDING
                                )
                        )
                );
            }

            // =================================
            // NO REQUESTS FOUND
            // =================================

            if (model.getRowCount() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "No equipment requests found.",
                        "Information",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (SQLException ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load equipment requests.\n\n" +
                            "Error: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
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

        button.setPreferredSize(
                new Dimension(130, 40)
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
    }
}
