package Model;

import javafx.scene.paint.Paint;

public class Edge {
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private Paint color;
	
	public Edge() {
		
	}

	public Edge(double x1, double y1, double x2, double y2, Paint color) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
	}

	public double getX1() {
		return x1;
	}
	
	public double getY1() {
		return y1;
	}

	public void setPoint1(double x1, double y1) {
		this.x1 = x1;
		this.y1 = y1;
	}

	public double getX2() {
		return x2;
	}
	
	public double getY2() {
		return y2;
	}

	public void setPoint2(double x2, double y2) {
		this.x2 = x2;
		this.y2 = y2;
	}

	public Paint getColor() {
		return color;
	}

	public void setColor(Paint color) {
		this.color = color;
	}
}
