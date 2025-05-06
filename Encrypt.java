// Class to perform Playfair cipher encryption
public class Encrypt {

    // 5x5 matrix used for Playfair cipher key square
    private char[][] keySquare;

    // Constructor that initializes the key square using the given key
    public Encrypt(String key) {
        keySquare = generateKeySquare(key);
    }

    // Generates the 5x5 matrix (key square) based on the given key
    private char[][] generateKeySquare(String key) {
        boolean[] used = new boolean[26]; // To keep track of letters already used
        key = key.toUpperCase().replace("J", "I"); // Convert to uppercase and replace J with I

        char[][] matrix = new char[5][5]; // Declare the 5x5 matrix
        int index = 0; // Tracks current matrix index (row * 5 + col)

        // Insert characters from the key into the matrix
        for (char c : key.toCharArray()) {
            if (c >= 'A' && c <= 'Z' && !used[c - 'A']) { // Only use valid, unused letters
                matrix[index / 5][index % 5] = c; // Compute row and col from index
                used[c - 'A'] = true; // Mark character as used
                index++; // Move to next position in the matrix
            }
        }

        // Fill remaining matrix with unused letters A-Z (excluding J)
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J' && !used[c - 'A']) {
                matrix[index / 5][index % 5] = c;
                used[c - 'A'] = true;
                index++;
            }
        }

        return matrix; // Return the filled key square
    }

    // Getter method to access the key square
    public char[][] getKeySquare() {
        return keySquare;
    }

    // Alternate getter, same as getKeySquare()
    public char[][] getMatrix() {
        return keySquare;
    }

    // Encrypts the given text using the Playfair cipher
    public String encrypt(String text) {
        text = prepareText(text); // Preprocess the input text
        StringBuilder encryptedText = new StringBuilder();

        // Loop through the text two characters at a time
        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);     // First letter of the pair
            char b = text.charAt(i + 1); // Second letter of the pair

            int[] posA = findPosition(a); // Find position of letter A in the matrix
            int[] posB = findPosition(b); // Find position of letter B in the matrix

            // If both letters are in the same row
            if (posA[0] == posB[0]) {
                encryptedText.append(keySquare[posA[0]][(posA[1] + 1) % 5]); // Shift right
                encryptedText.append(keySquare[posB[0]][(posB[1] + 1) % 5]);
            }
            // If both letters are in the same column
            else if (posA[1] == posB[1]) {
                encryptedText.append(keySquare[(posA[0] + 1) % 5][posA[1]]); // Shift down
                encryptedText.append(keySquare[(posB[0] + 1) % 5][posB[1]]);
            }
            // Letters form a rectangle: swap columns
            else {
                encryptedText.append(keySquare[posA[0]][posB[1]]);
                encryptedText.append(keySquare[posB[0]][posA[1]]);
            }
        }

        return encryptedText.toString(); // Return the final encrypted text
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
        return null; // Should not happen if input is clean
    }

    // Prepares the plaintext for encryption:
    // - Converts to uppercase
    // - Replaces J with I
    // - Removes non-letter characters
    // - Adds X between duplicate letters in a pair
    // - Adds X at the end if the length is odd
    private String prepareText(String text) {
        text = text.toUpperCase().replace("J", "I").replaceAll("[^A-Z]", ""); // Sanitize text
        StringBuilder sb = new StringBuilder();

        // Insert X between repeated characters
        for (int i = 0; i < text.length(); i++) {
            sb.append(text.charAt(i));
            if (i < text.length() - 1 && text.charAt(i) == text.charAt(i + 1)) {
                sb.append('X'); // Insert 'X' between duplicate letters
            }
        }

        // If the result has odd length, pad with X
        if (sb.length() % 2 != 0) {
            sb.append('X');
        }

        return sb.toString(); // Return processed text
    }
}
