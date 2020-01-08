package com.example.branch.spatial;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Reusable geo-spatial distance utility methods.
 */
public class GeoDistance {

	// WGS84 earth-ellipsoid major (a) minor (b) radius, (f) flattening
	static final double SEMIMAJOR_AXIS = 6_378_137; // [m]
	static final double FLATTENING = 1.0 / 298.257223563;
	static final double SEMIMINOR_AXIS = SEMIMAJOR_AXIS * (1.0 - FLATTENING); // 6_356_752.31420; // [m]
	static final double SEMIMAJOR_AXIS2 = SEMIMAJOR_AXIS * SEMIMAJOR_AXIS;
	static final double SEMIMINOR_AXIS2 = SEMIMINOR_AXIS * SEMIMINOR_AXIS;

	/**
	 * Compute the distance between two geo-points using vincenty distance formula
	 * Vincenty uses the oblate spheroid whereas haversine uses unit sphere, this
	 * will give roughly 22m better accuracy (in worst case) than haversine
	 *
	 * @param lonA longitudinal coordinate of point A (in degrees)
	 * @param latA latitudinal coordinate of point A (in degrees)
	 * @param lonB longitudinal coordinate of point B (in degrees)
	 * @param latB latitudinal coordinate of point B (in degrees)
	 * @return distance (in meters) between point A and point B
	 */
	public static final double vincentyDistance(final double lonA, final double latA, final double lonB, final double latB) {
		final double L = StrictMath.toRadians(lonB - lonA);
		final double oF = 1 - FLATTENING;
		final double U1 = StrictMath.atan(oF * StrictMath.tan(StrictMath.toRadians(latA)));
		final double U2 = StrictMath.atan(oF * StrictMath.tan(StrictMath.toRadians(latB)));
		final double sU1 = StrictMath.sin(U1);
		final double cU1 = StrictMath.cos(U1);
		final double sU2 = StrictMath.sin(U2);
		final double cU2 = StrictMath.cos(U2);

		double sigma, sinSigma, cosSigma;
		double sinAlpha, cos2Alpha, cos2SigmaM;
		double lambda = L;
		double lambdaP;
		double iters = 100;
		double sinLambda, cosLambda, c;

		do {
			sinLambda = StrictMath.sin(lambda);
			cosLambda = Math.cos(lambda);
			sinSigma = Math.sqrt((cU2 * sinLambda) * (cU2 * sinLambda)
					+ (cU1 * sU2 - sU1 * cU2 * cosLambda) * (cU1 * sU2 - sU1 * cU2 * cosLambda));
			if (sinSigma == 0) {
				return 0;
			}

			cosSigma = sU1 * sU2 + cU1 * cU2 * cosLambda;
			sigma = Math.atan2(sinSigma, cosSigma);
			sinAlpha = cU1 * cU2 * sinLambda / sinSigma;
			cos2Alpha = 1 - sinAlpha * sinAlpha;
			cos2SigmaM = cosSigma - 2 * sU1 * sU2 / cos2Alpha;

			c = FLATTENING / 16 * cos2Alpha * (4 + FLATTENING * (4 - 3 * cos2Alpha));
			lambdaP = lambda;
			lambda = L + (1 - c) * FLATTENING * sinAlpha * (sigma + c * sinSigma * (cos2SigmaM + c * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
		} while (StrictMath.abs(lambda - lambdaP) > 1E-12 && --iters > 0);

		if (iters == 0) {
			return 0;
		}

		final double uSq = cos2Alpha * (SEMIMAJOR_AXIS2 - SEMIMINOR_AXIS2) / (SEMIMINOR_AXIS2);
		final double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
		final double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
		final double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)
				- B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));

		return (SEMIMINOR_AXIS * A * (sigma - deltaSigma));
	}
}