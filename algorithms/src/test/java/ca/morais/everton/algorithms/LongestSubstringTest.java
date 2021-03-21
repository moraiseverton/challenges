package ca.morais.everton.algorithms;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class LongestSubstringTest {

    @Test
    public void longestSubstring_whenSubstringIsInTheBeginning_returnsCorrectly() {
        String str = "abcabcbb";
        String resulted = LongestSubstring.longestSubstring(str);
        String expected = "abc";

        assertThat(resulted, is(expected));
    }

    @Test
    public void longestSubstring_whenLongestSubstringIsInTheMiddle_returnsCorrectly() {
        String str = "pwwkew";
        String resulted = LongestSubstring.longestSubstring(str);
        String expected = "wke";

        assertThat(resulted, is(expected));
    }

    @Test
    public void longestSubstring_whenLongestSubstringIsAlmostInTheEnd_returnsCorrectly() {
        String str = "abcabcbbprogamb";
        String resulted = LongestSubstring.longestSubstring(str);
        String expected = "bprogam";

        assertThat(resulted, is(expected));
    }

    @Test
    public void longestSubstring_whenLongestSubstringIsInTheEnd_returnsCorrectly() {
        String str = "abcabcbrprogam";
        String resulted = LongestSubstring.longestSubstring(str);
        String expected = "progam";

        assertThat(resulted, is(expected));
    }

    @Test
    public void longestSubstring_whenStringIsEmpty_returnsEmptyString() {
        String str = "";
        String resulted = LongestSubstring.longestSubstring(str);
        String expected = "";

        assertThat(resulted, is(expected));
    }

    @Test
    public void longestSubstring_whenStringHasOnlyOneChar_returnsSameString() {
        String str = "g";
        String resulted = LongestSubstring.longestSubstring(str);
        String expected = "g";

        assertThat(resulted, is(expected));
    }

    @Test
    public void longestSubstring_whenStringHasOnlyRepeatedChars_returnsSameString() {
        String str = "ggggggggggggggggggggggg";
        String resulted = LongestSubstring.longestSubstring(str);
        String expected = "g";

        assertThat(resulted, is(expected));
    }
}