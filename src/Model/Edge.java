package Model;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class Edge extends Line {
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private Paint color;
	private double weight;
	Text textWeight = new Text();
	// private int arrow = 0;//0 -> undirected, 1 -> directed at (x1,y1), 2 ->
	// directed at (x2,y2)
	private boolean directed = false;
	Line arrow1 = new Line();
	Line arrow2 = new Line();

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

	public void setEdge(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		super.setStartX(x1);
		super.setStartY(y1);
		super.setEndX(x2);
		super.setEndY(y2);
	}

	public void updatePositionOfTextWeight() {// update position of Text Weight when edge moving
		this.textWeight.setX((x1 + x2) / 2);
		this.textWeight.setY((y1 + y2) / 2);
	}

	public void setTextWeight(String text) {
		this.textWeight.setStyle("-fx-fill: red");
		this.textWeight.setText(text);
		setWeight(Integer.valueOf(text));
	}

	public Text getTextWeight() {
		return textWeight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getWeight() {
		return weight;
	}

	public void setDirection(boolean directed) {
		this.directed = directed;
	}

	public boolean getDirection() {
		return directed;
	}

	/*
	 * public void setArrow (int arrow) { this.arrow = arrow; }
	 * 
	 * public int getArrow() { return arrow; }
	 */

	public void calculateArrow() {
		double arrowLength = 20;
		double arrowWidth = 7;
		double sx = x1;
		double sy = y1;
		double ex = x2;
		double ey = y2;

		arrow1.setEndX(ex);
		arrow1.setEndY(ey);
		arrow2.setEndX(ex);
		arrow2.setEndY(ey);

		if (directed) {
			arrow1.setStrokeWidth(2);
			arrow1.setStroke(Color.BLUEVIOLET);
			arrow2.setStrokeWidth(2);
			arrow2.setStroke(Color.BLUEVIOLET);

			if (ex == sx && ey == sy) {// arrow parts of length 0
				arrow1.setStartX(ex);
				arrow1.setStartY(ey);
				arrow2.setStartX(ex);
				arrow2.setStartY(ey);

			} else {
				double factor = arrowLength / Math.hypot(sx - ex, sy - ey);
				double factorO = arrowWidth / Math.hypot(sx - ex, sy - ey);

				// part in direction of main line
				double dx = (sx - ex) * factor;
				double dy = (sy - ey) * factor;

				// part ortogonal to main line
				double ox = (sx - ex) * factorO;
				double oy = (sy - ey) * factorO;

				arrow1.setStartX(ex + dx - oy);
				arrow1.setStartY(ey + dy + ox);
				arrow2.setStartX(ex + dx + oy);
				arrow2.setStartY(ey + dy - ox);
			}
		} else { //undirected, we don't draw an arrow, just draw a point
			arrow1.setStartX(ex);
			arrow1.setStartY(ey);
			arrow2.setStartX(ex);
			arrow2.setStartY(ey);
		}
	}

	public Line getArrow1() {
		return arrow1;
	}

	public Line getArrow2() {
		return arrow2;
	}

	public void setX1(double x1) {
		this.x1 = x1;
		super.setStartX(x1);
	}

	public void setY1(double y1) {
		this.y1 = y1;
		super.setStartY(y1);
	}

	public void setX2(double x2) {
		this.x2 = x2;
		super.setEndX(x2);
	}

	public void SetY2(double y2) {
		this.y2 = y2;
		super.setEndY(y2);
	}

	public void setPoint2(double x2, double y2) {
		this.x2 = x2;
		super.setEndX(x2);
		this.y2 = y2;
		super.setEndY(y2);
		updatePositionOfTextWeight();
	}

	public double getX1() {
		return x1;// line.getStartX();
	}

	public double getY1() {
		return y1;// line.getStartY();
	}

	public double getX2() {
		return x2;// line.getEndX();
	}

	public double getY2() {
		return y2;// line.getEndY();
	}

	public Paint getColor() {
		return color;
	}

	public void setColor(Paint color) {
		this.color = color;
		super.setStroke(color);
	}
}
