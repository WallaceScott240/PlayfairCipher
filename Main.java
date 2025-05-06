import javax.swing.*; // Imports all Swing classes for GUI components.
import java.awt.*; // Imports AWT classes like Layouts, Color, Insets, etc.
import java.awt.event.ActionEvent; // For handling action events like button clicks.
import java.io.File; // For file handling.
import java.io.IOException; // For handling IO exceptions.
import java.nio.file.Files; // To read and write files using NIO.

public class Main {

    // Constant for the file name where encrypted text will be saved.
    private static final String FILE_NAME = "encrypted.txt";

    // Table component to display the 5x5 Playfair matrix.
    private static JTable matrixTable;

    // Main method - entry point of the program.
    public static void main(String[] args) {
        // Ensures the GUI runs on the Swing event dispatch thread.
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    // Method to set up and display the GUI.
    private static void createAndShowGUI() {
        // Create the main application window.
        JFrame frame = new JFrame("Playfair Cipher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit app on close.
        frame.setSize(700, 600); // Set window size.
        frame.setLayout(new BorderLayout(10, 10)); // Set layout with spacing.
        frame.getContentPane().setBackground(Color.WHITE); // Set background color.

        // Input panel (for key and message input).
        JPanel inputPanel = new JPanel(new GridBagLayout()); // Flexible layout.
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input")); // Titled border.
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints(); // Constraint for GridBagLayout.
        gbc.insets = new Insets(10, 10, 5, 10); // Padding.
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField keyField = new JTextField(); // Input for encryption/decryption key.
        JTextArea messageArea = new JTextArea(4, 20); // Input area for the message.
        JScrollPane messageScroll = new JScrollPane(messageArea); // Scroll if message is long.
        messageArea.setLineWrap(true); // Wrap long lines.
        messageArea.setWrapStyleWord(true); // Wrap at word boundaries.

        // Add components to input panel using GridBag layout.
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Key:"), gbc); // Label for key.
        gbc.gridy++;
        inputPanel.add(keyField, gbc); // TextField for key.
        gbc.gridy++;
        inputPanel.add(new JLabel("Message:"), gbc); // Label for message.
        gbc.gridy++;
        inputPanel.add(messageScroll, gbc); // TextArea for message.

        // Buttons panel (Encrypt, Decrypt, Clear).
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton encryptButton = new JButton("Encrypt & Save");
        JButton decryptButton = new JButton("Decrypt File");
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        buttonPanel.add(clearButton);

        // Result display panel.
        JPanel resultPanel = new JPanel(new BorderLayout(10, 10));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Result")); // Titled border.
        JTextArea resultArea = new JTextArea(6, 40); // Area to display encrypted/decrypted text.
        resultArea.setEditable(false); // Make result read-only.
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultPanel.add(resultScroll, BorderLayout.CENTER);

        // Create the 5x5 cipher matrix table.
        String[] columns = { "", "", "", "", "" }; // 5 columns, unnamed.
        String[][] data = new String[5][5]; // 5x5 empty data array.
        matrixTable = new JTable(data, columns); // Table for displaying cipher matrix.
        matrixTable.setEnabled(false); // Read-only.
        matrixTable.setRowHeight(30); // Set height for each row.
        matrixTable.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Better alignment.
        matrixTable.setGridColor(Color.GRAY); // Color of grid lines.
        matrixTable.setShowGrid(true); // Enable grid.

        // Panel to hold the matrix table.
        JPanel matrixPanel = new JPanel(new BorderLayout());
        matrixPanel.setBorder(BorderFactory.createTitledBorder("Playfair 5x5 Grid"));
        matrixPanel.add(matrixTable.getTableHeader(), BorderLayout.NORTH);
        matrixPanel.add(matrixTable, BorderLayout.CENTER);

        // Central panel combining input, buttons, and result areas.
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        centerPanel.add(resultPanel, BorderLayout.SOUTH);

        // Add central panel and matrix panel to the main frame.
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(matrixPanel, BorderLayout.EAST);

        // ENCRYPT button logic.
        encryptButton.addActionListener((@SuppressWarnings("unused") ActionEvent e) -> {
            String key = keyField.getText().trim(); // Get key.
            String message = messageArea.getText().trim(); // Get message.
            if (!key.isEmpty() && !message.isEmpty()) {
                Encrypt encryptor = new Encrypt(key); // Create encryptor object.
                String encryptedText = encryptor.encrypt(message); // Encrypt message.
                try {
                    // Save to file.
                    Files.write(new File(FILE_NAME).toPath(), encryptedText.getBytes());
                    resultArea.setText("Encrypted text saved to " + FILE_NAME + ":\n" + encryptedText);
                    updateMatrixTable(encryptor.getMatrix()); // Show matrix.
                } catch (IOException ex) {
                    resultArea.setText("Error saving file: " + ex.getMessage());
                }
            } else {
                resultArea.setText("Please provide both key and message.");
            }
        });

        // DECRYPT button logic.
        decryptButton.addActionListener((@SuppressWarnings("unused") ActionEvent e) -> {
            String key = keyField.getText().trim(); // Get key.
            if (!key.isEmpty()) {
                try {
                    String encryptedText = Files.readString(new File(FILE_NAME).toPath()); // Read file.
                    Decrypt decryptor = new Decrypt(key); // Create decryptor.
                    String decryptedText = decryptor.decrypt(encryptedText); // Decrypt message.
                    resultArea.setText("Decrypted message:\n" + decryptedText);
                    updateMatrixTable(decryptor.getMatrix()); // Show matrix.
                } catch (IOException ex) {
                    resultArea.setText("Error reading file: " + ex.getMessage());
                }
            } else {
                resultArea.setText("Please provide a key to decrypt.");
            }
        });

        // CLEAR button logic.
        clearButton.addActionListener((@SuppressWarnings("unused") ActionEvent e) -> {
            keyField.setText(""); // Clear key field.
            messageArea.setText(""); // Clear message area.
            resultArea.setText(""); // Clear result area.
            // Clear matrix table.
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 5; j++)
                    matrixTable.setValueAt("", i, j);
        });

        // Final setup
        frame.setLocationRelativeTo(null); // Center the window.
        frame.setVisible(true); // Show the GUI.
    }

    // Helper method to update the matrix table with the current 5x5 Playfair matrix.
    private static void updateMatrixTable(char[][] matrix) {
        if (matrix == null)
            return;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                matrixTable.setValueAt(String.valueOf(matrix[i][j]), i, j); // Set cell value.
    }
}
