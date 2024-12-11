package org.books.threadSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientStart {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 1255);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedReader line = new BufferedReader(new InputStreamReader(System.in));
            String userLine;

            ServerListener s1 = new ServerListener(in);
            s1.start();

            while ((userLine = line.readLine()) != null) {
                if (userLine.equals("Finish")) {
                    System.out.println("Finishing connection");
                    break;
                }
                out.println(userLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
