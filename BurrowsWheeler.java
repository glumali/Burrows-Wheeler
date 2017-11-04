/******************************************************************************
  *  Name:    Greg Umali
  * 
  *  Description:  Allows the client to perform a Burrows-Wheeler transform
  *  or inverse transform on an input string, from standard input. A Burrows-
  *  Wheeler transform reversibly changes an input so common letters cluster
  *  together more.
  * 
  *****************************************************************************/
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input 
    // and writing to standard output
    public static void transform() {
        String input = BinaryStdIn.readString();
        int len = input.length();
        
        CircularSuffixArray suffArray = new CircularSuffixArray(input);
        
        // find and print location of original string on its own line
        for (int j = 0; j < len; j++) {
            if (suffArray.index(j) == 0) {
                // found the line of the original string
                BinaryStdOut.write(j);
                BinaryStdOut.flush();
                // stop looking
                break;
            }
        }
        
        // print each last character of the suffixes onto a new line
        for (int i = 0; i < len; i++) {
            int start = suffArray.index(i);
            // use modulus to get the last character of the circular suffix
            BinaryStdOut.write(input.charAt((start + len - 1) % len));
        }
        BinaryStdOut.flush();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input 
    // and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        
        // read in input
        String input = BinaryStdIn.readString();
        int len = input.length();
        int R = 256;   // extend ASCII alphabet size
        
        // will be used to reconstruct the original string
        // constructed through key-indexed counting
        int[] next = new int[len];

        // compute frequency counts
        int[] count = new int[R+1];
        for (int i = 0; i < len; i++)
            count[input.charAt(i) + 1]++;
        
        // compute cumulates
        for (int r = 0; r < R; r++)
            count[r+1] += count[r];
        
        // move data
        for (int i = 0; i < len; i++)
            next[ count[ input.charAt(i) ]++ ] = i; 
        
        // reconstruct the original string
        // if first denotes the row of the original string
        // therefore, input.charAt(t) would give us the last character
        // however, we want the first, so we just take next[first], since
        // it wraps around
        int idx = next[first];
        for (int i = 0; i < len; i++) {
            BinaryStdOut.write(input.charAt(idx));
            idx = next[idx];
        }
        BinaryStdOut.flush();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) BurrowsWheeler.transform();
        else if (args[0].equals("+")) BurrowsWheeler.inverseTransform();
        else throw new IllegalArgumentException();
    }
}