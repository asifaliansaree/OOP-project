package ChatAndVideoConsultaion;
import java.net.*;
import java.util.*;
import java.io.*;

public class ChatServer {
    private static final int PORT = 5000;
    private static List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Chat Server started on port " + PORT);

        while (true) {
            Socket client = serverSocket.accept();
            clients.add(client);
            new Thread(() -> handleClient(client)).start();
        }
    }

    private static void handleClient(Socket client) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            String msg;
            while ((msg = in.readLine()) != null) {
                broadcast(msg, client);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected.");
        } finally {
            clients.remove(client);
        }
    }

    private static void broadcast(String message, Socket sender) {
        for (Socket client : clients) {
            if (client != sender) {
                try {
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    out.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


