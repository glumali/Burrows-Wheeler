/******************************************************************************
  *  Name:    Greg Umali
  * 
  *  Description:  Represents a CircularSuffixArray (in which the
  *  original String is iteratively shifted left to form all possible
  *  suffixes) that is sorted. Supports functions to find where the ith
  *  suffix is in the sorted array.
  * 
  *****************************************************************************/
import java.util.Arrays;

public class CircularSuffixArray {
    // length of the input
    private int N;

    // represents the circular suffix array
    private CircularSuffix[] suffixes;
    
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new NullPointerException();
        
        // store length of the input string
        N = s.length();
        
        // construct the sorted CircularSuffix array
        suffixes = new CircularSuffix[N];
        for (int i = 0; i < N; i++) {
            suffixes[i] = new CircularSuffix(i, s);
        }
        
        Arrays.sort(suffixes, 0, suffixes.length);
        
        // iterate through the sorted array, store the last character
    }
    
    private class CircularSuffix implements Comparable<CircularSuffix> {
        // where in the original string the suffix starts
        private int startIdx;
        private String input;
        
        // constructor
        public CircularSuffix(int i, String s) {
            startIdx = i;
            input = s;
        }
        
        // takes the two starting locations in the original string and
        // compares each suffix character by character
        public int compareTo(CircularSuffix that) {
            // initialize the first characters to compare
            int thisIdx = startIdx;
            int thatIdx = that.startIdx;
            
            char thisChar = input.charAt(thisIdx);
            char thatChar = input.charAt(thatIdx);
            
            // tracks the number of characters compared
            int len = 0;
            
            // breaks loop when it compares two different characters or
            // compares N characters (has gone completely through the suffixes)
            while (thisChar == thatChar && len < N) {
                // increments the char to be compared, modulus = wraparound
                thisChar = input.charAt(++thisIdx % N);
                thatChar = input.charAt(++thatIdx % N);
                len++;
            }
            // at this point, have two different chars or went all the way
            // through without finding two diff chars (returns 0)
            return thisChar - thatChar;
        }
    }
    
    // length of s
    public int length() {
        return N;
    }
    
    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= N) {
            throw new IndexOutOfBoundsException();
        }
        
        return suffixes[i].startIdx;
    }
    
    // unit testing (not graded)
    public static void main(String[] args) {
        String input = "ABRACADABRA!";
        CircularSuffixArray test = new CircularSuffixArray(input);
        
        System.out.println("Input: " + input);
        System.out.println("Length of input: " + test.length());
        // should print 2
        System.out.println("11th place in sorted array: " + test.index(11));
        // should print 7
        System.out.println("2nd place in sorted array: " + test.index(2));
    }
}