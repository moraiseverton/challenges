package ca.morais.everton.algorithms;

/**
 * Function in Java to find the longest substring without any repeating characters.
 * Example:
 *
 * Input: "abcabcbb"
 * Expected Output: "abc"
 */
public class LongestSubstring {

    static String longestSubstring(String str) {
        int size = str.length();
        if (size <= 1) {
            return str;
        }

        int index = 0;
        int start = 0;
        int end = 0;
        int longest = 0;

        String pos;
        String word = "";

        while (index < size) {
            pos = String.valueOf(str.charAt(index));

            if (word.indexOf(pos) == -1) {
                word += pos;
                index++;
            } else {
                if (word.length() > longest) {
                    longest = word.length();
                    start = str.indexOf(word);
                    end = index;
                }
                word = word.substring(word.indexOf(pos) + 1);
            }
        }

        if (word.length() > longest) {
            start = str.indexOf(word);
            end = index;
        }

        return str.substring(start, end);
    }
}
