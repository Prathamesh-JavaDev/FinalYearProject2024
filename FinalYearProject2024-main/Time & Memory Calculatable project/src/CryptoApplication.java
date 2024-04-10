import javax.swing.SwingUtilities;

import gui.CryptoToolGUI;

public class CryptoApplication {
    public static void main(String[] args) {
        // Start the CryptoToolGUI
        SwingUtilities.invokeLater(CryptoToolGUI::new);
    }
}
