import java.util.ArrayList;

public class Board 
{
// unit tests (not graded)    
    public static void main(String[] args) 
    {}
// unit tests (not graded)
    
    
    
    
// construct a board from an n-by-n array of blocks        
    public Board(int[][] blocks) 
    {
        b = blocks;
        ham = 0;
        man = 0;
        r0 = 0;
        c0 = 0;
        for (int i = 0; i<b.length; i++)
        {
            for (int j = 0; j<b.length; j++)
            {
                if (b[i][j]!=0&&b[i][j]!=i*b.length+j+1)
                {
                    ham = ham+1;
                    int row = (b[i][j]-1)/b.length;
                    int col = (b[i][j]-1)%b.length;
                    man = man+Math.abs(i-row)+Math.abs(j-col);                   
                }
                else if (b[i][j]==0)
                {r0=i;c0=j;} 
            }
        }
    }
// construct a board from an n-by-n array of blocks 
// (where blocks[i][j] = block in row i, column j)
    
    private int[][] b;
    private int ham;
    private int man;
    private int r0;
    private int c0;
    
    
    
    
// board dimension n    
    public int dimension()  
    {
        return b.length;
    }
// board dimension n
    
    
    
    
// number of blocks out of place    
    public int hamming()     
    {return ham;}
// number of blocks out of place
    
    
    
    
// sum of Manhattan distances between blocks and goal    
    public int manhattan()    
    {return man;}
// sum of Manhattan distances between blocks and goal
    
    
    
    
// is this board the goal board?    
    public boolean isGoal()  
    {return (ham==0);}
// is this board the goal board?
    
    
    
// a board that is obtained by exchanging any pair of blocks        
    public Board twin() 
    {
        int[][] twin = copy(b);
        if (twin[0][0]!=0&&twin[0][1]!=0)
        {
            twin[0][0] = b[0][1];
            twin[0][1] = b[0][0];
        }
        else
        {
            twin[1][0] = b[1][1];
            twin[1][1] = b[1][0];
        }     
        return (new Board(twin));
    }
// a board that is obtained by exchanging any pair of blocks
    
    
    
    
// does this board equal y?        
    public boolean equals(Object y)     
    {
        if (!(y instanceof Board))
        {return false;}
        
        Board yBoard = (Board) y;
        if (this.b.length!=yBoard.b.length)
        {return false;}
        
        for (int i = 0; i<b.length; i++)
        {
            for (int j = 0; j<b.length; j++)
            {
                if (this.b[i][j]!=yBoard.b[i][j])
                {
                    return false;
                }
            }
        }        
        return true;
    }
// does this board equal y?
    
    
    
    
// all neighboring boards        
    public Iterable<Board> neighbors()
    {
        ArrayList<Board> results = new ArrayList<Board>();
        
        if (r0!=0)
        {
            int[][] b1 = copy(b);
            b1[r0][c0] = b[r0-1][c0];
            b1[r0-1][c0] = b[r0][c0];
            results.add(new Board(b1));
        }
        
        if (r0!=b.length-1)
        {
            int[][] b2 = copy(b);
            b2[r0][c0] = b[r0+1][c0];
            b2[r0+1][c0] = b[r0][c0];
            results.add(new Board(b2));
        }
        
        if (c0!=0)
        {
            int[][] b3 = copy(b);
            b3[r0][c0] = b[r0][c0-1];
            b3[r0][c0-1] = b[r0][c0];
            results.add(new Board(b3));
        }
        
        if (c0!=b.length-1)
        {
            int[][] b4 = copy(b);
            b4[r0][c0] = b[r0][c0+1];
            b4[r0][c0+1] = b[r0][c0];
            results.add(new Board(b4));
        }        
        
        
        return results;
    }
// all neighboring boards
    
    
    private int[][] copy(int[][] b)
    {
        int[][] c = new int[b.length][b.length];
        for (int i = 0; i<b.length; i++)
        {
            for (int j = 0; j<b.length; j++)
            {c[i][j] = b[i][j];}
        }
        return c;
    }
    
    
// string representation of this board (in the output format specified below)        
    public String toString()     
    {
        StringBuilder s = new StringBuilder();
        int n = b.length;
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", b[i][j]));
            }
            s.append("\n");
        }
        return s.toString();        
    }
// string representation of this board (in the output format specified below)
    
    
}
