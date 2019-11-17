import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints 
{
// finds all line segments containing 4 or more points    
    public FastCollinearPoints(Point[] points)   
    {
        if (points==null)
        {throw new IllegalArgumentException();}
        for (int i = 0; i < points.length; i++)
        {
            if (points[i]==null)
            {throw new IllegalArgumentException();}
        }
        
        Point[] copy = points.clone();
        Arrays.sort(copy);
        if (checkDuplicate(copy))
        {throw new IllegalArgumentException();}  
        
        lineSeg = new ArrayList<LineSegment>();
        
        for (int i = 0; i < copy.length-3; i++)
        {
            Arrays.sort(copy);
            Arrays.sort(copy, copy[i].slopeOrder());
            
            Point p = copy[0];
            for (int first = 1, last = 2; last<copy.length; last++)
            {
                while (last<copy.length && 
                       Double.compare(p.slopeTo(copy[first]),
                                      p.slopeTo(copy[last]))==0)
                {last++;}
                if (last-first>=3 && p.compareTo(copy[first])<0)
                {lineSeg.add(new LineSegment(p,copy[last-1]));}
                first = last;
            }
        }
    }
// finds all line segments containing 4 or more points
    
    
    private ArrayList<LineSegment> lineSeg;
    
    private boolean checkDuplicate(Point[] points)
    {
        for (int i = 0; i < points.length-1; i++)
        {
            if (points[i].compareTo(points[i+1])==0)
            {return true;}
        }
        return false;
    }
    
    
// the number of line segments   
    public           int numberOfSegments()
    {return lineSeg.size();}
// the number of line segments
    
    
// the line segments   
    public LineSegment[] segments()
    {return lineSeg.toArray(new LineSegment[lineSeg.size()]);}
// the line segments    
    
}
