package algo;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA{

    private static BigInteger n, d, e;
    private static boolean keysGenerated = false;

    // Key generation
    private static void generateKeysIfNeeded(int bitLength) {
        if (!keysGenerated) {
            SecureRandom random = new SecureRandom();
            BigInteger p = new BigInteger(bitLength / 2, 100, random);
            BigInteger q = new BigInteger(bitLength / 2, 100, random);
            n = p.multiply(q);

            BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

            e = new BigInteger("65537"); // Commonly used value for e
            d = e.modInverse(phi);

            keysGenerated = true;
        }
    }

    // Encryption
    public static StringBuffer encrypt(String str){
        return RSA.encrypt(str, 0);
    }
    public static StringBuffer encrypt(String message,int k) {
        generateKeysIfNeeded(1024);
        byte[] bytes = message.getBytes();
        BigInteger plaintext = new BigInteger(bytes);
        BigInteger ciphertext = plaintext.modPow(e, n);
        return new StringBuffer(ciphertext.toString());
    }

    // Decryption
    public static StringBuffer decrypt(String str){
        return RSA.decrypt(str, 0);
    }
    public static StringBuffer decrypt(String ciphertext,int k) {
        generateKeysIfNeeded(1024);
        BigInteger encrypted = new BigInteger(ciphertext);
        BigInteger decrypted = encrypted.modPow(d, n);
        byte[] bytes = decrypted.toByteArray();
        return new StringBuffer(new String(bytes));
    }

    public static void main(String[] args) {
        // Example usage
        String originalMessage = "";
        StringBuffer encryptedMessage = encrypt(originalMessage,0);
        StringBuffer decryptedMessage = decrypt(encryptedMessage.toString(),0);

        System.out.println("Original message: " + originalMessage);
        System.out.println("Encrypted message: " + encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage);
    }
}
