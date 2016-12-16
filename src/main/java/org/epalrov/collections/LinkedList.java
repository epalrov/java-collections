/*
 * LinkedList.java - example of Linked List implementation
 *
 * Copyright (C) 2014 Paolo Rovelli
 *
 * Author: Paolo Rovelli <paolorovelli@yahoo.it>
 */

package org.epalrov.collections;

import java.util.List;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Linked list implementation of the <tt>List</tt> interface. 
 *
 * All of the operations perform as could be expected for a doubly-linked
 * list. Operations that index into the list will traverse the list from
 * the beginning or the end, whichever is closer to the specified index.
 *
 * Note that this implementation is not synchronized. If multiple threads
 * access a linked list concurrently, and at least one of the threads modifies
 * the list structurally, it must be synchronized externally. This is typically
 * accomplished by synchronizing on some object that naturally encapsulates
 * the list.
 */

public class LinkedList<E> implements List<E>
{
	private ListNode<E> head;
	private int size;

	/**
	 * Constructs an empty <tt>LinkedList</tt>
	 */
	public LinkedList() {
		head = new ListNode<E>(null, null, null);
		head.next = head;
		head.prev = head;
		size = 0;
	}

	/**
	 * Returns the number of elements in this list.
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns <tt>true</tt> if this list contains no elements.
	 */
	public boolean isEmpty() {
		return size == 0 ? true : false;
	}

	/**
	 * Appends the specified element to the end of this list.
	 */
	public boolean add(E e) {
		ListNode<E> n = head;
		ListNode<E> node = new ListNode<E>(e, n, n.prev);
		n.prev.next = node;
		n.prev = node;
		size++;
		return true;
	}

	/**
	 * Inserts the specified element at the specified position in this list.
	 */
	public void add(int index, E e) {
		ListNode<E> n = head;
		if (index == size) {
			n = head;
		} else if (index >= 0 && index < size/2) {
			for (int i = 0; i <= index; i++)
				n = n.next;
		} else if (index < size && index >= size/2) {
			for (int i = size; i > index; i--)
				n = n.prev;
		} else {
			throw new IndexOutOfBoundsException(
				"Index: " + index + ", Size: " + size);
		}
		ListNode<E> node = new ListNode<E>(e, n, n.prev);
		n.prev.next = node;
		n.prev = node;
		size++;
	}

	/**
	 * Returns the element at the specified position in this list.
	 */
	public E get(int index) {
		ListNode<E> n = head;
		if (index >= 0 && index < size/2) {
			for (int i = 0; i <= index; i++)
				n = n.next;
		} else if (index < size && index >= size/2) {
			for (int i = size; i > index; i--)
				n = n.prev;
		} else {
			throw new IndexOutOfBoundsException(
				"Index: " + index + ", Size: " + size);
		}
		return n.elem;
	}

	/**
	 * Replaces the element at the specified position in this list with the
	 * specified element.
	 */
	public E set(int index, E e) {
		ListNode<E> n = head;
		if (index >= 0 && index < size/2) {
			for (int i = 0; i <= index; i++)
				n = n.next;
		} else if (index < size && index >= size/2) {
			for (int i = size; i > index; i--)
				n = n.prev;
		} else {
			throw new IndexOutOfBoundsException(
				"Index: " + index + ", Size: " + size);
		}
		E oldElem = n.elem;
		n.elem = e;
		return oldElem;
	}

	/**
	 * Removes the element at the specified position in this list.
	 */
	public E remove(int index) {
		ListNode<E> n = head;
		if (index >= 0 && index < size/2) {
			for (int i = 0; i <= index; i++)
				n = n.next;
		} else if (index < size && index >= size/2) {
			for (int i = size; i > index; i--)
				n = n.prev;
		} else {
			throw new IndexOutOfBoundsException(
				"Index: " + index + ", Size: " + size);
		}
		n.next.prev = n.prev;
		n.prev.next = n.next;
		size--;
		return n.elem;
	}

	/**
	 * Removes the first occurrence of the specified element from this list,
	 * if it is present.
	 */
	public boolean remove(Object o) {
		ListNode<E> n = head;
		for (int i = 0; i < size; i++) {
			n = n.next;
			if ((o != null && o.equals(n.elem)) ||
					(o == null && n.elem == null)) {
				n.next.prev = n.prev;
				n.prev.next = n.next;
				size--;
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes all of the elements from this list.
	 */
	public void clear() {
		ListNode<E> n = head;
		while (size > 0) {
			n.next.prev = n.prev;
			n.prev.next = n.next;
			size--;
		}
	}

	/**
	 * Returns the index of the first occurrence of the specified element
	 * in this list, or -1 if this list does not contain the element.
	 */
	public int indexOf(Object o) {
		ListNode<E> n = head;
		for (int i = 0; i < size; i++) {
			n = n.next;
			if ((o != null && o.equals(n.elem)) ||
					(o == null && n.elem == null))
				return i;
		}
		return -1;
	}

	/**
	 * Returns the index of the last occurrence of the specified element
	 * in this list, or -1 if this list does not contain the element.
	 */
	public int lastIndexOf(Object o) {
		ListNode<E> n = head;
		for (int i = 0; i < size; i++) {
			n = n.prev;
			if ((o != null && o.equals(n.elem)) ||
					(o == null && n.elem == null))
				return size - 1 - i;
		}
		return -1;
	}

	public boolean contains(Object o) {
		return indexOf(o) != -1 ? true : false;
	}

	/**
	 * Returns an iterator over the elements in this list in proper
	 * sequence.
	 */
	public Iterator<E> iterator() {
		return new LinkedListIterator(0);
	}

	/**
	 * Returns a list iterator over the elements in this list in proper
	 * sequence.
	 */
	public ListIterator<E> listIterator() {
		return new LinkedListIterator(0);
	}

	/**
	 * Returns a list iterator over the elements in this list (in proper
	 * sequence), starting at the specified position in the list.
	 */
	public ListIterator<E> listIterator(int index) {
		return new LinkedListIterator(index);
	}

	private class LinkedListIterator implements ListIterator<E> {
		private ListNode<E> currNode;
		private ListNode<E> nextNode;
		private int nextIndex;

		LinkedListIterator(int index) {
			currNode = head;
			nextNode = head;
			if (index >= 0 && index < size/2) {
				for (int i = 0; i <= index; i++)
					nextNode = nextNode.next;
			} else if (index <= size && index >= size/2) {
				for (int i = size; i >= index; i--)
					nextNode = nextNode.prev;
			} else {
				throw new IndexOutOfBoundsException(
					"Index: " + index + ", Size: " + size);
			}
			nextIndex = index;
		}

		public boolean hasNext() {
			return nextIndex == size ? false : true;
		}

		public E next() {
			currNode = nextNode;
			nextNode = nextNode.next;
			nextIndex++;
			return currNode.elem;
		}

		public int nextIndex() {
			return nextIndex;
		}

		public boolean hasPrevious() {
			return nextIndex == 0 ? false : true;
		}

		public E previous() {
			currNode = nextNode;
			nextNode = nextNode.prev;
			nextIndex--;
			return currNode.elem;
		}

		public int previousIndex() {
			return nextIndex;
		}

		public void set(E e) {
			currNode.elem = e;
		}

		public void add(E e) {
			ListNode<E> n = nextNode;
			ListNode<E> node = new ListNode<E>(e, n, n.prev);
			n.prev.next = node;
			n.prev = node;
			nextIndex++;
			size++;
		}

		public void remove() {
			ListNode<E> n = nextNode;
			//n.next.prev = n.prev;
			//n.prev.next = n.next;
			n.prev.prev.next = n;
			n.prev = n.prev.prev;
			nextIndex--;
			size--;
		}
	}

	/**
	 * Returns an array containing all of the elements in this list in
	 * proper sequence (from first to last element).
	 */
	public Object[] toArray() {
		Object[] o = new Object[size];
		ListNode<E> n = head;
		for (int i = 0; i < size; i++) {
			n = n.next;
			o[i] = n.elem;
		}

		return o;
	} 

	/**
	 * Returns an array containing all of the elements in this list in
	 * proper sequence (from first to last element); the runtime type of
	 * the returned array is that of the specified array.
	 */
	public <T> T[] toArray(T[] a) {
		/* realloc */
		if (a.length < size)
			a = (T[])java.lang.reflect.Array.newInstance(
				a.getClass().getComponentType(), size);

		ListNode<E> n = head;
		for (int i = 0; i < size; i++) {
			n = n.next;
			a[i] = (T)n.elem;
		}

		return a;
	}

	/**
	 * Returns <tt>true</tt> if this list contains all of the elements of
	 * the specified collection.
	 */
	public boolean containsAll(Collection<? extends Object> c) {
		Iterator<? extends Object> i = c.iterator();
		while (i.hasNext())
			if (!contains(i.next()))
				return false;

		return true;
	}

	/**
	 * Appends all of the elements in the specified collection to the end of
	 * this list.
	 */
	public boolean addAll(Collection<? extends E> c) {
		ListNode<E> n = head;
		Iterator<? extends E> i = c.iterator();
		while (i.hasNext()) {
			ListNode<E> node =
				new ListNode<E>((E)i.next(), n, n.prev);
			n.prev.next = node;
			n.prev = node;
			size++;
		}
		return true;
	}

	/**
	 * Inserts all of the elements in the specified collection into this
	 * list at the specified position.
	 */
	public boolean addAll(int index, Collection<? extends E> c) {
		ListNode<E> n = head;
		if (index == size) {
			n = head;
		} else if (index >= 0 && index < size/2) {
			for (int i = 0; i <= index; i++)
				n = n.next;
		} else if (index < size && index >= size/2) {
			for (int i = size; i > index; i--)
				n = n.prev;
		} else {
			throw new IndexOutOfBoundsException(
				"Index: " + index + ", Size: " + size);
		}
		
		Iterator<? extends E> i = c.iterator();
		while (i.hasNext()) {
			ListNode<E> node =
				new ListNode<E>((E)i.next(), n, n.prev);
			n.prev.next = node;
			n.prev = node;
			size++;
		}
		return true;
	}	

	/**
	 * Removes from this list all of its elements that are contained in the
	 * specified collection.
	 */
	public boolean removeAll(Collection<?> c) {
		ListNode<E> n = head;
		for (int i = 0, count = size; i < count; i++) {
			n = n.next;
			if (c.contains(n.elem)) {
				n.next.prev = n.prev;
				n.prev.next = n.next;
				size--;
			}
		}
		return true;
	}

	/**
	 * Retains only the elements in this list that are contained in the
	 * specified collection.
	 */
	public boolean retainAll(Collection<?> c) {
		ListNode<E> n = head;
		for (int i = 0, count = size; i < count; i++) {
			n = n.next;
			if (!c.contains(n.elem)) {
				n.next.prev = n.prev;
				n.prev.next = n.next;
				size--;
			}
		}
		return true;
	}

	/**
	 * Not implemented, throws an <tt>UnsupportedOperationException</tt>
	 */
	public List<E> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Compares the specified object with this list for equality; returns
	 * <tt>true</tt> if and only if the specified object is also a list,
	 * both lists have the same size, and all corresponding pairs of
	 * elements in the two lists are equal.
	 */
	public boolean equals(Object o) {
		// same object reference
		if (o == this)
			return true;

		// check instance type (List)
		if (!(o instanceof List))
			return false;

		// check all elements
		Iterator<E> i = iterator();
		Iterator<?> j = ((List<?>) o).iterator();
		while (i.hasNext() && j.hasNext()) {
			E elem = i.next();
			Object obj = j.next();
			if (!(obj != null && obj.equals(elem)) ||
					(obj == null && elem == null))
				return false;
		}

		// check size
		return !(i.hasNext() || j.hasNext());
	}

	/**
	 * Returns the hash code value for this list.
	 */
	public int hashCode() {
		int hash = 1;
		Iterator<E> i = iterator();
		while (i.hasNext()) {
			E elem = i.next();
			hash = 31*hash + (elem == null ? 0 : elem.hashCode());
		}

		return hash;
	}

	private static class ListNode<E> {
		E elem;
		ListNode<E> next;
		ListNode<E> prev;

		ListNode(E e, ListNode<E> n, ListNode<E> p) {
			elem = e;
			next = n;
			prev = p;
		}
	}
}

