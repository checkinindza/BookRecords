package org.books.threadSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Start {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Socket socket = null;
        ArrayList<User> allConnectedUsers = new ArrayList<>();

        try {
            serverSocket = new ServerSocket(1255);
            while (true) {
                socket = serverSocket.accept();
                System.out.println("New client detected, port num given: " + socket.getPort());
                org.books.threadSocket.User user = new org.books.threadSocket.User(socket, allConnectedUsers);
                allConnectedUsers.add(user);
                user.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && serverSocket != null) {
                serverSocket.close();
                socket.close();
            }
        }
    }
}
