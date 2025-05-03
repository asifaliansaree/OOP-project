package ChatAndVideoConsultaion;

import java.io.*;
import java.time.LocalDateTime;

public class ChatLogger {
    private static final String LOG_FILE = "chat/chat_history.txt";

    static {
        new File("chat").mkdirs(); // Ensure folder exists
    }

    public static void logMessage(String username, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write("[" + LocalDateTime.now() + "] " + username + ": " + message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println(" Failed to write chat log: " + e.getMessage());
        }
    }
}

