package server;

import utils.MessagePackage;
import utils.MessageType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Router {
    private final Map<String, ClientHandler> clients = new ConcurrentHashMap<>();

    public boolean addClient(String username, ClientHandler client) {
        return clients.putIfAbsent(username, client) == null;
    }

    public void removeClient(String username) {
        if(username != null) {
            clients.remove(username);
        }
    }

    public void routeMessage(MessagePackage msg) throws IOException {
        String receiver = msg.getReceiver();
        ClientHandler destinationHandler = clients.get(receiver);

        if (destinationHandler != null) {
            destinationHandler.sendMessage(msg);
        } else {
            sendSystemNotification(msg.getSender(), "Erro: Usuário '" + receiver + "' não encontrado ou offline.");
        }
    }

    public void handleUsersCommand(String requestingUser) throws IOException {
        ClientHandler destinationHandler = clients.get(requestingUser);
        if (destinationHandler == null) return;

        StringBuilder usersList = new StringBuilder("Usuários Conectados");
        for (String username : clients.keySet()) {
            usersList.append("- ").append(username).append("\n");
        }

        sendSystemNotification(requestingUser, usersList.toString().trim());
    }

    private void sendSystemNotification(String sender, String message) throws IOException {
        ClientHandler handler = clients.get(sender);
        if(handler != null) {
            MessagePackage sysMsg = new MessagePackage(
                    MessageType.CMD_USERS, "SYSTEM", sender, LocalDateTime.now(),null, message.getBytes(StandardCharsets.UTF_8)
            );
            sysMsg.setPayloadLength(sysMsg.getPayload().length);

            handler.sendMessage(sysMsg);
        }
    }

}
