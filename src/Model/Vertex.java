package Model;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Vertex extends Circle{
	private double x;
	private double y;
	private double r;
	private Paint color;
	private Text label = new Text();
	
	public Vertex() {
		
	}

	public Vertex(double x, double y, double r, Paint color) {
		super();
		this.x = x;
		this.y = y;
		this.r = r;
		this.color = color;
		super.setCenterX(x);
		super.setCenterY(y);
		super.setRadius(r);
		super.setFill(color);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
		super.setCenterX(x);
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		super.setCenterY(y);
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
		super.setRadius(r);
	}

	public Paint getColor() {
		return color;
	}

	public void setColor(Paint color) {
		this.color = color;
		super.setFill(color);
	}

	public Text getLabel() {
		return label;
	}

	public void setLabel (double xText, double yText, Integer index, String color) {
		this.label.setX(xText);
		this.label.setY(yText);
		this.label.setText(Integer.toString(index));
		this.label.setStyle(color);
	}
	
}
