package ChatAndVideoConsultaion;

import java.io.*;
import java.net.*;

public class ChatClient {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String username;

    public interface MessageListener {
        void onMessageReceived(String message);
    }

    private MessageListener listener;

    public ChatClient(String host, int port, String username, MessageListener listener) {
        this.username = username;
        this.listener = listener;

        try {
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = reader.readLine()) != null) {
                        listener.onMessageReceived(msg); // Send to GUI
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected.");
                }
            }).start();

        } catch (IOException e) {
            System.out.println("Connection failed.");
        }
    }

    public void sendMessage(String message) {
        String formatted = username + ": " + message;
        writer.println(formatted);
        ChatLogger.logMessage(username, message);
    }
}

