import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SymbolNumberInputPanel extends JPanel {
    private JFrame frame;
    private JPanel parentPanel;
    private JTextField symbolNumberField;
    private Map<Integer, Double[]> symbolDataMap;
    private String serverIP;
    private int serverPort;
    private JPanel contentPane;
    private JPanel backgroundImagePanel;

    public SymbolNumberInputPanel(JFrame frame, JPanel parentPanel, String serverIP, int serverPort, JPanel contentPane) {
        this.frame = frame;
        this.parentPanel = parentPanel;
        this.symbolDataMap = readStudentData("studentdata.csv");
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.contentPane = contentPane;
        createInputPanel();
    }

    private void createInputPanel() {
        setLayout(new BorderLayout());
    
        // Create the symbol number label and text field
        JLabel symbolNumberLabel = new JLabel("Enter Symbol Number:");
        symbolNumberField = new JTextField(10);
    
        // Create the submit button
        JButton submitButton = new JButton("Submit");
    
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Get the entered symbol number
                String symbolNumberText = symbolNumberField.getText();
    
                // Check if the symbol number is valid
                if (isValidSymbolNumber(symbolNumberText)) {
                    int symbolNumber = Integer.parseInt(symbolNumberText);
    
                    // Check if the symbol number exists in the data map
                    if (symbolDataMap.containsKey(symbolNumber)) {
                        Double[] gpaData = symbolDataMap.get(symbolNumber);
    
                        // Send the GPA data to the server
                        sendGPADataToServer(gpaData);
    
                        // Create an instance of TableDisplayPanel
                        TableDisplayPanel tableDisplayPanel = new TableDisplayPanel(gpaData, parentPanel);
    
                        // Add the table display panel to the parent panel
                        parentPanel.add(tableDisplayPanel, "tableDisplayPanel");
    
                        // Switch to the table display panel
                        CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                        cardLayout.show(parentPanel, "tableDisplayPanel");
                    } else {
                        // Show an error message for invalid symbol number
                        JOptionPane.showMessageDialog(frame, "Symbol Number not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // Show an error message for invalid symbol number
                    JOptionPane.showMessageDialog(frame, "Invalid Symbol Number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
    // Create an image icon component and resize the image
        ImageIcon imageIcon = new ImageIcon("Welcome.jpg");
        Image image = imageIcon.getImage();
        int width = 330; // Adjust the width as needed
        int height = 220; // Adjust the height as needed
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(imageIcon);
        
        // Create a panel for the input components
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(symbolNumberLabel, BorderLayout.WEST);
        inputPanel.add(symbolNumberField, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);
        inputPanel.add(imageLabel, BorderLayout.SOUTH);
    
        // Add the input panel to the center of the panel
        add(inputPanel, BorderLayout.CENTER);
    }
    
    
    

    private boolean isValidSymbolNumber(String symbolNumberText) {
        try {
            int symbolNumber = Integer.parseInt(symbolNumberText);
            return symbolNumber > 0; // Validate against your specific criteria
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Map<Integer, Double[]> readStudentData(String fileName) {
        Map<Integer, Double[]> symbolDataMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                int symbolNumber = Integer.parseInt(data[0]);
                Double[] gpaData = new Double[data.length - 1];

                for (int i = 1; i < data.length; i++) {
                    gpaData[i - 1] = Double.parseDouble(data[i]);
                }

                symbolDataMap.put(symbolNumber, gpaData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return symbolDataMap;
    }

    private void sendGPADataToServer(Double[] gpaData) {
        try (Socket socket = new Socket(serverIP, serverPort)) {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            for (Double gpa : gpaData) {
                writer.println(gpa);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}