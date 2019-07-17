package Model;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class Edge extends Line{
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
		super.setStartX(x1);
		super.setStartY(y1);
		super.setEndX(x2);
		super.setEndY(y2);
		super.setStroke(color);
		
		
	}

	public double getX1() {
		return x1;//line.getStartX();
	}
	
	public double getY1() {
		return y1;//line.getStartY();
	}

	public double getX2() {
		return x2;//line.getEndX();
	}
	
	public double getY2() {
		return y2;//line.getEndY();
	}

	public Paint getColor() {
		return color;
	}

	public void setColor(Paint color) {
		this.color = color;
		super.setStroke(color);
	}
}
