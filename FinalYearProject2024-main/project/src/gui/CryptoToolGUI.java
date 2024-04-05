import javax.swing.*;
import cryptoAlgos.AlgorithmLoader;
import cryptoAlgos.CryptoAlgorithm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class CryptoToolGUI extends JFrame {
    private JLabel algorithmLabel;
    private JComboBox<String> algorithmComboBox;
    private JLabel inputLabel;
    private JLabel outputLabel;
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JButton encryptButton;
    private JButton decryptButton;
    private List<Class<? extends CryptoAlgorithm>> algorithmClasses;

    public CryptoToolGUI() {
        setTitle("Crypto Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize algorithm classes
        algorithmClasses = AlgorithmLoader.loadAlgorithmClasses();

        // Create components
        algorithmLabel = new JLabel("Select Algorithm:");
        algorithmComboBox = new JComboBox<>();
        for (Class<? extends CryptoAlgorithm> algorithmClass : algorithmClasses) {
            algorithmComboBox.addItem(algorithmClass.getSimpleName());
        }
        inputLabel = new JLabel("Input Text:");
        outputLabel = new JLabel("Output Text:");
        inputTextArea = new JTextArea(10, 40);
        outputTextArea = new JTextArea(10, 40);
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");

        // Setup layout manager
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Horizontal group
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(algorithmLabel)
                        .addComponent(algorithmComboBox)
                )
                .addComponent(inputLabel)
                .addComponent(inputTextArea)
                .addComponent(outputLabel)
                .addComponent(outputTextArea)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(encryptButton)
                        .addComponent(decryptButton)
                )
        );

        // Vertical group
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(algorithmLabel)
                        .addComponent(algorithmComboBox)
                )
                .addComponent(inputLabel)
                .addComponent(inputTextArea)
                .addComponent(outputLabel)
                .addComponent(outputTextArea)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(encryptButton)
                        .addComponent(decryptButton)
                )
        );

        // Add action listeners for buttons
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encryptText();
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decryptText();
            }
        });

        pack();
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    // Method to instantiate selected algorithm class
    private CryptoAlgorithm instantiateAlgorithm() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        int selectedIndex = algorithmComboBox.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < algorithmClasses.size()) {
            Class<? extends CryptoAlgorithm> algorithmClass = algorithmClasses.get(selectedIndex);
            Constructor<? extends CryptoAlgorithm> constructor = algorithmClass.getConstructor();
            return constructor.newInstance();
        } else {
            throw new IllegalArgumentException("Invalid algorithm selection");
        }
    }

    // Method to encrypt text
    private void encryptText() {
        try {
            CryptoAlgorithm algorithm = instantiateAlgorithm();
            String inputText = inputTextArea.getText();
            String encryptedText = algorithm.encrypt(inputText);
            outputTextArea.setText(encryptedText);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error encrypting text: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Method to decrypt text
    private void decryptText() {
        try {
            CryptoAlgorithm algorithm = instantiateAlgorithm();
            String inputText = inputTextArea.getText();
            String decryptedText = algorithm.decrypt(inputText);
            outputTextArea.setText(decryptedText);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error decrypting text: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
