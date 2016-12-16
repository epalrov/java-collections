/*
 * TreeSetTest.java - unit test for Tree Set
 *
 * Copyright (C) 2014 Paolo Rovelli
 *
 * Author: Paolo Rovelli <paolorovelli@yahoo.it>
 */

package org.epalrov.collections;

import java.util.Set;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * Unit test for TreeSet.
 */
public class TreeSetTest extends TestCase
{
	/**
	 * Create the test case
	 */
	public TreeSetTest(String testName) {
		super(testName);
	}

	/**
	 * Return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(TreeSetTest.class);
	}

	/**
	 * Rigourous Test
	 */
	public void testTreeSet() {
		String[] a = {
			"paolo", "love", "valeria", ":", "i", "mimmi", "bimbi"
		};
		Set<String> s = new TreeSet<String>();

		// add
		for (int i = 0; i < a.length; i++)
			assertTrue(s.add(a[i]));
		assertThat(s.size(), is(a.length));

		// search
		for (int i = 0; i < a.length; i++)
			assertThat(s.contains(a[i]), is(true));

		// remove
		for (int i = 0; i < a.length; i++)
			assertTrue(s.remove(a[i]));
		assertTrue(s.isEmpty());

		// bulk
		assertThat(s.addAll(java.util.Arrays.asList(a)), is(true));
		assertThat(s.containsAll(java.util.Arrays.asList(a)), is(true));
		assertThat(s.retainAll(java.util.Arrays.asList(a)), is(true));
		assertThat(s.size(), is(a.length));

		// iterator
		Iterator<String> it = s.iterator();
		while (it.hasNext())
			assertNotNull(it.next());
	}

}
