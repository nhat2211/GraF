package application;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
	   
	   
	  
	   @Override
	   public void initialize(URL location, ResourceBundle resources) {
	 
	       
	      
	   }
	 
	  @FXML
	   public void createVertice(MouseEvent  event) {
		   System.out.println("clicked");
		   Circle circle = new Circle();
			// Setting the position of the circle
			circle.setCenterX(event.getX());
			circle.setCenterY(event.getY());
			circle.setRadius(15);
			circle.setFill(Color.BLUE);

			// Setting the stroke width of the circle
			circle.setStrokeWidth(20);
			//circle.setFill(Color.DARKSLATEBLUE);
			rightPane.getChildren().add(circle);
	      
	      
	   }
	   
	   


	
	  
	}