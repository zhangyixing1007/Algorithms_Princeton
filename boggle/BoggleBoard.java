import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class BoggleBoard 
{
    private final int M;        // number of rows
    private final int N;        // number of columns
    private char[][] board;     // the M-by-N array of characters

    
    
    
    // the 16 Boggle dice (1992 version)
    private static final String[] boggle1992 = 
    {
        "LRYTTE", "VTHRWE", "EGHWNE", "SEOTIS",
        "ANAEEG", "IDSYTT", "OATTOW", "MTOICU",
        "AFPKFS", "XLDERI", "HCPOAS", "ENSIEU",
        "YLDEVR", "ZNRNHL", "NMIQHU", "OBBAOJ"
    };

    


    // letters and frequencies of letters in the English alphabet
    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final double[] frequencies = 
    {
        0.08167, 0.01492, 0.02782, 0.04253, 0.12703, 0.02228,
        0.02015, 0.06094, 0.06966, 0.00153, 0.00772, 0.04025,
        0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987,
        0.06327, 0.09056, 0.02758, 0.00978, 0.02360, 0.00150,
        0.01974, 0.00074
    };

    
    // Initializes a random 4-by-4 Boggle board.
    // (by rolling the Hasbro dice)
    public BoggleBoard() 
    {
        M = 4;
        N = 4;
        StdRandom.shuffle(boggle1992);
        board = new char[M][N];
        for (int i = 0; i < M; i++) 
        {
            for (int j = 0; j < N; j++) 
            {
                String letters = boggle1992[N*i+j];
                int r = StdRandom.uniform(letters.length());
                board[i][j] = letters.charAt(r);
            }
        }
    }
    // Initializes a random 4-by-4 Boggle board.
    // (by rolling the Hasbro dice)
    
    
    
    
    // Initializes a random m-by-n Boggle board.
    // (using the frequency of letters in the English language)    
    public BoggleBoard(int M, int N) 
    {
        this.M = M;
        this.N = N;
        board = new char[M][N];
        for (int i = 0; i < M; i++) 
        {
            for (int j = 0; j < N; j++) 
            {
                int r = StdRandom.discrete(frequencies);
                board[i][j] = alphabet.charAt(r);//much more explicit
            }
        }
    }
    // Initializes a random m-by-n Boggle board.
    // (using the frequency of letters in the English language)
    
 
    
    
    // Initializes a Boggle board from the specified filename.    
    public BoggleBoard(String filename) 
    {
        In in = new In(filename);
        M = in.readInt();
        N = in.readInt();
        board = new char[M][N];
        for (int i = 0; i < M; i++) 
        {
            for (int j = 0; j < N; j++) 
            {
                String letter = in.readString().toUpperCase();
                if (letter.equals("QU"))
                    board[i][j] = 'Q';
                else if (letter.length() != 1)
                    throw new IllegalArgumentException("invalid character: " + letter);
                else if (alphabet.indexOf(letter) == -1)
                    throw new IllegalArgumentException("invalid character: " + letter);
                else 
                    board[i][j] = letter.charAt(0);
            }
        }
    }
    // Initializes a Boggle board from the specified filename.
    
    
    

    // Initializes a Boggle board from the 2d char array.
    // (with 'Q' representing the two-letter sequence "Qu")    
    public BoggleBoard(char[][] a) 
    {
        this.M = a.length;
        this.N = a[0].length;
        board = new char[M][N];
        for (int i = 0; i < M; i++) 
        {
            if (a[i].length != N)
                throw new IllegalArgumentException("char[][] array is ragged");
            for (int j = 0; j < N; j++) 
            {
                if (alphabet.indexOf(a[i][j]) == -1)
                {throw new IllegalArgumentException("invalid character: " + a[i][j]);}
                board[i][j] = a[i][j];
            }
        }
    }
    // Initializes a Boggle board from the 2d char array.
    // (with 'Q' representing the two-letter sequence "Qu")




    // Returns the number of rows.
    public int rows() { return M; }


    // Returns the number of columns.
    public int cols() { return N; }

    

    // Returns the letter in row i and column j.
    // (with 'Q' representing the two-letter sequence "Qu")    
    public char getLetter(int i, int j) { return board[i][j]; }


    
    
    // Returns a string representation of the board.    
    public String toString() 
    {
        StringBuilder sb = new StringBuilder(M + " " + N + "\n");
        for (int i = 0; i < M; i++) 
        {
            for (int j = 0; j < N; j++) 
            {
                sb.append(board[i][j]);
                if (board[i][j] == 'Q') sb.append("u ");
                else sb.append("  ");
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }
    // Returns a string representation of the board.

    
}
