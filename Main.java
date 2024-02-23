import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

// A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
// Uses a Caesar cipher variation
// May change to using a String builder for better efficiency

public class Main {
    public static void main(String[] args) {
        // Declarations with default values for command terminal arguments
        String mode = "enc";
        int key = 0;
        String data = "";
        String inFilePath = "";
        String outFilePath = "";
        String alg = "";


        // Terminal arguments
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mode":
                    mode = args[i + 1];
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    data = args[i + 1];
                    break;
                case "-in":
                    inFilePath = args[i + 1];
                    break;
                case "-out":
                    outFilePath = args[i + 1];
                    break;
                case "-alg":
                    alg = args[i + 1];
                    break;
            }
        }

        // if there is no -data and no -in it will assume that data is an empty string
        if (!inFilePath.equals("") && data.equals("")) {
            File file = new File(inFilePath);
            try (Scanner scanner = new Scanner(file)) {
                if (scanner.hasNextLine()) {
                    data = scanner.nextLine();
                }
            } catch (FileNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }
        }

        // Lambda expression to process input
        // I.e. if mode = enc it runs encrypt else runs decrypt
        String result = "";
        if(alg.equals("") || alg.equals("shift")){
            result = mode.equals("enc") ? encryptShift(data, key) : decryptShift(data, key);
        } else if(alg.equals("unicode")){
            result = mode.equals("enc") ? encryptUnicode(data, key) : decryptUnicode(data, key);
        } else{
            System.out.println("Invalid algorithm choice");
        }

        // If there is no -out argument, it must print data to standard output
        if (!outFilePath.equals("")) {
            try (PrintWriter writer = new PrintWriter(outFilePath)) {
                writer.println(result);
            } catch (FileNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println(result);
        }
    }

    //Encrypts inputted data
    private static String encryptShift(String message, int key) {
        String encoded = "";
        for (int i = 0; i < message.length(); i++) {
            char letter = message.charAt(i);

            // ASCII printalbe symbol range
            // 127 - 32 = 95
            int base = 32;
            int range = 95;

            int oldPosition = letter - base;
            int newPosition = (oldPosition + key) % range;
            char newLetter = (char) (newPosition + base);

            encoded += newLetter;
        }

        return encoded;
    }
    //Encrypts inputted data from A to Z and a to z
    private static String encryptUnicode(String message, int key) {
        String encoded = "";
        for (int i = 0; i < message.length(); i++) {
            char letter = message.charAt(i);
            if (letter >= 'A' && letter <= 'Z') {
                // Shift uppercase letters
                char shifted = (char) (((letter - 'A' + key) % 26) + 'A');
                encoded += shifted;
            } else if (letter >= 'a' && letter <= 'z') {
                // Shift lowercase letters
                char shifted = (char) (((letter - 'a' + key) % 26) + 'a');
                encoded += shifted;
            } else {
                // Leave non-alphabetic characters unchanged
                encoded += letter;
            }
        }
        return encoded;
    }

    // Decrypts unicode from A to Z and a to z
    private static String decryptUnicode(String message, int key) {
        String decoded = "";
        for (int i = 0; i < message.length(); i++) {
            char letter = message.charAt(i);
            char oldLetter = (char) (letter - key);
            decoded += oldLetter;
        }

        return decoded;
    }



    // Decrypts inputted data if correct key is provided
    private static String decryptShift(String message, int key) {
        String decoded = "";
        for (int i = 0; i < message.length(); i++) {
            char letter = message.charAt(i);

            // ASCII printalbe symbol range
            // 127 - 32 = 95
            int start = 32;
            int range = 95;

            int encryptedPosition = letter - start;
            int oldPosition = (encryptedPosition - key) % range;
            if (oldPosition < 0) {
                oldPosition += range;
            }

            char oldLetter = (char) (oldPosition + start);
            decoded += oldLetter;
        }

        return decoded;
    }
}


