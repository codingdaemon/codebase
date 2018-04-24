package exam;

public class Regex {
//  support * . ^ $

  /**
   * checks if the given string matches completely the regex
   * @param string
   * @param regex
   */
  public static boolean match(String string, String regex){
    return match(string, 0, regex, 0);
  }

  /**
   * the recursive function which check for validity
   * @param string
   * @param stringIndex
   * @param regex
   * @param regexIndex
   * @return
   */
  private static boolean match(String string, int stringIndex, String regex, int regexIndex) {
    // success case
    if(stringIndex == string.length() && regexIndex == regex.length()){
      return true;
    }else if(regexIndex == regex.length() - 1 && regex.charAt(regexIndex) == '$' && stringIndex == string.length()){
      return true;
    } else if (!(stringIndex < string.length() && regexIndex < regex.length())){
      return false;
    }

    char currRegChar = regex.charAt(regexIndex);
    char currStrChar = string.charAt(stringIndex);
    if(isStart(currRegChar) && regexIndex == 0 && stringIndex == 0){
//      case for start
      return match(string, stringIndex, regex, regexIndex + 1);
    }

    if(isStar(currRegChar)){
      throw new RuntimeException("IllegalRegex unplaced star");
    }else{
      if(regexIndex < regex.length()-1 && regex.charAt(regexIndex + 1) == '*'){
        if(isDot(currRegChar) || currStrChar == currRegChar){
          return match(string, stringIndex + 1, regex, regexIndex) || match(string, stringIndex + 1, regex, regexIndex + 2);
        }else{
          return match(string, stringIndex, regex, regexIndex + 2 );
        }
      }else if(isDot(currRegChar) || currRegChar == currStrChar){
        return match(string, stringIndex + 1, regex, regexIndex + 1);
      }else{
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
    String regex = "ab*c";
    String string = "ac";
    System.out.println(match(string, regex));
  }
}
