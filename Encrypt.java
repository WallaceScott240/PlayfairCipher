public class Encrypt {
    private char[][] keySquare;
    
    public Encrypt(String key) {
        keySquare = generateKeySquare(key);
    }

    private char[][] generateKeySquare(String key) {
        boolean[] used = new boolean[26];
        key = key.toUpperCase().replace("J", "I");
        char[][] matrix = new char[5][5];
        int index = 0;

        for (char c : key.toCharArray()) {
            if (c >= 'A' && c <= 'Z' && !used[c - 'A']) {
                matrix[index / 5][index % 5] = c;
                used[c - 'A'] = true;
                index++;
            }
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J' && !used[c - 'A']) {
                matrix[index / 5][index % 5] = c;
                used[c - 'A'] = true;
                index++;
            }
        }

        return matrix;
    }

    public char[][] getKeySquare() {  // <-- Add this method
        return keySquare;
    }

    public String encrypt(String text) {
        text = prepareText(text);
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);
            int[] posA = findPosition(a);
            int[] posB = findPosition(b);

            if (posA[0] == posB[0]) {
                encryptedText.append(keySquare[posA[0]][(posA[1] + 1) % 5]);
                encryptedText.append(keySquare[posB[0]][(posB[1] + 1) % 5]);
            } else if (posA[1] == posB[1]) {
                encryptedText.append(keySquare[(posA[0] + 1) % 5][posA[1]]);
                encryptedText.append(keySquare[(posB[0] + 1) % 5][posB[1]]);
            } else {
                encryptedText.append(keySquare[posA[0]][posB[1]]);
                encryptedText.append(keySquare[posB[0]][posA[1]]);
            }
        }

        return encryptedText.toString();
    }

    private int[] findPosition(char c) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (keySquare[row][col] == c) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    private String prepareText(String text) {
        text = text.toUpperCase().replace("J", "I").replaceAll("[^A-Z]", "");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            sb.append(text.charAt(i));
            if (i < text.length() - 1 && text.charAt(i) == text.charAt(i + 1)) {
                sb.append('X');
            }
        }

        if (sb.length() % 2 != 0) {
            sb.append('X');
        }

        return sb.toString();
    }
}
