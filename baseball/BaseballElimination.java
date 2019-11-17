import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;

public class BaseballElimination
{ 
    
    public static void main(String[] args) 
    {
        BaseballElimination division = new BaseballElimination("teams5.txt");
        for (String team : division.teams()) 
        {
            if (division.isEliminated(team)) 
            {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) 
                {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else 
            {
                StdOut.println(team + " is not eliminated");
            }
        }        
        
    }    
    
    
    private HashMap<String, Integer> teams;
    private String[] name;
    private int[] w,l,r;
    private int[][] g;
    private FlowNetwork fn;
    private ArrayList<String> R;
    private boolean elim;
    private boolean marked;
    private int subset;
    private int n; 
    private FordFulkerson ff;
        
    
    
    // create a baseball division 
    // from given filename in format specified below
    public BaseballElimination(String filename) 
    {
        if (filename == null)
        {throw new java.lang.IllegalArgumentException();}
            
        In input = new In(filename);

        n = input.readInt();
        teams = new HashMap<String, Integer>();
        name = new String[n];//name for teams
        w = new int[n];//win
        l = new int[n];//lose
        r = new int[n];//remain
        g = new int[n][n];//games left to play between team i and j
        
        
        for (int i=0; i<n; i++)
        {
            String s = input.readString();
            teams.put(s,i);
            name[i]=s;

            w[i] = input.readInt();
            l[i] = input.readInt();           
            r[i] = input.readInt();
            
            for (int j=0; j<n; j++)
            {g[i][j] = input.readInt();}                
        }
    }
    // create a baseball division 
    // from given filename in format specified below
    
    
    
    
    // number of teams
    public              int numberOfTeams() 
    {return n;}
    // number of teams

    
    
    
    // all teams    
    public Iterable<String> teams()  
    {return teams.keySet();}
    // all teams
       
    
        
        
    // number of wins for given team    
    public              int wins(String team)    
    {
        if (teams.containsKey(team)==false)
        {throw new java.lang.IllegalArgumentException();}
        return w[teams.get(team)];
    }
    // number of wins for given team
       
        
        
        
    // number of losses for given team    
    public              int losses(String team)
    {
        if (teams.containsKey(team)==false)
        {throw new java.lang.IllegalArgumentException();}        
        return l[teams.get(team)];
    }
    // number of losses for given team
        
        
        
    
    // number of remaining games for given team    
    public              int remaining(String team)   
    {
        if (teams.containsKey(team)==false)
        {throw new java.lang.IllegalArgumentException();}        
        return r[teams.get(team)];
    }
    // number of remaining games for given team
        
        
        
    
    // number of remaining games between team1 and team2    
    public              int against(String team1, String team2)
    {
        if (teams.containsKey(team1)==false||
            teams.containsKey(team2)==false)
        {throw new java.lang.IllegalArgumentException();}        
        return g[teams.get(team1)][teams.get(team2)];
    }
    // number of remaining games between team1 and team2
        
        
    
        
    // is given team eliminated?        
    public          boolean isEliminated(String team)
    {
        if (teams.containsKey(team)==false)
        {throw new java.lang.IllegalArgumentException();} 
        
        int x = teams.get(team);
        marked = false;
        for (int i =0; i<n; i++)
        {
            if ((i!=x)&&(w[x]+r[x]- w[i]<0))
            {
                marked = true;
                subset = i;
                return true;
            }
        }
     
        FF(team);
        return elim;         
    }
    // is given team eliminated?
    
    
    
    
    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team)
    {
        if (teams.containsKey(team)==false)
        {throw new java.lang.IllegalArgumentException();} 
        
        
        R = new ArrayList<String>();
        if (isEliminated(team) == false)
        {return null;}
        else
        {
            if (marked)
            {R.add(name[subset]);}
            else
            {
                for (String item : teams.keySet())
                {
                    int id = teams.get(item);   
                    if (ff.inCut(id)== true&&id!=teams.get(team))
                    {R.add(item);}
                }               
            }            
        }
        return R;
    }
    // subset R of teams that eliminates given team; null if not eliminated

    
    
    private void FF(String team)
    {
        int n = numberOfTeams();
        fn = new FlowNetwork(n*n/2-n/2+2);
        
        int x = teams.get(team);
        int totalR = 0;
        
        for (int i=0; i<n; i++)
        {
            if (i!=x)
            {
                FlowEdge e = new FlowEdge(i,n*n/2-n/2+1,w[x]+r[x]- w[i]);                
                fn.addEdge(e);
            }
        }
        
        for (int id = n; id<n*n/2-n/2+1;)
        {
            for (int i = 1; i<n; i++)
            {
                for (int j=0; j<i; j++)
                {
                    if((i!=x)&&(j!=x))
                    {
                        totalR = totalR + g[i][j];
                        FlowEdge e1 = new FlowEdge(x,id,g[i][j]);
                        fn.addEdge(e1);
                        FlowEdge e2 = new FlowEdge(id,i,Double.POSITIVE_INFINITY);
                        fn.addEdge(e2);
                        FlowEdge e3 = new FlowEdge(id,j,Double.POSITIVE_INFINITY);
                        fn.addEdge(e3);
                        id = id+1;
                    }
                }
            }
        }
        
        ff = new FordFulkerson(fn, x, n*n/2-n/2+1);
        
        if (totalR == (int)ff.value())
        {elim = false;}
        else
        {elim = true;}
        
    }
}
