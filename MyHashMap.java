import java.util.Iterator;

public class MyHashMap<K, V>  implements MapInterface<K,V>
{
	  protected MapEntry[] map;
	  
	  protected final int DEFCAP = 1000;     // default capacity
	  protected final double DEFLOAD = 0.75; // default load
	  
	  protected int origCap;  // original capacity
	  protected int currCap;  // current capacity
	  protected double load;
	  
	  protected int numPairs = 0;    // number of pairs in this map
	   
	  public MyHashMap() 
	  {
	    map =  new MapEntry[DEFCAP];
	    origCap = DEFCAP;
	    currCap = DEFCAP;
	    load = DEFLOAD;
	  }

	  public MyHashMap(int initCap, double initLoad) 
	  {
	    map = new MapEntry[initCap];
	    origCap = initCap;
	    currCap = initCap;
	    load = initLoad;
	  }

	  private void enlarge()
	  // Increments the capacity of the map by an amount 
	  // equal to the original capacity.
	  {
	    // create a snapshot iterator of the map and save current size
	    Iterator<MapEntry<K,V>> i = iterator();
	    int count = numPairs;

	    // create the larger array and reset variables
	    map = new MapEntry[currCap + origCap];
	    currCap = currCap + origCap; 
	    numPairs = 0;
	    
	    // put the contents of the current map into the larger array
	    MapEntry entry;
	    for (int n = 1; n <= count; n++)
	    {
	      entry = i.next();
	      this.put((K)entry.getKey(), (V)entry.getValue());
	    }
	  }
	  
	 public V put(K k, V v) {
	  // If an entry in this map with key k already exists then the value 
	  // associated with that entry is replaced by value v and the original
	  // value is returned; otherwise, adds the (k, v) pair to the map and
	  // returns null.
	  
		  if (k == null)
		      throw new IllegalArgumentException("Maps do not allow null keys.");

		  //create new MapEntry object	
		  MapEntry<K,V> entry = new MapEntry<K,V>(k,v); //give generic constructor in <>
		  //and input value in ()
		  
		  //use hashing and compression to find array location for entry
		  int location = Math.abs(k.hashCode()) % currCap;
		  
		  //use linear probing to find an available location
		  while(map[location] != null && !(map[location].getKey().equals(k))) {
			  location = (location + 1) % currCap;
		  }
		  
		  if(map[location] == null) {
			  map[location] = entry;
			  numPairs++;
			  if((float)numPairs/currCap > load) {
				  enlarge();
			  }
			  return null;
		  } else {
			  V temp = (V) map[location].getValue();
			  map[location] = entry;
			  return temp;
		  }
		  //check if key entry is already in map
		  //if so use that same location
	  }

	  public V get(K k)
	  // If an entry in this map with a key k exists then the value associated 
	  // with that entry is returned; otherwise null is returned.
	  {
		  if (k == null)
		      throw new IllegalArgumentException("Maps do not allow null keys.");
		  
		  //use hashing and compression to find array location for entry
		  int location = Math.abs(k.hashCode()) % currCap;
		  
		  //use linear probing to find the location
		  while(map[location] != null && !(map[location].getKey().equals(k))) {
			  location = (location + 1) % currCap;
		  }
		  
		  //if map location is null return null
		  if(map[location] == null) {
			  return null;
		  } else {
			  return (V) map[location].getValue();
		  }
		  //else return the value at the location 
	  }

	  public V remove(K k)
	//If an entry in this map with a key k exists then the value associated 
	  // with that entry is returned and the entry is removed; otherwise null is returned.
	  {
	    	    
	    //If you have time to implement here is the algorithm	 
			  if (k == null)
			      throw new IllegalArgumentException("Maps do not allow null keys.");
			  
			  //use hashing and compression to find array location for entry
			  int location = Math.abs(k.hashCode()) % currCap;
			  //use linear probing to find the location
			  while(map[location] != null && !(map[location].getKey().equals(k))) {
				  location = (location + 1) % currCap;
			  }
			  //if map location is null return null
			  if(map[location] == null) {
				  return null;
			  } else
			  //store the value in a temporary variable
			  {
				  V temp = (V) map[location];
			  }
			  //set the map at this location to null
			  map[location] = null;
			  //rehash all values 
			  	//1. Start with location + 1
			  int rehash = location;
			  rehash = (rehash + 1) % currCap;
			  	//2. While location is not null
			 
			  	         //a. Hold onto copy of key pairs (MapEntry obj)
			  	         //b. Set location to null
			  			 //c. decrement size 
			  			 //d. Put key value pair back in map using put method
			  
			  //decrement size
			  
			  //return value	  
			  return null;
	  }    

	  public boolean contains(K k)
	  // Returns true if an entry in this map with key k exists;
	  // Returns false otherwise.
	  {
	    if (k == null)
	      throw new IllegalArgumentException("Maps do not allow null keys.");

	    //use hashing and compression to find array location for entry
	    int location = Math.abs(k.hashCode()) % currCap;
	  //use loop from location until found or null
		 while(map[location] != null) {
			 if(map[location].getKey().equals(k)) {
				 return true;
			 }
			  location = (location + 1) % currCap;
		  }
	    //if exit from loop return false not found
		 return false;
	  }
	   
	  public boolean isEmpty()
	  // Returns true if this map is empty; otherwise, returns false.
	  {
	    return (numPairs == 0);
	  }
	  
	  public boolean isFull()
	  // Returns true if this map is full; otherwise, returns false.
	  {
	    return false;  // An HMap is never full
	  }

	  public int size()
	  // Returns the number of entries in this map.
	  {
	    return numPairs;
	  }

	  private class MapIterator implements Iterator<MapEntry<K,V>>
	  // Provides a snapshot Iterator over this map.
	  // Remove is not supported and throws UnsupportedOperationException.
	  {
	    int listSize = size();
	    private MapEntry[] list = new MapEntry[listSize];
	    private int previousPos = -1; // previous position returned from list
	      
	    public MapIterator()
	    {
	      int next = -1;
	      for (int i = 0; i < listSize; i++)
	      {
	        next++;
	        while (map[next] == null)
	          next++;
	        list[i] = map[next];
	      }
	    }
	      
	    public boolean hasNext()
	    // Returns true if the iteration has more entries; otherwise returns false.
	    {
	      return (previousPos < (listSize - 1)) ;
	    }
	      
	    public MapEntry<K,V> next()
	    // Returns the next entry in the iteration.
	    // Throws NoSuchElementException - if the iteration has no more entries
	    { 
	      if (!hasNext())
	        throw new IndexOutOfBoundsException("illegal invocation of next " + 
	                           " in HMap iterator.\n");
	      previousPos++;
	      return list[previousPos];
	    }

	    public void remove()
	    // Throws UnsupportedOperationException.
	    // Not supported. Removal from snapshot iteration is meaningless.
	    {
	      throw new UnsupportedOperationException("Unsupported remove attempted on " 
	                                            + "HMap iterator.\n");
	    }
	  }

	  public Iterator<MapEntry<K,V>> iterator()
	  // Returns a snapshot Iterator over this map.
	  // Remove is not supported and throws UnsupportedOperationException.

	  {
	    return new MapIterator();
	  }
}
