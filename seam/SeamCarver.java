import  edu.princeton.cs.algs4.Picture;


public class SeamCarver 
{
    
    public static void main(String[] args) 
    {}   

        
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) 
    {
        if (picture == null)
        {throw new java.lang.IllegalArgumentException();}
        
        int width = picture.width();
        int height = picture.height();
        colors = new int[width][height];
       
        for (int i=0; i<width; i++)
        {
            for (int j=0; j<height; j++)
            {
                colors[i][j]=picture.getRGB(i, j);
            }
        }                   
    }
    // create a seam carver object based on the given picture

    
    private int[][] colors;
    

    // current picture
    public Picture picture()    
    {
        Picture picture = new Picture(colors.length,colors[0].length); 
        for (int i=0; i<colors.length; i++)
        {
            for (int j=0; j<colors[0].length; j++)
            {
                picture.setRGB(i,j,colors[i][j]);
            }
        }        
        return picture;
    }
    // current picture
       
    
    
    
    // width of current picture    
    public     int width() 
    {return this.colors.length;}
    // width of current picture
        
    
    
    
    // height of current picture    
    public     int height()
    {return this.colors[0].length;}
    // height of current picture
        
    
    
        
    // energy of pixel at column x and row y    
    public  double energy(int x, int y)
    {
        if (x<0||x>this.width()-1||y<0||y>this.height()-1)
        {throw new java.lang.IllegalArgumentException();}
        
        if (x==0||x==this.width()-1||y==0||y==this.height()-1)
        {return 1000.0;}

        return Math.sqrt(gradx(x, y)+grady(x, y));
    }
    // energy of pixel at column x and row y
        
        
    private int gradx(int x, int y)
    {
        int rgb1 = colors[x-1][y];
        int r1 = (rgb1 >> 16) & 0xFF;
        int g1 = (rgb1 >>  8) & 0xFF;
        int b1 = (rgb1 >>  0) & 0xFF;
  
        int rgb2 = colors[x+1][y];
        int r2 = (rgb2 >> 16) & 0xFF;
        int g2 = (rgb2 >>  8) & 0xFF;
        int b2 = (rgb2 >>  0) & 0xFF;
        
        int sum = (r1-r2)*(r1-r2)
            +(g1-g2)*(g1-g2)
            +(b1-b2)*(b1-b2);
        return sum;
    }
    
    private int grady(int x, int y)
    {
        int rgb1 = colors[x][y-1];
        int r1 = (rgb1 >> 16) & 0xFF;
        int g1 = (rgb1 >>  8) & 0xFF;
        int b1 = (rgb1 >>  0) & 0xFF;
  
        int rgb2 = colors[x][y+1];
        int r2 = (rgb2 >> 16) & 0xFF;
        int g2 = (rgb2 >>  8) & 0xFF;
        int b2 = (rgb2 >>  0) & 0xFF;
        
        int sum = (r1-r2)*(r1-r2)
            +(g1-g2)*(g1-g2)
            +(b1-b2)*(b1-b2);
        return sum;
    }
        

    
    // sequence of indices for horizontal seam   
    public   int[] findHorizontalSeam()    
    {
        this.colors = transpose(this.colors);
        int[] seam = findVerticalSeam();
        this.colors = transpose(this.colors);
        return seam;
    }
    // sequence of indices for horizontal seam
        
    
    private int[][] transpose(int[][] org)
    { 
        if (org == null) throw new IllegalArgumentException();
        int[][] rvs = new int[org[0].length][org.length];
        for (int x=0; x<org[0].length; x++)
        {
            for(int y=0; y<org.length; y++)
            {
                rvs[x][y] = org[y][x];
            }
        }
        return rvs;           
    }

        
    // sequence of indices for vertical seam    
    public   int[] findVerticalSeam()  
    {
        //find smallest path via topological order
        int n = colors.length*colors[0].length;    
        int[] vertexTo = new int[n];
        double[] distTo = new double[n];
        
        //set initial distance
        for (int v=0; v<n; v++)
        {
            if (v<colors.length)
            {distTo[v] = 0;}
            else
            {distTo[v] = Double.POSITIVE_INFINITY;}
        }
        
        for (int j=0; j<colors[0].length-1; j++)
        {
            for (int i=0; i<colors.length; i++)
            {
                for (int k=-1; k<2; k++)
                {
                    if ((i+k<colors.length)&&(i+k>=0))
                    {
                        int v = i+j*colors.length;//from [i][j]
                        int w = i+k+(j+1)*colors.length;//to [i+k][j+1]
                        double energy = energy(i+k,j+1);
                        if (distTo[w] > distTo[v]+energy)
                        {
                            distTo[w] = distTo[v]+energy;
                            vertexTo[w] = v;
                        }
                    }
                }
            }
        }
           
        //generate int[] seam!
        int[] seam =new int[colors[0].length];
        
        //find the last element of seam[]
        double min = distTo[(height()-1)*width()];
        int end = (height()-1)*width();
        for (int i=(height()-1)*width(); i<height()*width(); i++)
        {
            if (min>=distTo[i])
            {min = distTo[i]; end = i;}
        }     
        
        //get value of seam[] from what we built
        seam[height()-1] = end - (height()-1)*width();        
        for (int i=height()-2; i>-1; i--)
        {   
            end = vertexTo[end];
            seam[i] = end - i*width();          
        }         
        return seam;
    }
    // sequence of indices for vertical seam


    private int index(int x, int y) 
    {
        return width() * y + x;
    }
    
    
    
    
    //     remove horizontal seam from current picture    
    public    void removeHorizontalSeam(int[] seam)   
    {       
        if (seam == null)
        {throw new java.lang.IllegalArgumentException();}  
                
        if (seam.length!=width())
        {throw new java.lang.IllegalArgumentException();}  
        for (int i=0; i<seam.length; i++)
        {
            if (seam[i]<0||seam[i]>height()-1)
            {throw new java.lang.IllegalArgumentException();}  
            if (i!=seam.length-1&&(seam[i]-seam[i+1]>1||seam[i]-seam[i+1]<-1))
            {throw new java.lang.IllegalArgumentException();}        
        }            
        if (height()<=1)
        {throw new java.lang.IllegalArgumentException();}
                
        int[][] update = new int[width()][height()-1];        
        for (int j=0; j<width(); j++)
        {
            if (seam[j] == 0)
            {System.arraycopy(this.colors[j], 1, update[j], 0, height()-1);}
            else if (seam[j] == height()-1)
            {System.arraycopy(this.colors[j], 0, update[j], 0, height()-1);}
            else
            {
                System.arraycopy(this.colors[j],0, update[j], 0, seam[j]);
                System.arraycopy(this.colors[j], seam[j]+1, 
                          update[j], seam[j], height()-seam[j]-1);
            }
        }    
        this.colors = update;             
    }
    // remove horizontal seam from current picture
    
    
    
    
    // remove vertical seam from current picture    
    public    void removeVerticalSeam(int[] seam)  
    {
        this.colors = transpose(this.colors);
        removeHorizontalSeam(seam);
        this.colors = transpose(this.colors);        
    }
    // remove vertical seam from current picture
    
        
}
