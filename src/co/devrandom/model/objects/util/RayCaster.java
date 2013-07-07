package co.devrandom.model.objects.util;

import java.util.List;
import java.util.ListIterator;

import org.jbox2d.common.Vec2;

import co.devrandom.model.objects.PhysicsObject;
import co.devrandom.util.Vector;

public class RayCaster {

	/**
	 * Find the intersection closest to a given point
	 * 
	 * @param point1
	 * @param point2
	 * @param proximityTarget
	 * @return A Vec2 object containing the intersection found closest to the
	 *         provided position or NULL if no intersestions were found
	 */
	public static Ray getClosestIntersect(Vector origin, Vector direction, PhysicsObject source,
			List<PhysicsObject> objects) {
		direction = direction.norm();

		Vec2 point1 = new Vec2(origin.x, origin.y);
		Vec2 point2 = new Vec2(origin.x + direction.x * 1000, origin.y + direction.y * 1000);

		// Get a iterator for the Walls
		ListIterator<PhysicsObject> objectsI = objects.listIterator();

		// Variable that stores the distance to the closest intersection found
		float closestProximitySquared = Float.MAX_VALUE;
		// Variable containing the position of the closest intersection found
		Vector closestIntersection = null;
		PhysicsObject closestObject = null;

		// Declare variable to store the Shape's number of segments
		int linesCount;
		// Declare variable to store the Shape's number of segments
		Vec2[] vertices;

		// Run through the list of Walls
		while (objectsI.hasNext()) {
			// Load a Wall
			PhysicsObject p = objectsI.next();

			if (p.equals(source))
				continue;

			// WALLS ARE ALWAYS POLYGON SHAPES OR THIS EXPLODES!

			Vec2[] polyVertices = p.getVertices(); 
			
			// Load the vertices of this Shape
			vertices = new Vec2[polyVertices.length];
			// Load the number of lines in this Shape
			linesCount = vertices.length;

			for (int i = 0; i < vertices.length; i++) {
				vertices[i] = (p.getBody().getWorldPoint(polyVertices[i]));
			}

			// This iterator represents the end point of a segment
			int ii = 1;
			// For the number of lines in the Shape
			for (int i = 0; i < linesCount; i++) {
				Vector v1 = new Vector(vertices[i].x, vertices[i].y);
				Vector v2 = new Vector(vertices[ii].x, vertices[ii].y);

				// Call Java2D intersect method which tells us IF there is
				// an
				// intersection between the given lines
				if (linesIntersect(
				// Supply the beginning and end point of the provided
				// segment
						point1.x, point1.y, point2.x, point2.y,

						// The start-vertex of the Shape's segment
						v1.x, v1.y,
						// The end-vertex of the Shape's segment
						v2.x, v2.y)) {

					float x1 = point1.x;
					float x2 = point2.x;
					float x3 = vertices[i].x;
					float x4 = vertices[ii].x;
					float y1 = point1.y;
					float y2 = point2.y;
					float y3 = vertices[i].y;
					float y4 = vertices[ii].y;

					Vector intersection = getLineLineIntersection(x1, y1, x2, y2, x3, y3, x4, y4);

					// Calculate the distance to this intersection
					float proximitySquared = intersection.minus(origin).mag2();
					// If it is closer than the closest yet found
					if (proximitySquared < closestProximitySquared) {
						// Update these variables
						closestProximitySquared = proximitySquared;
						closestIntersection = intersection;
						closestObject = p;
					}
				}

				// Increment Shape's segment end-vertex
				ii++;
				// If it is greater than the array's length, we take the
				// first
				// vertex at index zero
				if (ii == linesCount) {
					ii = 0;
				}

			}

		}

		// Return the closest intersection found or NULL if there were no
		// intersections
		if (closestIntersection != null)
			return new Ray(origin, closestIntersection, source, closestObject);
		else return null;
	}

	public static boolean linesIntersect(double x1, double y1, double x2, double y2, double x3,
			double y3, double x4, double y4) {
		// Return false if either of the lines have zero length
		if (x1 == x2 && y1 == y2 || x3 == x4 && y3 == y4) {
			return false;
		}
		// Fastest method, based on Franklin Antonio's
		// "Faster Line Segment Intersection" topic "in Graphics Gems III" book
		// (http://www.graphicsgems.org/)
		double ax = x2 - x1;
		double ay = y2 - y1;
		double bx = x3 - x4;
		double by = y3 - y4;
		double cx = x1 - x3;
		double cy = y1 - y3;

		double alphaNumerator = by * cx - bx * cy;
		double commonDenominator = ay * bx - ax * by;
		if (commonDenominator > 0) {
			if (alphaNumerator < 0 || alphaNumerator > commonDenominator) {
				return false;
			}
		} else if (commonDenominator < 0) {
			if (alphaNumerator > 0 || alphaNumerator < commonDenominator) {
				return false;
			}
		}
		double betaNumerator = ax * cy - ay * cx;
		if (commonDenominator > 0) {
			if (betaNumerator < 0 || betaNumerator > commonDenominator) {
				return false;
			}
		} else if (commonDenominator < 0) {
			if (betaNumerator > 0 || betaNumerator < commonDenominator) {
				return false;
			}
		}
		if (commonDenominator == 0) {
			// This code wasn't in Franklin Antonio's method. It was added by
			// Keith Woodward.
			// The lines are parallel.
			// Check if they're collinear.
			double y3LessY1 = y3 - y1;
			double collinearityTestForP3 = x1 * (y2 - y3) + x2 * (y3LessY1) + x3 * (y1 - y2); // see
																								// http://mathworld.wolfram.com/Collinear.html
			// If p3 is collinear with p1 and p2 then p4 will also be collinear,
			// since p1-p2 is parallel with p3-p4
			if (collinearityTestForP3 == 0) {
				// The lines are collinear. Now check if they overlap.
				if (x1 >= x3 && x1 <= x4 || x1 <= x3 && x1 >= x4 || x2 >= x3 && x2 <= x4
						|| x2 <= x3 && x2 >= x4 || x3 >= x1 && x3 <= x2 || x3 <= x1 && x3 >= x2) {
					if (y1 >= y3 && y1 <= y4 || y1 <= y3 && y1 >= y4 || y2 >= y3 && y2 <= y4
							|| y2 <= y3 && y2 >= y4 || y3 >= y1 && y3 <= y2 || y3 <= y1 && y3 >= y2) {
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}

	public static Vector getLineLineIntersection(double x1, double y1, double x2, double y2,
			double x3, double y3, double x4, double y4) {
		double det1And2 = det(x1, y1, x2, y2);
		double det3And4 = det(x3, y3, x4, y4);
		double x1LessX2 = x1 - x2;
		double y1LessY2 = y1 - y2;
		double x3LessX4 = x3 - x4;
		double y3LessY4 = y3 - y4;
		double det1Less2And3Less4 = det(x1LessX2, y1LessY2, x3LessX4, y3LessY4);
		if (det1Less2And3Less4 == 0) {
			// the denominator is zero so the lines are parallel and there's
			// either no solution (or multiple solutions if the lines overlap)
			// so return null.
			return null;
		}
		double x = (det(det1And2, x1LessX2, det3And4, x3LessX4) / det1Less2And3Less4);
		double y = (det(det1And2, y1LessY2, det3And4, y3LessY4) / det1Less2And3Less4);
		return new Vector((float) x, (float) y);
	}

	protected static double det(double a, double b, double c, double d) {
		return a * d - b * c;
	}
}
