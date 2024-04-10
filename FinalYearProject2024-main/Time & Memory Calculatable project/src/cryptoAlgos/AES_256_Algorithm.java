package cryptoAlgos;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class AES_256_Algorithm implements CryptoAlgorithm {

    private static final String ALGORITHM = "AES";

    @Override
    public String encrypt(String text) {
        try {
            long startTime = System.nanoTime();

            String key = JOptionPane.showInputDialog(null, "Enter 256-bit encryption key (16 chars):");
            if (key == null || key.isEmpty()) {
                throw new RuntimeException("Encryption key cannot be empty");
            }

            long keyInputTime = System.nanoTime() - startTime;

            if (text.isEmpty()) {
                byte[] data = readFile();
                if (data == null) {
                    return null;
                }
                byte[] encryptedBytes = performEncryption(data, key);

                saveToFile(encryptedBytes);

                long endTime = System.nanoTime();
                double elapsedTimeMilliseconds = (TimeUnit.NANOSECONDS.toMillis(endTime - startTime)) - TimeUnit.NANOSECONDS.toMillis(keyInputTime);
                System.out.println("(AES 256-Bits) File Encryption Time: " + elapsedTimeMilliseconds + " milliseconds");
                System.out.println("(AES 256-Bits) File Encryption Memory: " + getMemoryUsage() + " bytes");

                return null;
            } else {
                byte[] encryptedBytes = performEncryption(text.getBytes(StandardCharsets.UTF_8), key);

                long endTime = System.nanoTime();
                double elapsedTimeMilliseconds = (TimeUnit.NANOSECONDS.toMillis(endTime - startTime)) - TimeUnit.NANOSECONDS.toMillis(keyInputTime);
                System.out.println("(AES 256-Bits) String Encryption Time: " + elapsedTimeMilliseconds + " milliseconds");
                System.out.println("(AES 256-Bits) String Encryption Memory: " + getMemoryUsage() + " bytes");

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
            long startTime = System.nanoTime();

            String key = JOptionPane.showInputDialog(null, "Enter 256-bit decryption key (16 chars):");
            if (key == null || key.isEmpty()) {
                throw new RuntimeException("Decryption key cannot be empty");
            }

            long keyInputTime = System.nanoTime() - startTime;

            if (encryptedText.isEmpty()) {
                byte[] encryptedBytes = readFile();
                if (encryptedBytes == null) {
                    return null;
                }
                byte[] decryptedBytes = performDecryption(encryptedBytes, key);

                saveToFile(decryptedBytes);

                long endTime = System.nanoTime();
                double elapsedTimeMilliseconds = (TimeUnit.NANOSECONDS.toMillis(endTime - startTime)) - TimeUnit.NANOSECONDS.toMillis(keyInputTime);
                System.out.println("(AES 256-Bits) File Decryption Time: " + elapsedTimeMilliseconds + " milliseconds");
                System.out.println("(AES 256-Bits) File Decryption Memory: " + getMemoryUsage() + " bytes");

                return null;
            } else {
                byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
                byte[] decryptedBytes = performDecryption(decodedBytes, key);

                String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);

                long endTime = System.nanoTime();
                double elapsedTimeMilliseconds = (TimeUnit.NANOSECONDS.toMillis(endTime - startTime)) - TimeUnit.NANOSECONDS.toMillis(keyInputTime);
                System.out.println("(AES 256-Bits) String Decryption Time: " + elapsedTimeMilliseconds + " milliseconds");
                System.out.println("(AES 256-Bits) String Decryption Memory: " + getMemoryUsage() + " bytes");

                return decryptedText;
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] performDecryption(byte[] encryptedBytes, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(encryptedBytes);
    }

    private byte[] performEncryption(byte[] data, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(data);
    }

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
        return null;
    }

    private long getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
