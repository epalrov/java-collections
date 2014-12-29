/*
 * TreeSet.java - example of Tree Set implementation
 *
 * Copyright (C) 2014 Paolo Rovelli
 *
 * Author: Paolo Rovelli <paolorovelli@yahoo.it>
 */

package org.epalrov.collections;

import java.util.Set;
import java.util.Collection;
import java.util.Iterator;

/**
 * Unbalanced tree implementation of the <tt>Set</tt> interface. 
 *
 * All of the operations perform as could be expected for an unbalanced binary
 * tree. Balancing as a Red-Black binary tree is strightforward.
 *
 * Note that this implementation is not synchronized. If multiple threads
 * access a linked list concurrently, and at least one of the threads modifies
 * the list structurally, it must be synchronized externally. This is typically
 * accomplished by synchronizing on some object that naturally encapsulates
 * the list.
 */

public class TreeSet<E> implements Set<E>
{
	private Entry<E> root;
	private int size;

        /**
         * Constructs an empty <tt>TreeSet</tt>
         */
	public TreeSet() {
		root = null;
		size = 0;
	}

	// Query Operations

	/**
	 * Returns the number of elements in this set.
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns <tt>true</tt> if this set contains no elements.
	 */
	public boolean isEmpty() {
		return size == 0 ? true : false;
	}

	/**
	 * Returns <tt>true</tt> if this set contains the specified element.
	 */
	public boolean contains(Object o) {
		int cmp = 0;
		Entry<E> e = root;

		// a null element is not comparable
		if (o == null)
			throw new NullPointerException();

		// walk the tree looking for the element if present
		while (e != null) {
			cmp = ((Comparable<? super E>)o).compareTo(e.elem);
			if (cmp < 0)
				e = e.left;
			else if (cmp > 0)
				e = e.right;
			else // (cmp == 0)
				return true;
		}
		return false;
	}

	/**
	 * Returns an iterator over the elements in this set.
	 */
	public Iterator<E> iterator() {
		return new TreeSetIterator();
	}

	private class TreeSetIterator implements Iterator<E> {
		private Entry<E> currEntry;
		private Entry<E> nextEntry;

		TreeSetIterator() {
			currEntry = null;
			nextEntry = getFirstEntry();
		}

		public boolean hasNext() {
			return nextEntry != null ? true : false;
		}

		public E next() {
			if (nextEntry == null)
				throw new IllegalStateException();
			currEntry = nextEntry;
			nextEntry = getNextEntry(currEntry);
			return currEntry.elem;
		}
        
		public void remove() {
			if (currEntry == null)
				throw new IllegalStateException();
			removeEntry(currEntry);
			currEntry = null;
			size--;
		}
	}

	/**
	 * Returns an array containing all of the elements in this set.
	 */
	public Object[] toArray() {
		Object[] o = new Object[size];
		Iterator<E> it = new TreeSetIterator();
		for (int i = 0; i < size; i++)
			o[i] = it.hasNext() ? it.next() : null;

		return o;
	}

	/**
	 * Returns an array containing all of the elements in this set; the
	 * runtime type of the returned array is that of the specified array.
	 */
	public <T> T[] toArray(T[] a) {
                /* realloc */
                if (a.length < size)
                        a = (T[])java.lang.reflect.Array.newInstance(
                                a.getClass().getComponentType(), size);

                Iterator<E> it = new TreeSetIterator();
                for (int i = 0; i < size; i++)
                        a[i] = it.hasNext() ? (T)it.next() : null; 
                
		return a;
	}

	// Modification Operations

	/**
	 * Adds the specified element to this set if it is not already present.
	 * If this set already contains the element, the call leaves the set
	 * unchanged and returns <tt>false</tt>.
	 */
	public boolean add(E elem) {
		int cmp = 0;
		Entry<E> e = root;
		Entry<E> parent = e;

		// a null element is not comparable
		if (elem == null)
			throw new NullPointerException();

		// add the new element to an empty set
		if (e == null) {
			root = new Entry<E>(elem, null);
			size++;
			return true;
		}

		// walk the tree looking for the element if already present
		while (e != null) {
			parent = e;
			cmp = ((Comparable<? super E>)elem).compareTo(e.elem);
			if (cmp < 0)
				e = e.left;
			else if (cmp > 0)
				e = e.right;
			else // (cmp == 0)
				return false;
		}

		// add the new entry as leaf of the current parent position
		e = new Entry<E>(elem, parent);
		if (cmp < 0)
			parent.left = e;
		else
			parent.right = e;
		size++;
		return true;
	}

	/**
	 * Removes the specified element from this set if it is present.
	 */
	public boolean remove(Object o) {
		int cmp = 0;
		Entry<E> e = root;

		// a null element is not comparable
		if (o == null)
			throw new NullPointerException();

		// walk the tree looking for the element if present
		while (e != null) {
			cmp = ((Comparable<? super E>)o).compareTo(e.elem);
			if (cmp < 0) {
				e = e.left;
			} else if (cmp > 0) { 
				e = e.right;
			} else { // (cmp == 0)
				removeEntry(e);
        			size--;
				return true;
			}
		}

		// element not found
		return false;
	}

	// Bulk Operations

	/**
	 * Returns <tt>true</tt> if this set contains all of the elements of
	 * the specified collection.
	 */
	public boolean containsAll(Collection<?> c) {
                Iterator<?> i = c.iterator();
                while (i.hasNext())
                        if (!contains(i.next()))
                                return false;

                return true;
	}

	/**
	 * Adds all of the elements in the specified collection to this set if
	 * they're not already present.
	 */
	public boolean addAll(Collection<? extends E> c) {
                Iterator<? extends E> i = c.iterator();
                while (i.hasNext())
                        if (!add(i.next()))
				return false;

                return true;
	}

	/**
	 * Retains only the elements in this set that are contained in the
	 * specified collection (optional operation).
	 */
	public boolean retainAll(Collection<?> c) {
                Iterator<?> i = c.iterator();
                while (i.hasNext()) {
			Object o = i.next();
			if (!contains(o))
				remove(o);
		}
                return true;
	}

	/**
	 * Removes from this set all of its elements that are contained in the
	 * specified collection.
	 */
	public boolean removeAll(Collection<?> c) {
                Iterator<?> i = c.iterator();
                while (i.hasNext()) {
			Object o = i.next();
			if (contains(o))
				remove(o);
		}
                return true;
	}

	/**
	 * Removes all of the elements from this set.
	 */
	public void clear() {
		root = null;
		size = 0;
	}

	// Comparison and hashing

	/**
	 * Compares the specified object with this set for equality.
	 */
	public boolean equals(Object o) {
                // same object reference
                if (o == this)
                        return true;

                // check instance type (Set)
                if (!(o instanceof Set))
                        return false;

                // check all elements
                Iterator<E> i = iterator();
                Iterator<?> j = ((Set<?>) o).iterator();
                while (i.hasNext() && j.hasNext()) {
                        E elem = i.next();
                        Object obj = j.next();
                        if (!((obj != null && obj.equals(elem)) ||
					(obj == null && elem == null)))
                                return false;
                }

                // check size
                return !(i.hasNext() || j.hasNext());
	}

	/**
	 * Returns the hash code value for this set. The hash code of a set is
	 * defined to be the sum of the hash codes of the elements in the set,
	 * where the hash code of a <tt>null</tt> element is defined to be zero.
	 */
	public int hashCode() {
                int hash = 0;
                Iterator<E> i = iterator();
                while (i.hasNext()) {
                        E elem = i.next();
                        hash += (elem == null ? 0 : elem.hashCode());
                }

                return hash;
	}

	/**
	 * Unbalanced Binary Tree implementation.
	 */
	private static class Entry<E> {
		E elem;
		Entry<E> parent;
		Entry<E> left;
		Entry<E> right;

		Entry(E e, Entry<E> p) {
			elem = e;
			parent = p;
			left = null;
			right = null;
		}
	}

	private Entry<E> getFirstEntry() {
		Entry<E> e = root;
		if (e != null)
			while (e.left != null)
				e = e.left;
		return e;
	}

	private Entry<E> getNextEntry(Entry<E> e) {
		Entry<E> n;

        	if (e == null)
			return null;

		if (e.right == null) {
			Entry<E> c = e;
			n = e.parent;
			while (n != null && c == n.right) {
				c = n;
				n = n.parent;
			}
		} else {
			n = e.right;
			while (n.left != null)
				n = n.left;
		}
		return n;
	}

	private void removeEntry(Entry<E> e) {
        	if (e == null)
			return;

		// Entry with two children:
		// - replace the specified entry with its successor by copying.
		// - note that a successor always exixts!
		// - note also that the replacement alghoritm continue!
		if (e.left != null && e.right != null) {
			Entry<E> n = getNextEntry(e);
			e.elem = n.elem;
			e = n;
		}

		// Entry with one child or no children
		Entry<E> c = (e.left != null ? e.left : e.right);
		if (c != null)
			c.parent = e.parent;

		if (e.parent == null)
			root = c;
		else if (e == e.parent.left)
			e.parent.left = c;
		else
			e.parent.right = c;
	}

}
