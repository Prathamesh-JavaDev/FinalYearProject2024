package cryptoAlgos;

import javax.swing.*;
import java.io.*;

public class BitManipulationFixedKeyAlgorithm implements CryptoAlgorithm {
    private static final int CONSTANT = 255;

    @Override
    public String encrypt(String plaintext) {
        // If input text is empty, prompt for file input
        if (plaintext.isEmpty()) {

            File inputFile = selectFile("Select input file for encryption");
            byte[] encryptedData = encryptFile(inputFile);
            if (encryptedData != null) {
                saveToFile(encryptedData, selectFile("Select output file to save encrypted data"));
            }

            return null; // Return null as the output is saved to a file
        } else {
            long startTime = System.nanoTime();
            // Encryption logic for text input
            byte[] byteArray = plaintext.getBytes();
            int[] intArray = new int[byteArray.length];
            for (int i = 0; i < byteArray.length; i++) {
                intArray[i] = byteArray[i] & 0xFF;
            }
            for (int i = 0; i < intArray.length; i++) {
                intArray[i] = ~intArray[i];
            }
            for (int i = 0; i < intArray.length; i++) {
                intArray[i] ^= CONSTANT;
            }
            StringBuilder encryptedText = new StringBuilder();
            for (int i : intArray) {
                encryptedText.append((char) i);
            }

            long endTime = System.nanoTime();
            double elapsedTime = (endTime - startTime) / 1e6; // Convert to milliseconds
            System.out.println("(Bit Manipulation) String Encryption Time: " + elapsedTime + " milliseconds");

            long memoryRequired = measureMemoryUsage();
            System.out.println("(Bit Manipulation) String Encryption Memory Required: " + memoryRequired + " bytes");

            return encryptedText.toString();
        }
    }

    @Override
    public String decrypt(String ciphertext) {
        // If input text is empty, prompt for file input
        if (ciphertext.isEmpty()) {

            File inputFile = selectFile("Select input file for decryption");
            byte[] decryptedData = decryptFile(inputFile);
            if (decryptedData != null) {
                saveToFile(decryptedData, selectFile("Select output file to save decrypted data"));
            }

            return null; // Return null as the output is saved to a file
        } else {
            long startTime = System.nanoTime();
            // Decryption logic for text input
            int[] intArray = new int[ciphertext.length()];
            for (int i = 0; i < ciphertext.length(); i++) {
                intArray[i] = ciphertext.charAt(i);
            }
            for (int i = 0; i < intArray.length; i++) {
                intArray[i] ^= CONSTANT;
            }
            for (int i = 0; i < intArray.length; i++) {
                intArray[i] = ~intArray[i];
            }
            StringBuilder decryptedText = new StringBuilder();
            for (int i : intArray) {
                decryptedText.append((char) i);
            }

            long endTime = System.nanoTime();
            double elapsedTime = (endTime - startTime) / 1e6; // Convert to milliseconds
            System.out.println("(Bit Manipulation) String Decryption Time: " + elapsedTime + " milliseconds");

            long memoryRequired = measureMemoryUsage();
            System.out.println("(Bit Manipulation) String Decryption Memory Required: " + memoryRequired + " bytes");

            return decryptedText.toString();
        }
    }

    public byte[] encryptFile(File inputFile) {
        long startTime = System.nanoTime();
        // Encryption logic for file input
        try {
            FileInputStream fis = new FileInputStream(inputFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int data;
            while ((data = fis.read()) != -1) {
                data ^= CONSTANT;
                bos.write(data);
            }
            fis.close();
            bos.close();
            byte[] encryptedData = bos.toByteArray();

            long endTime = System.nanoTime();
            double elapsedTime = (endTime - startTime) / 1e6; // Convert to milliseconds
            System.out.println("(Bit Manipulation) File Encryption Time: " + elapsedTime + " milliseconds");

            long memoryRequired = measureMemoryUsage();
            System.out.println("(Bit Manipulation) File Encryption Memory Required: " + memoryRequired + " bytes");

            return encryptedData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] decryptFile(File inputFile) {
        long startTime = System.nanoTime();
        // Decryption logic for file input is the same as encryption
        byte[] decryptedData = encryptFile(inputFile);
        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) / 1e6; // Convert to milliseconds
        System.out.println("(Bit Manipulation) File Decryption Time: " + elapsedTime + " milliseconds");

        long memoryRequired = measureMemoryUsage();
        System.out.println("(Bit Manipulation) File Decryption Memory Required: " + memoryRequired + " bytes");

        return decryptedData;
    }

    private File selectFile(String dialogTitle) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(dialogTitle);
        int returnValue = fileChooser.showDialog(null, "Select");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }

    private void saveToFile(byte[] data, File outputFile) {
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(data);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long measureMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
