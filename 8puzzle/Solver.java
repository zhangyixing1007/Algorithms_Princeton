import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;

public class Solver 
{
    
// solve a slider puzzle (given below)        
    public static void main(String[] args) 
    {}
// solve a slider puzzle (given below)
    
    
    
// find a solution to the initial board (using the A* algorithm)    
    public Solver(Board initial)           
    {
        if (initial==null)
        {throw new IllegalArgumentException();}
        
        priority p1 = new priority();       
        MinPQ<Node> pq = new MinPQ<Node>(p1);
        Node node = new Node(initial);
        pq.insert(node);
        
        
        priority p2 = new priority();
        MinPQ<Node> twinPq = new MinPQ<Node>(p2);
        Node twinNode = new Node(initial.twin());
        twinPq.insert(twinNode);
        
        Node min = pq.delMin();
        Node twinMin = twinPq.delMin();
        
        while(!min.board.isGoal()&&!twinMin.board.isGoal())
        {
            for (Board current : min.board.neighbors())
            {
                if (min.prev == null||!min.prev.board.equals(current))
                {
                    Node s = new Node(current);
                    s.moves = min.moves+1;
                    s.prev = min;
                    pq.insert(s);
                }
            }
            
            for (Board current : twinMin.board.neighbors())
            {
                if (twinMin.prev == null||!twinMin.prev.board.equals(current))
                {
                    Node s = new Node(current);
                    s.moves = twinMin.moves+1;
                    s.prev = twinMin;
                    twinPq.insert(s);
                }
            } 
            
            min = pq.delMin();
            twinMin = twinPq.delMin();               
        }
        
        if (min.board.isGoal())
        {goal = min;}
        else
        {goal = null;}
        
    }
// find a solution to the initial board (using the A* algorithm)
    
    
    private Node goal;
    
    
    private class Node
    {
        private Board board;
        private int moves;
        private int manhattan;      
        private Node prev;

        
        private Node(Board b)
        {
            board = b;
            moves = 0;
            manhattan = b.manhattan();
            prev = null;
        }
    }
    
    
    private class priority implements Comparator<Node>
    {
        public int compare(Node s1, Node s2)
        {
            int c = s1.manhattan+s1.moves
                -s2.manhattan-s2.moves;
            if (c<0) {return -1;}
            else if (c>0) {return 1;}
            else {return 0;}
        }
    }
    
    
// is the initial board solvable?    
    public boolean isSolvable()    
    {return (goal!=null);}
// is the initial board solvable?
    
    
    
// min number of moves to solve initial board; -1 if unsolvable    
    public int moves()    
    {
        if(isSolvable())
        {return goal.moves;}
        else
        {return -1;}
    }
// min number of moves to solve initial board; -1 if unsolvable
    
    
    
// sequence of boards in a shortest solution; null if unsolvable    
    public Iterable<Board> solution()  
    {
        if (!isSolvable()) return null;
        
        Stack<Board> result = new Stack<Board>();
        for (Node node = goal; node!=null; node = node.prev)
        {result.push(node.board);}
        return result;
    }
// sequence of boards in a shortest solution; null if unsolvable
    
    
    
    
