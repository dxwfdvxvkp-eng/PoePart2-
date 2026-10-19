/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package poepart2.pkg1;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class PoePart21 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Login register = new Login();
        Scanner scan = new Scanner(System.in);
        // FIX: pass the one shared Scanner into Message instead of
        // letting it open a second Scanner(System.in) - see Message.java.
        Message msg = new Message(scan);

        // The brief requires this exact welcome message text.
        System.out.println("Welcome to QuickChat.");

        System.out.println("\n===== Register =====");

        System.out.println("Please enter the username: ");
        String name = scan.nextLine();

        System.out.println("Enter password: ");
        String password = scan.nextLine();

        System.out.println("Enter cellphone: ");
        String cellphone = scan.nextLine();

        System.out.println(register.registerUser(name, password, cellphone));

        if (!register.registerStatus(name, password, cellphone)) {
            return;
        }

        System.out.println("\n===== Login =====");
        System.out.print("Enter username: ");
        String loginUsername = scan.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = scan.nextLine();

        boolean status = register.loginUser(loginUsername, loginPassword);

        System.out.println(register.returnLoginStatus(status));

        if (status) {

            int option;

            do {
                System.out.println("\n===== Select any of the option below =====");
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");

                // FIX: scan.nextInt() used to be called directly, which
                // throws an uncaught InputMismatchException (crashing the
                // program) if the user typed something that isn't a
                // number. We now re-prompt instead of crashing.
                while (!scan.hasNextInt()) {
                    System.out.println("Please enter a valid option (1-3).");
                    scan.next();
                }
                option = scan.nextInt();
                scan.nextLine();

                switch (option) {
                    case 1 ->
                        msg.sendingMessages();
                    case 2 ->
                        // This feature is still in development - the
                        // brief requires this exact message.
                        System.out.println("Coming Soon.");
                    case 3 ->
                        System.out.println("Good bye!!");
                    default ->
                        System.out.println("Invalid option!!");
                }

            } while (option != 3);
        }
    }
}
