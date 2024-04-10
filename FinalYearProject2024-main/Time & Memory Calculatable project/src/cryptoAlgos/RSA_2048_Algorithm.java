package cryptoAlgos;

import javax.crypto.*;
import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA_2048_Algorithm implements CryptoAlgorithm {

    private static final String ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;

    @Override
    public String encrypt(String text) {

        try {
            // If input text area is null, prompt for input and output files
            if (text == null || text.isEmpty()) {
                byte[] data = readFile();
                if (data == null) {
                    return null; // File selection cancelled
                }

                // Ask user if they have existing key pairs
                int option = JOptionPane.showConfirmDialog(null, "Do you have existing public and private key pairs?");
                Key key;
                if (option == JOptionPane.YES_OPTION) {
                    key = readKey();
                } else {
                    key = generateAndSaveKeyPair().getPublic();
                }

                // Start timer for encryption
                long startTime = System.nanoTime();

                // Measure memory before encryption
                long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, key);

                // Encrypt data
                byte[] encryptedBytes = cipher.doFinal(data);

                // Measure memory after encryption
                long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                // Stop timer for encryption
                long endTime = System.nanoTime();

                // Calculate elapsed time for encryption
                long elapsedTime = endTime - startTime;
                System.out.println("(RSA 2048-Bits) File Encryption Time: " + (elapsedTime / 1_000_000) + " milliseconds");

                // Estimate memory required for encryption
                long memoryRequired = memoryAfter - memoryBefore;
                System.out.println("(RSA 2048-Bits) File Encryption Memory Required: " + memoryRequired + " bytes");

                // Save encrypted data to file
                saveToFile(encryptedBytes, chooseOutputFile());

                return null; // Return null as the output is saved to a file
            } else {
                // Ask user if they have existing key pairs
                int option = JOptionPane.showConfirmDialog(null, "Do you have existing public and private key pairs?");
                Key key;
                if (option == JOptionPane.YES_OPTION) {
                    key = readKey();
                } else {
                    key = generateAndSaveKeyPair().getPublic();
                }

                // Start timer for encryption
                long startTime = System.nanoTime();

                // Measure memory before encryption
                long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, key);

                // Encrypt text
                byte[] encryptedBytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

                // Measure memory after encryption
                long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                // Stop timer for encryption
                long endTime = System.nanoTime();

                // Calculate elapsed time for encryption
                long elapsedTime = endTime - startTime;
                System.out.println("(RSA 2048-Bits) String Encryption Time: " + (elapsedTime / 1_000_000) + " milliseconds");

                // Estimate memory required for encryption
                long memoryRequired = memoryAfter - memoryBefore;
                System.out.println("(RSA 2048-Bits) String Encryption Memory Required: " + memoryRequired + " bytes");

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
            // If input text area is null, prompt for input and output files
            if (encryptedText == null || encryptedText.isEmpty()) {
                byte[] encryptedBytes = readFile();
                if (encryptedBytes == null) {
                    return null; // File selection cancelled
                }

                // Ask user to specify key file
                Key key = readKey();

                // Start timer for decryption
                long startTime = System.nanoTime();

                // Measure memory before decryption
                long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, key);

                // Decrypt data
                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

                // Measure memory after decryption
                long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                // Stop timer for decryption
                long endTime = System.nanoTime();

                // Calculate elapsed time for decryption
                long elapsedTime = endTime - startTime;
                System.out.println("(RSA 2048-Bits) File Decryption Time: " + (elapsedTime / 1_000_000) + " milliseconds");

                // Estimate memory required for decryption
                long memoryRequired = memoryAfter - memoryBefore;
                System.out.println("(RSA 2048-Bits) File Decryption Memory Required: " + memoryRequired + " bytes");

                // Save decrypted data to file
                saveToFile(decryptedBytes, chooseOutputFile());

                return null; // Return null as the output is saved to a file
            } else {
                // Ask user to specify key file
                Key key = readKey();

                // Start timer for decryption
                long startTime = System.nanoTime();

                // Measure memory before decryption
                long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, key);

                // Decode and decrypt text
                byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
                byte[] decryptedBytes = cipher.doFinal(decodedBytes);

                // Measure memory after decryption
                long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                // Stop timer for decryption
                long endTime = System.nanoTime();

                // Calculate elapsed time for decryption
                long elapsedTime = endTime - startTime;
                System.out.println("(RSA 2048-Bits) String Decryption Time: " + (elapsedTime / 1_000_000) + " milliseconds");

                // Estimate memory required for decryption
                long memoryRequired = memoryAfter - memoryBefore;
                System.out.println("(RSA 2048-Bits) String Decryption Memory Required: " + memoryRequired + " bytes");

                return new String(decryptedBytes, StandardCharsets.UTF_8);
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to generate and save key pair
    private KeyPair generateAndSaveKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            saveKeyToFile(keyPair.getPublic(), "public.key");
            saveKeyToFile(keyPair.getPrivate(), "private.key");

            return keyPair;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to save key to file
    private void saveKeyToFile(Key key, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(key.getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to read key from file
    private Key readKey() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose key file");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile))) {
                byte[] keyBytes = (byte[]) ois.readObject();
                KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
                if (selectedFile.getName().contains("public")) {
                    return keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
                } else {
                    return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
                }
            } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Helper method to save data to a file
    private void saveToFile(byte[] data, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(data);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to read data from a file
    private byte[] readFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose file");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(selectedFile)) {
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

    // Helper method to choose output file
    private String chooseOutputFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose output file");
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
}
