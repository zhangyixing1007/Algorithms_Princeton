import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
import java.util.LinkedList;

public class MoveToFront 
{
    // apply move-to-front encoding, reading from 
    // standard input and writing to standard output
    public static void encode() 
    {
        LinkedList<Character> list = new LinkedList<Character>();
        for (int i = 0; i < 256; i++) 
        {list.add((char) i);}
        while (!BinaryStdIn.isEmpty()) 
        {
            char c = BinaryStdIn.readChar();
            int idx = list.indexOf(c);
            BinaryStdOut.write(idx, 8);
            list.remove(idx);
            list.addFirst(c);
        }
        BinaryStdOut.flush();
    }

    // apply move-to-front decoding, reading 
    // from standard input and writing to standard output
    public static void decode() 
    {
        LinkedList<Character> list = new LinkedList<Character>();
        for (int i = 0; i < 256; i++) 
        {list.add((char) i);}
        while (!BinaryStdIn.isEmpty()) 
        {
            char idx = BinaryStdIn.readChar();
            char c = list.get(idx);
            BinaryStdOut.write(c);
            list.remove(idx);
            list.addFirst(c);
        }
        
        BinaryStdOut.flush();

    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) 
    {
        if (("-").equals(args[0])) 
        {encode();} 
        else if (("+").equals(args[0])) 
        {decode();}
    }
}
