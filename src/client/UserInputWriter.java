package client;

import utils.MessagePackage;
import utils.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserInputWriter implements Runnable {
    Scanner keyboard = new Scanner(System.in);
    String name;
    ObjectOutputStream output;

    public UserInputWriter(String name, ObjectOutputStream output) {
        this.name = name;
        this.output = output;
    }

    @Override
    public void run() {
        while (true) {
            String text = keyboard.nextLine();
            String fileName = null;
            String receiver = null;
            String payload = null;
            MessageType type;
            byte[] payloadBytes = new byte[0];

            if (text.startsWith("/send message")) {
                type = MessageType.TEXT;
                receiver = text.split(" ")[2];
                payload = text.split(" ", 4)[3];
                payloadBytes = payload.getBytes();
            } else if (text.startsWith("/send file")) {
                type = MessageType.FILE;
                receiver = text.split(" ")[2];
                String caminhoArquivo = text.split(" ", 4)[3];
                try {
                    Path path = Paths.get(caminhoArquivo);
                    fileName = path.getFileName().toString();
                    payloadBytes = Files.readAllBytes(path);
                } catch (java.io.IOException e) {
                    System.out.println("Erro: Arquivo não encontrado ou não pode ser lido!");
                    continue;
                }
            } else if (text.startsWith("/users")) {
                type = MessageType.CMD_USERS;
            } else if (text.startsWith("/sair")) {
                type = MessageType.CMD_EXIT;
            } else {
                continue;
            }

            MessagePackage message = new MessagePackage(
                type, name, receiver, LocalDateTime.now(), payloadBytes.length, fileName, payloadBytes
            );

            try {
                output.writeObject(message);
                output.flush();

                if (type == MessageType.CMD_EXIT) {
                    System.out.println("Desconectando...");
                    break;
                }
            } catch (IOException e) {
                System.out.println("Erro de conexão ao tentar enviar: " + e.getMessage());
                break;
            }
        }
    }
}
