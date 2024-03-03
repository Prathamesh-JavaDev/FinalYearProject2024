import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import cryptoAlgos.AlgorithmLoader;
import cryptoAlgos.CryptoAlgorithm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class CryptoToolGUI extends JFrame {
    private JLabel algorithmLabel;
    private JComboBox<String> algorithmComboBox;
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton loadFromFileButton;
    private JButton saveButton;
    private JFileChooser fileChooser;
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
        inputTextArea = new JTextArea(10, 40);
        outputTextArea = new JTextArea(10, 40);
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");
        loadFromFileButton = new JButton("Load from File");
        saveButton = new JButton("Save to File");

        // Initialize file chooser
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

        // Setup layout manager
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Horizontal group
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(algorithmLabel)
                        .addComponent(algorithmComboBox)
                )
                .addComponent(inputScrollPane)
                .addComponent(outputScrollPane)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(loadFromFileButton)
                        .addComponent(encryptButton)
                        .addComponent(decryptButton)
                        .addComponent(saveButton)
                )
        );

        // Vertical group
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(algorithmLabel)
                        .addComponent(algorithmComboBox)
                )
                .addComponent(inputScrollPane)
                .addComponent(outputScrollPane)
                .addGroup(layout.createParallelGroup()
                        .addComponent(loadFromFileButton)
                        .addComponent(encryptButton)
                        .addComponent(decryptButton)
                        .addComponent(saveButton)
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

        loadFromFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFromFile();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
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
            String encryptedText = algorithm.encrypt(inputText, "key"); // Change "key" to the actual key
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
            String decryptedText = algorithm.decrypt(inputText, "key"); // Change "key" to the actual key
            outputTextArea.setText(decryptedText);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error decrypting text: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Method to load text from file
    private void loadFromFile() {
        int returnVal = fileChooser.showOpenDialog(CryptoToolGUI.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                StringBuilder content = new StringBuilder();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();
                inputTextArea.setText(content.toString());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    // Method to save text to file
    private void saveToFile() {
        int returnVal = fileChooser.showSaveDialog(CryptoToolGUI.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(outputTextArea.getText());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CryptoToolGUI();
            }
        });
    }
}
