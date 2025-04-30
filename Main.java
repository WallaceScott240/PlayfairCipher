import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {

    private static final String FILE_NAME = "encrypted.txt";
    private static JTable matrixTable;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Playfair Cipher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(Color.WHITE);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input"));
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField keyField = new JTextField();
        JTextArea messageArea = new JTextArea(4, 20);
        JScrollPane messageScroll = new JScrollPane(messageArea);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Key:"), gbc);
        gbc.gridy++;
        inputPanel.add(keyField, gbc);
        gbc.gridy++;
        inputPanel.add(new JLabel("Message:"), gbc);
        gbc.gridy++;
        inputPanel.add(messageScroll, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton encryptButton = new JButton("Encrypt & Save");
        JButton decryptButton = new JButton("Decrypt File");
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);

        // Result Panel
        JPanel resultPanel = new JPanel(new BorderLayout(10, 10));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Result"));
        JTextArea resultArea = new JTextArea(6, 40);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultPanel.add(resultScroll, BorderLayout.CENTER);

        // Cipher Matrix Table
        String[] columns = {"", "", "", "", ""};
        String[][] data = new String[5][5];
        matrixTable = new JTable(data, columns);
        matrixTable.setEnabled(false);
        matrixTable.setRowHeight(30);
        matrixTable.setFont(new Font("Monospaced", Font.PLAIN, 16));
        matrixTable.setGridColor(Color.GRAY);
        matrixTable.setShowGrid(true);

        JPanel matrixPanel = new JPanel(new BorderLayout());
        matrixPanel.setBorder(BorderFactory.createTitledBorder("Playfair 5x5 Grid"));
        matrixPanel.add(matrixTable.getTableHeader(), BorderLayout.NORTH);
        matrixPanel.add(matrixTable, BorderLayout.CENTER);

        // Add to main frame
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        centerPanel.add(resultPanel, BorderLayout.SOUTH);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(matrixPanel, BorderLayout.EAST);

        // Event Listeners
        encryptButton.addActionListener((@SuppressWarnings("unused") ActionEvent e) -> {
            String key = keyField.getText().trim();
            String message = messageArea.getText().trim();
            if (!key.isEmpty() && !message.isEmpty()) {
                Encrypt encryptor = new Encrypt(key);
                String encryptedText = encryptor.encrypt(message);
                try {
                    Files.write(new File(FILE_NAME).toPath(), encryptedText.getBytes());
                    resultArea.setText("Encrypted text saved to " + FILE_NAME + ":\n" + encryptedText);
                    updateMatrixTable(encryptor.getMatrix());
                } catch (IOException ex) {
                    resultArea.setText("Error saving file: " + ex.getMessage());
                }
            } else {
                resultArea.setText("Please provide both key and message.");
            }
        });

        decryptButton.addActionListener((@SuppressWarnings("unused") ActionEvent e) -> {
            String key = keyField.getText().trim();
            if (!key.isEmpty()) {
                try {
                    String encryptedText = Files.readString(new File(FILE_NAME).toPath());
                    Decrypt decryptor = new Decrypt(key);
                    String decryptedText = decryptor.decrypt(encryptedText);
                    resultArea.setText("Decrypted message:\n" + decryptedText);
                    updateMatrixTable(decryptor.getMatrix());
                } catch (IOException ex) {
                    resultArea.setText("Error reading file: " + ex.getMessage());
                }
            } else {
                resultArea.setText("Please provide a key to decrypt.");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void updateMatrixTable(char[][] matrix) {
        if (matrix == null) return;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                matrixTable.setValueAt(String.valueOf(matrix[i][j]), i, j);
    }
}
