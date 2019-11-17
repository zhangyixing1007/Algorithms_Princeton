import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF; 


public class Percolation 
{
    public static void main(String[] args)
    {}
    
    private int[][] p;
    private WeightedQuickUnionUF uf;
    private int nos;
    
    // create n-by-n grid, with all sites blocked
    public Percolation(int n) 
    {
        if (n<1){
            throw new java.lang.IllegalArgumentException();
        }
        else{
            p = new int[n+1][n+1];
            uf = new WeightedQuickUnionUF(n*n+1);
            nos = 0;
        }
    }                
    // create n-by-n grid, with all sites blocked
    
    
    
    
    // open site (row, col) if it is not open already
    public    void open(int row, int col)   
    {
        int n = p.length-1;
        if (row<1||col<1||row>n||col>n){
            throw new java.lang.IllegalArgumentException();
        }
        else if(isOpen(row,col)==false) 
        {
            p[row][col] = 1;
            int po = (row-1)*n+col-1;
            nos = nos+1;//number of open sites
            
            if (row==1)
            {
                uf.union(po,n*n);
            }
            if (row==n)
            {
                p[row][col] = 2;             
            }


            if ((row>1)&&(isOpen(row-1,col)==true))               
            {   
                update(row,col,row-1,col);
            }
            
            
            if ((row<n)&&(isOpen(row+1,col)==true))
            {
                update(row,col,row+1,col);
            }
            
            
            if ((col>1)&&(isOpen(row,col-1)==true))
            {
                update(row,col,row,col-1);
            }
            
            
            if((col<n)&&(isOpen(row,col+1)==true))
            {    
                update(row,col,row,col+1);
            }            
        }
    } 
   // open site (row, col) if it is not open already
    
    
    private void update (int row, int col, int r, int c)
    {
        int n = p.length-1;
        int po = uf.find((row-1)*n+col-1);
        int qo = uf.find((r-1)*n+c-1);
        uf.union(po,qo);
        if (p[po/n+1][po%n+1] == 2||p[qo/n+1][qo%n+1] == 2)
        {
            int root = uf.find((r-1)*n+c-1);
            if (root == n*n){p[0][0] = 2;}
            else
            {p[root/n+1][root%n+1] = 2;}
        }
    }
    
    
    
    
    // is site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        int n = p.length-1;
        if (row<1||col<1||row>n||col>n){
            throw new java.lang.IllegalArgumentException();
        }
        else{
            return (p[row][col]>0);
        }
    }
   // is site (row, col) open?
    
    
    
   
    // is site (row, col) full? 
    public boolean isFull(int row, int col)
    {
        int n = p.length-1;
        if (row<1||col<1||row>n||col>n){    
            throw new java.lang.IllegalArgumentException();
        }
        else if(isOpen(row,col)==false){return false;}       
        else{
            int po = (row-1)*n+col-1;
            return (uf.connected(n*n,po));            
        }
    }
    // is site (row, col) full?
    
    
    
    
    // number of open sites
    public int numberOfOpenSites()
    {
        return nos;
    }
    // number of open sites
    
    
    
    
    // does the system percolate? 
    public boolean percolates()
    {
        
        int n = p.length-1;
        if ((n==1)&&(numberOfOpenSites()==0))
        {return false;}
        int root = uf.find(n*n);
        if (root == n*n){return p[0][0]==2;}//====
        return (p[root/n+1][root%n+1]==2);//return (p[root/n][root%n]==2);
    }
    // does the system percolate? 
}
