/*
 * HashMap.java - example of Hash Map implementation
 *
 * Copyright (C) 2014 Paolo Rovelli
 *
 * Author: Paolo Rovelli <paolorovelli@yahoo.it>
 */

package org.epalrov.collections;

import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.Iterator;

/**
 * Hash table based implementation of the <tt>Map</tt> interface.
 *
 * <p>This implementation provides constant-time performance for the basic
 * operations (<tt>get</tt> and <tt>put</tt>), assuming the hash function
 * disperses the elements properly among the buckets.  Iteration over
 * collection views requires time proportional to the "capacity" of the
 * <tt>HashMap</tt> instance (the number of buckets) plus its size (the number
 * of key-value mappings). Thus, it's very important not to set the initial
 * capacity too high (or the load factor too low) if iteration performance is
 * important.
 */

public class HashMap<K,V>  implements Map<K,V>
{
	private Entry[] table;
	private int size;

	/**
	 * Constructs an empty <tt>HashMap</tt> with the specified capacity.
	 */
	public HashMap(int capacity) {
		if (capacity <= 0)
			throw new IllegalArgumentException(
				"Illegal capacity: " + capacity);
		table = new Entry[capacity];
		size = 0;
	}

	/**
	 * Constructs an empty <tt>HashMap</tt> with the default capacity (16).
	 */
	public HashMap() {
		this(16);
	}

	/**
	 * Constructs a new <tt>HashMap</tt> with the same mappings as the
	 * specified <tt>Map</tt>. The <tt>HashMap</tt> is created with a
	 * capacity sufficient to hold the mappings in the specified
	 * <tt>Map</tt>.
	 */
	public HashMap(Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException();	
	}

	/**
	 * Returns the number of key-value mappings in this map.
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns <tt>true</tt> if this map contains no key-value mappings.
	 */
	public boolean isEmpty() {
		return size == 0 ? true : false;
	}

	/**
	 * Returns the value to which the specified key is mapped,
	 * or {@code null} if this map contains no mapping for the key.
	 */
	public V get(Object key) {
		int hash = key == null ? 0 : key.hashCode();
		int index = key == null ? 0 : hash % table.length;

		// search for the specified key
		for (Entry<K,V> e = table[index]; e != null; e = e.next) {
			if (hash == e.hash && (key == e.key ||
			   (key != null && key.equals(e.key)))) {
				return e.value;
			}
		}
		return null;
	}

	/**
	 * Returns <tt>true</tt> if this map contains a mapping for the
	 * specified key.
	 */
	public boolean containsKey(Object key) {
		int hash = key == null ? 0 : key.hashCode();
		int index = key == null ? 0 : hash % table.length;

		// search for the specified key at the hashed index
		for (Entry<K,V> e = table[index]; e != null; e = e.next)
			if (hash == e.hash && (key == e.key ||
			   (key != null && key.equals(e.key))))
				return true;
		return false;
	}

	/**
	 * Returns <tt>true</tt> if this map maps one or more keys to the
	 * specified value.
	 */
	public boolean containsValue(Object value) {
		// search for the specified value in the whole map
		for (int index = 0; index < table.length; index++)
			for (Entry<K,V> e = table[index]; e != null; e = e.next)
				if (value == e.value ||
				   (value != null && value.equals(e.value)))
					return true;
		return false;
	}

	/**
	 * Associates the specified value with the specified key in this map.
	 * If the map previously contained a mapping for the key, the old
	 * value is replaced.
	 */
	public V put(K key, V value) {
		int hash = key == null ? 0 : key.hashCode();
		int index = key == null ? 0 : hash % table.length;

		// check if the key is already contained: update the value
		for (Entry<K,V> e = table[index]; e != null; e = e.next) {
			if (hash == e.hash && (key == e.key ||
			   (key != null && key.equals(e.key)))) {
				V oldValue = e.value;
				e.value = value;
				return oldValue;
			}
		}

		// insert the new mapping at the beginning of the list
		Entry<K,V> e = new Entry(hash, key, value, table[index]);
		table[index] = e;
		size++;
		return null;
	}

	/**
	 * Copies all of the mappings from the specified map to this map.
	 */
	public void putAll(Map<? extends K, ? extends V> m) {
		if (m.size() == 0)
			return;

		for (Iterator<? extends Map.Entry<? extends K, ? extends V>> i =
				m.entrySet().iterator(); i.hasNext(); ) {
			Map.Entry<? extends K, ? extends V> e = i.next();
			put(e.getKey(), e.getValue());
		}
	}

	/**
	 * Removes the mapping for the specified key from this map if present.
	 * Returns null if the HashMap contains no mapping for this key.
	 */
	public V remove(Object key) {
		int hash = key == null ? 0 : key.hashCode();
		int index = key == null ? 0 : hash % table.length;

		// search for the specified key
		Entry<K,V> p = table[index];
		for (Entry<K,V> e = table[index]; e != null; e = e.next) {
			if (hash == e.hash && (key == e.key ||
			   (key != null && key.equals(e.key)))) {
				if (p == e)
					table[index] = e.next;
				else
					p.next = e.next;
                		size--;
				return e.value;
			}
			p = e;
		}

		return null;
	}

	/**
	 * Removes all of the mappings from this map.
	 */
	public void clear() {
		for (int index = 0; index < table.length; index++)
			table[index] = null;
		size = 0;
	}

	static class Entry<K,V> implements Map.Entry<K,V> {
        	final int hash;
		final K key;
		V value;
	        Entry<K,V> next;

		Entry(int h, K k, V v, Entry<K,V> n) {
			hash = h;
			key = k;
			value = v;
			next = n;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public V setValue(V v) {
			V val = value;
            		value = v;
            		return val;
		}

		public boolean equals(Object o) {
			// same object reference
			if (o == this)
				return true;

			// check instance type (Map.Entry)
			if (!(o instanceof Map.Entry))
				return false;

			// check k,v pair
			K k1 = getKey();
			V v1 = getValue();
			K k2 = ((Entry<K,V>)o).getKey();
			V v2 = ((Entry<K,V>)o).getValue();
			if ((k1 == k2 || (k1 != null && k1.equals(k2))) &&
				(v1 == v2 || (v1 != null && v1.equals(v2))))
					return true;
			return false;
		}

		public int hashCode() {
			 return (key == null ? 0 : key.hashCode()) ^
				(value == null ? 0 : value.hashCode());
		}
	}

	// Views

	/**
	 * Returns a <tt>Set</tt> view of the keys contained in this map.
	 * The set is backed by the map, so changes to the map are
	 * reflected in the set, and vice-versa. 
	 * The returned set extends the java.util.AbstractSet, so
	 * it only needs to define the methods size() and iterator().
	 */
	public Set<K> keySet() {
		return new KeySet();
	}
	
	private class KeySet extends java.util.AbstractSet<K> {
		public int size() {
			return size;
		}

		public Iterator<K> iterator() {
			return new KeySetIterator();
		}
	}

	private class KeySetIterator extends HashIterator<K> {
		public K next() {
			return nextEntry().getKey();
		}
	}

	/**
	 * Returns a <tt>Collection</tt> view of the values contained in this
	 * map. The collection is backed by the map, so changes to the map are
	 * reflected in the collection, and vice-versa.
	 * The returned collection extends the java.util.AbstractCollection, so
	 * it only needs to define the methods size() and iterator().
	 */
	public Collection<V> values() {
		return new Values();
	}

	private class Values extends java.util.AbstractCollection<V> {
		public int size() {
			return size;
		}

		public Iterator<V> iterator() {
			return new ValuesIterator();
		}
	}

	private class ValuesIterator extends HashIterator<V> {
		public V next() {
			return nextEntry().getValue();
		}
	}

	/**
	 * Returns a <tt>Set</tt> view of the mappings contained in this map.
	 * The set is backed by the map, so changes to the map are
	 * reflected in the set, and vice-versa. 
	 * The returned set extends the java.util.AbstractSet, so
	 * it only needs to define the methods size() and iterator().
	 */
	public Set<Map.Entry<K,V>> entrySet() {
		return new EntrySet();
	}

	private class EntrySet extends java.util.AbstractSet<Map.Entry<K,V>> {
		public int size() {
			return size;
		}

		public Iterator<Map.Entry<K,V>> iterator() {
			return new EntrySetIterator();
		}
	}

	private class EntrySetIterator extends HashIterator<Map.Entry<K,V>> {
		public Map.Entry<K,V> next() {
			return nextEntry();
		}
	}

	/**
	 * Provides a skeletal implementation of a hash iterator over the
	 * elements in this hash map.
	 */
	private abstract class HashIterator<T> implements Iterator<T> {
		private int index;	
		private Entry<K,V> currEntry;
		private Entry<K,V> nextEntry;

		// initialize the iterator to the first entry.
		public HashIterator() {
			index = 0;
			currEntry = null;
			nextEntry = null;
			for ( ; index < table.length; index++)
				if (table[index] != null)
					nextEntry = table[index];
		}

		public boolean hasNext() {
			return nextEntry != null ? true : false;
		}

		// the next() method has to be implemeted for the specific type
		// T, by extending the abstract class, and making use of the
		// more generic nextEntry() method here below.
		public abstract T next();

		public Entry<K,V> nextEntry() {
			currEntry = nextEntry;
			if (nextEntry.next != null) {
				nextEntry = nextEntry.next;
			} else {
				nextEntry = null;
				for ( ; index < table.length; index++)
					if (table[index] != null)
						nextEntry = table[index];
			}

			return currEntry;
		}

		// since this hash map uses a sigle linked list to record its
		// mappings, it's not easy to remove the Entry without breaking
		// the list. the simpler solution is to call the remove()
		// method for the specified key
		public void remove() {
			HashMap.this.remove(nextEntry.getKey());
		}
	}

	// Comparison and hashing

	/**
	 * Compares the specified object with this map for equality.  Returns
	 * <tt>true</tt> if the given object is also a map and the two maps
	 * represent the same mappings.
	 */
	public boolean equals(Object o) {
		// trivial check
		if (o == this)
			return true;

		// check that it's an instance of Map
		if (!(o instanceof Map))
			return false;		
		Map<K,V> m = (Map<K,V>)o;

		// check that the size is the same */
		if (m.size() != size)
			return false;
		// and that each element is contained in the map */
		Set<Map.Entry<K,V>> s = entrySet();
		for (Iterator<Map.Entry<K,V>> i = s.iterator(); i.hasNext(); ) {
			Map.Entry<K,V> e = i.next();
			K key = e.getKey();
			V value = e.getValue();
			
			if (!m.containsKey(key))
				return false;
			if (!value.equals(m.get(key)))
				return false;
		}
		return true;
	}

	/**
	 * Returns the hash code value for this map.  The hash code of a map is
	 * defined to be the sum of the hash codes of each entry in the map's
	 * <tt>entrySet()</tt> view.
	 */
	public int hashCode() {
		int hash = 0;
		Set<Map.Entry<K,V>> s = entrySet();
		for (Iterator<Map.Entry<K,V>> i = s.iterator(); i.hasNext(); )
			hash += i.next().hashCode();

		return hash;
	} 

}
