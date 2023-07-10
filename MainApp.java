import javax.swing.*;
import java.awt.CardLayout;
import java.io.IOException;
import java.net.Socket;

public class MainApp {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        // Create the JFrame and set its properties
        JFrame frame = new JFrame("Student Grade Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create an instance of the parent panel
        JPanel parentPanel = new JPanel(new CardLayout());

        // Create an instance of SymbolNumberInputPanel
        SymbolNumberInputPanel inputPanel = new SymbolNumberInputPanel(frame, parentPanel, SERVER_IP, SERVER_PORT);

        // Add the input panel to the parent panel
        parentPanel.add(inputPanel, "inputPanel");

        // Add the parent panel to the frame
        frame.getContentPane().add(parentPanel);

        // Set the frame to be visible
        frame.setVisible(true);
    }
}
