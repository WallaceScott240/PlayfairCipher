import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Playfair Cipher Encryption & Decryption");
        System.out.println("1. Encrypt a message");
        System.out.println("2. Decrypt a file");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            System.out.print("Enter the key: ");
            String key = scanner.nextLine();
            System.out.print("Enter the message to encrypt: ");
            String message = scanner.nextLine();

            Encrypt encryptor = new Encrypt(key);
            String encryptedText = encryptor.encrypt(message);

            try {
                Files.write(new File("encrypted.txt").toPath(), encryptedText.getBytes());
                System.out.println("Encrypted text saved to encrypted.txt");
            } catch (IOException e) {
                System.out.println("Error saving file: " + e.getMessage());
            }
        } else if (choice == 2) {
            System.out.print("Enter the key: ");
            String key = scanner.nextLine();

            try {
                String encryptedText = Files.readString(new File("encrypted.txt").toPath());
                Decrypt decryptor = new Decrypt(key);
                String decryptedText = decryptor.decrypt(encryptedText);
                System.out.println("Decrypted message: " + decryptedText);
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid choice!");
        }

        scanner.close();
    }
}
