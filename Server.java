import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final int PORT = 8888;
    private static final String STUDENT_DATA_FILE = "studentdata.csv";

    private Map<Integer, Double[]> symbolDataMap;

    public Server() {
        symbolDataMap = readStudentData(STUDENT_DATA_FILE);
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Listening on port: " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Handle client requests in a separate thread
                Thread clientThread = new Thread(() -> handleClientRequest(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClientRequest(Socket clientSocket) {
        try (
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream())
        ) {
            // Read symbol number from the client
            int symbolNumber = (int) inputStream.readObject();

            // Check if the symbol number exists in the data map
            if (symbolDataMap.containsKey(symbolNumber)) {
                Double[] gpaData = symbolDataMap.get(symbolNumber);
                // Send the GPA data back to the client
                outputStream.writeObject(gpaData);
            } else {
                // Send an empty array to indicate symbol number not found
                outputStream.writeObject(new Double[0]);
            }

            // Close the client socket
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
