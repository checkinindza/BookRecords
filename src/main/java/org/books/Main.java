package org.books;

import org.books.Model.BookExchange;
import org.books.utils.MenuOperation;
import org.books.utils.ReadWriteOperation;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cmd = "";

        BookExchange bookExchange = ReadWriteOperation.readFromFile();
        if (bookExchange == null) {
            bookExchange = new BookExchange();
        }

        while (!cmd.equals("q")) {
            System.out.println("""
                    Choose an option\s
                    u - work with users
                    p - work with publications
                    w - write to file as text all users
                    q - quit
                    """);
            cmd = scanner.nextLine();

            switch (cmd) {
                case "u":
                    MenuOperation.generateUserMenu(scanner, bookExchange);
                    break;
                case "p":
                    break;
                case "w":
                    ReadWriteOperation.writeToFileAsText(bookExchange.getAllUsers());
                    break;
                case "q":
                    System.out.println("Bye");
                default:
                    System.out.println("Learn to read");

            }
        }
    }
}
