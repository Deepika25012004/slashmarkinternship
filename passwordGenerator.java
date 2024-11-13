import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class passwordGenerator {
    private static SecretKey secretKey;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize the AES encryption key
        initializeKey();

        boolean running = true;
        while (running) {
            System.out.println("\n--- Password Manager ---");
            System.out.println("1. Generate Password");
            System.out.println("2. Encrypt Password");
            System.out.println("3. Decrypt Password");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Generated Password: " + generatePassword());
                    break;
                case 2:
                    System.out.print("Enter password to encrypt: ");
                    String password = scanner.nextLine();
                    String encrypted = encrypt(password);
                    System.out.println("Encrypted Password: " + encrypted);
                    break;
                case 3:
                    System.out.print("Enter encrypted password to decrypt: ");
                    String encryptedPassword = scanner.nextLine();
                    String decrypted = decrypt(encryptedPassword);
                    System.out.println("Decrypted Password: " + decrypted);
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting Password Manager...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void initializeKey() {
        try {
            // Generate a new AES key for encryption
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128); // AES key size can be 128, 192, or 256 bits
            secretKey = keyGen.generateKey();
        } catch (Exception e) {
            System.out.println("Error generating key: " + e.getMessage());
        }
    }

    private static String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(12);

        for (int i = 0; i < 12; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    private static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            System.out.println("Error encrypting data: " + e.getMessage());
            return null;
        }
    }

    private static String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData);
        } catch (Exception e) {
            System.out.println("Error decrypting data: " + e.getMessage());
            return null;
        }
    }
}

