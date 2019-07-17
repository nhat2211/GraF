package application;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Controller implements Initializable {

	@FXML
	private AnchorPane parentPane;
	@FXML
	private SplitPane splitPane;
	@FXML
	private AnchorPane leftPane;
	@FXML
	private AnchorPane rightPane;
	@FXML
	private RadioButton rbVertex;
	@FXML
	private RadioButton rbEdge;

	private List<Vertex> vertices = new ArrayList<>();
	private List<Edge> edges = new ArrayList<>();
	private List<Text> labels = new ArrayList<Text>();
	private String eventOnLeftPane = "vertex";
	private boolean isClickedInsideVertex = false;
	private Vertex currentVertex = null;
	private Text currentLabel = null;
	private Edge currentEdge = null;
	private int indexVertex =-1;
	private boolean isDrawingEdge = false;
	private double deltaX, deltaY;//use to move the Vertex
	private double firstX, firstY;//save the first position of the Vertex before moving the Vertex

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rbVertex.setSelected(true);

	}

	public void handleVertexPress() {
		System.out.println("Vertex is pressed");
		eventOnLeftPane = "vertex"; 
	}
	
	public void handleEdgePress() {
		System.out.println("Edge is pressed");
		eventOnLeftPane = "edge";
	}
	
	@FXML
	public void clickMouse(MouseEvent event) {
		
		
	}
	
	@FXML
	public void pressMouse(MouseEvent event) {
		if(eventOnLeftPane == "vertex") {//draw vertice
			//System.out.println("clicked");
			if(isOnAVertex(event.getX(), event.getY())) {// if mouse click inside the vertice then move when drag else create a new vertice
				isClickedInsideVertex = true;
				deltaX = event.getX() - currentVertex.getX();
				deltaY = event.getY() - currentVertex.getY();
				firstX = currentVertex.getX();//save the first position of Vertex before moving
				firstY = currentVertex.getY();
			}else {
				Vertex vertex = new Vertex(event.getX(),event.getY(),20,Color.CADETBLUE);
				vertices.add(vertex);
				
				Text label = new Text();
				label.setX(event.getX());
				label.setY(event.getY());
				label.setText(Integer.toString(++indexVertex));
				labels.add(label);
				vertex.setLabel(label);

				// Setting the stroke width of the circle
				rightPane.getChildren().add(vertex);
				rightPane.getChildren().add(label);
			}
			
		} else if (eventOnLeftPane == "edge") {//get starting point
			if(isOnAVertex(event.getX(), event.getY())) {// if mouse click inside the vertice then move when drag else create a new vertice
				Edge edge = new Edge(currentVertex.getX(), currentVertex.getY(), event.getX(),event.getY(),Color.BLUEVIOLET);
				edges.add(edge);
				currentEdge = edge;
				rightPane.getChildren().add(currentEdge);
				isDrawingEdge = true;
			} else {
				System.out.println("You should click inside the Vertex for drawing the line");
			}
		} else {
			//do nothing
		}
	}
	
	public boolean isOnAVertex(double xM,double yM) {
		boolean flag = false;
		if(vertices.size()>0) {
			for(int i=0;i<vertices.size();i++) {
				if(vertices.get(i).contains(xM, yM)) {
					currentVertex = vertices.get(i);
					flag = true;
					break;
				}
			}
			
			for(int j=0;j<labels.size();j++) {
				if(currentVertex !=null) {
					if(currentVertex.contains(labels.get(j).getX(),labels.get(j).getY())) {
						currentLabel = labels.get(j);
						break;
					}
				}
			}
		}
		return flag;
	}
	
	@FXML
	public void moveVertex(MouseEvent event) {
		if(isClickedInsideVertex) {
			double x = event.getX() - deltaX;
			double y = event.getY() - deltaY;
			currentVertex.setX(x);
			currentVertex.setY(y);
			currentLabel.setX(x);
			currentLabel.setY(y);
			//move the edges together with Vertex
			movingTheLines(x,y);
			
		}
		if(isDrawingEdge) {
			currentEdge.setX2(event.getX());
			currentEdge.SetY2(event.getY());
		}
	}

	public void releaseMouse(MouseEvent event) {
		isClickedInsideVertex = false;
		if(eventOnLeftPane == "vertex") {
			
		} else if (eventOnLeftPane == "edge") {
			if(!isOnAVertex(event.getX(),event.getY())) {
				rightPane.getChildren().remove(currentEdge);
			} else {//is on a vertex -> set the ending point of edge is the central of Vertex
				currentEdge.setX2(currentVertex.getX());
				currentEdge.SetY2(currentVertex.getY());
			}
			isDrawingEdge = false;
		} else {
			//do nothing
		}
	}
	
	public void movingTheLines(double newX, double newY) {// TODO
		for (Edge edge : edges) {
			if (firstX == edge.getX1() && firstY == edge.getY1()) {
				edge.setEdge(newX, newY, edge.getX2(), edge.getY2());
			} else if (firstX == edge.getX2() && firstY == edge.getY2()) {
				edge.setEdge(edge.getX1(), edge.getY1(), newX, newY);
			}
		}
		firstX = newX;
		firstY = newY;
	}

}