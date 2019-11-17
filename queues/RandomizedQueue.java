import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> 
{
// unit testing (optional)
    public static void main(String[] args)  
    {}
// unit testing (optional)
    
    
    
    
// construct an empty randomized queue       
    public RandomizedQueue()    
    {
        items = (Item[]) new Object[1];
        size = 0;
    }
// construct an empty randomized queue
    
    
    private Item[] items;
    private int size;
    
    
    
// is the randomized queue empty?        
    public boolean isEmpty()  
    {return size==0;}
// is the randomized queue empty?
    
    
    
    
// return the number of items on the randomized queue        
    public int size()  
    {return size;}
// return the number of items on the randomized queue
    
    
    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i=0; i<size; i++)
        {copy[i] = items[i];}
        items = copy;
    }
    
    
// add the item        
    public void enqueue(Item item)  
    {
        if (item == null)
        {throw new IllegalArgumentException();}
        if (size == items.length)
        {resize(2*size);}
        items[size++] = item;
    }
// add the item
    
    
    
    
// remove and return a random item        
    public Item dequeue()     
    {
        if (isEmpty())
        {throw new java.util.NoSuchElementException();}
        if (size == items.length/4)
        {resize(size*2);}
        int random = StdRandom.uniform(size);
        Item item = items[random];
        items[random] = items[--size];
        return item;
    }
// remove and return a random item
    
    
    
    
// return a random item (but do not remove it)        
    public Item sample()       
    {
        if (isEmpty())
        {throw new java.util.NoSuchElementException();}  
        return items[StdRandom.uniform(size)];
    }
// return a random item (but do not remove it)
    
    
    
    
// return an independent iterator over items in random order        
    public Iterator<Item> iterator()  
    {return new RandomizedQueueIterator();}
    
    
    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private int[] random;
        private int current;
        
        public RandomizedQueueIterator()
        {
            random = new int[size];
            for (int i=0; i<size; i++)
            {random[i] = i;}
            StdRandom.shuffle(random);
            current = 0;
        }
        
        public boolean hasNext()
        {return (current<size);}
        
        
        public Item next()
        {
            if (!hasNext())
            {throw new java.util.NoSuchElementException();}
            
            return items[random[current++]];
        }
        
        public void remove()
        {throw new java.lang.UnsupportedOperationException();}         
        
    }
       
// return an independent iterator over items in random order
    
}
