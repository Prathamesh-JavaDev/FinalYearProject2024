package cryptoAlgos;

import javax.swing.*;
import java.io.*;

public class ModifiedCaesarCipherAlgorithm implements CryptoAlgorithm {

    @Override
    public String encrypt(String plaintext) {
        // If input text area is null, prompt for file input
        if (plaintext == null || plaintext.isEmpty()) {
            byte[] data = readFile();
            if (data == null) {
                return null; // File selection cancelled
            }
            byte[] encryptedData = performEncryption(data);
            saveToFile(encryptedData);
            return null; // Return null as the output is saved to a file
        } else {
            
            String encryptedText = performEncryption(plaintext);
            
            return encryptedText;
        }
    }

    @Override
    public String decrypt(String ciphertext) {
        // If input text area is null, prompt for file input
        if (ciphertext == null || ciphertext.isEmpty()) {
            byte[] data = readFile();
            if (data == null) {
                return null; // File selection cancelled
            }
            byte[] decryptedData = performDecryption(data);
            saveToFile(decryptedData);
            return null; // Return null as the output is saved to a file
        } else {
            
            String decryptedText = performDecryption(ciphertext);
            
            return decryptedText;
        }
    }

    // Helper method to perform encryption on byte array
    private byte[] performEncryption(byte[] data) {
        int SHIFT = Integer.parseInt(JOptionPane.showInputDialog("Enter the Key for shift : "));
        
        byte[] encryptedData = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            if (Character.isLetter((char) b)) {
                char base = Character.isUpperCase((char) b) ? 'A' : 'a';
                int shifted = (((char) b - base + SHIFT) % 26 + 26) % 26 + base;
                encryptedData[i] = (byte) shifted;
            } else {
                encryptedData[i] = b;
            }
        }
        
        return encryptedData;
    }

    // Helper method to perform decryption on byte array
    private byte[] performDecryption(byte[] data) {
        int SHIFT = Integer.parseInt(JOptionPane.showInputDialog("Enter the Key for Reshift : "));
        
        byte[] decryptedData = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            if (Character.isLetter((char) b)) {
                char base = Character.isUpperCase((char) b) ? 'A' : 'a';
                int shifted = (((char) b - base - SHIFT + 26) % 26 + 26) % 26 + base;
                decryptedData[i] = (byte) shifted;
            } else {
                decryptedData[i] = b;
            }
        }
        
        return decryptedData;
    }

    // Helper method to save data to a file
    private void saveToFile(byte[] data) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File outputFile = fileChooser.getSelectedFile();
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(data);
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Helper method to read data from a file
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

    // Helper method to perform encryption on string
    private String performEncryption(String plaintext) {
        int SHIFT = Integer.parseInt(JOptionPane.showInputDialog("Enter the Key for shift : "));
        
        StringBuilder encryptedText = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int shifted = (((c - base) + SHIFT) % 26 + 26) % 26 + base;
                encryptedText.append((char) shifted);
            } else {
                encryptedText.append(c);
            }
        }
        
        return encryptedText.toString();
    }

    // Helper method to perform decryption on string
    private String performDecryption(String ciphertext) {
        int SHIFT = Integer.parseInt(JOptionPane.showInputDialog("Enter the Key for Reshift : "));
        
        StringBuilder decryptedText = new StringBuilder();
        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int shifted = (((c - base) - SHIFT + 26) % 26 + 26) % 26 + base;
                decryptedText.append((char) shifted);
            } else {
                decryptedText.append(c);
            }
        }

        
        return decryptedText.toString();
    }
}
