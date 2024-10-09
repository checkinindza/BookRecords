package org.books.utils;

import org.books.Model.BookExchange;
import org.books.Model.Client;
import org.books.Model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadWriteOperation {
    public static void writeToFile(String fileName, BookExchange bookExchange) {
        // write user to file
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            objectOutputStream.writeObject(bookExchange);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BookExchange readFromFile() {
        // read user from file
        BookExchange bookExchange = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data.txt"));) {
            bookExchange = (BookExchange) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Klaida");
        }
        return bookExchange;
    }

    public static void writeToFileAsText(List<User> users) {
        try (FileWriter fileWriter = new FileWriter("users.txt");){
            for (User user : users) {
                // fileWritter.append
                fileWriter.write(user.getId() + ":" + user.getLogin() + ":" + user.getName() + ":" + user.getSurname() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<User> readUsersFromFile() {
        ArrayList<User> users = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("users.txt"));) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                //Split data
                String[] data = line.split(":");
                //Create object
                Client client = new Client(data[0], data[1], data[2], data[3], data[4]);
                //Add to list
                users.add(client);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return users;
    }
}
