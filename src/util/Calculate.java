package util;

import java.awt.geom.Point2D;

import com.sun.javafx.geom.Line2D;

import javafx.scene.paint.Color;

public class Calculate {

	/*
	 * Calculate the height of the Triangle when we know 3 vertices of the triangle
	 * A(x1, y1); B(x2, y2); C(x3, y3) -> Calculate the height from Vertice A to the
	 * edge BC
	 * 		A
	 *a(AB)/|\ 
	 *    / | \ b(AC)
	 *   /  |  \  
	 * B/___|___\ C 
	 * 		H c(BC) => h(AH)
	 */
	public static int heightOfTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
		double a = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		double b = Math.sqrt((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3));
		double c = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
		// Apply Heron's Formula for Area
		double S = (a + b + c) / 2;
		double Area = Math.sqrt(S * (S - a) * (S - b) * (S - c));
		double h = 2 * Area / c;
		return (int) h;
	}

	public static Point2D getTheNearestPointOnEdge(double x1, double y1, double x2, double y2, double x3, double y3) {
		Point2D point = new Point2D.Double();
		double x = 0, y = 0, h = 0, times = 0;
		double height = heightOfTriangle(x1, y1, x2, y2, x3, y3);
		if (height == 0) {
			 x = x1;
			 y = y1;
		} else {
			while ((h != height) && (times < 10)) {
				double a = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
				double b = Math.sqrt((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3));
				x = (x2 + x3) / 2;
				y = (y2 + y3) / 2;
				if (a > b) {
					x2 = x;
					y2 = y;
				} else if (a < b) {
					x3 = x;
					y3 = y;
				} else {// a = b
					break;
				}
				times++;
			}
		}
		point.setLocation(x, y);
		return point;
	}

	/*
	 * There are two points: A(x1,y1) and B(x2,y2) with R is radius of B Calculate
	 * thePoint cross the line AB with circle of B
	 */
	public static Point2D getPoint(double x1, double y1, double x2, double y2, int R) {
		Point2D thePoint = new Point2D.Double();
		double x3 = (x1 + x2) / 2;
		double y3 = (y1 + y2) / 2;
		double d = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
		int times = 0;
		while ((int) d > R) {
			x1 = x3;
			y1 = y3;
			x3 = (x3 + x2) / 2;
			y3 = (y3 + y2) / 2;
			d = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
			while ((int) d < R && times < 10) {// set times to catch error here.
				x3 = (x3 + x1) / 2;
				y3 = (y3 + y1) / 2;
				d = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
				times++;
			}
		}
		thePoint.setLocation(x3, y3);
		return thePoint;
	}

	public static Line2D getNewArc(double x1, double y1, double x2, double y2, double length) {
		Line2D newArc = new Line2D();
		Point2D p1 = getNewPoint(x1, y1, x2, y2, true, length);
		Point2D p2 = getNewPoint(x2, y2, x1, y1, false, length);
		newArc.setLine((float)p2.getX(), (float)p2.getY(), (float)p1.getX(), (float)p1.getY());
		return newArc;
	}
	
	private static Point2D getNewPoint(double x1, double y1, double x2, double y2, boolean whichPoint, double length) {
		Point2D newPoint1 = new Point2D.Double();
		Point2D newPoint2 = new Point2D.Double();
		double arrowLength = length;
		double arrowWidth = length/3;
		double sx = x1; // x1
		double sy = y1; // y1
		double ex = x2; // x2
		double ey = y2; // y2


			if (ex == sx && ey == sy) {// arrow parts of length 0
				newPoint1.setLocation(ex, ey);
				newPoint2.setLocation(ex, ey);
			} else {
				double factor = arrowLength / Math.hypot(sx - ex, sy - ey);
				double factorO = arrowWidth / Math.hypot(sx - ex, sy - ey);

				// part in direction of main line
				double dx = (sx - ex) * factor;
				double dy = (sy - ey) * factor;

				// part ortogonal to main line
				double ox = (sx - ex) * factorO;
				double oy = (sy - ey) * factorO;

				newPoint1.setLocation(ex + dx - oy, ey + dy + ox);
				newPoint2.setLocation(ex + dx + oy, ey + dy - ox);
			}

		return whichPoint ? newPoint1 : newPoint2;
	}
}
