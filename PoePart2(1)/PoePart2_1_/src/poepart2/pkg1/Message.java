/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poepart2.pkg1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

/**
 * Handles creating, validating, hashing and storing chat messages.
 *
 * @author User
 */
public class Message {

    ArrayList<String> messages = new ArrayList<>();
    ArrayList<String> messageIdsList = new ArrayList<>();
    HashSet<String> messageIds = new HashSet<>();
    ArrayList<String> msgHash = new ArrayList<>();
    ArrayList<String> recipientNumber = new ArrayList<>();
    ArrayList<String> messageStatus = new ArrayList<>();

    Random random = new Random();
    Scanner input;

 
    int numMessageSent = 0;

    public Message() {
        this.input = new Scanner(System.in);
    }

    /**
     * Accepts a Scanner from the caller instead of always opening a
     * second Scanner on System.in. Two separate Scanner objects reading
     * the same InputStream can each buffer ahead and "steal" input meant
     * for the other, causing confusing skipped prompts. PoePart1 passes
     * its single Scanner in here so there is only ever one Scanner
     * reading the console.
     *
     * @param sharedScanner the single Scanner the whole application reads
     * console input from
     */
    public Message(Scanner sharedScanner) {
        this.input = sharedScanner;
    }

    public void sendingMessages() {

        System.out.print("How many messages do you want to send? ");
        int numberOfMessages = readInt();

        input.nextLine();

        for (int i = 1; i <= numberOfMessages; i++) {

            System.out.println("\n==============================");
            System.out.println("MESSAGE " + i);
            System.out.println("==============================");

            String messageID = generateMessageID();
            System.out.println(createMessageIDReport(messageID));

            System.out.print("Enter recipient cell number: ");
            String recipient = input.nextLine();

            String recipientResult = checkRecipientCell(recipient);

            if (!recipientResult.equals(CELL_SUCCESS)) {
                System.out.println(recipientResult);
                i--;
                continue;
            }
            System.out.println(recipientResult);

            System.out.print("Enter your message: ");
            String messageText = input.nextLine();

            String lengthResult = checkMessageLength(messageText);

            if (!lengthResult.equals(MESSAGE_READY)) {
                System.out.println(lengthResult);
                i--;
                continue;
            }
            System.out.println(lengthResult);

           
            int displayMessageNumber = i;

         
            String messageHash = createMessageHash(messageID, numMessageSent, messageText);

            msgHash.add(messageHash);
            messages.add(messageText);
            messageIdsList.add(messageID);
            messageIds.add(messageID);
            recipientNumber.add(recipient);

            String status = SentMessage();

            messageStatus.add(status);

            if (status.equals("Sent")) {
                numMessageSent++;
            }
        }

        printMessages();

        System.out.println("Total messages sent: " + returnTotalMessages());

        storeMessagesAsJSON("messages.json");
    }

    public int returnTotalMessages() {
        return numMessageSent;
    }

     //Builds the Message Hash: the first two characters of the Message
     //ID, a colon, the message number, a colon, then the first and last
     //word of the message, all in upper case - e.g. "00:0:HITONIGHT".
    
    public String createMessageHash(String messageID, int messageNumber, String messageText) {

        String firstTwoNumbers = messageID.substring(0, 2);

        String cleanMessage = messageText.trim();

        String[] words = cleanMessage.split("\\s+");

        String firstWord = words[0];

        String lastWord = words[words.length - 1];

        String hash = firstTwoNumbers + ":" + messageNumber + ":" + firstWord + lastWord;

        return hash.toUpperCase();
    }


    public String generateMessageID() {

        String messageID;

        do {
            StringBuilder randomDigits = new StringBuilder();
            for (int d = 0; d < 15; d++) {
                randomDigits.append(random.nextInt(10));
            }
            messageID = randomDigits.substring(0, 10);

        } while (messageIds.contains(messageID) || !checkMessageID(messageID));

        return messageID;
    }

    // Ensures the Message ID is no more than ten characters long.
     
    public boolean checkMessageID(String messageID) {
        return messageID != null && messageID.length() <= 10;
    }


    public String createMessageIDReport(String messageID) {
        return "Message ID generated: " + messageID;
    }

    private static final String CELL_SUCCESS = "Cell phone number successfully captured.";
    private static final String CELL_FAILURE = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";

    public String checkRecipientCell(String cellphone) {
        if (cellphone != null && cellphone.matches("^\\+27[0-9]{9}$")) {
            return CELL_SUCCESS;
        } else {
            return CELL_FAILURE;
        }
    }

    private static final int MAX_MESSAGE_LENGTH = 250;
    private static final String MESSAGE_READY = "Message ready to send.";

    //Validates that the message is not more than 250 characters long.
     
    public String checkMessageLength(String message) {
        if (message == null) {
            message = "";
        }
        if (message.length() <= MAX_MESSAGE_LENGTH) {
            return MESSAGE_READY;
        } else {
            int excess = message.length() - MAX_MESSAGE_LENGTH;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }


    public String SentMessage() {

        System.out.println("\nChoose what you want to do with this message!");

        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");

        System.out.println("Enter your choice: ");
        int choice = readInt();

        input.nextLine();

        switch (choice) {

            case 1 -> {
                System.out.println("Message successfully sent.");
                return "Sent";
            }

            case 2 -> {
                System.out.println("Press 0 to delete the message.");
                int deleteChoice = readInt();

                input.nextLine();

                if (deleteChoice == 0) {
                    System.out.println("Message deleted");

                    int lastIndex = messages.size() - 1;

                    messageIds.remove(messageIdsList.get(lastIndex));
                    messageIdsList.remove(lastIndex);
                    recipientNumber.remove(lastIndex);
                    messages.remove(lastIndex);
                    msgHash.remove(lastIndex);

                    return "Disregarded";
                } else {
                    System.out.println("Invalid option!");
                    return "Disregarded";
                }
            }

            case 3 -> {
                System.out.println("Message successfully stored.");
                return "Stored";
            }

            default -> {
                System.out.println("Invalid option");
                return "Disregarded";
            }
        }
    }


    public String printMessages() {

        StringBuilder allMessages = new StringBuilder();

        System.out.println("\n===== All Messages =====");

        for (int i = 0; i < messages.size(); i++) {
            int messageNumber = i + 1;

            allMessages.append("\nMessage Number: ").append(messageNumber);
            allMessages.append("\nMessage ID: ").append(messageIdsList.get(i));
            allMessages.append("\nMessage Hash: ").append(msgHash.get(i));
            allMessages.append("\nRecipient: ").append(recipientNumber.get(i));
            allMessages.append("\nMessage: ").append(messages.get(i));
            allMessages.append("\nStatus: ").append(messageStatus.get(i));

            System.out.println("\n==============================");

            System.out.println("\nMessage Number: " + messageNumber);
            System.out.println("Message ID: " + messageIdsList.get(i));
            System.out.println("Message Hash: " + msgHash.get(i));
            System.out.println("Recipient: " + recipientNumber.get(i));
            System.out.println("Message: " + messages.get(i));
            System.out.println("Status: " + messageStatus.get(i));
        }

        return allMessages.toString();
    }

    /**
     * Stores every message (and its ID, recipient, hash and status) to
     * a JSON file, satisfying the "Message is stored as a JSON File"
     * requirement. Written by hand (no external JSON library needed) so
     * the project compiles out of the box.
     *
     * @param filePath the path of the JSON file to write the messages to
     */
    public void storeMessagesAsJSON(String filePath) {

        StringBuilder json = new StringBuilder();
        json.append("[\n");

        for (int i = 0; i < messages.size(); i++) {
            json.append("  {\n");
            json.append("    \"messageNumber\": ").append(i + 1).append(",\n");
            json.append("    \"messageId\": \"").append(escapeJson(messageIdsList.get(i))).append("\",\n");
            json.append("    \"recipient\": \"").append(escapeJson(recipientNumber.get(i))).append("\",\n");
            json.append("    \"message\": \"").append(escapeJson(messages.get(i))).append("\",\n");
            json.append("    \"messageHash\": \"").append(escapeJson(msgHash.get(i))).append("\",\n");
            json.append("    \"status\": \"").append(escapeJson(messageStatus.get(i))).append("\"\n");
            json.append("  }");
            if (i < messages.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("]\n");

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json.toString());
            System.out.println("Messages stored to " + filePath);
        } catch (IOException e) {
            System.out.println("Could not store messages to JSON file: " + e.getMessage());
        }
    }

    /**
     * Escapes characters that are not allowed inside a JSON string
     * (double quotes and backslashes), so a message containing a quote
     * can't break the JSON file that gets written out.
     */
    private String escapeJson(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private int readInt() {
        while (!input.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            input.next();
        }
        return input.nextInt();
    }
}
