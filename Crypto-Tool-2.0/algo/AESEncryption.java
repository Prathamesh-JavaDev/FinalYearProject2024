package algo;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESEncryption {

    //private static final String SECRET_KEY = "YourSecretKey123"; // Replace with your secret key

    public static StringBuffer encrypt(String plaintext) {

        String SECRET_KEY = JOptionPane.showInputDialog("Enter the Secret key:(16 chars only) ");

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            return new StringBuffer(Base64.getEncoder().encodeToString(encryptedBytes));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static StringBuffer decrypt(String ciphertext) {

        String SECRET_KEY = JOptionPane.showInputDialog("Enter the Secret key:(16 chars only) ");

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
            return new StringBuffer(new String(decryptedBytes, StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String originalText = "Hello, AES!";
        
        // Encryption
        StringBuffer encryptedText = encrypt(originalText);
        System.out.println("Encrypted: " + encryptedText);

        // Decryption
        StringBuffer decryptedText = decrypt(encryptedText.toString());
        System.out.println("Decrypted: " + decryptedText);
    }
}

