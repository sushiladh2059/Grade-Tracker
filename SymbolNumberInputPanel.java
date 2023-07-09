import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolNumberInputPanel extends JPanel {
    private JTextField symbolNumberField;
    private JFrame frame;
    private TableDisplayPanel tableDisplayPanel;
    private Map<String, List<Double>> studentData;

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
                List<Double> gpaData = studentData.get(symbolNumber);
                if (gpaData != null) {
                    showTable(gpaData);
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

        // Initialize student data
        studentData = readStudentData();
    }

    private Map<String, List<Double>> readStudentData() {
        Map<String, List<Double>> studentData = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("studentdata.csv"))) {
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] values = line.split(",");
                String symbolNumber = values[0];
                List<Double> gpaData = new ArrayList<>();
                for (int i = 1; i < values.length; i++) {
                    String gpaStr = values[i];
                    double gpa;
                    if (gpaStr.equalsIgnoreCase("F")) {
                        gpa = -1.0; // Use -1.0 to represent "F" grade
                    } else {
                        gpa = Double.parseDouble(gpaStr);
                    }
                    gpaData.add(gpa);
                }
                studentData.put(symbolNumber, gpaData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return studentData;
    }

    private void showTable(List<Double> gpaData) {
        if (tableDisplayPanel != null) {
            frame.getContentPane().remove(tableDisplayPanel);
        }
        tableDisplayPanel = new TableDisplayPanel(gpaData);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(tableDisplayPanel);
        frame.revalidate();
        frame.repaint();
    }
}
