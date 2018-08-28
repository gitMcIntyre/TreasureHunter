package com.treasurehunter.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import com.treasurehunter.PathFinder;
import com.treasurehunter.PathFinderImpl;

public class PathFinderTest {
	
	PathFinder finder = new PathFinderImpl();
	
	@Test
	public void test_empty_instructions() {
		
		finder.setPaths(null);
		Assert.assertFalse(finder.findTreasure().isPresent());
	}

	@Test
	public void test_N() {
		List<String> paths = Arrays.asList("Walk,60 min,N");
		
		finder.setPaths(paths);
		
		Optional<String> res = finder.findTreasure();
		Assert.assertTrue(res.isPresent());
		Assert.assertEquals("3 mile(s) to the North", res.get());
	}

	@Test
	public void test_S() {
		List<String> paths = Arrays.asList("Walk,60 min,S");
		
		finder.setPaths(paths);
		
		Optional<String> res = finder.findTreasure();
		Assert.assertTrue(res.isPresent());
		Assert.assertEquals("3 mile(s) to the South", res.get());
	}

	@Test
	public void test_E() {
		List<String> paths = Arrays.asList("Walk,60 min,E");
		
		finder.setPaths(paths);
		
		Optional<String> res = finder.findTreasure();
		Assert.assertTrue(res.isPresent());
		Assert.assertEquals("3 mile(s) to the East", res.get());
	}

	@Test
	public void test_W() {
		List<String> paths = Arrays.asList("Walk,60 min,W");
		
		finder.setPaths(paths);
		
		Optional<String> res = finder.findTreasure();
		Assert.assertTrue(res.isPresent());
		Assert.assertEquals("3 mile(s) to the West", res.get());
	}

	@Test
	public void test_NW() {
		List<String> paths = Arrays.asList("Walk,60 min,NW");
		
		finder.setPaths(paths);
		
		Optional<String> res = finder.findTreasure();
		Assert.assertTrue(res.isPresent());
		Assert.assertEquals("2.12 mile(s) to the North, 2.12 mile(s) to the West", res.get());
	}

	@Test
	public void test_SW() {
		List<String> paths = Arrays.asList("Walk,60 min,SW");
		
		finder.setPaths(paths);
		
		Optional<String> res = finder.findTreasure();
		Assert.assertTrue(res.isPresent());
		Assert.assertEquals("2.12 mile(s) to the South, 2.12 mile(s) to the West", res.get());
	}

	@Test
	public void test_SE() {
		List<String> paths = Arrays.asList("Walk,60 min,SE");
		
		finder.setPaths(paths);
		
		Optional<String> res = finder.findTreasure();
		Assert.assertTrue(res.isPresent());
		Assert.assertEquals("2.12 mile(s) to the South, 2.12 mile(s) to the East", res.get());
	}
	
	@Test
	public void test_two_paths() {
		List<String> paths = Arrays.asList("Walk,60 min,N", "Walk,1 hour,S");
		
		finder.setPaths(paths);
		
		Optional<String> res = finder.findTreasure();
		Assert.assertTrue(res.isPresent());
		Assert.assertEquals("You have arrived.", res.get());
	}
	
	@Test
	public void test_walk_run() {
		List<String> paths = Arrays.asList("Walk,60 min,N", "Run,1 hour,W","Walk,1 hour,S");
		
		finder.setPaths(paths);
		
		Optional<String> res = finder.findTreasure();
		Assert.assertTrue(res.isPresent());
		Assert.assertEquals("6 mile(s) to the West", res.get());
	}
	
	@Test
	public void test_N_Elephant() {
		List<String> paths = Arrays.asList("Walk,60 min,N");
		
		finder.setPaths(paths);
		
		Optional<String> res = finder.findTreasure();
		Assert.assertTrue(res.isPresent());
		Assert.assertEquals("3 mile(s) to the North", res.get());
	}

}
