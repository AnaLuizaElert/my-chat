package server;

import utils.MessagePackage;
import utils.MessageType;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

import static server.ConnectionLogger.logConnection;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final Router router;
    private String username;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ClientHandler(Socket socket, Router router) {
        this.socket = socket;
        this.router = router;
    }

    public void run() {
        try {
            logConnection(socket.getInetAddress().getHostAddress(), LocalDateTime.now());

            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.output.flush();
            this.input = new ObjectInputStream(socket.getInputStream());

            MessagePackage initialMsg = (MessagePackage) input.readObject();
            this.username = initialMsg.getSender();

            boolean registered = router.addClient(this.username, this);
            if(!registered) {
                socket.close();
                return;
            }

            while(!socket.isClosed()) {
                MessagePackage msg = (MessagePackage) input.readObject();

                if(msg.getType() == MessageType.CMD_EXIT) {
                    break;
                } else if (msg.getType() == MessageType.CMD_USERS) {
                    router.handleUsersCommand(this.username);
                } else {
                    router.routeMessage(msg);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(MessagePackage msg) throws IOException{
        output.writeObject(msg);
        output.flush();
    }

    private void closeConnection() {
        try {
            if (socket !=  null &&  !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
