import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class TableDisplayPanel extends JPanel {
    private DefaultTableModel tableModel;

    public TableDisplayPanel(ArrayList<Object[]> tableData, Map<String, Integer> columnMap) {
        setLayout(new BorderLayout());

        // Create the table model with the correct number of columns
        String[] columnNames = {"Subject", "GPA"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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

        // Create the average GPA label
        JLabel averageGPALabel = createAverageGPALabel(tableModel);
        averageGPALabel.setFont(tableFont);

        // Add the scroll pane and average GPA label to this panel
        add(scrollPane, BorderLayout.CENTER);
        add(averageGPALabel, BorderLayout.SOUTH);
    }

    private JLabel createAverageGPALabel(DefaultTableModel tableModel) {
        double totalGPA = 0.0;
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            String gpaString = (String) tableModel.getValueAt(row, 1);
            double gpa = Double.parseDouble(gpaString);
            totalGPA += gpa;
        }
        double averageGPA = totalGPA / tableModel.getRowCount();

        // Format the average GPA to two decimal places
        String formattedAverageGPA = String.format("%.2f", averageGPA);

        // Create the average GPA label
        JLabel averageGPALabel = new JLabel("Average GPA: " + formattedAverageGPA);

        return averageGPALabel;
    }
}
