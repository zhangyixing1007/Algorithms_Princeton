import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> 
{
// unit testing (optional)    
    public static void main(String[] args)
    {}
// unit testing (optional)    
    
    
    private class Node
    {
        private Item item;
        private Node prev;
        private Node next;
        
        private Node()
        {         
        }
        
    }
    
    private Node first;
    private Node last;
    private int size;
    
    
// construct an empty deque    
    public Deque()   
    {
        first = null;
        last = null;
        size = 0;
    }
// construct an empty deque
    
    
    
    
// is the deque empty?    
    public boolean isEmpty() 
    {return size==0;}
// is the deque empty?
    
    
    
    
// return the number of items on the deque    
    public int size()
    {return size;}
// return the number of items on the deque
    
    
    
    
// add the item to the front    
    public void addFirst(Item item)  
    {
        if (item == null)
        {throw new java.lang.IllegalArgumentException();}
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldfirst;
        if (oldfirst == null)
        {last = first;}
        else
        {oldfirst.prev = first;}
        size = size+1;           
    }
// add the item to the front
    
    
    
    
// add the item to the end    
    public void addLast(Item item) 
    {
        if (item == null)
        {throw new java.lang.IllegalArgumentException();}
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (oldlast == null)
        {first = last;}
        else
        {oldlast.next = last;}
        size = size+1;              
    }
// add the item to the end
    
    
    
    
// remove and return the item from the front    
    public Item removeFirst() 
    {
        if(isEmpty())
        {throw new java.util.NoSuchElementException();}
        Item item = first.item;
        first = first.next;
        if (first==null)
        {last = first;}
        else
        {first.prev = null;}
        size = size-1;
        return item;
    }
// remove and return the item from the front
    
    

// remove and return the item from the end    
    public Item removeLast() 
    {
        if(isEmpty())
        {throw new java.util.NoSuchElementException();}
        Item item = last.item;
        last = last.prev;
        if (last==null)
        {first = last;}
        else
        {last.next = null;}
        size = size-1;
        return item;        
    }
// remove and return the item from the end
    
    
    
    
// return an iterator over items in order from front to end    
    public Iterator<Item> iterator()  
    {return new DequeIterator();}
    
    
    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first;
        
        public boolean hasNext()
        {return current!=null;}
        
        public Item next()
        {
            if (!hasNext())
            {throw new java.util.NoSuchElementException();}
            Item item = current.item;
            current = current.next;
            return item;
        }
        
        public void remove()
        {throw new java.lang.UnsupportedOperationException();}      
    }
// return an iterator over items in order from front to end
   
}
