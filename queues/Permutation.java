import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation 
{
    public static void main(String[] args)
    {
        RandomizedQueue<String> strs = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) 
        {strs.enqueue(StdIn.readString());}
        
        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) 
        {StdOut.println(strs.dequeue());}
    }
}
