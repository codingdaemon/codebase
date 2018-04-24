package exam;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class FindStringOccurances {

  public static void main(String[] args) {
    String[] sentences = new String[]{"bob and alice like to text each other", "bob does not like to ski", "alice likes to ski"};
    String[] queries = new String[]{"bob alice", "alice", "like", "nitiraj", "ski nattu"};
    findStringOccurances(sentences, queries);
  }

  private static void findStringOccurances(String[] sentences, String[] queries) {
    Map<String, Set<Integer>> wordSentenceMap = new HashMap<>();

    for (int i = 0; i < sentences.length; i++) {
      String sent = sentences[i];
      String[] words = sent.split("\\s");
      for (String word : words) {
        Set<Integer> integers = wordSentenceMap.computeIfAbsent(word, k -> new HashSet<>());
        integers.add(i);
      }
    }

    for (String query : queries) {
      String[] queryWords = query.split("\\s");
      Set<Integer> ansSet = null;
      boolean foundAns = true;
      for (String qword : queryWords) {
        if (null != wordSentenceMap.get(qword)) {
          if (null == ansSet) {
            ansSet = new HashSet<>(wordSentenceMap.get(qword));
          } else {
            ansSet.retainAll(wordSentenceMap.get(qword));
          }
        } else {
          foundAns = false;
          break;
        }
      }

      if (!foundAns || ansSet == null || ansSet.isEmpty()) {
        System.out.println(-1);
      } else {
        StringJoiner stringJoiner = new StringJoiner(" ");

        for (int i : ansSet) {
          String sent = sentences[i];
          String[] words = sent.split("\\s");
          Map<String, Integer> wordCount = new HashMap<>();
          for (String word : words) {
            wordCount.merge(word, 1, (a, b) -> a + b);
          }

          int count = Integer.MAX_VALUE;
          for (String qwords : queryWords) {
            int currCount = wordCount.get(qwords);
            if (currCount < count) {
              count = currCount;
            }
          }
          for (int j = 0; j < count; j++) {
            stringJoiner.add(String.valueOf(i));
          }
        }
        System.out.println(stringJoiner.toString());
      }
    }
  }
}
