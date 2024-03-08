package cryptoAlgos;

public class CaesarCipherAlgorithm implements CryptoAlgorithm {
    private static final int SHIFT = 3; // Example shift value

    @Override
    public String encrypt(String plaintext) {
        StringBuilder encryptedText = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int shifted = (c - base + SHIFT) % 26 + base;
                encryptedText.append((char) shifted);
            } else {
                encryptedText.append(c);
            }
        }
        return encryptedText.toString();
    }

    @Override
    public String decrypt(String ciphertext) {
        StringBuilder decryptedText = new StringBuilder();
        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int shifted = (c - base - SHIFT + 26) % 26 + base;
                decryptedText.append((char) shifted);
            } else {
                decryptedText.append(c);
            }
        }
        return decryptedText.toString();
    }

    @Override
    public int getModifiers() {
        // Return the modifiers if needed
        return 0;
    }
}
