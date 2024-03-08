package cryptoAlgos;

import javax.swing.JOptionPane;

public class ModifiedCaesarCipherAlgorithm implements CryptoAlgorithm {

    @Override
    public String encrypt(String plaintext) {
        int shift = Integer.parseInt(JOptionPane.showInputDialog("Enter the Key : "));
        StringBuilder encryptedText = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int shifted = (c - base + shift) % 26 + base;
                encryptedText.append((char) shifted);
            } else {
                encryptedText.append(c);
            }
        }
        return encryptedText.toString();
    }

    @Override
    public String decrypt(String ciphertext) {
        int shift = Integer.parseInt(JOptionPane.showInputDialog("Enter the Key : "));
        StringBuilder decryptedText = new StringBuilder();
        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int shifted = (c - base - shift + 26) % 26 + base;
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
