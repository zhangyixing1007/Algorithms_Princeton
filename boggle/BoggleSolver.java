import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;


public class BoggleSolver 
{
    
    //node for the class BoggleDictionary 
    private class DictionaryNode 
    {
        private char c; 
        private DictionaryNode left, mid, right; 
        private String val; 
    }
    
    

    // this data structure is very similar to "TrieST" in PPT
    // API of edu.princeton.cs.algs4.TrieST<Value> :
    // algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/TrieST.html
    // code of edu.princeton.cs.algs4.TrieST<Value>:
    // https://algs4.cs.princeton.edu/52trie/TrieST.java.html
    
    // DIY will make it more efficient for our questions
    private class BoggleDictionary 
    {
        private DictionaryNode root;
        
        public void add(String s) 
        {
            root = put(root, s, s, 0);
        }
        
        private DictionaryNode put(DictionaryNode x, String s, String val, int d) 
        {
            char c = s.charAt(d);
            if (x == null) 
            {
                x = new DictionaryNode();
                x.c = c;
            }
            if (c < x.c)
                x.left = put(x.left, s, val, d);
            else if (c > x.c)
                x.right = put(x.right, s, val, d);
            else if (d < s.length() - 1)
                x.mid = put(x.mid, s, val, d + 1);
            else
                x.val = val;
            return x;
        }
        
        
        private boolean contains(String key) 
        {return get(key) != null;}
        
        
        private String get(String key) 
        {
            if (key == null)
                throw new NullPointerException();
            if (key.length() == 0)
                throw new IllegalArgumentException("key must have length >= 1");
            DictionaryNode x = get(root, key, 0);
            if (x == null)
                return null;
            return x.val;
        }
        
        private DictionaryNode get(DictionaryNode x, String key, int d) 
        {
            if (x == null)
                return null;
            char c = key.charAt(d);
            if (c < x.c)
                return get(x.left, key, d);
            else if (c > x.c)
                return get(x.right, key, d);
            else if (d < key.length() - 1)
                return get(x.mid, key, d + 1);
            else
                return x;
        }
    }
    
    private final BoggleDictionary dictionaryTrie;
    
    
    
    // Initializes the data structure 
    //using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary 
    //contains only the uppercase letters A through Z.)    
    public BoggleSolver(String[] dictionary) 
    {
        if (dictionary == null) 
        {throw new IllegalArgumentException();}
        dictionaryTrie = new BoggleDictionary();       
        String[] newDic = new String[dictionary.length];
        for (int i = 0; i < dictionary.length; i++) 
        {newDic[i] = dictionary[i];}
        StdRandom.shuffle(newDic);
        for (String word : newDic)
        {dictionaryTrie.add(word);}
    }
    // Initializes the data structure 
    // using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary 
    // contains only the uppercase letters A through Z.)       
    
    

    
    // Returns the set of all valid words in the given Boggle board, 
    // as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) 
    {
        if (board == null) 
        {throw new IllegalArgumentException("Board is mandatory");}
        
        List<String> results = new ArrayList<String>();
        for (int i = 0; i < board.rows(); i++) 
        {
            for (int j = 0; j < board.cols(); j++) 
            {
                String prefix = getSuffix(board, i, j);
                DictionaryNode node = 
                    dictionaryTrie.get(dictionaryTrie.root, prefix, 0);
                boolean[] visitedPositions = 
                    new boolean[board.rows()*board.cols()];                 
                if (node != null) 
                {exploreBoard(results, board, i, j, 
                              visitedPositions, node);}                    
            }
        }
        
        List<String> sortedResults = 
            new ArrayList<String>(new HashSet<String>(results));
        Collections.sort(sortedResults);
        
        return sortedResults;
    }
    // Returns the set of all valid words in the given Boggle board, 
    // as an Iterable.
    
    
    
    // board.getLetter(int row, int col) can only return characters
    // we need to deal with special case 'Q'
    private String getSuffix(BoggleBoard board, int i, int j) 
    {
        char letter = board.getLetter(i, j);
        if (letter == 'Q') 
        {return "QU";} 
        else {return String.valueOf(letter);}
    }
    
    
    
    
    // most important recursion function 
    // explore current node, and find the next node to explore
    private void exploreBoard(List<String> words, BoggleBoard board, 
                              int x, int y,
                              boolean[] visitedPositions, 
                              DictionaryNode node)     
    {
        String v = node.val;
        if (v != null && v.length() >= 3) 
        {words.add(v);}       
        visitedPositions[x*board.cols()+y] = true;
        
        for (int i = Math.max(0, x - 1); 
             i <= Math.min(x + 1, board.rows() - 1); i++) 
        {
            for (int j = Math.max(0, y - 1); 
                 j <= Math.min(y + 1, board.cols() - 1); j++) 
            {
                if (visitedPositions[i*board.cols()+j] == true)
                {continue;}
                String suffix = getSuffix(board, i, j);
                DictionaryNode nextNode = 
                    dictionaryTrie.get(node.mid, suffix, 0);
                if (nextNode != null) 
                {exploreBoard(words, board, i, j, visitedPositions, nextNode);}
            }
        }
        visitedPositions[x*board.cols()+y] = false;
        
    }
    
    


    // Returns the score of the given word 
    // if it is in the dictionary, zero otherwise.
    // (You can assume the word contains 
    // only the uppercase letters A through Z.)    
    public int scoreOf(String word) 
    {
        if (word == null) {
            throw new IllegalArgumentException("Word is mandatory");
        }
        if (word.length() <= 2 || !dictionaryTrie.contains(word)) {
            return 0;
        }
        
        switch (word.length()) {
            case 3:
            case 4:
                return 1;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 5;
            case 8:
            default:
                return 11;
        }
    }
    // Returns the score of the given word 
    // if it is in the dictionary, zero otherwise.
    // (You can assume the word contains 
    // only the uppercase letters A through Z.)       
    
}
