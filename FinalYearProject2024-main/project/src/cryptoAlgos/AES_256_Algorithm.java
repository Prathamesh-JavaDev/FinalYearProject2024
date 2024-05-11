package cryptoAlgos;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AES_256_Algorithm implements CryptoAlgorithm {

    private static final String ALGORITHM = "AES";

    @Override
    public String encrypt(String text) {
        try {
            String key = JOptionPane.showInputDialog(null, "Enter 256-bit encryption key (16 chars):");
            if (key == null || key.isEmpty()) {
                throw new RuntimeException("Encryption key cannot be empty");
            }
            // Check if text is empty, if so, prompt for file input
            if (text.isEmpty()) {
                byte[] data = readFile();
                if (data == null) {
                    return null; // File selection cancelled
                }
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

                byte[] encryptedBytes = cipher.doFinal(data);

                // Prompt the user to save the encrypted data to a file
                saveToFile(encryptedBytes);

                return null; // Return null as the output is saved to a file
            } else {
                // Pad the text to ensure it meets the block size requirement
                String paddedText = padText(text);

                Cipher cipher = Cipher.getInstance(ALGORITHM);
                SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

                byte[] encryptedBytes = cipher.doFinal(paddedText.getBytes(StandardCharsets.UTF_8));

                return Base64.getEncoder().encodeToString(encryptedBytes);
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String decrypt(String encryptedText) {
        try {
            String key = JOptionPane.showInputDialog(null, "Enter 256-bit decryption key (16 chars):");
            if (key == null || key.isEmpty()) {
                throw new RuntimeException("Decryption key cannot be empty");
            }
            // If encrypted text is empty, prompt for file input
            if (encryptedText.isEmpty()) {
                byte[] encryptedBytes = readFile();
                if (encryptedBytes == null) {
                    return null; // File selection cancelled
                }
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

                // Prompt the user to save the decrypted data to a file
                saveToFile(decryptedBytes);

                return null; // Return null as the output is saved to a file
            } else {
                // If encrypted text is provided as input, perform decryption
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

                byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
                byte[] decryptedBytes = cipher.doFinal(decodedBytes);

                // Return decrypted text as string
                String decryptedText = removePadding(decryptedBytes);

                return decryptedText;
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to remove padding from decrypted text
    private String removePadding(byte[] decryptedBytes) {
        int paddingLength = decryptedBytes[decryptedBytes.length - 1];
        return new String(decryptedBytes, 0, decryptedBytes.length - paddingLength, StandardCharsets.UTF_8);
    }

    // Helper method to pad the text to meet the block size requirement
    private String padText(String text) {
        int blockSize = 16; // AES block size is 16 bytes
        int paddingLength = blockSize - (text.length() % blockSize);
        StringBuilder paddedText = new StringBuilder(text);
        for (int i = 0; i < paddingLength; i++) {
            paddedText.append((char) paddingLength);
        }
        return paddedText.toString();
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
}
