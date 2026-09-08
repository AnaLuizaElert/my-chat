package client;

import utils.MessagePackage;
import utils.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public ChatClient(String ipServer, int port, String username) {
        try {
            Socket connection = new Socket(ipServer, port);
            ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(connection.getInputStream());

            UserInputWriter user = new UserInputWriter(username, output);
            ServerListener server = new ServerListener(input);

            MessagePackage pacoteDeRegistro = new MessagePackage(
                    MessageType.CMD_USERS, username, "SERVER", java.time.LocalDateTime.now(), 0, null, new byte[0]
            );

            output.writeObject(pacoteDeRegistro);
            output.flush();

            Thread writer = new Thread(user);
            Thread listener = new Thread(server);

            writer.start();
            listener.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("=== BEM-VINDO AO CHAT ===");
        System.out.print("Digite o seu nome de usuário: ");
        String username = teclado.nextLine();

        String ip = "127.0.0.1";
        int port = 12345;

        System.out.println("Conectando...");

        new ChatClient(ip, port, username);
    }
}