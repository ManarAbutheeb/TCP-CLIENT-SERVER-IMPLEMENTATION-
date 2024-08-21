package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket clientSocket = null ;// Connection to the server
        try {
            clientSocket  = new Socket("127.0.0.1", 1111);
        } catch (Exception e) {
            System.err.println("Try again later(the server is currently unavailable)");// Show if server is down
        }
        
        if(clientSocket != null){
        // For communicating with the server
        DataInputStream input = new DataInputStream(clientSocket.getInputStream());
        
        DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
        
        Scanner scanner = new Scanner(System.in);// Reading user input
        
        while (true) {
            System.out.println("\nChoose commands(B: to convert to binary, H: to convert to hexadecimal, Q: to quit the client program)");
            System.out.print("Enter a command: ");
            String command = scanner.nextLine().toUpperCase();
            switch (command) {
            case "Q": // Break loop and close the socket
                clientSocket.close();
                scanner.close();
                return;

            case "B":
            	System.out.print("Enter a number to convert to binary: ");
                String binaryNumber = scanner.nextLine();
                processNumber(binaryNumber, command, output, input);
                break;
            case "H":
            	System.out.print("Enter a number to convert to hexadecimal: ");
                String hexNumber = scanner.nextLine();
                processNumber(hexNumber, command, output, input);
                break;
            case "":
            	System.out.print("Enter a number: ");
                String number = scanner.nextLine();
                processNumber(number, command, output, input);
                break;
                // Analyzing the number
            default:
                // Error message command is unavailable
                System.out.println("Try again(unavailable command)");
                break;
        }
        }
    }
    }
        static void processNumber(String number, String command, DataOutputStream output, DataInputStream input) {
        	try {
                if (number.length() != 0) {
                    Integer.valueOf(number);
                }
                // Sending to server
                output.writeBytes(command + " " + number + "\n");

                String response = input.readLine();// From the server
                String[] parts = response.split(" ");// Split the response by space

                System.out.print("Response: ");
                if (parts[0].equals("200")) { // Is it "200" so indicate success
                    System.out.println("200 and the converted number is: " + parts[1]);
                }
                // Otherwise error message
                else {
                    for (String part : parts) {
                        System.out.print(part + " ");
                    }
                    System.out.println("");
                }
            }
            // Error message number should be integer
            catch (NumberFormatException e) {
                System.out.println("Try again(invalid number)");
            }
            // Error message issue with server communication
        	catch (IOException e) {
                System.out.println("Error communicating with the server. Try again.");
            }
        }
    }



