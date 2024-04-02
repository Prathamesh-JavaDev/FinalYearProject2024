package cryptoAlgos;

public class ArithmeticNoKeyAlgorithm implements CryptoAlgorithm {
    
    @Override
    public String encrypt(String plaintext) {
        byte[] byteArray = plaintext.getBytes();
        int[] intArray = new int[byteArray.length];
        intArray[0]=byteArray[0];
        
        //ROUND-1
        // Convert byte array to int array
        for (int i = 1; i < byteArray.length; i++) {
            intArray[i] = byteArray[i] - byteArray[i - 1];
        }

        //ROUND-2
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = ((intArray[i] + 25) * 10) ;
        }
       
        //ROUND-3
        for (int i = 1; i < intArray.length; i++) {
            intArray[i] = intArray[i] + intArray[i-1] ;
        }

        //ROUND-4
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = intArray[i] - 100 ;
        }

        // Convert back to ASCII and create encrypted string
        StringBuilder encryptedText = new StringBuilder();
        for (int value : intArray) {
            encryptedText.append((char) value);
        }
        return encryptedText.toString();
    }

    @Override
    public String decrypt(String ciphertext) {
        int[] intArray = new int[ciphertext.length()];
        
        // Convert ASCII characters to int array
        for (int i = 0; i < ciphertext.length(); i++) {
            intArray[i] = ciphertext.charAt(i);
        }
       
        //ROUND-1
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = intArray[i] + 100 ;
        }

        //ROUND-2
        for (int i = intArray.length - 1; i > 0; i--) {
            intArray[i] = intArray[i] - intArray[i-1] ;
        }

        //ROUND-3
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = ((intArray[i] / 10) -25 );
        }

        //ROUND-4
        for (int i = 1; i < intArray.length; i++) {
            intArray[i] = intArray[i] + intArray[i-1] ;
        }
        
        //Decrypted txt to console...
        StringBuilder decryptedText = new StringBuilder();
        for (int value : intArray) {
            decryptedText.append((char) value);
        }
        return decryptedText.toString();
    }
}
