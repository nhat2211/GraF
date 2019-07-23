package util;

import java.awt.geom.Point2D;

public class Calculate {

	/*
	 * Calculate the height of the Triangle when we know 3 vertices of the triangle
	 * A(x1, y1); B(x2, y2); C(x3, y3) -> Calculate the height from Vertice A to the
	 * edge BC
	 * 
	 * A * /|\ a(AB)/ | \ b(AC) / | \ B /___|___\ C H c(BC) h(AH)
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

}
