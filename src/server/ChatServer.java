package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer{
    public static void main(String args[]) throws IOException {
        Router router = new Router();
        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Servidor iniciado na porta 12345!");
        while (true) {
            Socket socket = servidor.accept();
            new ClientHandler(socket, router).start();
        }
    }
}
