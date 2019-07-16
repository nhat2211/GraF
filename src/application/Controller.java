package application;

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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

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
	private RadioButton rbVertice;
	@FXML
	private RadioButton rbEdge;

	private List<Vertice> vertices = new ArrayList<>();
	private List<Edge> edges = new ArrayList<>();
	private String eventOnLeftPane = "vertice";
	Line line = new Line();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void handleVerticePress() {
		System.out.println("Vertice is pressed");
		eventOnLeftPane = "vertice"; 
	}
	
	public void handleEdgePress() {
		System.out.println("Edge is pressed");
		eventOnLeftPane = "edge";
	}
	
	@FXML
	public void clickMouse(MouseEvent event) {
		if(eventOnLeftPane == "vertice") {//draw vertice
			System.out.println("clicked");
			Circle circle = new Circle();
			// Setting the position of the circle
			circle.setCenterX(event.getX());
			circle.setCenterY(event.getY());
			circle.setRadius(20);
			circle.setFill(Color.CADETBLUE);
			
			//add Vertice to the list
			Vertice vertice = new Vertice(event.getX(),event.getY(),20,Color.CADETBLUE);
			vertices.add(vertice);

			// Setting the stroke width of the circle
			circle.setStrokeWidth(20);
			// circle.setFill(Color.DARKSLATEBLUE);
			rightPane.getChildren().add(circle);
		} else if (eventOnLeftPane == "edge") {//get starting point
			line.setStartX(event.getX());
			line.setStartY(event.getY());
			line.setFill(Color.DARKMAGENTA);
			
		} else {
			//do nothing
		}
	}

	public void releaseMouse(MouseEvent event) {
		if(eventOnLeftPane == "vertice") {
			
		} else if (eventOnLeftPane == "edge") {
			line.setEndX(event.getX());
			line.setEndY(event.getY());
			rightPane.getChildren().add(line);
			
			//add Line to the list
			Edge edge = new Edge(line.getStartX(),line.getStartY(),line.getEndX(),line.getEndY(),Color.DARKMAGENTA);
			edges.add(edge);
		} else {
			//do nothing
		}
	}

}