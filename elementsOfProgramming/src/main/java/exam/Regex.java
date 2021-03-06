package exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;

import java.util.Optional;

public class Regex {
//  support * . ^ $

  /**
   * checks if the given string matches completely the regex
   *
   * @param string
   * @param regex
   */
  public static boolean match(String string, String regex) {
    return match(string, 0, regex, 0);
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  static class Match {
    private int start;
    private int end;
  }

  /**
   * the recursive function which check for validity
   *
   * @param string
   * @param stringIndex
   * @param regex
   * @param regexIndex
   * @return
   */
  private static Optional<Match> search(String string, int stringIndex, String regex, int regexIndex) {
    // success case
    if (regexIndex == regex.length()) {
      return Optional.of(new Match(0, 0));
    } else if (regexIndex == regex.length() - 1 && regex.charAt(regexIndex) == '$' && stringIndex == string.length()) {
      return Optional.of(new Match(0, 0));
    }

    char currRegChar = regex.charAt(regexIndex);
    if (isStart(currRegChar) && regexIndex == 0 && stringIndex == 0) {
//      case for start
      return search(string, stringIndex, regex, regexIndex + 1);
    }

//    TODO : if there is an endswith $ then should we do the reverse search for better performance?

    boolean endsWith = false;
    if(regex.endsWith("$")){
      endsWith = true;
      regex = regex.substring(0, regex.length()-1); // removed end $
    }
//    if there is no endswith then do the normal search so that first results are shown first.
    for (int i = stringIndex; i < string.length(); i++) {
      for (int j = i; j < string.length(); j++) {
        if (match(string.substring(i, j + 1), 0, regex, regexIndex)){
          if(!endsWith || j == string.length() - 1)
            return Optional.of(new Match(i, j));
        }

      }
    }

    return Optional.empty();
  }

  private static Optional<Match> search(String string, String regex) {
    return search(string, 0, regex, 0);
  }

  /**
   * ^ and $ are not treated as special
   * @param string
   * @param stringIndex
   * @param regex
   * @param regexIndex
   * @return
   */
  private static boolean match(String string, int stringIndex, String regex, int regexIndex) {
    // success case
    if (stringIndex == string.length() && regexIndex == regex.length()) {
      return true;
    } else if (stringIndex < string.length() && regexIndex >= regex.length()) {
      return false;
    }

    char currRegChar = regex.charAt(regexIndex);
    if (isStar(currRegChar)) {
      throw new RuntimeException("IllegalRegex unplaced star");
    } else {
      if (regexIndex < regex.length() - 1 && regex.charAt(regexIndex + 1) == '*') {
        if (stringIndex == string.length()) {
          return match(string, stringIndex, regex, regexIndex + 2);
        }

        char currStrChar = string.charAt(stringIndex);
        if (isDot(currRegChar) || currStrChar == currRegChar) {
          return match(string, stringIndex + 1, regex, regexIndex) || match(string, stringIndex + 1, regex, regexIndex + 2) || match(string, stringIndex, regex, regexIndex + 2);
        } else {
          return match(string, stringIndex, regex, regexIndex + 2);
        }
      } else if (stringIndex != string.length() && (isDot(currRegChar) || currRegChar == string.charAt(stringIndex))) {
        return match(string, stringIndex + 1, regex, regexIndex + 1);
      } else {
        return false;
      }
    }
  }

  private static boolean isDot(char currRegChar) {
    return currRegChar == '.';
  }

  private static boolean isStar(char currRegChar) {
    return currRegChar == '*';
  }

  private static boolean isChar(char currChar) {
    return currChar != '.' && currChar != '*';
  }

  private static boolean isStart(char c) {
    return c == '^';
  }

  public static void main(String[] args) {
    Assert.assertTrue(match("abc", "abc"));
    Assert.assertTrue(match("abc", "ab*c"));
    Assert.assertTrue(match("abbc", "ab*c"));
    Assert.assertTrue(match("abbc", "ab*bc"));
    Assert.assertTrue(match("abbc", "ab*bbc"));
    Assert.assertTrue(match("abc", "a.*c"));
    Assert.assertTrue(match("aCc", "a.*c"));
    Assert.assertTrue(match("abbbc", "a.*c"));
    Assert.assertTrue(match("cbd", ".*d"));
    Assert.assertTrue(match("cbd", "c.*"));
    Assert.assertTrue(match("", ""));
    Assert.assertTrue(match("ac", "a.*c"));
    Assert.assertTrue(match("ac", ".*"));

    Assert.assertFalse(match("ac", "a*a"));

    Assert.assertFalse(match("c", "c*a"));
    Assert.assertFalse(match("cd", "c*e"));
    Assert.assertFalse(match("cde", "ced"));
    Assert.assertFalse(match("ced", "cede"));
    Assert.assertFalse(match("cvd", "cbd"));
    Assert.assertFalse(match(".*dd", "cbd"));
    Assert.assertFalse(match("c.*dd", "cbd"));

    Assert.assertTrue(match("^a^c", "^a^c"));
    Assert.assertTrue(match("^a$c$", "^a$c$"));

    Assert.assertFalse(match("a", "^a.*"));
    Assert.assertTrue(match("^a", "^a.*"));

    Assert.assertTrue(match("a", "."));


    Assert.assertTrue(match("a", "."));
    Assert.assertFalse(match("ab", "."));
    Assert.assertFalse(match("a.", "."));
    Assert.assertFalse(match("..", "."));
    Assert.assertTrue(match(".", "."));
    Assert.assertTrue(match(".", "."));
    Assert.assertFalse(match(".", ".."));
    Assert.assertFalse(match("a", ".."));
    Assert.assertTrue(match("ab", ".."));

    Optional<Match> match = search("abcd", "abc");

    Assert.assertTrue(match.isPresent());
    Assert.assertEquals(0, match.get().getStart());
    Assert.assertEquals(2, match.get().getEnd());

    match = search("abcd", "abcd");
    Assert.assertTrue(match.isPresent());
    Assert.assertEquals(0, match.get().getStart());
    Assert.assertEquals(3, match.get().getEnd());

    match = search("abcd", "abcd");
    Assert.assertTrue(match.isPresent());
    Assert.assertEquals(0, match.get().getStart());
    Assert.assertEquals(3, match.get().getEnd());

    match = search("abcd", "abc.");
    Assert.assertTrue(match.isPresent());
    Assert.assertEquals(0, match.get().getStart());
    Assert.assertEquals(3, match.get().getEnd());

    match = search("abcd", "ab..");
    Assert.assertTrue(match.isPresent());
    Assert.assertEquals(0, match.get().getStart());
    Assert.assertEquals(3, match.get().getEnd());

    match = search("abcd", "a.cd");
    Assert.assertTrue(match.isPresent());
    Assert.assertEquals(0, match.get().getStart());
    Assert.assertEquals(3, match.get().getEnd());

    match = search("abcd", ".bcd");
    Assert.assertTrue(match.isPresent());
    Assert.assertEquals(0, match.get().getStart());
    Assert.assertEquals(3, match.get().getEnd());

    match = search("abcd", "....");
    Assert.assertTrue(match.isPresent());
    Assert.assertEquals(0, match.get().getStart());
    Assert.assertEquals(3, match.get().getEnd());

    match = search("abcd", "a...");
    Assert.assertTrue(match.isPresent());
    Assert.assertEquals(0, match.get().getStart());
    Assert.assertEquals(3, match.get().getEnd());

    match = search("abcd", "ab.*");
    Assert.assertTrue(match.isPresent());
    Assert.assertEquals(0, match.get().getStart());
    Assert.assertEquals(1, match.get().getEnd());

    match = search("abcd", "a.*d");
    Assert.assertTrue(match.isPresent());
    Assert.assertEquals(0, match.get().getStart());
    Assert.assertEquals(3, match.get().getEnd());

    match = search("abcd", ".*d");
    Assert.assertTrue(match.isPresent());
    Assert.assertEquals(0, match.get().getStart());
    Assert.assertEquals(3, match.get().getEnd());

    match = search("aabcd", "abcd");
    Assert.assertTrue(match.isPresent());
    Assert.assertEquals(1, match.get().getStart());
    Assert.assertEquals(4, match.get().getEnd());

    match = search("aabcd", "acd");
    Assert.assertFalse(match.isPresent());

    match = search("aabcd", "acd");
    Assert.assertFalse(match.isPresent());

    match = search("aabcda", "a*cda");
    Assert.assertTrue(match.isPresent());
    Assert.assertEquals(3, match.get().getStart());
    Assert.assertEquals(5, match.get().getEnd());

    match = search("aabcda", "a*acda");
    Assert.assertFalse(match.isPresent());

    match = search("aabcdaa", "a*bcd");
    Assert.assertTrue(match.isPresent());
    Assert.assertEquals(0, match.get().getStart());
    Assert.assertEquals(4, match.get().getEnd());

    match = search("aabcd", "^a.*d$");
    Assert.assertTrue(match.isPresent());
    Assert.assertEquals(0, match.get().getStart());
    Assert.assertEquals(4, match.get().getEnd());


    match = search("aabcd", "^ab*c*d*");
    Assert.assertTrue(match.isPresent());

    match = search("aabcd", "^a.*c$");
    Assert.assertFalse(match.isPresent());

    match = search("aabcd", "acd");
    Assert.assertFalse(match.isPresent());

  }
}
