public class EOP_25_36 {
  /**
   * 25.36 SEARCH FOR A PAIR-SUM IN AN ABS-SORTED ARRAY Q*
   * An abs-sorted array is an array of numbers in which \A[i]\ < \A[j]\ whenever i < j.
   * For example, the array in Figure 25.10, though not sorted in the standard sense, is
   * abs-sorted.
   * -49 75 103 -147 164 -197 -238 314 348 -422
   * A[0] Al1] A[2] Al3] Al4] Al5] A16] Am A[8] Al9]
   * Figure 25.10: An abs-sorted array.
   * Design an algorithm that takes an abs-sorted array A and a number K, and returns a
   * pair of indices of elements in A that sum up to K. For example, if the input to your
   * 502
   * algorithm is the array in Figure 25.10 on the facing page and K = 167, your algorithm
   * should output (3, 7). Output (-1,-1) if there is no such pair.
   */


  public static void main(String[] args) {
//                 0   1    2     3    4     5     6    7    8     9
    int[] arr = {-49, 75, 103, -147, 164, -197, -238, 314, 348, -422};
    int k = 167;
    findSum(arr, arr[7] + arr[6]);
  }

  private static void findSum(int[] arr, int k) {
    int back = arr.length - 1;
    int front = 0;
    front = initFront(front, arr);
    back = initBack(back, arr);
    int count = arr.length; // do iteration maximum arr.length times
    boolean foundAns = false;
    while (true) {
      if (count <= 0) {
        break;
      }
      int sum = arr[front] + arr[back];
      if (sum == k) {
        foundAns = true;
        break;
      } else if (sum < k) {
        front = incFront(front, arr);
      } else {
        back = decBack(back, arr);
      }

      count--;
    }

    if (foundAns) {
      System.out.println("(" + ((front > back) ? back : front) + "," + ((front > back) ? front : back) + ")");
    }else{
      System.out.println("(-1,-1)");
    }
  }

  private static int incFront(int front, int[] arr) {
    boolean isFrontNeg = arr[front] < 0;
    if(!isFrontNeg){
      front++;
      while(front < arr.length && arr[front] < 0) front++;
    }else{
      front--;
      while(front >= 0 && arr[front] > 0 )front--;

      if(front < 0){
        front = 0 ;
        while(front < arr.length && arr[front] < 0) front++;
      }
    }

    return front;
  }

  private static int decBack(int back, int[] arr) {
    boolean isBackNeg = arr[back] < 0;
    if(!isBackNeg){
      back--;
      while(back >=0 && arr[back] < 0) back--;
      if( back < 0 ){
//        we have to go reverse
        back = 0;
        while(back < arr.length && arr[back] > 0 ) back++;
      }
    }else{
      back++;
      while(back < arr.length && arr[back] > 0 ) back++;
    }
    return back;
  }

  private static int initBack(int back, int[] arr) {
    back = arr.length - 1;
    while (back >= 0 && arr[back] < 0) {
      back--;
    }

    if (back < 0) return 0;
    else return back;
  }

  private static int initFront(int front, int[] arr) {
    front = arr.length - 1;
    while (front >= 0 && arr[front] > 0) {
      front--;
    }

    if (front < 0) return 0;
    else return front;
  }

}
