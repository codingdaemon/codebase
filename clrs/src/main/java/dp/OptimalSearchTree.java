package dp;

public class OptimalSearchTree {

  public static void main(String[] args) {
    double[] kprobs = new double[]{}; // k[0] not considered
    double[] dprobs = new double[]{};
    double cost = new OptimalSearchTree().costOfOptimalSearchTree(kprobs, dprobs);
  }

  private double costOfOptimalSearchTree(double[] kprobs, double[] dprobs) {
    int [] kdepths = new int[kprobs.length];
    int [] ddepths = new int[dprobs.length];

    costOfOptimalSearchTree(kprobs, dprobs, 1, kprobs.length, kdepths, ddepths);
     return 0.0;
  }

  private void costOfOptimalSearchTree(double[] kprobs, double[] dprobs, int startIndex, int endIndex, int [] kdepths, int [] ddepths) {
    if( startIndex == endIndex){
      kdepths[startIndex] = 0;
      ddepths[startIndex-1] = 1;
      ddepths[startIndex] = 1;
      return;
    }

    if(startIndex == endIndex + 1){
      ddepths[endIndex] = 0;
      return;
    }

    if( )
    for(int i = startIndex; i <= endIndex )
  }
}
