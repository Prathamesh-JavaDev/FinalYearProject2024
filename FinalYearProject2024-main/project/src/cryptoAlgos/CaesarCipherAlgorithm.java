package cryptoAlgos;

import javax.swing.*;
import java.io.*;

public class CaesarCipherAlgorithm implements CryptoAlgorithm {
    private static final int SHIFT = 3; // Fixed shift value

    @Override
    public String encrypt(String plaintext) {
        long startTime = System.nanoTime();

        StringBuilder encryptedText = new StringBuilder();

        if (plaintext == null || plaintext.isEmpty()) {
            byte[] data = readFile();
            if (data == null) {
                return null; // File selection cancelled
            }
            for (byte b : data) {
                if (Character.isLetter((char) b)) {
                    char base = Character.isUpperCase((char) b) ? 'A' : 'a';
                    int shifted = (((char) b - base + SHIFT) % 26 + 26) % 26 + base;
                    encryptedText.append((char) shifted);
                } else {
                    encryptedText.append((char) b);
                }
            }
        } else {
            for (char c : plaintext.toCharArray()) {
                if (Character.isLetter(c)) {
                    char base = Character.isUpperCase(c) ? 'A' : 'a';
                    int shifted = (((c - base) + SHIFT) % 26 + 26) % 26 + base;
                    encryptedText.append((char) shifted);
                } else {
                    encryptedText.append(c);
                }
            }
        }

        long endTime = System.nanoTime();
        double elapsedTimeMilliseconds = (endTime - startTime) / 1_000_000.0; // Convert nanoseconds to milliseconds
        System.out.println("(Caesar Cipher) Encryption Time: " + elapsedTimeMilliseconds + " milliseconds");

        return encryptedText.toString();
    }

    @Override
    public String decrypt(String ciphertext) {
        long startTime = System.nanoTime();

        StringBuilder decryptedText = new StringBuilder();

        if (ciphertext == null || ciphertext.isEmpty()) {
            byte[] data = readFile();
            if (data == null) {
                return null; // File selection cancelled
            }
            for (byte b : data) {
                if (Character.isLetter((char) b)) {
                    char base = Character.isUpperCase((char) b) ? 'A' : 'a';
                    int shifted = (((char) b - base - SHIFT + 26) % 26 + 26) % 26 + base;
                    decryptedText.append((char) shifted);
                } else {
                    decryptedText.append((char) b);
                }
            }
        } else {
            for (char c : ciphertext.toCharArray()) {
                if (Character.isLetter(c)) {
                    char base = Character.isUpperCase(c) ? 'A' : 'a';
                    int shifted = (((c - base) - SHIFT + 26) % 26 + 26) % 26 + base;
                    decryptedText.append((char) shifted);
                } else {
                    decryptedText.append(c);
                }
            }
        }

        long endTime = System.nanoTime();
        double elapsedTimeMilliseconds = (endTime - startTime) / 1_000_000.0; // Convert nanoseconds to milliseconds
        System.out.println("(Caesar Cipher) Decryption Time: " + elapsedTimeMilliseconds + " milliseconds");

        return decryptedText.toString();
    }

    // Helper method to save data to a file
    private byte[] readFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File inputFile = fileChooser.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(inputFile)) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len;
                while ((len = fis.read(buf)) > 0) {
                    bos.write(buf, 0, len);
                }
                return bos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null; // Return null if file selection cancelled or error occurred
    }
}
