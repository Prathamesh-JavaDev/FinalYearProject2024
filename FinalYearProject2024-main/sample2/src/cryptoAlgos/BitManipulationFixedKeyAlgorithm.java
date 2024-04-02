package cryptoAlgos;

public class BitManipulationFixedKeyAlgorithm implements CryptoAlgorithm {
    private static final int CONSTANT = 255;

    @Override
    public String encrypt(String plaintext) {
        // Step 1: Convert plaintext to ASCII bytearray
        byte[] byteArray = plaintext.getBytes();
        
        // Step 2: Convert bytearray to int array
        int[] intArray = new int[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            intArray[i] = byteArray[i] & 0xFF; // Convert byte to unsigned int
        }

        // Step 3: Find 1's complement
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = ~intArray[i];
        }

        // Step 4: XOR each element with constant
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] ^= CONSTANT;
        }

        // Step 5: Display as ASCII character string
        StringBuilder encryptedText = new StringBuilder();
        for (int i : intArray) {
            encryptedText.append((char) i);
        }

        return encryptedText.toString();
    }

    @Override
    public String decrypt(String ciphertext) {
        // Step 1: Convert encrypted text to int array
        int[] intArray = new int[ciphertext.length()];
        for (int i = 0; i < ciphertext.length(); i++) {
            intArray[i] = ciphertext.charAt(i);
        }

        // Step 2: XOR each element with constant
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] ^= CONSTANT;
        }

        // Step 3: Find 1's complement
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = ~intArray[i];
        }

        // Step 4: Display as ASCII character string
        StringBuilder decryptedText = new StringBuilder();
        for (int i : intArray) {
            decryptedText.append((char) i);
        }

        return decryptedText.toString();
    }
}

