import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints 
{
    
    
// finds all line segments containing 4 points    
    public BruteCollinearPoints(Point[] points)  
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
        double s1;
        double s2;
        double s3;
        for (int p = 0; p < copy.length-3; p++)
        {
            for (int q = p+1; q < copy.length-2; q++)
            {
                s1 = copy[p].slopeTo(copy[q]);
                for (int r = q+1; r < copy.length-1; r++)
                { 
                    s2 = copy[p].slopeTo(copy[r]);
                    if (s1 != s2){continue;}
                    for (int s = r+1; s < copy.length; s++)
                    {
                        s3 = copy[p].slopeTo(copy[s]);
                        if (s1 != s3) {continue;}
                        LineSegment l = new LineSegment(copy[p],copy[s]);
                        lineSeg.add(l);
                    }
                }
            }
        }
    }
// finds all line segments containing 4 points
    
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
