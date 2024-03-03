package cryptoAlgos;

public interface CryptoAlgorithm {
    String encrypt(String plaintext, String key);
    String decrypt(String ciphertext, String key);
    int getModifiers();
}