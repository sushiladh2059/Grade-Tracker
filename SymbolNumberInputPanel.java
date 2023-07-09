import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SymbolNumberInputPanel extends JPanel {
    private JTextField symbolNumberField;
    public SymbolNumberInputPanel(JFrame frame) {
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
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new TableDisplayPanel(updatedData));
                frame.revalidate();
                frame.repaint();
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
    }

    private ArrayList<Object[]> getUpdatedTableData(String symbolNumber) {
        ArrayList<Object[]> updatedData = new ArrayList<>();

        // Add the initial table data (dummy data for demonstration)
        Object[][] initialData = {
                {"Maths", 3.6},
                {"English", 3.75},
                {"Web", 3.85},
                {"Logic", 3.75}
        };

        // Check the symbol number and add additional data if necessary
        if (symbolNumber.equals("12345")) {
            updatedData.add(initialData[0]);
            updatedData.add(initialData[1]);
            updatedData.add(initialData[2]);
            updatedData.add(initialData[3]);
        }

        return updatedData;
    }
}
