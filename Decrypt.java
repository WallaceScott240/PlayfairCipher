public class Decrypt {
    private char[][] keySquare;

    public Decrypt(String key) {
        Encrypt encrypt = new Encrypt(key);
        keySquare = encrypt.getKeySquare();  // Get keySquare using the getter
    }

    public String decrypt(String text) {
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);
            int[] posA = findPosition(a);
            int[] posB = findPosition(b);

            if (posA[0] == posB[0]) { 
                decryptedText.append(keySquare[posA[0]][(posA[1] + 4) % 5]);
                decryptedText.append(keySquare[posB[0]][(posB[1] + 4) % 5]);
            } else if (posA[1] == posB[1]) {
                decryptedText.append(keySquare[(posA[0] + 4) % 5][posA[1]]);
                decryptedText.append(keySquare[(posB[0] + 4) % 5][posB[1]]);
            } else { 
                decryptedText.append(keySquare[posA[0]][posB[1]]);
                decryptedText.append(keySquare[posB[0]][posA[1]]);
            }
        }

        return decryptedText.toString();
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
}
