package application;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import Enum.StateOnLeftPane;
import GeneralController.AbstractController;
import GeneralController.ChangeLabelPopupController;
import GeneralController.PopupEdgeController;
import Model.*;
import javafx.application.Platform;
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
import javafx.scene.paint.ImagePattern;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.Calculate;
import util.ValidateInput;

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
	private RadioButton rbChangeLbl;
	@FXML
	private RadioButton rbMoveLbl;
	@FXML
	private RadioButton rbRemoveIcon;
	@FXML
	private RadioButton rbVertexIcon;
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
	private boolean isMovingLabel = false;
	private Vertex currentVertex = null;
	private Edge currentEdge = null;
	private int indexVertex = -1;
	private int distanceToDeleteEdge = 15;// the limit of distance when click to delete the edge
	private boolean isDrawingEdge = false;
	private double deltaX, deltaY;// use to move the Vertex
	private double firstX, firstY;// save the first position of the Vertex before moving the Vertex
	private List<Integer> hasPoints = new ArrayList<>();
	private double radius = 20;// radius of Vertex
	// HashMap<Vertex, Vertex> mapVP = new HashMap<Vertex, Vertex>(); // save every
	// vertex has one piercings(also be a vertex for display)
	String typeEdge = "";
	String weightEdge = "";
	HashMap<String, Object> resultMap = null;
	Image imageDead;
	Image imageCross;
	private List<Vertex> verticesRemove = new ArrayList<Vertex>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Class<?> clazz = this.getClass();

		// InputStream input =
		// clazz.getResourceAsStream("resources/images/death_head.png");
		// Image image = new Image(input);
		// ImageView imageView = new ImageView(image);
		rbVertex.setSelected(true);
		// fix width left pane when resize window
		menuBar.prefWidthProperty().bind(parentPane.widthProperty());
		splitPane.setResizableWithParent(leftPane, Boolean.FALSE);
		imageDead = new Image("/death_head.png", 25, 25, false, false);
		imageCross = new Image("/cross.jpg", 25, 25, false, false);
		rbRemoveIcon.setGraphic(new ImageView(imageDead));
		rbVertexIcon.setGraphic(new ImageView(imageCross));
		// imageView = new ImageView(imageCross);
		// leftPane.getChildren().add(imageView);

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

	public void handleChangeLabelPress() {
		System.out.println("Change Label is pressed");
		eventOnLeftPane = StateOnLeftPane.CHANGE_LABEL;
	}

	public void handleMoveLabelPress() {
		System.out.println("Move Label is pressed");
		eventOnLeftPane = StateOnLeftPane.MOVE_LABEL;
	}

	public void handleRemoveIconPress() {
		System.out.println("RemoveIconPress");
		eventOnLeftPane = StateOnLeftPane.REMOVE_ICON;
	}

	public void handleVertexIconPress() {
		System.out.println("VertexIconPress");
		eventOnLeftPane = StateOnLeftPane.VERTEX_ICON;
	}

	@FXML
	public void clickMouse(MouseEvent event) {

	}

	@FXML
	public void pressMouse(MouseEvent event) {
		if (eventOnLeftPane == StateOnLeftPane.VERTEX || eventOnLeftPane == StateOnLeftPane.VERTEX_ICON) {// draw
																											// vertice
			if (isOnAVertex(event.getX(), event.getY())) {// if mouse click inside the vertice then move when drag else
															// create a new vertice
				isClickedInsideVertex = true;
				deltaX = event.getX() - currentVertex.getX();
				deltaY = event.getY() - currentVertex.getY();
				firstX = currentVertex.getX();// save the first position of Vertex before moving
				firstY = currentVertex.getY();
			} else {
				if (eventOnLeftPane == StateOnLeftPane.VERTEX) {
					Vertex vertex = new Vertex(event.getX(), event.getY(), radius, Color.CADETBLUE);
					vertices.add(vertex);
					System.out.println("Size of vertices: " + vertices.size());
					vertex.setLabel(event.getX(), event.getY(), ++indexVertex, "-fx-fill: yellow");
					// Setting the stroke width of the circle
					rightPane.getChildren().add(vertex);
					rightPane.getChildren().add(vertex.getLabel());
				} else if (eventOnLeftPane == StateOnLeftPane.VERTEX_ICON) {
					Vertex vertexIcon = new Vertex(event.getX(), event.getY(), radius);
					// System.out.println("Size of vertices: " + vertices.size());
					vertexIcon.setLabel(event.getX(), event.getY(), ++indexVertex, "-fx-fill: yellow");
					// Setting the stroke width of the circle
					vertexIcon.setFill(new ImagePattern(imageCross));
					vertices.add(vertexIcon);
					rightPane.getChildren().add(vertexIcon);
					rightPane.getChildren().add(vertexIcon.getLabel());

				} else {
					// do nothing
				}

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
				// firstVertex = currentVertex;// save the first Vertex for comparing later
			} else {
				System.out.println("You should click inside the Vertex for drawing the line");
			}
		}

		else if (eventOnLeftPane == StateOnLeftPane.REMOVE || eventOnLeftPane == StateOnLeftPane.REMOVE_ICON) {// remove
																												// all
																												// objects
			if (eventOnLeftPane == StateOnLeftPane.REMOVE) {
				removeObject(event.getX(), event.getY());
			} else if (eventOnLeftPane == StateOnLeftPane.REMOVE_ICON) {
				removeObject(event.getX(), event.getY());
				Vertex verDead = new Vertex(event.getX(), event.getY(), radius);
				verDead.setFill(new ImagePattern(imageDead));
				verticesRemove.add(verDead);
				rightPane.getChildren().add(verDead);
			} else {
				// do nothing
			}

		} else if (eventOnLeftPane == StateOnLeftPane.CHANGE_LABEL) {
			if (isOnALabel(event.getX(), event.getY())) {
				showChangeLabelPopupEdge(currentEdge);
				System.out.println("New value of label: " + currentEdge.getTextWeight().getText());
			}
		} else if (eventOnLeftPane == StateOnLeftPane.MOVE_LABEL) {
			if (isOnALabel(event.getX(), event.getY())) {
				isMovingLabel = true;
				System.out.println("You just clicked inside the Label!");
			} 
		}

		else {
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

	public boolean isOnALabel(double xL, double yL) {
		boolean flag = false;
		if (edges.size() > 0) {
			for (int i = 0; i < edges.size(); i++) {
				if (edges.get(i).getCircleLable().contains(xL, yL)) {
					currentEdge = edges.get(i);
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
			movingTheEdges(x, y);
		}
		if (isDrawingEdge) {
			currentEdge.setX2(event.getX());
			currentEdge.SetY2(event.getY());
		}
		if (isMovingLabel) {
			currentEdge.setDeltaText(event.getX(), event.getY());
		}
	}

	public Edge getExistEdge(Edge E) {
		Edge edge = null;
		for (Edge e : edges) {
			if (e.getX1() == E.getX1() && e.getY1() == E.getY1() && e.getX2() == E.getX2() && e.getY2() == E.getY2()) {
				edge = e;
				break;
			} else if (e.getX1() == E.getX2() && e.getY1() == E.getY2() && e.getX2() == E.getX1()
					&& e.getY2() == E.getY1()) {
				edge = e;
				break;
			}
		}
		return edge;
	}

	public void releaseMouse(MouseEvent event) {
		isClickedInsideVertex = false;

		if (eventOnLeftPane == StateOnLeftPane.VERTEX) {

		} else if (eventOnLeftPane == StateOnLeftPane.EDGE) {
			if (isOnAVertex(event.getX(), event.getY())) {// && currentVertex != firstVertex ->cancel this
				// is on a vertex -> set the ending point of edge is the central of Vertex
				resultMap = showPopupEdge();
				typeEdge = (String) resultMap.get("typeEdge");
				weightEdge = (String) resultMap.get("weight");
				if (ValidateInput.userWantToCreate(typeEdge, weightEdge)) {
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
					// add the edge and its feature
					currentEdge.setPoint2(currentVertex.getX(), currentVertex.getY());
					rightPane.getChildren().add(currentEdge.getTextWeight());
					// draw a curve
					if (currentEdge.getX1() == currentEdge.getX2() && currentEdge.getY1() == currentEdge.getY2()) {
						System.out.println("This is a point, not an edge! Draw a curve");
						// if(currentEdge.getCircle() == null) {
						currentEdge.setCircle(radius);
						rightPane.getChildren().add(currentEdge.getCircle());
						currentEdge.getCircle().toBack();
						rightPane.getChildren().add(currentEdge.getArrow1());
						rightPane.getChildren().add(currentEdge.getArrow2());
						currentEdge.updateEdge();
						// }

					} else {// draw an edge
						rightPane.getChildren().add(currentEdge.getArrow1());
						rightPane.getChildren().add(currentEdge.getArrow2());
						currentEdge.updateEdge();
					}
					// update the edge
					Edge edge = getExistEdge(currentEdge);
					if (edge != null) {
						rightPane.getChildren().remove(edge.getCircle());// remove the last circle
						System.out.println("This edge is existed! Update the new edge!");
						removeEdge(edge);
					}
					edges.add(currentEdge);
					currentEdge = null;// remove it after we don't use it anymore.

				} else {
					rightPane.getChildren().remove(currentEdge);
				}
			} else { // mouse release not on a vertex -> remove current Edge
				rightPane.getChildren().remove(currentEdge);
			}
			isDrawingEdge = false;
		} else if (eventOnLeftPane == StateOnLeftPane.MOVE_LABEL) {
			if (isMovingLabel) {
				System.out.println("You just released the Label!");
				currentEdge.setDeltaText(event.getX(), event.getY());
				isMovingLabel = false;
			}
			currentEdge = null; // remove it after we don't use it anymore.

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
		if (isOnAVertex(cX, cY)) {// delete Vertex
			System.out.println("Delete Vertex!");

			for (Vertex v : vertices) {
				if (v.contains(cX, cY)) {
					Point2D point = new Point2D.Double(v.getCenterX(), v.getCenterY());
					// Line2D deleteLine = new Line2D.Double();
					for (Edge edge : edges) {
						if (point.getX() == edge.getX1() && point.getY() == edge.getY1()) {
							// convert the line that has started point to Point
							edge.setEdge(point.getX(), point.getY(), point.getX(), point.getY());
							hasPoints.add(edges.indexOf(edge));
						} else if (point.getX() == edge.getX2() && point.getY() == edge.getY2()) {
							// convert the line that has ended point to Point
							edge.setEdge(point.getX(), point.getY(), point.getX(), point.getY());
							hasPoints.add(edges.indexOf(edge));
						}
					}
					// delete the points in the list of lines from highest index to lowest index
					for (int i = hasPoints.size() - 1; i >= 0; i--) {
						System.out.println("-> Delete edge at index: " + hasPoints.get(i));
						removeEdge(edges.get(hasPoints.get(i)));
					}
					hasPoints.clear();// clear hasPoints (*_*)

					// delete Vertex on the list and rightPane
					vertices.remove(v);// remove Vertex on the list (note: Vertex include label inside)
					rightPane.getChildren().remove(v);// remove Vertex on the rightPane
					rightPane.getChildren().remove(v.getLabel());// remove Label of Vertex on rightPane
					break;
				}
			}
		} else {// delete Edge
			for (Edge edge : edges) {
				int distance = Calculate.heightOfTriangle(cX, cY, edge.getX1(), edge.getY1(), edge.getX2(),
						edge.getY2());
				if (edge.getCircle() == null && distance <= distanceToDeleteEdge) {
					System.out.println("You select the line with the distance to the edge is: " + distance);
					hasPoints.add(edges.indexOf(edge));// save index of edge in edges
				}
				if (edge.getCircle() != null && edge.getCircle().contains(cX, cY)) {
					System.out.println("Delete the circle Edge!");
					hasPoints.add(edges.indexOf(edge));// save index of edge in edges
				}
			}
			// delete the points in the list of lines from highest index to lowest index
			for (int i = hasPoints.size() - 1; i >= 0; i--) {// we have to delete from highest index to lowest index
				System.out.println("-> Delete line at index: " + hasPoints.get(i));
				removeEdge(edges.get(hasPoints.get(i)));
			}
			hasPoints.clear();// clear hasPoints (*_*)
		}
	}

	private void removeEdge(Edge edge) {
		System.out.println("Deleted the edge!");
		if (edge.getCircle() != null) {
			rightPane.getChildren().remove(edge.getCircle());
		}
		edges.remove(edge);//
		rightPane.getChildren().remove(edge.getTextWeight());
		rightPane.getChildren().remove(edge.getArrow1());
		rightPane.getChildren().remove(edge.getArrow2());
		rightPane.getChildren().remove(edge);
	}

	private HashMap<String, Object> showPopupEdge() {
		// HashMap<String, Object> resultMap = new HashMap<String, Object>();
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
		indexVertex = -1;// reset index vertex
	}

	private Edge showChangeLabelPopupEdge(Edge edge) {
		// HashMap<String, Object> resultMap = new HashMap<String, Object>();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/ui/ChangeLabelPopup.fxml"));
		// initializing the controller
		ChangeLabelPopupController changeLabelController = new ChangeLabelPopupController(edge);
		loader.setController(changeLabelController);
		Parent layout;
		try {
			layout = loader.load();
			Scene scene = new Scene(layout);
			// this is the popup stage
			Stage popupStage = new Stage();
			// Giving the popup controller access to the popup stage (to allow the
			// controller to close the stage)
			changeLabelController.setStage(popupStage);
			if (this.main != null) {
				popupStage.initOwner(main.getPrimaryStage());
			}
			popupStage.initModality(Modality.WINDOW_MODAL);
			popupStage.setScene(scene);
			popupStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return changeLabelController.getResult();
	}

	public void onNewPage() {
		System.out.println("open new page");

		Main main = new Main();
		Stage primaryStage = new Stage();
		main.start(primaryStage);

	}

	public void onClosePage() {
		System.out.println("close page");
	}

	public void onExit() {
		System.out.println("exit");
		Platform.exit();
		System.exit(0);
	}

}