package client;

import utils.MessagePackage;
import utils.MessageType;

import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServerListener implements Runnable {
    ObjectInputStream input;

    public ServerListener(ObjectInputStream input) {
        this.input = input;
    }

    @Override
    public void run() {
        while (true) {
            try {
                MessagePackage message = (MessagePackage) input.readObject();
                if (message.getType() == MessageType.TEXT) {
                    String text = new String(message.getPayload());
                    System.out.println(message.getSender() + " diz: " + text);

                } else if (message.getType() == MessageType.FILE) {
                    System.out.println("Recebendo arquivo " + message.getFileName() + " de " + message.getSender());
                    Path caminhoParaSalvar = Paths.get(message.getFileName());
                    Files.write(caminhoParaSalvar, message.getPayload());
                    System.out.println("Arquivo salvo com sucesso!");

                } else if (message.getType() == MessageType.CMD_USERS) {
                    String listaUsuarios = new String(message.getPayload());
                    System.out.println("[Servidor]:\n" + listaUsuarios);

                }
            } catch (Exception e) {
                System.out.println("Conexão encerrada com o servidor.");
                break;
            }
        }
    }
}