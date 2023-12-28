// --== CS400 Fall 2023 File Header Information ==--
// Name: Karan Kapur
// Email: kkapur5@wisc.edu
// Group: F15
// TA: Anvay Grover
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {
  protected class Pair {

    public KeyType key;
    public ValueType value;

    public Pair(KeyType key, ValueType value) {
      this.key = key;
      this.value = value;
    }

  }
  private java.util.LinkedList<Pair>[] table;
  private int size;

  /**
   * Creates a hashtable map with the given capacity
   *
   * @param capacity
   */
  @SuppressWarnings("unchecked")
  public HashtableMap(int capacity){
    table = (java.util.LinkedList<Pair>[]) new LinkedList[capacity];

    for (int i = 0; i < capacity; i++) {
      table[i] = new LinkedList<Pair>();
    }
  }

  /**
   * Creates a hashtable map with default capacity of 32
   */
  public HashtableMap(){
    this(32);
  } // with default capacity = 32


  /**
   * Associates the specified value with the specified key in this map and replaces the old
   * mapping for the key if there was any
   * @param key   the key of the key,value pair
   * @param value the value that key maps to
   * @throws IllegalArgumentException
   */
  @Override
  public void put(KeyType key, ValueType value) throws IllegalArgumentException {
    if (key == null) {
      throw new IllegalArgumentException("key is null");
    }

    int index = Math.abs(key.hashCode()) % getCapacity();

    if (containsKey(key)) {
      throw new IllegalArgumentException("list already contains key");
    }


    table[index].add(new Pair(key, value));

    size++;

    if ((double) size / (double) getCapacity() >= 0.75) {
      enforceHashtableProperties();
    }

  }

  /**
   * Resizes and rehashes the hashtable.
   * Called when the current load factor exceeds a certain threshold, indicating that the
   * hashtable is becoming too full. It doubles the capacity of the hashtable and rehashes all
   * existing key-value pairs into the new table.
   */
  @SuppressWarnings("unchecked")
  private void enforceHashtableProperties() {
    int index;
    int newCapacity = getCapacity() * 2;
    java.util.LinkedList<Pair>[] tempTable =
        (java.util.LinkedList<Pair>[]) new LinkedList[newCapacity];

    for (int i = 0; i < newCapacity; i++) {
      tempTable[i] = new LinkedList<Pair>();
    }

    for (int i = 0; i < getCapacity(); i++) {
      for (Pair pair : table[i]) {
        index = Math.abs(pair.key.hashCode()) % newCapacity;
        tempTable[index].add(pair);
      }
    }

    table = tempTable;

  }

  /**
   * Checks if the given key is present in the map.
   *
   * @param key the key whose presence in the map is to be tested
   * @return true if this map contains a mapping for the specified key
   * @throws IllegalArgumentException if the key is null
   */
  @Override
  public boolean containsKey(KeyType key) {
    if (key == null) {
      throw new IllegalArgumentException("key is null");
    }

    int index = Math.abs(key.hashCode()) % getCapacity();
    for (Pair pair : table[index]) {
      if (pair.key.equals(key)) {
        return true;
      }
    }
    return false;
  }
  /**
   * Returns the value to which the specified key is mapped, or throws if this map contains no
   * mapping for the key.
   *
   * @param key the key whose associated value is to be returned
   * @return the value to which the specified key is mapped
   * @throws IllegalArgumentException if the key is null
   * @throws NoSuchElementException   if the key does not exist in the hashtable
   */
  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    if (key == null) {
      throw new IllegalArgumentException("key is null");
    }

    int index = Math.abs(key.hashCode()) % getCapacity();
    for (Pair pair : table[index]) {
      if (pair.key.equals(key)) {
        return pair.value;
      }
    }
    throw new NoSuchElementException("key does not exist in the hashtable");
  }

  /**
   * Removes the mapping for a key from this map if it is present.
   * Returns the value to which this map previously associated the key, or throws if the map
   * contained no mapping for the key.
   *
   * @param key key whose mapping is to be removed from the map
   * @return the previous value associated with key, or null if there was no mapping for key
   * @throws IllegalArgumentException if the key is null
   * @throws NoSuchElementException   if the key does not exist in the hashtable
   */
  @Override
  public ValueType remove(KeyType key) throws NoSuchElementException {
    if (key == null) {
      throw new IllegalArgumentException("key is null");
    }
    int index = Math.abs(key.hashCode()) % getCapacity();
    LinkedList<Pair> pairs = table[index];

    for (int i = 0; i < pairs.size(); i++) {
      Pair pair = pairs.get(i);

      if (pair.key.equals(key)) {
        ValueType returnVal = pair.value;
        pairs.remove(i);
        size--;
        return returnVal;
      }
    }
    throw new NoSuchElementException("key does not exist in the hashtable");
  }
  /**
   * Clears the map, removing all key-value pairs.
   */
  @Override
  public void clear() {
    for(int i = 0; i < getCapacity(); i++) {
      table[i] = new LinkedList<Pair>();
    }
    size = 0;
  }

  /**
   * Returns the number of key-value mappings in the map.
   *
   * @return the number of key-value mappings in the map
   */
  @Override
  public int getSize() {
    return size;
  }
  /**
   * Returns the current capacity of the hashtable (i.e., the number of buckets).
   *
   * @return the current capacity of the hashtable
   */
  @Override
  public int getCapacity() {
    return table.length;
  }

  /**
   * Tests the put() and get() method
   */
    @Test
    public void testPutAndGet() {
      HashtableMap<String, Integer> map = new HashtableMap<>();
      map.put("One",1);
      map.put("Two",2);
      Assertions.assertEquals(1, map.get("One"));
      Assertions.assertEquals(2, map.get("Two"));
    }
  /**
   * Tests the containsKey() method
   */
    @Test
    public void testContainsKey() {
      HashtableMap<String, Integer> map = new HashtableMap<>();
      map.put("One",1);
      Assertions.assertTrue(map.containsKey("One"));
      Assertions.assertFalse(map.containsKey("Two"));
    }
  /**
   * Tests the remove() method
   */
    @Test
    public void testRemove() {
      HashtableMap<String, Integer> map = new HashtableMap<>();
      map.put("One",1);
      map.put("Two",2);
      map.remove("One");
      Assertions.assertThrows(NoSuchElementException.class, () -> map.get("One"));
      Assertions.assertEquals(2, map.get("Two"));
    }
  /**
   * Tests the clear() and getSize() methods
   */
    @Test
    public void testClearAndSize() {
      HashtableMap<String, Integer> map = new HashtableMap<>();
      map.put("One",1);
      map.put("Two",2);
      map.clear();
      Assertions.assertEquals(0, map.getSize());
    }
  /**
   * Tests the throwing of Illegal Argument Exception
   */
    @Test
    public void testIllegalArgumentException() {
      HashtableMap<String, Integer> map = new HashtableMap<>();
      map.put("One",1);
      Assertions.assertThrows(IllegalArgumentException.class, () -> map.put("One",2));
    }
}
