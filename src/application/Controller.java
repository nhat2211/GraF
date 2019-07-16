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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
	private RadioButton rbVertice;
	@FXML
	private RadioButton rbEdge;

	private List<Vertice> vertices = new ArrayList<>();
	private List<Edge> edges = new ArrayList<>();
	private List<Text> labels = new ArrayList<Text>();
	private String eventOnLeftPane = "vertice";
	Line line = new Line();
	private boolean isClickedInsideVertice = false;
	private Vertice currentVertice = null;
	private Text currentLabel = null;
	private int indexVertice =-1;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rbVertice.setSelected(true);

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
		
		
	}
	
	@FXML
	public void pressMouse(MouseEvent event) {
		if(eventOnLeftPane == "vertice") {//draw vertice
			//System.out.println("clicked");
			if(isClickOnAVertice(event.getX(), event.getY())) {// if mouse click inside the vertice then move when drag else create a new vertice
				isClickedInsideVertice = true;
				
			}else {
				
				Vertice vertice = new Vertice();
				
				// Setting the position of the circle
				
				vertice.setX(event.getX());
				vertice.setY(event.getY());
				vertice.setR(20);
				vertice.setColor(Color.CADETBLUE);
				
				//add Vertice to the list
				//Vertice vertice = new Vertice(event.getX(),event.getY(),20,Color.CADETBLUE);
				vertices.add(vertice);
				Text label = new Text();
				label.setX(event.getX());
				label.setY(event.getY());
				label.setText(Integer.toString(++indexVertice));
				labels.add(label);
				vertice.setLabel(label);

				// Setting the stroke width of the circle
				//circle.setStrokeWidth(20);
				// circle.setFill(Color.DARKSLATEBLUE);
				rightPane.getChildren().add(vertice);
				rightPane.getChildren().add(label);
			}
			
		} else if (eventOnLeftPane == "edge") {//get starting point
			line.setStartX(event.getX());
			line.setStartY(event.getY());
			line.setFill(Color.DARKMAGENTA);
			
		} else {
			//do nothing
		}
		
		
	}
	
	public boolean isClickOnAVertice(double xM,double yM) {
		boolean flag = false;
		if(vertices.size()>0) {
			for(int i=0;i<vertices.size();i++) {
				
				if(vertices.get(i).contains(xM, yM)) {
					currentVertice = vertices.get(i);
					flag = true;
					break;
					
				}
			}
			
			for(int j=0;j<labels.size();j++) {
				if(currentVertice !=null) {
					if(currentVertice.contains(labels.get(j).getX(),labels.get(j).getY())) {
						currentLabel = labels.get(j);
						
						break;
					}
				}
				
				
			}
			
		}
		return flag;
	}
	
	@FXML
	public void moveVertice(MouseEvent event) {
		if(isClickedInsideVertice) {
			currentVertice.setX(event.getX());
			currentVertice.setY(event.getY());
			
			currentLabel.setX(event.getX());
			currentLabel.setY(event.getY());
			
		}
	
	}
	
	

	public void releaseMouse(MouseEvent event) {
		isClickedInsideVertice = false;
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