package cryptoAlgos;

import javax.swing.*;
import java.io.*;

public class ArithmeticNoKeyAlgorithm implements CryptoAlgorithm {

    @Override
    public String encrypt(String plaintext) {
        // If input text is empty, prompt for file input
        if (plaintext.isEmpty()) {
            //Timer starts here
            long startTime = System.nanoTime();

            byte[] encryptedData = encryptFile(selectFile("Select input file for encryption"));

            //Timer ends here
            long endTime = System.nanoTime();
            long elapsedTime = (endTime - startTime) / 1000; // Convert to microseconds
            System.out.println("(Arithmetic Algorithm) Encryption Time: " + elapsedTime + " microseconds");

            if (encryptedData != null) {
                saveToFile(encryptedData, selectFile("Select output file to save encrypted data"));
            }
            return null; // Return null as the output is saved to a file
        } else {
            //Timer starts here
            long startTime = System.nanoTime();

            // Encryption logic for text input
            byte[] byteArray = plaintext.getBytes();
            int[] intArray = new int[byteArray.length];
            intArray[0] = byteArray[0];
            //ROUND-1
            // Convert byte array to int array
            for (int i = 1; i < byteArray.length; i++) {
                intArray[i] = byteArray[i] - byteArray[i - 1];
            }

            //ROUND-2
            for (int i = 0; i < intArray.length; i++) {
                intArray[i] = ((intArray[i] + 25) * 10);
            }

            //ROUND-3
            for (int i = 1; i < intArray.length; i++) {
                intArray[i] = intArray[i] + intArray[i - 1];
            }

            //ROUND-4
            for (int i = 0; i < intArray.length; i++) {
                intArray[i] = intArray[i] - 100;
            }

            // Convert back to ASCII and create encrypted string
            StringBuilder encryptedText = new StringBuilder();
            for (int value : intArray) {
                encryptedText.append((char) value);
            }

            //Timer ends here
            long endTime = System.nanoTime();
            long elapsedTime = (endTime - startTime) / 1000; // Convert to microseconds
            System.out.println("(Arithmetic Algorithm) Encryption Time: " + elapsedTime + " microseconds");

            return encryptedText.toString();
        }
    }

    @Override
    public String decrypt(String ciphertext) {
        // If input text is empty, prompt for file input
        if (ciphertext.isEmpty()) {
            //Timer starts here
            long startTime = System.nanoTime();

            byte[] decryptedData = decryptFile(selectFile("Select input file for decryption"));

            //Timer ends here
            long endTime = System.nanoTime();
            long elapsedTime = (endTime - startTime) / 1000; // Convert to microseconds
            System.out.println("(Arithmetic Algorithm) Decryption Time: " + elapsedTime + " microseconds");

            if (decryptedData != null) {
                saveToFile(decryptedData, selectFile("Select output file to save decrypted data"));
            }
            return null; // Return null as the output is saved to a file
        } else {
            //Timer starts here
            long startTime = System.nanoTime();

            // Decryption logic for text input
            int[] intArray = new int[ciphertext.length()];

            // Convert ASCII characters to int array
            for (int i = 0; i < ciphertext.length(); i++) {
                intArray[i] = ciphertext.charAt(i);
            }

            //ROUND-1
            for (int i = 0; i < intArray.length; i++) {
                intArray[i] = intArray[i] + 100;
            }

            //ROUND-2
            for (int i = intArray.length - 1; i > 0; i--) {
                intArray[i] = intArray[i] - intArray[i - 1];
            }

            //ROUND-3
            for (int i = 0; i < intArray.length; i++) {
                intArray[i] = ((intArray[i] / 10) - 25);
            }

            //ROUND-4
            for (int i = 1; i < intArray.length; i++) {
                intArray[i] = intArray[i] + intArray[i - 1];
            }

            //Timer ends here
            long endTime = System.nanoTime();
            long elapsedTime = (endTime - startTime) / 1000; // Convert to microseconds
            System.out.println("(Arithmetic Algorithm) Decryption Time: " + elapsedTime + " microseconds");

            //Decrypted txt to console...
            StringBuilder decryptedText = new StringBuilder();
            for (int value : intArray) {
                decryptedText.append((char) value);
            }
            return decryptedText.toString();
        }
    }

    public byte[] encryptFile(File inputFile) {
        // Encryption logic for file input
        try {
            FileInputStream fis = new FileInputStream(inputFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int prevByte = fis.read();
            bos.write(prevByte);
            int data;
            while ((data = fis.read()) != -1) {
                data = data - prevByte;
                data = (data + 25) * 10;
                data = data + prevByte;
                data = data - 100;
                prevByte = data;
                bos.write(data);
            }
            fis.close();
            bos.close();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] decryptFile(File inputFile) {
        // Decryption logic for file input is the same as encryption
        return encryptFile(inputFile);
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
}
