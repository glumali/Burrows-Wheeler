/******************************************************************************
  *  Name:    Greg Umali
  * 
  *  Description:  Implements MoveToFront encoding, in which a sequence of
  *  characters is read in, and its position in the char array is printed.
  *  Then, the character is moved to the front of the array. This lets a
  *  string be reversibly encoded in a way where there is a greater 
  *  frequency of certain characters (great for Huffman Compression).
  * 
  *****************************************************************************/
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    // apply move-to-front encoding, reading from 
    // standard input and writing to standard output
    public static void encode() {
        // initialize the alphabet, setting each char to its index in ASCII
        int alphaSize = 256;
        char[] alphabet = new char[alphaSize];
        for (int i = 0; i < alphaSize; i++) {
            alphabet[i] = (char) i;
        }

        char in;
        while (!BinaryStdIn.isEmpty()) {
            // reads character by character
            in = BinaryStdIn.readChar();
            
            for (int i = 0; i < alphaSize; i++) {
                if (in == alphabet[i]) {
                    // writes as an 8-bit integer
                    BinaryStdOut.write(i, 8);
                    
                    char temp = alphabet[i];
                    // shift all other values down 1
                    for (int j = i; j > 0; j--) {
                        alphabet[j] = alphabet[j-1];
                    }
                    // move the character to the front
                    alphabet[0] = temp;
                    
                    break;
                }
            }
        }
        BinaryStdOut.flush();
    }
    
    // apply move-to-front decoding, reading from 
    // standard input and writing to standard output
    public static void decode() {
        // initialize the alphabet, setting each char to its index in ASCII
        int alphaSize = 256;
        char[] alphabet = new char[alphaSize];
        for (int i = 0; i < alphaSize; i++) {
            alphabet[i] = (char) i;
        }
        
        int idx;
        while (!BinaryStdIn.isEmpty()) {
            // reads character by character
            idx = BinaryStdIn.readInt(8);
            
            BinaryStdOut.write(alphabet[idx]);
            
            char temp = alphabet[idx];
            // shift all other values down 1
            for (int j = idx; j > 0; j--) {
                alphabet[j] = alphabet[j-1];
            }
            // move the character to the front
            alphabet[0] = temp;
        }
        BinaryStdOut.flush();
    }
    
    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) MoveToFront.encode();
        else if (args[0].equals("+")) MoveToFront.decode();
        else throw new IllegalArgumentException();
    }
}