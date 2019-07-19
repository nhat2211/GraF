package Model;

import java.awt.geom.Point2D;

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
	private Text textWeight = new Text();
	private boolean directed = false;
	private Line arrow1 = new Line();
	private Line arrow2 = new Line();
	private Point2D point1 = new Point2D.Double();//point 1,2 is intersected by edge and vertex
	private Point2D point2 = new Point2D.Double();//

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

	public void updateEdge() {
		//intersected by edge and vertex
		point1 = calculatePoint(x2,y2,x1,y1,20);
		point2 = calculatePoint(x1,y1,x2,y2,20);
		//update line
		super.setStartX(point1.getX());
		super.setStartY(point1.getY());
		super.setEndX(point2.getX());
		super.setEndY(point2.getY());
		updatePositionOfTextWeight();
		calculateArrow();
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

	private void updatePositionOfTextWeight() {// update position of Text Weight when edge moving
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

	private void calculateArrow() {
		double arrowLength = 20;
		double arrowWidth = 7;
		double sx = point1.getX(); 	//x1
		double sy = point1.getY(); 	//y1
		double ex = point2.getX();	//x2
		double ey = point2.getY();	//y2

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
	
	/*
	 * There are two points: A(x1,y1) and B(x2,y2) with R is radius of B
	 * Calculate thePoint is intersected by the line AB and circle of B
	 */
	public Point2D calculatePoint(double x1, double y1, double x2, double y2, int R) {
		Point2D thePoint = new Point2D.Double();
		double x3 = (x1 + x2) / 2;
		double y3 = (y1 + y2) / 2;
		double d = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
		int times = 0;
		while ((int) d > R) {
			x1 = x3;
			y1 = y3;
			x3 = (x3 + x2)/2;
			y3 = (y3 + y2)/2;
			d = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
			while((int) d < R && times < 10) {//set times to catch error here.
				x3 = (x3 + x1)/2;
				y3 = (y3 + y1)/2;
				d = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
				times++;
			}
		}
		thePoint.setLocation(x3, y3);
		return thePoint;
	}
}
