import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;


public class KdTree 
{
// unit testing of the methods (optional)    
    public static void main(String[] args)  
    {}                
// unit testing of the methods (optional)
    
    
    
    
// construct an empty set of points     
    public         KdTree()    
    {
        root = null;
        size = 0;
    }                           
// construct an empty set of points 
    
    
    private static class Node
    {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
        
        public Node(Point2D p, RectHV rect)
        {
            this.p = p;
            this.rect = rect;
            if (rect == null)
                this.rect = new RectHV(0,0,1,1);
        }
    }
    
    
    private Node root;
    private int size;
    
    
    
    
// is the set empty?    
    public           boolean isEmpty() 
    {return root==null;}   
// is the set empty? 
    
    
    
    
// number of points in the set     
    public               int size()  
    {return size;}
// number of points in the set 
    
    
    
    
// add the point to the set (if it is not already in the set)    
    public              void insert(Point2D p)     
    {
        if(p==null)
        {throw new java.lang.IllegalArgumentException();}
        if (isEmpty())
            root = insertV(root, p, null);
        else
            root = insertV(root, p, root.rect);
    }
// add the point to the set (if it is not already in the set)
    
    
    private Node insertV (Node node, Point2D p, RectHV rect)
    {
        if (node==null)
        {size++;return new Node(p,rect);}
        if (node.p.equals(p))
        {return node;}
        
        double cmp = p.x() - node.p.x();
        if (cmp<0)
        {
            if (node.lb==null)
            {
                RectHV r = new RectHV(rect.xmin(),rect.ymin(),node.p.x(),rect.ymax());
                node.lb = insertH(node.lb, p, r);
            }
            else 
            {node.lb = insertH(node.lb, p, node.lb.rect);}
        }
        else
        {
            if (node.rt==null)
            {
                RectHV r = new RectHV(node.p.x(),rect.ymin(),rect.xmax(),rect.ymax());
                node.rt = insertH(node.rt, p, r);                
            }
            else
            {node.rt = insertH(node.rt, p, node.rt.rect);}
        }
        return node;
    }
    
    
    private Node insertH (Node node, Point2D p, RectHV rect)
    {
        if (node==null)
        {size++;return new Node(p,rect);}
        if (node.p.equals(p))
        {return node;}
        
        double cmp = p.y() - node.p.y();
        if (cmp<0)
        {
            if (node.lb==null)
            {
                RectHV r = new RectHV(rect.xmin(),rect.ymin(),rect.xmax(),node.p.y());
                node.lb = insertV(node.lb, p, r);
            }
            else
            {node.lb = insertV(node.lb, p, node.lb.rect);}
        }
        else
        {
            if(node.rt==null)
            {
                RectHV r = new RectHV(rect.xmin(),node.p.y(),rect.xmax(),rect.ymax());
                node.rt = insertV(node.rt, p, r);
            }
            else
            {node.rt = insertV(node.rt, p, node.rt.rect);}
        }
        return node;
    }    
    
    
// does the set contain point p?     
    public           boolean contains(Point2D p)
    {
        if (p==null)
        {throw new java.lang.IllegalArgumentException();}  
        if (isEmpty())
        {return false;}
        return contains(root, p, true);
    }
// does the set contain point p? 
    
    
    
    private boolean contains (Node node, Point2D p, boolean vert)
    {
        if (node==null){return false;}
        if (node.p.equals(p)){return true;}
        
        double cmp;
        if (vert){cmp = p.x() - node.p.x();}
        else     {cmp = p.y() - node.p.y();}
        
        if (cmp<0)
        {return contains (node.lb, p, !vert);}
        else
        {return contains (node.rt, p, !vert);}  
    }
    
    
    
    
// draw all points to standard draw     
    public              void draw()  
    {
        StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
        if (isEmpty()){return;}
        draw(root, true);
    }
// draw all points to standard draw 
    
    
    private void draw(Node node, boolean vert)
    {
        if (node.lb!=null){draw(node.lb, !vert);}
        if (node.rt!=null){draw(node.rt, !vert);}
        
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        StdDraw.point(node.p.x(), node.p.y());      
        
        double xmin;
        double ymin;
        double xmax;
        double ymax;
        if (vert)
        {
            StdDraw.setPenColor(StdDraw.RED);
            xmin = node.p.x(); xmax = node.p.x();
            ymin = node.rect.ymin(); ymax = node.rect.ymax();
        }
        else
        {
            StdDraw.setPenColor(StdDraw.BLUE);
            xmin = node.rect.xmin(); xmax = node.rect.xmax();
            ymin = node.p.y(); ymax = node.p.y();            
        }
        StdDraw.setPenRadius();
        StdDraw.line(xmin, ymin, xmax, ymax);        
    }
    
    
    
// all points that are inside the rectangle (or on the boundary)     
    public Iterable<Point2D> range(RectHV rect)   
    {
        if(rect==null)
        {throw new java.lang.IllegalArgumentException();}  
        
        ArrayList<Point2D> l = new ArrayList<Point2D>();
        range(root, rect, l);
        return l;
    }
// all points that are inside the rectangle (or on the boundary)
    
    
//    private Iterable<Point2D> range(Node node, RectHV rect, ArrayList<Point2D> l)
    private void range(Node node, RectHV rect, ArrayList<Point2D> l)  
    {
        if (node==null)
        {throw new java.lang.IllegalArgumentException();}
        if(rect.contains(node.p)){l.add(node.p);}
        if(node.lb!=null&&node.lb.rect.intersects(rect))
        {range(node.lb,rect,l);}
        if(node.rt!=null&&node.rt.rect.intersects(rect))
        {range(node.rt,rect,l);} 
    }
    
    
    
    
    
// a nearest neighbor in the set to point p; null if the set is empty     
    public           Point2D nearest(Point2D p)      
    {
        if(p==null)
        {throw new java.lang.IllegalArgumentException();}  
        if(isEmpty()){return null;}
        
        return nearest(root, p, root.p, true);
    }
// a nearest neighbor in the set to point p; null if the set is empty 
    
    
    
    private Point2D nearest(Node node, Point2D p, Point2D mp, boolean vert)
    {
        Point2D min = mp;
        
        if (node.p.distanceSquaredTo(p)<min.distanceSquaredTo(p))
        {min = node.p;}
        
        double cmp;
        if (vert){cmp = p.x() - node.p.x();}
        else     {cmp = p.y() - node.p.y();}
        
        if (cmp<0)
        {
            if (node.lb!=null)
            {min = nearest(node.lb, p, min, !vert);}
            if (node.rt!=null && 
                node.rt.rect.distanceSquaredTo(p)<min.distanceSquaredTo(p))
            {min = nearest(node.rt, p, min, !vert);}
        }
        else
        {
            if (node.rt!=null)
            {min = nearest(node.rt, p, min, !vert);}
            if (node.lb!=null && 
                node.lb.rect.distanceSquaredTo(p)<min.distanceSquaredTo(p))
            {min = nearest(node.lb, p, min, !vert);}            
        }
        return min;
    }
    
    
}
