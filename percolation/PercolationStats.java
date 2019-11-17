import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF; 

public class PercolationStats 
{
    
    // test client     
    public static void main(String[] args)  
    {
        int n = 2;      
        int T = 10000;
        PercolationStats pers = new PercolationStats(n, T);   
        double lo = pers.confidenceLo();
        double hi = pers.confidenceHi();         
    }
    // test client 
        
    
    private int n;
    private int T;  
    
    private double[] thre;//stand for threshold
    private double mean;// mean for array thre
    private double std;// std for array thre
    private double confidenceLo;
    private double confidenceHi;
    
    
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) 
    {
        if (n<1||trials<1){
            throw new java.lang.IllegalArgumentException();
        }
        else{           
            thre = new double[trials];
            int T = trials;
            for (int i=0; i<T; i++)
            {
                int t = i+1; 
                Percolation per = new Percolation(n);               
                int nos = 0;
                while(nos<n*n)
                {
                    int row = StdRandom.uniform(1,n+1);
                    int col = StdRandom.uniform(1,n+1);
                    
                    if (per.isOpen(row,col)==true){continue;}
                    
                    per.open(row,col); 
                    nos = nos+1;     
                    
                    if (per.percolates()==true)
                    {
                        double pt = (double) nos/(n*n);
                        thre[i]=pt;                   
                        break;
                    }          
  
                }
            }
        }
        mean = StdStats.mean(thre);
        std = StdStats.stddev(thre);
        confidenceLo = mean - (1.96 * std) / Math.sqrt(trials);
        confidenceHi = mean + (1.96 * std) / Math.sqrt(trials);       
    }
    // perform trials independent experiments on an n-by-n grid
        
        
        
    // sample mean of percolation threshold    
    public double mean() 
    {return mean;}
    // sample mean of percolation threshold
    
        
        
    // sample standard deviation of percolation threshold    
    public double stddev()
    {return std;}
    // sample standard deviation of percolation threshold
    
    
        
    // low  endpoint of 95% confidence interval    
    public double confidenceLo()  
    {return confidenceLo;}
    // low  endpoint of 95% confidence interval
    
        
        
    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {return confidenceHi;}
    // high endpoint of 95% confidence interval         
}
