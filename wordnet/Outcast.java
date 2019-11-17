import  edu.princeton.cs.algs4.In;
import  edu.princeton.cs.algs4.StdOut;

public class Outcast {

    // see test client below    
    public static void main(String[] args)  
    {
       WordNet wordnet = new WordNet(args[0], args[1]);
       Outcast outcast = new Outcast(wordnet);
       for (int t = 2; t < args.length; t++) 
       {
           In in = new In(args[t]);
           String[] nouns = in.readAllStrings();
           StdOut.println(args[t] + ": " + outcast.outcast(nouns));
       }             
    }
    // see test client below
    
    private WordNet wordnet;

    // constructor takes a WordNet object        
    public Outcast(WordNet wordnet)    
    {
        if (wordnet == null)
        {throw new java.lang.IllegalArgumentException();} 
        this.wordnet = wordnet;
    }
    // constructor takes a WordNet object

    // given an array of WordNet nouns, return an outcast    
    public String outcast(String[] nouns) 
    {
        if (nouns == null)
        {throw new java.lang.IllegalArgumentException();}   
        check(nouns);
        
        int max = -1;
        int id = -1;
        for (int i=0; i<nouns.length; i++)
        {
            int dist = 0;
            for (int j=0; j<nouns.length; j++)
            {
                dist = dist + wordnet.distance(nouns[i],nouns[j]);
            }
            if (dist>max) {max = dist; id = i;}
        }
        return nouns[id];
    }
    // given an array of WordNet nouns, return an outcast
    
    private void check(String[] nouns)
    { 
        for (String noun : nouns)
        {
            if (!wordnet.isNoun(noun))
            {throw new java.lang.IllegalArgumentException();}    
        }
    }    

}
