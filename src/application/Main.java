package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			//Parent root = FXMLLoader.load(getClass().getResource("/application/MyScene.fxml"));
			Parent root = FXMLLoader.load(getClass().getResource("/ui/HomePage.fxml"));

			primaryStage.setTitle("GraF");
			primaryStage.setScene(new Scene(root));
			
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
