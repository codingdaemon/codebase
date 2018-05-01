package todo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SubSequence {
  public int nonOptimalMaxSubSeq(char[] a, char[] b){
    return nonOptimalMaxSubSeq(a, 0, b, 0);
  }

  private int nonOptimalMaxSubSeq(char[] a, int aIndex, char[] b, int bIndex) {
    if( aIndex >= a.length || bIndex >= b.length)
      return 0;

    if(a[aIndex] == b[bIndex]){
      return Math.max( nonOptimalMaxSubSeq(a, aIndex + 1, b, bIndex + 1)  + 1,  Math.max( nonOptimalMaxSubSeq( a, aIndex + 1, b, bIndex), nonOptimalMaxSubSeq( a, aIndex, b, bIndex + 1) ) );
    }else{
      return Math.max( nonOptimalMaxSubSeq( a, aIndex + 1, b, bIndex), nonOptimalMaxSubSeq( a, aIndex, b, bIndex + 1) );
    }
  }

  @Data
  @AllArgsConstructor
  static class Pair<A,B>{
    A a;
    B b;
  }

  public int optimalTopDownMaxSubSeq(char[] a, char[] b){
    return optimalTopDownMaxSubSeq(a, 0, b, 0, new HashMap<Pair<Integer, Integer>,Integer>());
  }
//  TODO : write this solution while iterating from right to left. That will save one comparison
  private int optimalTopDownMaxSubSeq(char[] a, Integer aIndex, char[] b, Integer bIndex, Map<Pair<Integer,Integer>,Integer> solutionMap) {
    if( aIndex >= a.length || bIndex >= b.length)
      return 0;

    Pair<Integer, Integer> indexes = new Pair<>(aIndex, bIndex);
    if(solutionMap.containsKey(indexes)){
      log.info("solution for indexes : {}, already present : {}", indexes, solutionMap.get(indexes));
      return solutionMap.get(indexes);
    }

    Integer currSolution = null;
    if(a[aIndex] == b[bIndex]){
       currSolution = Math.max( optimalTopDownMaxSubSeq(a, aIndex + 1, b, bIndex + 1, solutionMap)  + 1,
           Math.max( optimalTopDownMaxSubSeq( a, aIndex + 1, b, bIndex, solutionMap), optimalTopDownMaxSubSeq( a, aIndex, b, bIndex + 1, solutionMap) ) );
    }else{
      currSolution =  Math.max( optimalTopDownMaxSubSeq( a, aIndex + 1, b, bIndex, solutionMap), optimalTopDownMaxSubSeq( a, aIndex, b, bIndex + 1, solutionMap) );
    }

    solutionMap.put(indexes, currSolution);

    return currSolution;
  }

  public int optimalBottomUpMaxSubSeq(char[] a, char[] b){
    int matrix[][] = new int[a.length+1][b.length+1];

    for(int i = 0; i < a.length; i++){
      matrix[i][0] = 0;
    }
    for( int i= 0; i < b.length; i++){
      matrix[0][i] = 0;
    }

    for( int i = 1 ; i <= a.length; i++){
      for(int j = 1; j <= b.length; j++){
        matrix[i][j] = Math.max( matrix[i-1][j-1] + (a[i-1] == b[j-1]? 1 : 0), Math.max(matrix[i-1][j], matrix[i][j-1]) );
      }
    }

    return matrix[a.length][b.length];
  }

  public static void main(String[] args) {
    String a = "abcxbdaba";
    String b = "bdcaxbaa";

    log.info("a = {}, b = {}, LCS (nonOptimalMaxSubSeq) length = {}", a, b, new SubSequence().nonOptimalMaxSubSeq(a.toCharArray(), b.toCharArray()));

    log.info("a = {}, b = {}, LCS (optimalTopDownMaxSubSeq) length = {}", a, b, new SubSequence().optimalTopDownMaxSubSeq(a.toCharArray(), b.toCharArray()));

    log.info("a = {}, b = {}, LCS (optimalBottomUpMaxSubSeq) length = {}", a, b, new SubSequence().optimalBottomUpMaxSubSeq(a.toCharArray(), b.toCharArray()));

  }
}
