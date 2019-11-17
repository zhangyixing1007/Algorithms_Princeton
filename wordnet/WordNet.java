import  edu.princeton.cs.algs4.Bag;
import  edu.princeton.cs.algs4.Digraph;
import  edu.princeton.cs.algs4.DirectedCycle;
import  edu.princeton.cs.algs4.In;
import  edu.princeton.cs.algs4.StdIn;
import  edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.Map;

public class WordNet {
    
    
    // do unit testing of this class
    public static void main(String[] args)
    {   
    }
    // do unit testing of this class    
    
    private final Map<String, Bag<Integer>> words;
    private final Map<Integer, String> synsetsTable;
    private final SAP sap;
    private final Digraph G;
    
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        
        synsetsTable = new HashMap<Integer, String>();
        words = new HashMap<String, Bag<Integer>>();
        //word is a word list of all words
        //but one word might be in different synsets
        
        //read and analyze synsets.txt
        In syn = new In(synsets); 
        String line;
        
        
        int n = 0;
        while ((line = syn.readLine()) != null) 
        {
            String[] parts = line.split(",");
            n = Integer.parseInt(parts[0]);
            String[] syns = parts[1].split(" ");
            for (String s : syns) 
            {
                Bag<Integer> ids = words.get(s);
                if (ids == null) 
                {
                    ids = new Bag<Integer>();
                    words.put(s, ids);
                } 
                ids.add(n);
            }
            synsetsTable.put(n, parts[1]);
        }
        
        
        
        //read and analyze hypernyms.txt
        G = new Digraph(n+1);
        
       In hyp = new In(hypernyms);
       while ((line = hyp.readLine())!=null)
       {
           String[] parts = line.split(",");
           if (parts.length >= 2) 
           {
               int start = Integer.parseInt(parts[0]);
 
               for (int i=1; i<parts.length; i++)
               {
                   int end = Integer.parseInt(parts[i]);
                   G.addEdge(start,end);
               }
        
           }
       }
        
        //check if it is a DAG
        //if it does not contain circle
        DirectedCycle cycle = new DirectedCycle(G);
        if (cycle.hasCycle())
        {throw new IllegalArgumentException();}
        
        
        //check if it has only one root
        int root = -1;
        for (int i = 0; i<G.V(); i++)
        {
            if (!G.adj(i).iterator().hasNext())
            {
                if (root!=-1)
                {throw new IllegalArgumentException();}
                else
                {root = i;}
            }
        }              
        
        //no problem, we could create a SAP!    
        sap = new SAP(G);
    }
    // constructor takes the name of the two input files
    
    
    
    
    
    
    // returns all WordNet nouns
    //list of all nouns
    public Iterable<String> nouns()
    {
        return words.keySet();
    }
    // returns all WordNet nouns
    
    
    
    
    // is the word a WordNet noun?
    //contains?
    public boolean isNoun(String word)
    {
        if (word == null)
        {throw new IllegalArgumentException();}
        return words.containsKey(word);
    }
    // is the word a WordNet noun?
    
    
    
    
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB)
    {
        if (nounA == null||nounB == null)
        {throw new IllegalArgumentException();}
        
        Iterable<Integer> A = words.get(nounA);
        Iterable<Integer> B = words.get(nounB);
        if (A == null||B == null)
            //as to save times of visits
        {throw new IllegalArgumentException();}       
        return (sap.length(A,B));
    }
    // distance between nounA and nounB (defined below)
    
    
    
    
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB)
        //sap stands for shortest ancestral path
    {
        //corner case:
        //Any argument to the constructor or an instance method is null  
        //Any of the noun arguments in distance() or sap() is not a WordNet noun.
        if (nounA == null||nounB == null)
        {throw new IllegalArgumentException();}
        
        Iterable<Integer> A = words.get(nounA);
        Iterable<Integer> B = words.get(nounB);
        if (A == null||B == null)
        {throw new IllegalArgumentException();}       
        int a = sap.ancestor(A,B);
        if (a == -1)
        {throw new IllegalArgumentException();}
        return (synsetsTable.get(a));
        
    }
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    
    
}
