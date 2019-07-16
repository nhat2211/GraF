package Model;

import javafx.scene.paint.Paint;

public class Vertice {
	
	private double x;
	private double y;
	private double r;
	private Paint color;
	
	public Vertice() {
		
	}

	public Vertice(double x, double y, double r, Paint color) {
		super();
		this.x = x;
		this.y = y;
		this.r = r;
		this.color = color;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public Paint getColor() {
		return color;
	}

	public void setColor(Paint color) {
		this.color = color;
	}
	
}
