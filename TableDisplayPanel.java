import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.util.List;

public class TableDisplayPanel extends JPanel {
    public TableDisplayPanel(List<Double> gpaData) {
        setLayout(new BorderLayout());

        // Create the table model
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Subject", "GPA"}, 0);

        // Add the data rows to the table model
        tableModel.addRow(new Object[]{"Maths", gpaData.get(0)});
        tableModel.addRow(new Object[]{"English", gpaData.get(1)});
        tableModel.addRow(new Object[]{"Web", gpaData.get(2)});
        tableModel.addRow(new Object[]{"Logic", gpaData.get(3)});

        // Calculate the average GPA
        double averageGPA = calculateAverageGPA(gpaData);
        String averageGrade = getGradeString(averageGPA);

        // Add the average GPA row to the table model
        tableModel.addRow(new Object[]{"Average GPA", averageGrade});

        // Create the table
        JTable table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);

                // Set the background color for the average GPA row
                if (getValueAt(row, 0).equals("Average GPA")) {
                    component.setBackground(Color.LIGHT_GRAY);
                } else {
                    component.setBackground(Color.WHITE);
                }

                return component;
            }
        };

        // Set the font for the table
        Font tableFont = new Font("Arial", Font.PLAIN, 12);
        table.setFont(tableFont);

        // Center align the cells in the table
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to this panel
        add(scrollPane, BorderLayout.CENTER);
    }

    private double calculateAverageGPA(List<Double> gpaData) {
        double sum = 0.0;
        int count = 0;
        boolean hasFailedSubject = false;

        for (double gpa : gpaData) {
            if (gpa < 0.0 || gpa > 4.0) {
                continue; // Skip invalid GPAs
            }
            sum += gpa;
            count++;
            if (gpa == 0.0) {
                hasFailedSubject = true;
            }
        }

        if (hasFailedSubject) {
            return 0.0; // Overall GPA is "F" if any subject is failed
        }

        return count > 0 ? sum / count : 0.0; // Return the average GPA
    }

    private String getGradeString(double gpa) {
        if (gpa == 4.0) {
            return "A";
        } else if (gpa >= 3.7) {
            return "A-";
        } else if (gpa >= 3.3) {
            return "B+";
        } else if (gpa >= 3.0) {
            return "B";
        } else if (gpa >= 2.7) {
            return "B-";
        } else if (gpa >= 2.3) {
            return "C+";
        } else if (gpa >= 2) {
            return "C";
        } else if (gpa >= 1.7) {
            return "C-";
        } else if (gpa >= 1.3) {
            return "D+";
        } else if (gpa >= 1.0) {
            return "D";
        } else {
            return "F";
        }
    
    }
}
