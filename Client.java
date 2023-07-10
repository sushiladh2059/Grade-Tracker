import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        try {
            // Connect to the server
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected to server: " + SERVER_IP + ":" + SERVER_PORT);

            // Create object streams for sending/receiving data
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            // Read the symbol number from the user
            int symbolNumber = enterSymbolNumber();

            // Send the symbol number to the server
            outputStream.writeObject(symbolNumber);

            // Receive the GPA data from the server
            Double[] gpaData = (Double[]) inputStream.readObject();

            // Display the GPA data
            displayGPAData(gpaData);

            // Close the socket and streams
            socket.close();
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int enterSymbolNumber() {
        // You can implement your own logic to read the symbol number from the user
        // For simplicity, we'll use a hard-coded value here
        return 12345;
    }

    private static void displayGPAData(Double[] gpaData) {
        // Display the GPA data received from the server
        System.out.println("GPA Data:");

        for (int i = 0; i < gpaData.length; i++) {
            System.out.println("Subject " + (i + 1) + ": " + gpaData[i]);
        }
    }

    public void setSymbolNumber(int symbolNumber) {
    }

    public void start() {
    }
}
