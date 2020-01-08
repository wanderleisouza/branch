package com.example.branch.spatial;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
class GeoDistanceTest {

	private static final int TOLERANCE = 50; // in meters

	@Test
	void testVincentyDistanceSamePlace() {

		var lonA = 0.1246;
		var latA = 51.5007;
		var lonB = 0.1246;
		var latB = 51.5007;

		var expectedDistance = 0;

		var actualDistance = GeoDistance.vincentyDistance(lonA, latA, lonB, latB);
		assertEquals(expectedDistance, actualDistance, TOLERANCE);

	}

	@Test
	void testVincentyDistance() {

		var lonA = -46.739716;
		var latA = -23.525636;
		var lonB = -46.732307;
		var latB = -23.526009;

		var expectedDistance = 720;

		var actualDistance = GeoDistance.vincentyDistance(lonA, latA, lonB, latB);
		assertEquals(expectedDistance, actualDistance, TOLERANCE);

	}

}
