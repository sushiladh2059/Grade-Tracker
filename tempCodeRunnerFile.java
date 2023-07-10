import javax.swing.*;
import java.awt.CardLayout;


public class MainApp {
    public static void main(String[] args) {
        // Create the JFrame and set its properties
        JFrame frame = new JFrame("Student Grade Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create an instance of the parent panel
        JPanel parentPanel = new JPanel(new CardLayout());

        // Create an instance of SymbolNumberInputPanel
        SymbolNumberInputPanel inputPanel = new SymbolNumberInputPanel(frame, parentPanel);

        // Add the input panel to the parent panel
        parentPanel.add(inputPanel, "inputPanel");

        // Add the parent panel to the frame
        frame.getContentPane().add(parentPanel);

        // Set the frame to be visible
        frame.setVisible(true);
    }
}
