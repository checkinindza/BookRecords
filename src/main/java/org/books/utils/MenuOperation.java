package org.books.utils;

import org.books.Model.Book;
import org.books.Model.BookExchange;
import org.books.Model.Client;
import org.books.Model.User;

import java.util.Scanner;

public class MenuOperation {
    public static void generateUserMenu(Scanner scanner, BookExchange bookExchange) {
        int userCmd = 0;
        String consoleInput = "";
        while (userCmd != 6) {
            System.out.println("""
                    Choose an option\s
                    1 - create user
                    2 - update user info
                    3 - view user
                    4 - view all users
                    5 - delete user
                    6 - return to main menu
                    """);
            userCmd = scanner.nextInt();
            scanner.nextLine();

            switch (userCmd) {
                case 1:
                    System.out.println("Which type? C/A");
                    // Reiketu patikrinti userio inputa
                    consoleInput = scanner.nextLine();
                    if (consoleInput.equals("C")) {
                        System.out.println("Enter data: login;psw;name;surname;email");
                        consoleInput = scanner.nextLine();
                        String[] info = consoleInput.split(";");

                        Client client = new Client(info[0], info[1], info[2], info[3], info[4]);
                        bookExchange.getAllUsers().add(client);
                    } else if (consoleInput.equals("A")) {

                    }
                    break;
                case 2:
                    System.out.println("Enter login");
                    consoleInput = scanner.nextLine();
                    for (User u : bookExchange.getAllUsers()) {
                        if (u.getLogin().equals(consoleInput)) {
                            System.out.println("Enter new name");
                            consoleInput = scanner.nextLine();
                            u.setName(consoleInput);
                        }
                    }
                    break;
                case 3:
                    System.out.println("Enter login");
                    consoleInput = scanner.nextLine();
                    for (User u : bookExchange.getAllUsers()) {
                        if (u.getLogin().contains(consoleInput)) System.out.println(u);
                    }
                    break;
                case 4:
                    for (User u : bookExchange.getAllUsers()) {

                    }
                    break;
                default:
                    System.out.println("Learn to read");
            }
        }
    }
}
