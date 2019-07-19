package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import Enum.StateOnLeftPane;
import GeneralController.AbstractController;
import GeneralController.PopupEdgeController;
import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
	@FXML
	private Button btnRemoveAll;
	@FXML
	private MenuBar menuBar;
	@FXML
	private ImageView imageView;
	

	private List<Vertex> vertices = new ArrayList<>();
	private List<Edge> edges = new ArrayList<>();
	private StateOnLeftPane eventOnLeftPane = StateOnLeftPane.VERTEX;
	private boolean isClickedInsideVertex = false;
	private Vertex currentVertex = null;
	private Edge currentEdge = null;
	private int indexVertex = -1;
	private boolean isDrawingEdge = false;
	private double deltaX, deltaY;// use to move the Vertex
	private double firstX, firstY;// save the first position of the Vertex before moving the Vertex
	private List<Integer> hasPoints = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Class<?> clazz = this.getClass();
		 
       // InputStream input = clazz.getResourceAsStream("resources/images/death_head.png");
       // Image image = new Image(input);
       // ImageView imageView = new ImageView(image);
		rbVertex.setSelected(true);
		// fix width left pane when resize window
		menuBar.prefWidthProperty().bind(parentPane.widthProperty());
		//splitPane.setResizableWithParent(leftPane, Boolean.FALSE);
		//Image imageDead = new Image("/death_head.png");
		Image imageCross = new Image("/iconEdge.png");
		imageView = new ImageView(imageCross);
		leftPane.getChildren().add(imageView);
		
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
		if (eventOnLeftPane == StateOnLeftPane.VERTEX) {// draw vertice
			if (isOnAVertex(event.getX(), event.getY())) {// if mouse click inside the vertice then move when drag else
															// create a new vertice
				isClickedInsideVertex = true;
				deltaX = event.getX() - currentVertex.getX();
				deltaY = event.getY() - currentVertex.getY();
				firstX = currentVertex.getX();// save the first position of Vertex before moving
				firstY = currentVertex.getY();
			} else {
				Vertex vertex = new Vertex(event.getX(), event.getY(), 20, Color.CADETBLUE);
				vertices.add(vertex);
				System.out.println("Size of vertices: " + vertices.size());
				vertex.setLabel(event.getX(), event.getY(), ++indexVertex, "-fx-fill: yellow");
				// Setting the stroke width of the circle
				rightPane.getChildren().add(vertex);
				rightPane.getChildren().add(vertex.getLabel());
			}

		} else if (eventOnLeftPane == StateOnLeftPane.EDGE) {// get starting point
			if (isOnAVertex(event.getX(), event.getY())) {// if mouse click inside the vertice then move when drag else
															// create a new vertice
				Edge edge = new Edge(currentVertex.getX(), currentVertex.getY(), event.getX(), event.getY(),
						Color.BLUEVIOLET);
				edge.setStrokeWidth(2);
				System.out.println("Size of edges: " + edges.size());
				currentEdge = edge;
				rightPane.getChildren().add(currentEdge);
				isDrawingEdge = true;
			} else {
				System.out.println("You should click inside the Vertex for drawing the line");
			}
		} else if (eventOnLeftPane == StateOnLeftPane.REMOVE) {
			removeObject(event.getX(), event.getY());
		} else {
			// do nothing
		}
	}

	public boolean isOnAVertex(double xM, double yM) {
		boolean flag = false;
		if (vertices.size() > 0) {
			for (int i = 0; i < vertices.size(); i++) {
				if (vertices.get(i).contains(xM, yM)) {
					currentVertex = vertices.get(i);
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	@FXML
	public void moveVertex(MouseEvent event) {
		if (isClickedInsideVertex) {
			double x = event.getX() - deltaX;
			double y = event.getY() - deltaY;
			currentVertex.setX(x);
			currentVertex.setY(y);
			currentVertex.getLabel().setX(x);
			currentVertex.getLabel().setY(y);
			// move the edges together with Vertex
			movingTheEdges(x, y);
		}
		if (isDrawingEdge) {
			currentEdge.setX2(event.getX());
			currentEdge.SetY2(event.getY());
		}
	}

	public void releaseMouse(MouseEvent event) {
		isClickedInsideVertex = false;

		if (eventOnLeftPane == StateOnLeftPane.VERTEX) {

		} else if (eventOnLeftPane == StateOnLeftPane.EDGE) {
			if (!isOnAVertex(event.getX(), event.getY())) {// mouse release not on a vertex -> remove current Edge
				rightPane.getChildren().remove(currentEdge);
			} else {// is on a vertex -> set the ending point of edge is the central of Vertex
				HashMap<String, Object> resultMap = showPopupEdge();
				String typeEdge = (String) resultMap.get("typeEdge");
				String weightEdge = (String) resultMap.get("weight");
				if (weightEdge != null && !weightEdge.contentEquals("")) {
					currentEdge.setTextWeight(weightEdge);// add weight to the edge
				}

				if (typeEdge != null) {
					if (typeEdge.equalsIgnoreCase("directed")) {
						currentEdge.setDirection(true);
					} else {
						currentEdge.setDirection(false);
					}
				}
				//add the edge and its feature
				currentEdge.setPoint2(currentVertex.getX(), currentVertex.getY());
				rightPane.getChildren().add(currentEdge.getTextWeight());
				currentEdge.updateEdge();
				rightPane.getChildren().add(currentEdge.getArrow1());
				rightPane.getChildren().add(currentEdge.getArrow2());
				edges.add(currentEdge);
			}
			isDrawingEdge = false;
		} else {
			// do nothing
		}
	}

	public void movingTheEdges(double newX, double newY) {// TODO
		for (Edge edge : edges) {
			if (firstX == edge.getX1() && firstY == edge.getY1()) {
				edge.setEdge(newX, newY, edge.getX2(), edge.getY2());
				edge.updateEdge();
			} else if (firstX == edge.getX2() && firstY == edge.getY2()) {
				edge.setEdge(edge.getX1(), edge.getY1(), newX, newY);
				edge.updateEdge();
			}
		}
		firstX = newX;
		firstY = newY;
	}

	public void removeObject(double cX, double cY) {
		if(isOnAVertex(cX,cY)) {//delete Vertex
			System.out.println("Delete Vertex!");
			for (Vertex v : vertices) {
				if (v.contains(cX, cY)) {
					//delete Vertex on the list and rightPane
					vertices.remove(v);//remove Vertex on the list (note: Vertex include label inside)
					rightPane.getChildren().remove(v);//remove Vertex on the rightPane
					rightPane.getChildren().remove(v.getLabel());//remove Label of Vertex on rightPane
					break;
				}
			}
		} else {//delete Edge
			for(Edge edge: edges) {	
				int distance = Calculate.heightOfTriangle(cX, cY, edge.getX1(), edge.getY1(), edge.getX2(),edge.getY2());
				System.out.println(
						"The distance: " + distance + " < " + cX + " , " + cY + " >  ->   ( " + edge.getX1()
								+ " , " + edge.getY1() + " ) & ( " + edge.getX2() + " , " + edge.getY2() + " ) ");
				if (distance <= 5) {
					System.out.println("You select the line with the distance to the edge is: " + distance);
					hasPoints.add(edges.indexOf(edge));//save index of edge in edges
				}
			} // end while
			// delete the points in the list of lines from highest index to lowest index
			for (int i = hasPoints.size() - 1; i >= 0; i--) {//we have to delete from highest index to lowest index
				System.out.println("-> Delete line at index: " + hasPoints.get(i));
				removeEdge(edges.get(hasPoints.get(i)));
			}
			hasPoints.clear();// clear hasPoints (*_*)
		}
	}
	
	private void removeEdge(Edge edge) {
		System.out.println("Deleted the edge!");
		edges.remove(edge);//
		rightPane.getChildren().remove(edge.getTextWeight());
		rightPane.getChildren().remove(edge.getArrow1());
		rightPane.getChildren().remove(edge.getArrow2());
		rightPane.getChildren().remove(edge);
	}

	private HashMap<String, Object> showPopupEdge() {
		//HashMap<String, Object> resultMap = new HashMap<String, Object>();
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
			// Giving the popup controller access to the popup stage (to allow the
			// controller to close the stage)
			popupEdgeController.setStage(popupStage);
			if (this.main != null) {
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

	@FXML
	public void removeAll() {
		System.out.println("remove all");
		rightPane.getChildren().clear();
		edges.clear();
		vertices.clear();
		indexVertex = -1;//reset index vertex
	}
	
}