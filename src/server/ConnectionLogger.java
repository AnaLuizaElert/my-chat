package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConnectionLogger {
    private static final String LOG_FILE = "conections.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static synchronized void logConnection(String ip, LocalDateTime dateTime){
        String formattedDate = FORMATTER.format(dateTime);
        String logMessage = String.format("[%s] - %s%n", formattedDate, ip);

        try (
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))
        ) {
            writer.write(logMessage);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
        }
    }

}
