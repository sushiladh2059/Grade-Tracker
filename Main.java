import javax.swing.*;
import java.awt.*;


public class Main {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        // Create the JFrame and set its properties
        JFrame frame = new JFrame("Student Grade Tracker by SusHill");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create a JPanel as the content pane of the frame
        JPanel contentPane = new JPanel(new CardLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the image
                ImageIcon backgroundImage = new ImageIcon("me.jpg");

                // Draw the image on the panel only if the current panel is the input panel
                if (getParent().getComponent(0) == this) {
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Create an instance of the parent panel
        JPanel parentPanel = new JPanel(new CardLayout());

        // Create an instance of SymbolNumberInputPanel
        SymbolNumberInputPanel inputPanel = new SymbolNumberInputPanel(frame, parentPanel, SERVER_IP, SERVER_PORT, parentPanel);

        // Add the input panel to the parent panel
        parentPanel.add(inputPanel, "inputPanel");

        // Add the parent panel to the content pane
        contentPane.add(parentPanel);

        // Set the content pane of the frame
        frame.setContentPane(contentPane);

        // Set the frame to be visible
        frame.setVisible(true);
    }
}
