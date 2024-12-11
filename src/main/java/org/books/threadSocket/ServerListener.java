package org.books.threadSocket;

import java.io.BufferedReader;

public class ServerListener extends Thread {
    BufferedReader in = null;

    public ServerListener(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        String userInput;
        try {
            while ((userInput = in.readLine()) != null) {
                System.out.println("Testing messages: " + userInput);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
