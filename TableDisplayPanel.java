import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TableDisplayPanel extends JPanel {
    private JPanel parentPanel;

    public TableDisplayPanel(List<Double> gpaData, JPanel parentPanel) {
        this.parentPanel = parentPanel;
        createTablePanel(gpaData);
    }

    private void createTablePanel(List<Double> gpaData) {
        setLayout(new BorderLayout());

        // Create the table model
        DefaultTableModel tableModel = new DefaultTableModel(new Object[] { "Subject", "GPA" }, 0);

        // Add the data rows to the table model
        tableModel.addRow(new Object[] { "Maths", gpaData.get(0) });
        tableModel.addRow(new Object[] { "English", gpaData.get(1) });
        tableModel.addRow(new Object[] { "Web", gpaData.get(2) });
        tableModel.addRow(new Object[] { "Logic", gpaData.get(3) });

        // Calculate the average GPA
        double averageGPA = calculateAverageGPA(gpaData);
        String averageGrade = getGradeString(averageGPA);

        // Add the average GPA row to the table model
        tableModel.addRow(new Object[] { "Average GPA", averageGrade });

        // Create the table
        JTable table = new JTable(tableModel);
        table.setShowGrid(true);
        table.setGridColor(Color.GRAY);

        // Center align the cells in the table
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to this panel
        add(scrollPane, BorderLayout.CENTER);

        // Create the back button
        JButton backButton = new JButton("BACK");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the parent panel (initial input panel)
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "inputPanel");

            }
        });

        // Create a panel for the back button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);

        // Add the button panel to the bottom of the panel
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private double calculateAverageGPA(List<Double> gpaData) {
        double sum = 0.0;
        int count = 0;

        for (double gpa : gpaData) {
            if (gpa < 0.0 || gpa > 4.0) {
                continue; // Skip invalid GPAs
            }
            sum += gpa;
            count++;
        }

        return count > 0 ? sum / count : 0.0; // Return the average GPA
    }

    private String getGradeString(double gpa) {
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
