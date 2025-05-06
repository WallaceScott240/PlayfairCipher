// Class to perform Playfair cipher decryption
public class Decrypt {

    // 5x5 matrix used for Playfair cipher key square
    private char[][] keySquare;

    // Constructor that initializes the key square using the Encrypt class's key square
    public Decrypt(String key) {
        // Initialize the Encrypt object to reuse the key square generated for encryption
        Encrypt encrypt = new Encrypt(key);
        keySquare = encrypt.getKeySquare();  // Get the key square from the Encrypt object
    }

    // Decrypts the given text using the Playfair cipher
    public String decrypt(String text) {
        StringBuilder decryptedText = new StringBuilder();  // StringBuilder to store the decrypted text

        // Loop through the text two characters at a time
        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);     // First letter of the pair
            char b = text.charAt(i + 1); // Second letter of the pair

            int[] posA = findPosition(a); // Find position of letter A in the matrix
            int[] posB = findPosition(b); // Find position of letter B in the matrix

            // If both letters are in the same row
            if (posA[0] == posB[0]) {
                // Shift left (i.e., subtract 1 from the column index and handle wrapping around using % 5)
                decryptedText.append(keySquare[posA[0]][(posA[1] + 4) % 5]);
                decryptedText.append(keySquare[posB[0]][(posB[1] + 4) % 5]);
            }
            // If both letters are in the same column
            else if (posA[1] == posB[1]) {
                // Shift up (i.e., subtract 1 from the row index and handle wrapping around using % 5)
                decryptedText.append(keySquare[(posA[0] + 4) % 5][posA[1]]);
                decryptedText.append(keySquare[(posB[0] + 4) % 5][posB[1]]);
            }
            // Letters form a rectangle: swap columns
            else {
                decryptedText.append(keySquare[posA[0]][posB[1]]);
                decryptedText.append(keySquare[posB[0]][posA[1]]);
            }
        }

        return decryptedText.toString();  // Return the final decrypted text
    }

    // Finds the row and column of a given character in the matrix
    private int[] findPosition(char c) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (keySquare[row][col] == c) {
                    return new int[]{row, col}; // Return the position as [row, col]
                }
            }
        }
        return null; // Should not happen if input is clean (character should be in key square)
    }

    // Getter for the key square matrix
    public char[][] getMatrix() {
        return keySquare; // Return the key square used for decryption
    }
}
