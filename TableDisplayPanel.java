import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TableDisplayPanel extends JPanel {
    private DefaultTableModel tableModel;

    public TableDisplayPanel(ArrayList<Object[]> tableData) {
        setLayout(new BorderLayout());

        // Create the table with two columns
        String[] columnNames = {"Subject", "GPA"};

        // Create a DefaultTableModel to hold the data
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable editing of table cells
            }
        };

        // Add the table data to the table model
        for (Object[] row : tableData) {
            tableModel.addRow(row);
        }

        // Create the table using the DefaultTableModel
        JTable table = new JTable(tableModel);

        // Set the font for the table cells
        Font tableFont = new Font("Arial", Font.PLAIN, 12);
        table.setFont(tableFont);

        // Set the row height for the table
        table.setRowHeight(30);

        // Create a scroll pane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);

        // Create or update the average GPA label
        double totalGPA = 0.0;
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            double gpa = (double) tableModel.getValueAt(row, 1);
            totalGPA += gpa;
        }
        double averageGPA = totalGPA / tableModel.getRowCount();

        // Format the average GPA to two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedAverageGPA = decimalFormat.format(averageGPA);

        // Create the average GPA label
        JLabel averageGPALabel = new JLabel("Average GPA: " + formattedAverageGPA);
        averageGPALabel.setFont(tableFont);

        // Add the scroll pane and average GPA label to this panel
        add(scrollPane, BorderLayout.CENTER);
        add(averageGPALabel, BorderLayout.SOUTH);
    }
}
