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
	private double x1, x2;
	private boolean isClickedInsideVertex = false;
	private Vertex currentVertex = null;
	private Text currentLabel = null;
	private int indexVertex =-1;

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
			if(isClickOnAVertex(event.getX(), event.getY())) {// if mouse click inside the vertice then move when drag else create a new vertice
				isClickedInsideVertex = true;
				
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
			x1 = event.getX();
			x2 = event.getY();
		} else {
			//do nothing
		}
	}
	
	public boolean isClickOnAVertex(double xM,double yM) {
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
			currentVertex.setX(event.getX());
			currentVertex.setY(event.getY());
			currentLabel.setX(event.getX());
			currentLabel.setY(event.getY());
		}
	}

	public void releaseMouse(MouseEvent event) {
		isClickedInsideVertex = false;
		if(eventOnLeftPane == "vertex") {
			
		} else if (eventOnLeftPane == "edge") {
			Edge edge = new Edge(x1, x2, event.getX(),event.getY(),Color.BLUEVIOLET);
			edges.add(edge);
			rightPane.getChildren().add(edge);
		} else {
			//do nothing
		}
	}

}