# Playfair Cipher Encryption & Decryption in Java

## Introduction
This Java program implements the **Playfair Cipher**, a classical encryption technique, to **encrypt** and **decrypt** messages using a key. The encrypted text is saved in a file (`encrypted.txt`), and it can be decrypted back to its original form using the same key.

---

## How the Playfair Cipher Works
The **Playfair Cipher** encrypts text using a **5x5 matrix** of letters derived from a keyword. Here’s how it works:

1.
   **Creating the Key Square:**
   - The given keyword (e.g., `SECRET`) is placed into a **5x5 grid**.
   - The rest of the alphabet (excluding `J`, which is replaced with `I`) fills the remaining spaces.

   **Example key square for `SECRET`:**
S E C R T
A B D F G
H I K L M
N O P Q U
V W X Y Z

pgsql
Copy
Edit

2. 
**Preparing the Text:**
- Converts text to uppercase and removes spaces and special characters.
- If a letter pair repeats (e.g., `HELLO` → `HE LX LO`), an `X` is inserted.
- If the text length is odd, an `X` is appended.

3. 
**Encryption Rules:**
- **Same row:** Each letter moves **right** (wrap around if needed).
- **Same column:** Each letter moves **down** (wrap around if needed).
- **Rectangle rule:** Swap letters diagonally within the matrix.

4. 
**Decryption Rules:**
- Similar to encryption but move **left** (for rows) and **up** (for columns).

---

## Files & Code Structure
### 1️⃣ `Main.java`
Handles user input and controls encryption & decryption.

### 2️⃣ `Encrypt.java`
- Generates the **5x5 key square** from the given key.
- Encrypts the input text using Playfair rules.

### 3️⃣ `Decrypt.java`
- Uses the same key square.
- Decrypts the message from `encrypted.txt`.

---

## How to Run the Program
### 1️⃣ Set Up the Project
mkdir PlayfairCipher cd PlayfairCipher touch Main.java Encrypt.java Decrypt.java README.md

bash
Copy
Edit
Copy and paste the Java files into the directory.

### 2️⃣ Compile the Java Files
javac Main.java Encrypt.java Decrypt.java

shell
Copy
Edit

### 3️⃣ Run the Program
java Main

pgsql
Copy
Edit

---

## Usage Instructions
1. **Encryption:**
   - Choose **1** (Encrypt a message).
   - Enter a **key** (e.g., `SECRET`).
   - Enter the **message** (e.g., `HELLO WORLD`).
   - The encrypted text is saved to `encrypted.txt`.

2. **Decryption:**
   - Choose **2** (Decrypt a file).
   - Enter the **same key** used for encryption.
   - The program will output the **decrypted message**.

---

## Example Output
### Encryption Process
Playfair Cipher Encryption & Decryption

Encrypt a message

Decrypt a file Choose an option: 1 Enter the key: SECRET Enter the message to encrypt: HELLO WORLD Encrypted text saved to encrypted.txt

pgsql
Copy
Edit
*(The encrypted text is stored in `encrypted.txt`.)*

### Decryption Process
Playfair Cipher Encryption & Decryption

Encrypt a message

Decrypt a file Choose an option: 2 Enter the key: SECRET Decrypted message: HELXLOXWORLDX

yaml
Copy
Edit
*(Note: `X` is used for repeated letters and padding.)*

---

## Playfair Cipher Rules Recap
| Condition    | Encryption  | Decryption  |
|-------------|------------|-------------|
| Same Row    | Move **right** | Move **left** |
| Same Column | Move **down**  | Move **up**   |
| Rectangle   | Swap diagonally | Swap diagonally |

---

## Notes & Improvements
✅ Works with any keyword for encryption.  
✅ Automatically removes spaces & special characters.  
✅ Handles repeated letters by inserting `X`.  
✅ Supports file-based encryption & decryption.  

🔹 **Future Improvements:**  
- Allow users to choose a custom file name.  
- Improve handling of special characters.  

---

## Author
**Wallace Scott**  
🛠️ Ethical Hacker, Software Engineer, and Security Enthusiast.  

---

This README provides everything needed to understand, set up, and run the **Playfair Cipher encryption & decryption** in Java! 🚀 Let me know if you want any modifications.



██     ██ ███████ 
██     ██ ██      
██  █  ██ ███████ 
██ ███ ██      ██ 
 ███ ███  ███████ 
                   | wallacescott.netlify.app | https://www.linkedin.com/in/wallace-dsouza/ | https://github.com/WallaceScott240