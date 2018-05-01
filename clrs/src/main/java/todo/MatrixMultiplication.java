package todo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatrixMultiplication {

//  public int nonOptimalMult(int[] rows, int [] cols, int startIndex, int endIndex){
//    if( endIndex == startIndex + 1){
//      return rows[startIndex] * cols[startIndex] * cols[endIndex];
//    }
//
//    for(int i = startIndex + 1 ; i <= endIndex; i++){
//      int left = nonOptimalMult(rows, cols, startIndex, i);
//      int right = nonOptimalMult(rows, cols, startIndex, i);
//    }
//  }

  public int optimalDP(int [] rows, int [] cols){
//    if( rows.length < 2){
//      return -1;
//    }
//
//    if(rows.length == 2){
//      return rows[0] * cols[0] * cols[1];
//    }
//
//    int [][] multi = new int[rows.length][rows.length];
//
//    for( int i = 0; i < rows.length - 1; i++){
//      multi[i][i+1] = rows[i] * cols[i] * cols[i+1];
//    }
//
//    int i = 0;
//    int j = 2;
//    int nextStart = 4;
//    while(true){
//      Math.max( multi[i][j-1] + rows[i] * cols[j-1] * cols[j], multi[i+1][j] + rows[i+1]*rows[i+1]* )
//      if( i == 0 && j == rows.length-1){
//        break;
//      }else if(j == rows.length-1){
//        j = nextStart;
//        nextStart++;
//      }
//    }

    return 0;
  }
  public static void main(String[] args) {

  }
}
