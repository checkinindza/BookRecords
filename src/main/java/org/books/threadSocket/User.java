package org.books.threadSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class User extends Thread {
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    ArrayList<User> allUsers = new ArrayList<>();

    public User (Socket socket, ArrayList<User> allUsers) {
        this.socket = socket;
        this.allUsers = allUsers;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine;

            if (in != null) {
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equals("Finish")) {
                        break;
                    } else if (inputLine.startsWith("pub: ")) {
                        String text = inputLine.substring(3);
                        for (User u : allUsers) {
                            u.sendMessage(text);
                        }
                    }
                    out.println("Answer: " + inputLine);
                }
                out.close();
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String text) {
        out.println(text);
    }
}
