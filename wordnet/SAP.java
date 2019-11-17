import  edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import  edu.princeton.cs.algs4.Digraph;
import  edu.princeton.cs.algs4.In;
import  edu.princeton.cs.algs4.StdIn;
import  edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.lang.Iterable;

public class SAP {

   // do unit testing of this class
   public static void main(String[] args)
   {      
        In in = new In(args[0]);
        Digraph G = new Digraph(in);//Digraph(In in)
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) 
        {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }       
   }
   // do unit testing of this class       
    
   
   private final Digraph graph;
   
   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G)
   { 
       assert G!=null;
       this.graph = new Digraph(G);
   }
   // constructor takes a digraph (not necessarily a DAG)
       
       
       

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w)
   {
       if (v<0||v>=graph.V()||w<0||w>=graph.V())
       {throw new java.lang.IllegalArgumentException();}
       BreadthFirstDirectedPaths V = new BreadthFirstDirectedPaths(graph,v);
       BreadthFirstDirectedPaths W = new BreadthFirstDirectedPaths(graph,w);
       int min = Integer.MAX_VALUE;
       for (int i=0; i<graph.V(); i++)
       {
           if (V.hasPathTo(i)&&W.hasPathTo(i))
           {
               int dist = V.distTo(i)+W.distTo(i);
               if (dist<min) {min = dist;}
           }
       }
       if (min == Integer.MAX_VALUE){min = -1;}
       return min;
   }
   // length of shortest ancestral path between v and w; -1 if no such path
   
   
   

   // a common ancestor of v and w 
   //that participates in a shortest ancestral path; 
   //-1 if no such path
   public int ancestor(int v, int w)
   {
       if (v<0||v>=graph.V()||w<0||w>=graph.V())
       {throw new java.lang.IllegalArgumentException();}
       BreadthFirstDirectedPaths V = new BreadthFirstDirectedPaths(graph,v);
       BreadthFirstDirectedPaths W = new BreadthFirstDirectedPaths(graph,w);
       int min = Integer.MAX_VALUE;
       int ancestor = -1;
       for (int i=0; i<graph.V(); i++)
       {
           if (V.hasPathTo(i)&&W.hasPathTo(i))
           {
               int dist = V.distTo(i)+W.distTo(i);
               if (dist<min) {min = dist; ancestor = i;}
           }
       }
       return ancestor;       
   }
   // a common ancestor of v and w 
   //that participates in a shortest ancestral path; 
   //-1 if no such path       
       
   
   
   private void check(Iterable<Integer> v)
   {
       for (int id:v)
       {
           if (id<0||id>=graph.V())
           {throw new java.lang.IllegalArgumentException();}
       }
   }
       

   // length of shortest ancestral path 
   //between any vertex in v and any vertex in w; 
   //-1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w)
   {
       if (v==null||w==null)
       {throw new java.lang.IllegalArgumentException();}
       check(v);
       check(w);
       BreadthFirstDirectedPaths V = new BreadthFirstDirectedPaths(graph,v);
       BreadthFirstDirectedPaths W = new BreadthFirstDirectedPaths(graph,w);
       int min = Integer.MAX_VALUE;
       for (int i=0; i<graph.V(); i++)
       {
           if (V.hasPathTo(i)&&W.hasPathTo(i))
           {
               int dist = V.distTo(i)+W.distTo(i);
               if (dist<min) {min = dist;}
           }
       }
       if (min == Integer.MAX_VALUE){min = -1;}
       return min;       
   }
   // length of shortest ancestral path 
   //between any vertex in v and any vertex in w; 
   //-1 if no such path       
       
       

       
   // a common ancestor 
   //that participates in shortest ancestral path; 
   //-1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
   {
       if (v==null||w==null)
       {throw new java.lang.IllegalArgumentException();}
       check(v);
       check(w);
       BreadthFirstDirectedPaths V = new BreadthFirstDirectedPaths(graph,v);
       BreadthFirstDirectedPaths W = new BreadthFirstDirectedPaths(graph,w);
       int min = Integer.MAX_VALUE;
       int ancestor = -1;
       for (int i=0; i<graph.V(); i++)
       {
           if (V.hasPathTo(i)&&W.hasPathTo(i))
           {
               int dist = V.distTo(i)+W.distTo(i);
               if (dist<min) {min = dist; ancestor = i;}
           }
       }
       return ancestor;        
   }
   // a common ancestor 
   //that participates in shortest ancestral path; 
   //-1 if no such path
