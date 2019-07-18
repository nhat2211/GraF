package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import Enum.StateOnLeftPane;
import GeneralController.AbstractController;
import Model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController extends AbstractController implements Initializable {

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
	private StateOnLeftPane eventOnLeftPane = StateOnLeftPane.VERTEX;
	private boolean isClickedInsideVertex = false;
	private Vertex currentVertex = null;
	private Text currentLabel = null;
	private Edge currentEdge = null;
	private int indexVertex =-1;
	private boolean isDrawingEdge = false;
	private double deltaX, deltaY;//use to move the Vertex
	private double firstX, firstY;//save the first position of the Vertex before moving the Vertex
	
	private List<ArrowLine> arrows = new ArrayList<ArrowLine>();
	private String currentType ="";
	private ArrowLine currentArrowLine = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rbVertex.setSelected(true);
	}

	public void handleVertexPress() {
		System.out.println("Vertex is pressed");
		eventOnLeftPane = StateOnLeftPane.VERTEX;
	}
	
	public void handleEdgePress() {
		System.out.println("Edge is pressed");
		eventOnLeftPane = StateOnLeftPane.EDGE;
	}
	
	public void handleRemoveObjPress() {
		System.out.println("Remove Object is pressed");
		eventOnLeftPane = StateOnLeftPane.REMOVE;
	}
	
	@FXML
	public void clickMouse(MouseEvent event) {
		
	}
	
	@FXML
	public void pressMouse(MouseEvent event) {
		if(eventOnLeftPane == StateOnLeftPane.VERTEX) {//draw vertice
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
				label.setX(event.getX()-5);
				label.setY(event.getY()+5);
				
				label.setText(Integer.toString(++indexVertex));
				label.setStyle("-fx-fill: yellow");
				labels.add(label);
				vertex.setLabel(label);

				// Setting the stroke width of the circle
				rightPane.getChildren().add(vertex);
				rightPane.getChildren().add(label);
			}
			
		} else if (eventOnLeftPane == StateOnLeftPane.EDGE) {//get starting point
			if(isOnAVertex(event.getX(), event.getY())) {// if mouse click inside the vertice then move when drag else create a new vertice
				Edge edge = new Edge(currentVertex.getX(), currentVertex.getY(), event.getX(),event.getY(),Color.BLUEVIOLET);
				// set line height
				edge.setStrokeWidth(5);
				edges.add(edge);
				currentEdge = edge;
				currentArrowLine = new ArrowLine();
				currentArrowLine.setStartX(currentVertex.getX());
				currentArrowLine.setStartY(currentVertex.getY());
				
				currentArrowLine.setEndX(event.getX());
				currentArrowLine.setEndY(event.getY());
				rightPane.getChildren().add(currentEdge);
				isDrawingEdge = true;
			} else {
				System.out.println("You should click inside the Vertex for drawing the line");
			}
		} else if(eventOnLeftPane == StateOnLeftPane.REMOVE) {
			System.out.println("Remove Object Start");
			removeObject(event.getX(), event.getY());
			System.out.println("Remove Object End");
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
			movingTheEdges(x,y);
			/*
			if(currentType == "undirected") {
				movingTheEdges(x,y);
			}else if(currentType == "directed") {
				//movingTheArrowLines(x, y);
			}*/
		}
		if(isDrawingEdge) {
			currentEdge.setX2(event.getX());
			currentEdge.SetY2(event.getY());
			currentArrowLine.setEndX(event.getX());
			currentArrowLine.setEndY(event.getY());
		}
	}
	
    
	public void releaseMouse(MouseEvent event) {
		isClickedInsideVertex = false;
		
		if(eventOnLeftPane == StateOnLeftPane.VERTEX) {
			
		} else if (eventOnLeftPane == StateOnLeftPane.EDGE) {
			if(!isOnAVertex(event.getX(),event.getY())) {//mouse release not on a vertex -> remove current Edge
				rightPane.getChildren().remove(currentEdge);
			} else {//is on a vertex -> set the ending point of edge is the central of Vertex
				HashMap<String, Object> resultMap = showPopupEdge();
				String typeEdge = (String) resultMap.get("typeEdge");
				String weightEdge = (String) resultMap.get("weight");
				currentEdge.setTextWeight(weightEdge);//add weight to the edge
				if(typeEdge.equalsIgnoreCase("directed")){
					currentType = "directed";
					currentArrowLine.setEndX(currentVertex.getX());
					currentArrowLine.setEndY(currentVertex.getY());
					arrows.add(currentArrowLine);
					rightPane.getChildren().add(currentArrowLine);
					rightPane.getChildren().remove(currentEdge);
				}else {
					currentType = "undirected";
					currentEdge.setPoint2(currentVertex.getX(), currentVertex.getY());
					rightPane.getChildren().add(currentEdge.getTextWeight());
					//test arrow
					currentEdge.calculateArrow();//calculate arrow 1 and arrow 2.
					rightPane.getChildren().add(currentEdge.getArrow1());
					rightPane.getChildren().add(currentEdge.getArrow2());
				}
			}
			isDrawingEdge = false;
		} else {
			//do nothing
		}
	}
	
	public void movingTheEdges(double newX, double newY) {// TODO
		for (Edge edge : edges) {
			if (firstX == edge.getX1() && firstY == edge.getY1()) {
				edge.setEdge(newX, newY, edge.getX2(), edge.getY2());
				edge.updatePositionOfTextWeight();
				edge.calculateArrow();
			} else if (firstX == edge.getX2() && firstY == edge.getY2()) {
				edge.setEdge(edge.getX1(), edge.getY1(), newX, newY);
				edge.updatePositionOfTextWeight();
				edge.calculateArrow();
			}
		}
		firstX = newX;
		firstY = newY;
	}
	
	/*
	public void movingTheArrowLines(double newX, double newY) {// TODO
		for (ArrowLine arrow : arrows) {
			if (firstX == arrow.getStartX() && firstY == arrow.getEndX()) {
				//arrow.setEdge(newX, newY, arrow.getEndX(), arrow.getEndY());
				arrow.setStartX(newX);
				arrow.setStartY(newY);
				
				arrow.setEndX(arrow.getEndX());
				arrow.setEndY(arrow.getEndY());
				
			} else if (firstX == arrow.getEndX() && firstY == arrow.getEndY()) {
				//edge.setEdge(edge.getX1(), edge.getY1(), newX, newY);
				arrow.setStartX(arrow.getStartX());
				arrow.setStartY(arrow.getEndX());
				
				arrow.setEndX(newX);
				arrow.setEndY(newY);
			}
		}
		firstX = newX;
		firstY = newY;
	}*/
	
	public void removeObject(double cX, double cY) {
		ObservableList<Node> elementsOnPane = rightPane.getChildren();
		for(Node n: elementsOnPane) {
			if(n.contains(cX, cY)) {
				elementsOnPane.remove(n);
				return;
			}
		}
	}
	
	private HashMap<String, Object> showPopupEdge() {
         HashMap<String, Object> resultMap = new HashMap<String, Object>();

         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("/ui/Popup.fxml"));
         // initializing the controller
         PopupEdgeController popupEdgeController = new PopupEdgeController();
         loader.setController(popupEdgeController);
         Parent layout;
         try {
             layout = loader.load();
             Scene scene = new Scene(layout);
             // this is the popup stage
             Stage popupStage = new Stage();
             // Giving the popup controller access to the popup stage (to allow the controller to close the stage) 
             popupEdgeController.setStage(popupStage);
             if(this.main!=null) {
                 popupStage.initOwner(main.getPrimaryStage());
             }
             popupStage.initModality(Modality.WINDOW_MODAL);
             popupStage.setScene(scene);
             popupStage.showAndWait();
         } catch (IOException e) {
             e.printStackTrace();
         }
         return popupEdgeController.getResult();
     }

}