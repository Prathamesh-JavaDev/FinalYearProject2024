package cryptoAlgos;

public interface CryptoAlgorithm {
    String encrypt(String plaintext);
    String decrypt(String ciphertext);
    int getModifiers();
}