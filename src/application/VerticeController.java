package application;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Vertice;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class VerticeController implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	public void createVertice(Vertice vertice) {
		// Drawing a Circle
		
		System.out.println("Create Circle");
		Circle circle = new Circle();
		// Setting the position of the circle
		circle.setCenterX(vertice.getX());
		circle.setCenterY(vertice.getY());
		circle.setRadius(vertice.getR());
		circle.setFill(vertice.getColor());

		// Setting the stroke width of the circle
		circle.setStrokeWidth(20);
		circle.setFill(Color.DARKSLATEBLUE);

		// Registering the event filter
		// circle.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

}
