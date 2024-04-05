import javax.swing.SwingUtilities;

public class CryptoApplication {
    public static void main(String[] args) {
        // Start the CryptoToolGUI
        SwingUtilities.invokeLater(CryptoToolGUI::new);
    }
}
