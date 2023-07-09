import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        // Create the JFrame and set its properties
        JFrame frame = new JFrame("Student Grade Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create an instance of SymbolNumberInputPanel
        SymbolNumberInputPanel inputPanel = new SymbolNumberInputPanel(frame);

        // Add the input panel to the frame
        frame.getContentPane().add(inputPanel);

        // Set the frame to be visible
        frame.setVisible(true);
    }
}
