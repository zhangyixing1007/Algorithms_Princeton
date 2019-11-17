import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;


public class PointSET 
{

    private SET<Point2D> set;

    // construct an empty set of points
    public PointSET() { set = new SET<Point2D>(); }
    
    // is the set empty?
    public boolean isEmpty() { return set.isEmpty(); }

    // number of points in the set
    public int size() { return set.size(); }

    // add the point p to the set (if it is not already in the set)
    // proportional to logarithm of N in the worst case
    public void insert(Point2D p) 
    { 
        if (p==null)
        {throw new java.lang.IllegalArgumentException();}         
        set.add(p); 
    }
    // add the point p to the set (if it is not already in the set)
    // proportional to logarithm of N in the worst case

    
    
    
    // does the set contain the point p?
    // proportional to logarithm of N in the worst case
    public boolean contains(Point2D p) 
    { 
        if (p==null)
        {throw new java.lang.IllegalArgumentException();}         
        return set.contains(p); 
    }
    // does the set contain the point p?
    // proportional to logarithm of N in the worst case
    
    
    
    
    // draw all of the points to standard draw
    public void draw() 
    {
        for (Point2D p:set)
        {p.draw();}        
    }
    // draw all of the points to standard draw

    
    
    
    // all points in the set that are inside the rectangle
    // proportional to N in the worst case
    public Iterable<Point2D> range(RectHV rect) 
    {
        if (rect==null)
        {throw new java.lang.IllegalArgumentException();}        
        SET<Point2D> pr = new SET<Point2D>();
        for (Point2D p:set)
        {
            if (rect.contains(p))
            {pr.add(p);}
        }
        return pr;        
    }
    // all points in the set that are inside the rectangle
    // proportional to N in the worst case    
    
    

    
    // a nearest neighbor in the set to p: null if set is empty
    // proportional to N
    public Point2D nearest(Point2D p) 
    {
        Point2D nst =null;
        if (p==null)
        {throw new java.lang.IllegalArgumentException();}
        if (isEmpty()) return null;
        
        double dist = Double.POSITIVE_INFINITY;
        double d = Double.POSITIVE_INFINITY;
        for (Point2D q:set)
        {
            d = q.distanceSquaredTo(p);
            if (d<dist)
            {nst = q;dist = d;}
        }
        return nst;        
    }
    // a nearest neighbor in the set to p: null if set is empty
    // proportional to N    
}
