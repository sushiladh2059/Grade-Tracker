import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TableDisplayPanel extends JPanel {
    private JPanel parentPanel;

    public TableDisplayPanel(Double[] gpaData, JPanel parentPanel) {
        this.parentPanel = parentPanel;
        createTablePanel(gpaData);
    }

    private void createTablePanel(Double[] gpaData) {
        setLayout(new BorderLayout());

        // Create the table model
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Subject", "GPA"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable editing for all cells
            }
        };

        // Add the data rows to the table model
        tableModel.addRow(new Object[]{"Maths", formatGrade(gpaData[0])});
        tableModel.addRow(new Object[]{"English", formatGrade(gpaData[1])});
        tableModel.addRow(new Object[]{"Web", formatGrade(gpaData[2])});
        tableModel.addRow(new Object[]{"Logic", formatGrade(gpaData[3])});

        // Calculate the average GPA
        double averageGPA = calculateAverageGPA(gpaData);

        // Add the average GPA row to the table model
        tableModel.addRow(new Object[]{"Average GPA", formatGrade(averageGPA)});

        // Create the table
        JTable table = new JTable(tableModel);
        table.setShowGrid(true);

        // Center align the cells in the table
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Set custom cell renderer for the "Average GPA" row
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Check if it is the "Average GPA" row
                if (table.getValueAt(row, 0).equals("Average GPA")) {
                    component.setBackground(Color.LIGHT_GRAY);
                    setFont(getFont().deriveFont(Font.BOLD));
                } else {
                    component.setBackground(Color.WHITE);
                    setFont(getFont().deriveFont(Font.PLAIN));
                }

                return component;
            }
        });

        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to this panel
        add(scrollPane, BorderLayout.CENTER);

        // Create the back button
        JButton backButton = new JButton("BACK");
        backButton.addActionListener(e -> {
            // Switch to the parent panel (initial input panel)
            CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
            cardLayout.show(parentPanel, "inputPanel");
        });

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);

        // Add the button panel to the bottom of the panel
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private double calculateAverageGPA(Double[] gpaData) {
        double sum = 0.0;
        int count = 0;
        boolean hasZeroGPA = false;

        for (Double gpa : gpaData) {
            if (gpa == null || gpa < 0.0 || gpa > 4.0 || gpa.isNaN()) {
                continue; // Skip invalid GPAs
            }
            sum += gpa;
            count++;
            if (gpa == 0.0) {
                hasZeroGPA = true;
            }
        }

        if (hasZeroGPA) {
            return 0.0;
        } else {
            return count > 0 ? sum / count : 0.0;
        }
    }

    private String formatGrade(double gpa) {
        if (gpa >= 3.85) {
            return "A";
        } else if (gpa >= 3.5) {
            return "A-";
        } else if (gpa >= 3.15) {
            return "B+";
        } else if (gpa >= 2.85) {
            return "B";
        } else if (gpa >= 2.5) {
            return "B-";
        } else if (gpa >= 2.15) {
            return "C+";
        } else if (gpa >= 1.85) {
            return "C";
        } else if (gpa >= 1.5) {
            return "C-";
        } else if (gpa >= 1.15) {
            return "D+";
        } else if (gpa >= 0.85) {
            return "D";
        } else {
            return "F";
        }
    }
}
