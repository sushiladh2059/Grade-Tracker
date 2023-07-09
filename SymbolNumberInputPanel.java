import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymbolNumberInputPanel extends JPanel {
    private JTextField symbolNumberField;
    private JFrame frame;
    private TableDisplayPanel tableDisplayPanel;
    private Map<String, Integer> columnMap;

    public SymbolNumberInputPanel(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        // Set the font for the title
        Font titleFont = new Font("Arial", Font.BOLD, 20);

        // Create the title label and set its font
        JLabel titleLabel = new JLabel("Student Grade Tracker");
        titleLabel.setFont(titleFont);

        // Create the text field for symbol number input
        symbolNumberField = new JTextField(10);

        // Create the button to submit symbol number
        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String symbolNumber = symbolNumberField.getText();
                // Perform any desired action with the submitted symbol number
                System.out.println("Submitted Symbol Number: " + symbolNumber);
                // Update the table data and show the table
                ArrayList<Object[]> updatedData = getUpdatedTableData(symbolNumber);
                if (updatedData != null) {
                    showTable(updatedData);
                } else {
                    JOptionPane.showMessageDialog(null, "Symbol Number not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                symbolNumberField.setText("");
                symbolNumberField.requestFocus();
            }
        });

        // Create a JPanel for the symbol number input components
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Symbol Number: "));
        inputPanel.add(symbolNumberField);
        inputPanel.add(enterButton);

        // Add the input panel and title label to this panel
        add(inputPanel, BorderLayout.NORTH);
        add(titleLabel, BorderLayout.CENTER);

        // Initialize column map
        columnMap = initializeColumnMap();
    }

    private Map<String, Integer> initializeColumnMap() {
        Map<String, Integer> map = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("studentdata.csv"))) {
            String headerLine = reader.readLine();
            if (headerLine != null) {
                String[] headers = headerLine.split(",");
                for (int i = 1; i < headers.length; i++) {
                    map.put(headers[i], i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private ArrayList<Object[]> getUpdatedTableData(String symbolNumber) {
        ArrayList<Object[]> updatedData = new ArrayList<>();

        // Read the CSV file and retrieve the data for the specified symbol number
        try (BufferedReader reader = new BufferedReader(new FileReader("studentdata.csv"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(symbolNumber)) {
                    found = true;
                    Object[] rowData = new Object[columnMap.size()];
                    for (int i = 1; i < values.length; i++) {
                        rowData[i - 1] = values[i];
                    }
                    updatedData.add(rowData);
                }
            }
            if (!found) {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return updatedData;
    }

    private void showTable(ArrayList<Object[]> updatedData) {
        if (tableDisplayPanel != null) {
            frame.getContentPane().remove(tableDisplayPanel);
        }
        tableDisplayPanel = new TableDisplayPanel(updatedData, columnMap);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(tableDisplayPanel);
        frame.revalidate();
        frame.repaint();
    }
}
