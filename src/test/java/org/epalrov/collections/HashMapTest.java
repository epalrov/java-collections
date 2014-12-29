/*
 * HashMaptTest.java - unit test for Hash Map
 *
 * Copyright (C) 2014 Paolo Rovelli
 *
 * Author: Paolo Rovelli <paolorovelli@yahoo.it>
 */

package org.epalrov.collections;

import java.util.Map;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * Unit test for HashMap.
 */
public class HashMapTest extends TestCase
{
	/**
	 * Create the test case
	 */
	public HashMapTest(String testName) {
		super(testName);
	}

	/**
	 * Return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(HashMapTest.class);
	}

	/**
	 * Rigourous Test
	 */
	public void testHashMap() {
		String[] a = { "Hello", "Mr.", "Paolo", "Rovelli" };
		Map<Integer,String> h = new HashMap<Integer,String>(3);

		// add
		for (int i = 0; i < a.length; i++)
			assertNull(h.put(i, a[i]));
		assertThat(h.size(), is(a.length));

		// search
		for (int i = 0; i < a.length; i++) {
			assertThat(h.get(i), is(a[i]));
			assertThat(h.containsKey(i), is(true));
			assertThat(h.containsValue(a[i]), is(true));
		}

		// iterator
		Iterator<Integer> it1 = h.keySet().iterator();
		while (it1.hasNext())
		 	assertTrue(it1.next() < a.length);
		Iterator<String> it2 = h.values().iterator();
		while (it2.hasNext())
		 	assertNotNull(it2.next());

		// remove
		for (int i = 0; i < a.length; i++)
			assertThat(h.remove(i), is(a[i]));
		assertTrue(h.isEmpty());
	}

}
